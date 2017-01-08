package project.as224qc.dv606.slcommuter;

import android.app.Application;

/**
 * Extend Application class, this will help handling
 * with lifecycle management.
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class SLApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // init database
        SQLiteContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }
}
