package project.as224qc.dv606.slcommuter.model.integration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.model.Subscription;
import project.as224qc.dv606.slcommuter.util.Constants;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model.integration
 */
public class SQLiteSubscription extends SQLiteOpenHelper {

    String CREATE_TABLE_QUERY = String.format("CREATE TABLE IF NOT EXISTS %s (%s TEXT,%s INTEGER,PRIMARY KEY(%s,%s))", SubscriptionEntry.TABLE_NAME, SubscriptionEntry.COLUMN_NAME, SubscriptionEntry.COLUMN_TYPE, SubscriptionEntry.COLUMN_NAME, SubscriptionEntry.COLUMN_TYPE);

    public SQLiteSubscription(Context context) {
        super(context, Constants.Database.DB_NAME, null, Constants.Database.DB_VERSION);
    }

    public SQLiteSubscription(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
     * Insert a subscription
     *
     * @param subscription
     */
    public void insertSubscription(Subscription subscription) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(SubscriptionEntry.COLUMN_NAME, subscription.getName());
            values.put(SubscriptionEntry.COLUMN_TYPE, subscription.getType());

            db.insertOrThrow(SubscriptionEntry.TABLE_NAME, null, values);

        } catch (SQLiteException e) {
            // Do nothing
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * Clear table
     */
    public void clearAll() {
        getWritableDatabase().delete(SubscriptionEntry.TABLE_NAME, null, null);
    }

    /**
     * Delete a subscription
     *
     * @param subscription
     */
    public void deleteSubscription(Subscription subscription) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(SubscriptionEntry.TABLE_NAME, SubscriptionEntry.COLUMN_NAME + "=? AND " + SubscriptionEntry.COLUMN_TYPE + "=?", new String[]{subscription.getName(), subscription.getType()});
    }

    /**
     * Get all subscriptions
     *
     * @return
     */
    public ArrayList<Subscription> getSubscription() {
        ArrayList<Subscription> subscriptions = new ArrayList<>();

        String query = String.format("SELECT * FROM %s", SubscriptionEntry.TABLE_NAME);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            subscriptions.add(parseSubscription(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return subscriptions;
    }

    /**
     * Helper method to parse a row from table
     *
     * @param cursor
     * @return
     */
    private Subscription parseSubscription(Cursor cursor) {
        Subscription subscription = new Subscription();

        subscription.setName(cursor.getString(cursor.getColumnIndexOrThrow(SubscriptionEntry.COLUMN_NAME)));
        subscription.setType(cursor.getString(cursor.getColumnIndexOrThrow(SubscriptionEntry.COLUMN_TYPE)));

        return subscription;
    }

    private static class SubscriptionEntry {
        private static final String TABLE_NAME = "subscription";
        private static final String COLUMN_NAME = "name";
        private static final String COLUMN_TYPE = "type";
    }
}
