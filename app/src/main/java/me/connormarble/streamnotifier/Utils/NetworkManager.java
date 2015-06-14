package me.connormarble.streamnotifier.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by connor on 6/14/15.
 */
public class NetworkManager {
    public static boolean isConnected(Context context){
        ConnectivityManager connectManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connectManager.getActiveNetworkInfo();

        return netInfo!=null;
    }


}
