package project.as224qc.dv606.slcommuter.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import project.as224qc.dv606.slcommuter.DeviationDetailActivity;
import project.as224qc.dv606.slcommuter.StationSearchActivity;
import project.as224qc.dv606.slcommuter.TripDetailActivity;
import project.as224qc.dv606.slcommuter.model.DeviationDTO;
import project.as224qc.dv606.slcommuter.model.StationDTO;
import project.as224qc.dv606.slcommuter.model.TripDTO;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.util
 */
public class IntentHelper {

    /**
     * Start search station activity
     *
     * @param fragment
     * @param viewId
     */
    public static void startStationSearchActivity(Fragment fragment, int viewId) {
        Intent intent = new Intent(fragment.getContext(), StationSearchActivity.class);
        intent.putExtra(StationSearchActivity.VIEW_ID, viewId);
        fragment.startActivityForResult(intent, Constants.RequestCode.STATION_SEARCH);
    }

    /**
     * Start search station activity
     *
     * @param activity
     * @param viewId
     */
    public static void startStationSearchActivity(Activity activity, int viewId) {
        Intent intent = new Intent(activity, StationSearchActivity.class);
        intent.putExtra(StationSearchActivity.VIEW_ID, viewId);
        activity.startActivityForResult(intent, Constants.RequestCode.STATION_SEARCH);
    }

    /**
     * Start detail deviation activity
     *
     * @param context
     * @param deviation
     */
    public static void startDeviationDetailActivity(Context context, DeviationDTO deviation) {
        Intent intent = new Intent(context, DeviationDetailActivity.class);
        intent.putExtra(DeviationDetailActivity.BUNDLE_DATA_DEVIATION, deviation);
        context.startActivity(intent);
    }

    /**
     * Start trip detail activity
     *
     * @param context
     * @param trip
     * @param origin
     * @param destination
     */
    public static void startTripDetailActivity(Context context, TripDTO trip, StationDTO origin, StationDTO destination) {
        Intent intent = new Intent(context, TripDetailActivity.class);
        intent.putExtra(TripDetailActivity.BUNDLE_DATA_TRIP, trip);
        intent.putExtra(TripDetailActivity.BUNDLE_ORIGIN, origin);
        intent.putExtra(TripDetailActivity.BUNDLE_DESTINATION, destination);
        context.startActivity(intent);
    }

}
