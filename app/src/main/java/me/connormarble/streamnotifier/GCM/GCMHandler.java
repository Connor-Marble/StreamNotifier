package me.connormarble.streamnotifier.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import me.connormarble.streamnotifier.Data.NotificationFilter;
import me.connormarble.streamnotifier.Utils.FilterManager;

import java.util.ArrayList;

/**
 * Created by connor on 7/7/15.
 */
public class GCMHandler extends IntentService{
    public GCMHandler() {
        super("GCMHandler");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        FilterManager manager = new FilterManager(getApplicationContext(), null);

        NotificationFilter[] filters = manager.getSavedFilters();

        ArrayList<String> enabledChannels = new ArrayList<String>();
        for(NotificationFilter filter: filters){
            if(filter.isActive()){
                enabledChannels.add(filter.getChannelName());
            }
        }

        GCMReceiver.completeWakefulIntent(intent);
    }

    private boolean getOnlineChannels(String[] channels){
        return false;
    }
}
