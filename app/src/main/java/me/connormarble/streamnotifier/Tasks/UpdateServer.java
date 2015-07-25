package me.connormarble.streamnotifier.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.Toast;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import me.connormarble.streamnotifier.Data.NotificationFilter;
import me.connormarble.streamnotifier.R;
import me.connormarble.streamnotifier.Utils.FilterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



/**
 * Created by connor on 6/16/15.
 */
public class UpdateServer extends AsyncTask {

    Context context;
    FilterManager filterManager;

    public UpdateServer(Context context, FilterManager filterManager){
        this.context = context;
        this.filterManager = filterManager;
    }

    @Override
    protected Object[] doInBackground(Object...objects) {

        if(isConnected()){
            updateServerFilters(filterManager);
        }

        return null;
    }

    private boolean isConnected(){
        ConnectivityManager connectManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connectManager.getActiveNetworkInfo();

        return netInfo!=null;
    }

    public boolean updateServerFilters(FilterManager filterManager){

        JSONObject message = buildUpstreamMessage(filterManager.getSavedFilters());

        if(message==null||!isConnected())
            return false;

        try {
            String appServerIP = context.getString(R.string.app_server_ip);
            URL url = new URL("http://"+appServerIP+":5000");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestProperty("Host", appServerIP);

            connection.connect();



            OutputStreamWriter outwr = new OutputStreamWriter(connection.getOutputStream());
            outwr.write(message.toString());
            outwr.close();

            Log.d("connect", connection.getResponseMessage());

        } catch (MalformedURLException ex){
            ex.printStackTrace();
            return false;
        }
        catch (IOException ex){
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    private JSONObject buildUpstreamMessage(NotificationFilter filters[]){
        JSONObject json = new JSONObject();

        try {
            String regID = getRegID();

            if(regID.length()==0){

                return null;
            }

            json.put("regID", regID);

            ArrayList<String> channelNames = new ArrayList<String>();

            for(NotificationFilter filter:filters){
                channelNames.add(filter.getChannelName());
            }

            json.put("Channels", new JSONArray(channelNames));

        } catch ( JSONException ex){
            ex.printStackTrace();
        }

        return json;
    }

    private String getRegID(){
        SharedPreferences preferences = context.getSharedPreferences("GCMPrefs",Context.MODE_PRIVATE);

        Long lastUpdate = 0l;
        PackageInfo info = null;
        boolean getNewID = false;

        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            lastUpdate = info.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        String id = preferences.getString("regID", "");
        long storedUpdate = preferences.getLong("lastUpdate", 0l);

        getNewID = getNewID||id.length()==0;
        getNewID = getNewID||lastUpdate==0l;
        getNewID = getNewID||lastUpdate!=storedUpdate;

        if(getNewID){
            try {
                Log.i("gcm","couldn't find valid registration id, requesting one...");
                String projectNum = context.getString(R.string.project_number);
                id = GoogleCloudMessaging.getInstance(context).register(projectNum);
            } catch (IOException e) {
                Log.d("get_reg_id","Connection to Google Services failed.");
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("regID", id);
            editor.putLong("lastUpdate", lastUpdate);
            editor.commit();
        }

        return id;
    }
}
