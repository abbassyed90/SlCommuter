package project.as224qc.dv606.slcommuter.util;

/**
 * Constants class
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.util
 */
public class Constants {

    /**
     * Helper constants for recyclerview
     * so that headers can be used
     */
    public static class RecyclerViewHelper {
        public static final int HEADER = 0;
        public static final int ITEM = 1;
    }

    /**
     * Sl traffic types
     */
    public static class TravelTypes {
        public static final String BUS = "Buss";
        public static final String SUBWAY = "Tunnelbanans";
        public static final String TRAIN = "Pendeltåg";
    }

    /**
     * Api urls
     */
    public static class API {
        public static final String BASE_URL = "http://api.sl.se/";

        public static final String URL_DEVIATIONS = "api2/deviations.json?key=%s";
        public static final String URL_STATIONS = "api2/typeahead.json?key=%s&searchstring=%s";
        public static final String URL_REAL_TIME = "api2/realtimedepartures.json?key=%s&siteid=%s&timewindow=%s";
        public static final String URL_TRIP = "api2/TravelplannerV2/trip.json?key=%s&originId=%s&destId=%s";
    }

    /**
     * Sl api keys
     */
    public static class API_KEYS {
        public static final String DEVIATION_API_KEY = "1e43464a602a4005bd852f5ef82e61cc";
        public static final String TRAVEL_PLANNER_API_KEY = "1e4567ade03b4c1aab5e6c305fc8c153";
        public static final String STATION_LOOK_UP_API_KEY = "ee64536dc99646468982a84f868e34e3";
        public static final String REAL_TIME_API_KEY = "8370e42e6fad4f098744aadee0f3d67c";
    }

    /**
     * Database constants
     */
    public static class Database {
        public static final String DB_NAME = "slcommuter.db";
        public static final int DB_VERSION = 1;
    }

    /**
     * Request codes for activities
     */
    public static class RequestCode {
        public static final int STATION_SEARCH = 0;
    }

}
