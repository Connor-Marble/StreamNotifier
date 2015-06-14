package me.connormarble.streamnotifier.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by connor on 6/14/15.
 */
public class NetworkManager {
    Context context;

    public NetworkManager(Context context){
        this.context = context;
    }

    private boolean isConnected(){
        ConnectivityManager connectManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connectManager.getActiveNetworkInfo();

        return netInfo!=null;
    }

    public boolean updateServerFilters(FilterManager filterManager){



        return false;
    }

}
