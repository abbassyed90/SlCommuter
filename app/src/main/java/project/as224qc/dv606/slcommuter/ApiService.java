package project.as224qc.dv606.slcommuter;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import project.as224qc.dv606.slcommuter.event.DeviationEvent;
import project.as224qc.dv606.slcommuter.event.StationEvent;
import project.as224qc.dv606.slcommuter.event.TimeEvent;
import project.as224qc.dv606.slcommuter.event.TripEvent;
import project.as224qc.dv606.slcommuter.model.DeviationDTO;
import project.as224qc.dv606.slcommuter.model.Leg;
import project.as224qc.dv606.slcommuter.model.RecyclerViewHeaderItem;
import project.as224qc.dv606.slcommuter.model.RecyclerViewItem;
import project.as224qc.dv606.slcommuter.model.RecyclerViewNormalItem;
import project.as224qc.dv606.slcommuter.model.StationDTO;
import project.as224qc.dv606.slcommuter.model.TransportationDTO;
import project.as224qc.dv606.slcommuter.model.TripDTO;
import project.as224qc.dv606.slcommuter.util.Constants;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class ApiService {

    private static final String DATE_PATTERN_COUNT_29 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String DATE_PATTERN_COUNT_19 = "yyyy-MM-dd'T'HH:mmZ";
    private static ApiService instance = new ApiService();
    private Gson gson;

    private ApiService() {
        gson = new Gson();
    }

    public static ApiService getInstance() {
        return instance;
    }

    public void getDeviations() {
        String url = String.format(Constants.API.URL_DEVIATIONS, Constants.API_KEYS.DEVIATION_API_KEY);
        RestApiClient.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("ResponseData");
                    SimpleDateFormat dateFormat = new SimpleDateFormat();

                    int length = jsonArray.length();
                    ArrayList<DeviationDTO> deviations = new ArrayList<>(length);

                    for (int i = 0; i < length; i++) {
                        JSONObject jsonDeviation = jsonArray.getJSONObject(i);

                        // user gson to parse json
                        DeviationDTO deviation = gson.fromJson(jsonDeviation.toString(), DeviationDTO.class);

                        // parse date
                        long created = parseDate(jsonDeviation.getString("Created"), dateFormat);
                        deviation.setCreated(created);

                        // parse date
                        long from = parseDate(jsonDeviation.getString("FromDateTime"), dateFormat);
                        deviation.setFromDate(from);

                        // parse date
                        long too = parseDate(jsonDeviation.getString("UpToDateTime"), dateFormat);
                        deviation.setToDate(too);

                        deviations.add(deviation);
                    }

                    // post data back
                    EventBus.getInstance().post(new DeviationEvent(deviations));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                // post failure
                EventBus.getInstance().post(new DeviationEvent());
            }
        });
    }


    /**
     * Get site id from input
     *
     * @param input
     */
    public void getStationId(String input) {
        if (input.isEmpty())
            return;

        if (input.length() < 4)
            return;

        String url = String.format(Constants.API.URL_STATIONS, Constants.API_KEYS.STATION_LOOK_UP_API_KEY, input);

        RestApiClient.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("ResponseData");

                    int length = jsonArray.length();
                    ArrayList<StationDTO> sites = new ArrayList<>(length);
                    for (int i = 0; i < length; i++) {
                        JSONObject jsonSite = jsonArray.getJSONObject(i);

                        // parse sites
                        StationDTO site = gson.fromJson(jsonSite.toString(), StationDTO.class);
                        sites.add(site);
                    }

                    // post data
                    EventBus.getInstance().post(new StationEvent(sites));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                // post failure
                EventBus.getInstance().post(new StationEvent());
            }
        });
    }

    public void realTimeInformation(StationDTO site, int timeWindow) {
        String url = String.format(Constants.API.URL_REAL_TIME, Constants.API_KEYS.REAL_TIME_API_KEY, site.getSiteId(), timeWindow);

        RestApiClient.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    JSONObject jsonObject = response.getJSONObject("ResponseData");

                    ArrayList<RecyclerViewItem> items = new ArrayList<>();
                    ArrayList<TransportationDTO> transportations = new ArrayList<>();
                    transportations.addAll(parseTransportation(jsonObject, "Metros", transportations, items, "Metro"));
                    transportations.addAll(parseTransportation(jsonObject, "Buses", transportations, items, "Buses"));
                    transportations.addAll(parseTransportation(jsonObject, "Trains", transportations, items, "Trains"));

                    // post result
                    EventBus.getInstance().post(new TimeEvent(transportations, items));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                // post failure
                EventBus.getInstance().post(new TimeEvent());
            }
        });
    }

    public void findTripNow(final Context context, StationDTO origin, StationDTO destination) {
        String url = String.format(Constants.API.URL_TRIP, Constants.API_KEYS.TRAVEL_PLANNER_API_KEY, origin.getSiteId(), destination.getSiteId());

        RestApiClient.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject jsonTripList = response.getJSONObject("TripList");
                    JSONArray tripArray = jsonTripList.getJSONArray("Trip");

                    int length = tripArray.length();
                    ArrayList<TripDTO> trips = new ArrayList<>(length);
                    for (int i = 0; i < length; i++) {
                        TripDTO trip = gson.fromJson(tripArray.getJSONObject(i).toString(), TripDTO.class);

                        ArrayList<Leg> legs = new ArrayList<>();
                        ArrayList<String> types = new ArrayList<>();

                        JSONObject legListJson = tripArray.getJSONObject(i).getJSONObject("LegList");

                        if (legListJson.get("Leg") instanceof JSONObject) {
                            JSONObject jsonLeg = legListJson.getJSONObject("Leg");
                            Leg leg = gson.fromJson(jsonLeg.toString(), Leg.class);

                            trip.setOriginTime(leg.getOrigin().getTime());

                            trip.setDestinationTime(leg.getDestination().getTime());

                            types.add(leg.getType());
                            legs.add(leg);
                        } else {
                            // parse all legs
                            JSONArray jsonArrayLegs = legListJson.getJSONArray("Leg");
                            for (int j = 0; j < jsonArrayLegs.length(); j++) {
                                Leg leg = gson.fromJson(jsonArrayLegs.getJSONObject(j).toString(), Leg.class);

                                if (j == 0) {
                                    trip.setOriginTime(leg.getOrigin().getTime());
                                }

                                if (j == jsonArrayLegs.length() - 1) {
                                    trip.setDestinationTime(leg.getDestination().getTime());
                                }

                                types.add(leg.getType());
                                legs.add(leg);
                            }
                        }

                        trip.setLegs(legs);
                        trips.add(trip);
                    }

                    EventBus.getInstance().post(new TripEvent(trips));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                // post failure
                EventBus.getInstance().post(new TripEvent());
            }
        });
    }

    private ArrayList<TransportationDTO> parseTransportation(JSONObject jsonObject, String tag, ArrayList<TransportationDTO> transportations, ArrayList<RecyclerViewItem> items, String headerTitle) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray(tag);

        if (jsonArray.length() == 0) {
            return transportations;
        }

        RecyclerViewHeaderItem header = new RecyclerViewHeaderItem(headerTitle);
        items.add(header);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonTransportation = jsonArray.getJSONObject(i);

            TransportationDTO transportation = gson.fromJson(jsonTransportation.toString(), TransportationDTO.class);

            transportations.add(transportation);

            RecyclerViewNormalItem item = new RecyclerViewNormalItem(transportations.size() - 1);
            items.add(item);
        }

        return transportations;
    }

    /**
     * Parse a date from a string to long
     *
     * @param date
     * @param dateFormat
     * @return
     */
    private long parseDate(String date, SimpleDateFormat dateFormat) {
        long dateLong = 0;

        try {
            if (date.length() < 20) {
                dateFormat.applyPattern(DATE_PATTERN_COUNT_19);
            } else {
                dateFormat.applyPattern(DATE_PATTERN_COUNT_29);
            }

            dateLong = dateFormat.parse(date).getTime();
        } catch (ParseException e) {
            return 0;
        }

        return dateLong;
    }
}
