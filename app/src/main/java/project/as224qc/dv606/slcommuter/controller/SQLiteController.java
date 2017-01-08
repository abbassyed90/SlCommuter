package project.as224qc.dv606.slcommuter.controller;

import android.content.Context;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.model.DeviationDTO;
import project.as224qc.dv606.slcommuter.model.PreviousTrip;
import project.as224qc.dv606.slcommuter.model.StationDTO;
import project.as224qc.dv606.slcommuter.model.Subscription;
import project.as224qc.dv606.slcommuter.model.integration.SQLiteDeviation;
import project.as224qc.dv606.slcommuter.model.integration.SQLitePreviousTrip;
import project.as224qc.dv606.slcommuter.model.integration.SQLiteStation;
import project.as224qc.dv606.slcommuter.model.integration.SQLiteSubscription;

/**
 * A controller to handle all sqlite database calls
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model.integration
 */
public class SQLiteController {

    private final SQLitePreviousTrip sqLitePreviousTrip;
    private SQLiteStation sqLiteStation;
    private SQLiteDeviation sqLiteDeviation;
    private SQLiteSubscription sqLiteSubscription;

    public SQLiteController(Context context) {
        sqLiteStation = new SQLiteStation(context);
        sqLitePreviousTrip = new SQLitePreviousTrip(context);
        sqLiteDeviation = new SQLiteDeviation(context);
        sqLiteSubscription = new SQLiteSubscription(context);
    }

    /**
     * Insert a station
     *
     * @param station
     */
    public void insertStation(StationDTO station) {
        sqLiteStation.insertStation(station);
    }

    /**
     * Get all searched stations
     *
     * @return
     */
    public ArrayList<StationDTO> getStations() {
        return sqLiteStation.getStations();
    }

    /**
     * Insert a trip
     *
     * @param origin
     * @param destination
     */
    public void insertTrip(StationDTO origin, StationDTO destination) {
        sqLitePreviousTrip.insertTrip(origin, destination);
    }

    /**
     * get all searched trips
     *
     * @return
     */
    public ArrayList<PreviousTrip> getTrips() {
        return sqLitePreviousTrip.getTrips();
    }

    /**
     * Insert subscription
     *
     * @param subscription
     */
    public void insertSubscription(Subscription subscription) {
        sqLiteSubscription.insertSubscription(subscription);
    }

    /**
     * Delete subscription
     *
     * @param subscription
     */
    public void deleteSubscription(Subscription subscription) {
        sqLiteSubscription.deleteSubscription(subscription);
    }

    /**
     * get all subscriptions
     *
     * @return
     */
    public ArrayList<Subscription> getSubscriptions() {
        return sqLiteSubscription.getSubscription();
    }

    /**
     * Insert a deviation
     *
     * @param deviationDTO
     */
    public void insertDeviation(DeviationDTO deviationDTO) {
        sqLiteDeviation.insertDeviation(deviationDTO);
    }

    /**
     * Delete a deviation
     *
     * @param deviationDTO
     */
    public void deleteDeviation(DeviationDTO deviationDTO) {
        sqLiteDeviation.deleteDeviation(deviationDTO);
    }

    /**
     * Get all deviations
     *
     * @return
     */
    public ArrayList<DeviationDTO> getDeviations() {
        return sqLiteDeviation.getDeviations();
    }

    /**
     * Clear database
     */
    public void clearAll() {
        sqLiteDeviation.clearAll();
        sqLiteStation.clearAll();
        sqLiteSubscription.clearAll();
        sqLitePreviousTrip.clearAll();
    }
}
