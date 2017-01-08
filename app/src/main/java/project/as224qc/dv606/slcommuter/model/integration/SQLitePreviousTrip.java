package project.as224qc.dv606.slcommuter.model.integration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.model.PreviousTrip;
import project.as224qc.dv606.slcommuter.model.StationDTO;
import project.as224qc.dv606.slcommuter.util.Constants;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model.integration
 */
public class SQLitePreviousTrip extends SQLiteOpenHelper {

    String CREATE_TABLE_QUERY = String.format("CREATE TABLE IF NOT EXISTS %s (%s TEXT,%s INTEGER,%s TEXT,%s INTEGER,PRIMARY KEY(%s,%s))", PreviousEntry.TABLE_NAME, PreviousEntry.COLUMN_ORIGIN_NAME, PreviousEntry.COLUMN_ORIGIN_STATION_ID, PreviousEntry.COLUMN_DESTINATION_STATION_NAME, PreviousEntry.COLUMN_DESTINATION_STATION_ID, PreviousEntry.COLUMN_ORIGIN_STATION_ID, PreviousEntry.COLUMN_DESTINATION_STATION_ID);

    public SQLitePreviousTrip(Context context) {
        super(context, Constants.Database.DB_NAME, null, Constants.Database.DB_VERSION);
    }

    public SQLitePreviousTrip(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Insert a trip
     *
     * @param origin
     * @param destination
     */
    public void insertTrip(StationDTO origin, StationDTO destination) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(PreviousEntry.COLUMN_ORIGIN_NAME, origin.getName());
            values.put(PreviousEntry.COLUMN_ORIGIN_STATION_ID, origin.getSiteId());
            values.put(PreviousEntry.COLUMN_DESTINATION_STATION_NAME, destination.getName());
            values.put(PreviousEntry.COLUMN_DESTINATION_STATION_ID, destination.getSiteId());

            db.insertOrThrow(PreviousEntry.TABLE_NAME, null, values);

        } catch (SQLiteException e) {
            // Do nothing
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * Get all previous trips
     *
     * @return
     */
    public ArrayList<PreviousTrip> getTrips() {
        ArrayList<PreviousTrip> trips = new ArrayList<>();

        String query = String.format("SELECT * FROM %s", PreviousEntry.TABLE_NAME);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            trips.add(parseTrip(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return trips;
    }

    /**
     * Clear table
     */
    public void clearAll() {
        getWritableDatabase().delete(PreviousEntry.TABLE_NAME, null, null);
    }

    /**
     * Helper method to parse a row from table
     *
     * @param cursor
     * @return
     */
    private PreviousTrip parseTrip(Cursor cursor) {
        String originName = cursor.getString(cursor.getColumnIndexOrThrow(PreviousEntry.COLUMN_ORIGIN_NAME));
        int originId = cursor.getInt(cursor.getColumnIndexOrThrow(PreviousEntry.COLUMN_ORIGIN_STATION_ID));
        String destinationName = cursor.getString(cursor.getColumnIndexOrThrow(PreviousEntry.COLUMN_DESTINATION_STATION_NAME));
        int destinationId = cursor.getInt(cursor.getColumnIndexOrThrow(PreviousEntry.COLUMN_DESTINATION_STATION_ID));

        return new PreviousTrip(new StationDTO(originName, originId), new StationDTO(destinationName, destinationId));
    }

    private static class PreviousEntry {
        private static final String TABLE_NAME = "previous_trips";
        private static final String COLUMN_ORIGIN_NAME = "origin_name";
        private static final String COLUMN_ORIGIN_STATION_ID = "origin_station_id";
        private static final String COLUMN_DESTINATION_STATION_NAME = "destination_name";
        private static final String COLUMN_DESTINATION_STATION_ID = "destination_station_id";
    }
}
