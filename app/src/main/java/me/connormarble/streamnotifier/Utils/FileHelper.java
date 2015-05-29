package me.connormarble.streamnotifier.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import me.connormarble.streamnotifier.Data.NotificationFilter;
import me.connormarble.streamnotifier.R;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by connor on 5/28/15.
 */
public class FileHelper {
    public static void saveFilter(NotificationFilter filter, Context context ){
        String fileName = context.getString(R.string.save_location);

        NotificationFilter[] oldFilters = getSavedFilters(context);

        try {
            FileOutputStream stream = new FileOutputStream(context.getFilesDir()+fileName, false);
            ObjectOutputStream objectOut = new ObjectOutputStream(stream);
            objectOut.writeObject(filter);

            for(NotificationFilter oldFilter:oldFilters){
                objectOut.writeObject(oldFilter);
            }

            objectOut.close();

        } catch (IOException ex){
            Toast.makeText(context, "Failed to save notification", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            return;
        }

        Toast.makeText(context,"New Notification Created", Toast.LENGTH_SHORT).show();
    }

    public static NotificationFilter[] getSavedFilters(Context context){
        String fileName = context.getString(R.string.save_location);

        ArrayList<NotificationFilter> filterList = new ArrayList<NotificationFilter>();

        try {
            FileInputStream fileIn = new FileInputStream(context.getFilesDir() + fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            for(NotificationFilter filter=(NotificationFilter)objectIn.readObject();
                filter != null;
                filter=(NotificationFilter)objectIn.readObject()){

                filterList.add(filter);
            }

        } catch (EOFException ex){
            Log.d("SaveDebug","End of filter list");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
            return null;
        }

        return filterList.toArray(new NotificationFilter[filterList.size()]);
    }
}
