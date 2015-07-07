package me.connormarble.streamnotifier.GCM;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by connor on 7/7/15.
 */
public class GCMReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName componentName = new ComponentName(context.getPackageName(), GCMHandler.class.getName());

        Log.d("GCM", "message received");
        startWakefulService(context, intent.setComponent(componentName));
        setResultCode(Activity.RESULT_OK);
    }
}
