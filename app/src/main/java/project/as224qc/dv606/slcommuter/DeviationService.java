package project.as224qc.dv606.slcommuter;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;
import project.as224qc.dv606.slcommuter.model.DeviationDTO;
import project.as224qc.dv606.slcommuter.model.Subscription;
import project.as224qc.dv606.slcommuter.util.Constants;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class DeviationService extends IntentService {

    private static final String TAG = "DeviationService";

    private static final String DATE_PATTERN_COUNT_29 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String DATE_PATTERN_COUNT_19 = "yyyy-MM-dd'T'HH:mmZ";
    private ArrayList<Subscription> subscriptions;

    public DeviationService() {
        super(TAG);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DeviationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent");

        subscriptions = SQLiteContext.getInstance().getController().getSubscriptions();

        if (subscriptions.size() == 0) {
            // no subscriptions
            // jump out of method

        } else {
            getDeviations();
        }
    }

    public void getDeviations() {
        final Gson gson = new Gson();

        String url = "http://api.sl.se/api2/deviations.json?key=" + Constants.API_KEYS.DEVIATION_API_KEY + "&transportMode=bus,metro,train";
        SyncHttpClient syncHttpClient = new SyncHttpClient();
        syncHttpClient.get(getApplicationContext(), url, new JsonHttpResponseHandler() {

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

                    parseData(deviations);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }
        });
    }

    private void parseData(ArrayList<DeviationDTO> deviations) {
        ArrayList<DeviationDTO> savedDeviations = SQLiteContext.getInstance().getController().getDeviations();
        removeOldDeviations(savedDeviations);

        // remove all deviations that have been saved
        if (savedDeviations.size() > 0) {
            Iterator<DeviationDTO> iterator = deviations.iterator();
            while (iterator.hasNext()) {
                DeviationDTO deviation = iterator.next();

                for (int i = 0; i < savedDeviations.size(); i++) {
                    DeviationDTO savedDeviation = savedDeviations.get(i);
                    if (deviation.getId() == savedDeviation.getId()) {
                        iterator.remove();
                    }
                }
            }
        }

        // insert every new deviation that has been found
        // in the database.
        int newDeviations = 0;
        int count = deviations.size();
        for (int i = 0; i < count; i++) {
            DeviationDTO deviation = deviations.get(i);
            String scope = deviation.getScope();
            for (int j = 0; j < subscriptions.size(); j++) {
                Subscription subscription = subscriptions.get(j);
                if (scope.contains(subscription.getName())) {
                    newDeviations += 1;
                    SQLiteContext.getInstance().getController().insertDeviation(deviation);
                }
            }
        }

        // show notification of all the new deviations
        if (!(newDeviations == 0))
            showNotification(newDeviations);
    }

    /**
     * Displays a notification with information about
     * how many new deviations have occurred.
     *
     * @param newDeviations
     */
    private void showNotification(int newDeviations) {
        // get preferences from users preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean showNotification = getBoolPreference(sharedPreferences, R.bool.default_show_notification_value, R.string.key_notification_show);
        boolean soundNotification = getBoolPreference(sharedPreferences, R.bool.default_sound_notification_value, R.string.key_notification_sound);
        boolean vibrateNotification = getBoolPreference(sharedPreferences, R.bool.default_vibrate_notification_value, R.string.key_notification_vibrate);

        // if the user doesn't want to
        // displays any notifications from this application
        // don't call the rest of this mehtod
        if (!showNotification) {
            return;
        }

        // setup notification
        Notification notification = new Notification.Builder(this)
                .setContentTitle(getString(R.string.deviations))
                .setContentText(String.format(getString(R.string.notification_new_deviations), String.valueOf(newDeviations)))
                .setSmallIcon(R.drawable.ic_directions_bus_blue_grey_500_18dp)
                .setAutoCancel(true).build();

        // set if notification should have sound, no sound, vibrate etc.
        if (soundNotification && vibrateNotification) {
            notification.defaults = Notification.DEFAULT_ALL;
        } else if (soundNotification) {
            notification.defaults = Notification.DEFAULT_SOUND;
        } else if (vibrateNotification) {
            notification.defaults = Notification.DEFAULT_VIBRATE;
        } else {
            notification.defaults = 0;
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    /**
     * Helper method for getting boolean values from shared preferences
     *
     * @param sharedPreferences
     * @param defaultBoolId
     * @param stringKeyId
     * @return
     */
    private boolean getBoolPreference(SharedPreferences sharedPreferences, int defaultBoolId, int stringKeyId) {
        return sharedPreferences.getBoolean(getString(stringKeyId), getResources().getBoolean(defaultBoolId));
    }

    /**
     * Remove all old deviations with dates that
     * have become inactive
     *
     * @param deviations
     */
    private void removeOldDeviations(ArrayList<DeviationDTO> deviations) {
        for (int i = 0; i < deviations.size(); i++) {
            DeviationDTO deviation = deviations.get(i);
            if (deviation.getToDate() < System.currentTimeMillis()) {
                SQLiteContext.getInstance().getController().deleteDeviation(deviation);
            }
        }
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
