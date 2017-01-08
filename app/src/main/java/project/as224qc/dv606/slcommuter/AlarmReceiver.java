package project.as224qc.dv606.slcommuter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Will start deviation service when it
 * receives a broadcast
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentDeviation = new Intent(context, DeviationService.class);
        context.startService(intentDeviation);
    }
}
