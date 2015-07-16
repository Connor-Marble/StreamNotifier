package me.connormarble.streamnotifier.GCM;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import me.connormarble.streamnotifier.Data.NotificationFilter;
import me.connormarble.streamnotifier.R;
import me.connormarble.streamnotifier.Utils.FilterManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

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

        FilterManager manager = new FilterManager(getBaseContext(), null);

        NotificationFilter[] filters = manager.getSavedFilters();

        ArrayList<String> enabledChannels = new ArrayList<String>();
        for(NotificationFilter filter: filters){
            boolean redundant = false;

            for(String existingName:enabledChannels){
                if (existingName.equalsIgnoreCase(filter.getChannelName()))
                    redundant=true;
            }

            if(filter.isActive()&&!redundant){
                String stream = getMatchingStream(filter);

                if(stream != null) {
                    enabledChannels.add(filter.getChannelName());
                    createNotification(filter.getChannelName(), stream, stream.hashCode());
                }
            }
        }


        GCMReceiver.completeWakefulIntent(intent);
    }

    private void createNotification(String channelName, String streamName,int notificationID){
        Uri channelPage = Uri.parse("twitch://stream/"+channelName);
        Intent viewPage = new Intent(Intent.ACTION_VIEW, channelPage);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,viewPage,0);

        Notification.Builder notificationBuild = new Notification.Builder(getApplicationContext());
        notificationBuild.setContentTitle(channelName + " is live:");
        notificationBuild.setContentText(streamName);
        notificationBuild.setSmallIcon(R.mipmap.notification_icon);
        notificationBuild.setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND);
        notificationBuild.setAutoCancel(true);
        notificationBuild.setContentIntent(pendingIntent);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            notificationBuild.setColor(Color.argb(255,100,255,100));
            notificationBuild.setLights(Color.RED, 100,300);
        }

        NotificationManager notificationManager = (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notificationBuild.build());
    }

    private String getMatchingStream(NotificationFilter filter){

        String channel = filter.getChannelName();

        try {
            URL url = new URL("https://api.twitch.tv/kraken/streams?channel=" + channel);
            URLConnection connection = url.openConnection();

            connection.connect();


            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            StringBuilder messageBuilder = new StringBuilder();

            String responseLine;
            while((responseLine=reader.readLine())!=null){
                messageBuilder.append(responseLine);
            }

            JSONObject json = new JSONObject(messageBuilder.toString());

            String streamName = json.getJSONArray("streams").getJSONObject(0).getJSONObject("channel").getString("status");
            if (streamName.contains(filter.getStreamName()))
                return streamName;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
