package project.as224qc.dv606.slcommuter;

import android.content.Context;

import project.as224qc.dv606.slcommuter.controller.SQLiteController;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class SQLiteContext {

    private static SQLiteContext instance = null;

    private SQLiteController controller;

    private SQLiteContext(Context context) {
        this.controller = new SQLiteController(context);
    }

    public static SQLiteContext getInstance() {
        if (instance == null) {
            throw new NullPointerException("SQLiteContext has not been initialized properly. Call SQLiteContext.init(Context) in your Application.onCreate() method and SQLiteContext.terminate() in your Application.onTerminate() method.");
        }
        return instance;
    }

    public static void init(Context context) {
        instance = new SQLiteContext(context);
    }

    public SQLiteController getController() {
        return controller;
    }
}
