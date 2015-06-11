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
            if(context!=null)
                Toast.makeText(context, "Failed to save notification", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            return;
        }

        if(context!=null)
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

    public static void removeFilter(NotificationFilter filter, Context context){
        NotificationFilter[] filters = getSavedFilters(context);
        NotificationFilter[] newFilters = new NotificationFilter[filters.length-1];

        boolean foundremoved = false;
        int i =0;
        while(i<newFilters.length){
            if(filters[i].equals(filter))
                foundremoved = true;

            int index = foundremoved?i+1:i;

            newFilters[i] = filters[index];
            i++;
        }

        overwriteFilters(newFilters, context);
    }

    private static void overwriteFilters(NotificationFilter[] filters, Context context){
        String fileName = context.getString(R.string.save_location);

        try{
            FileOutputStream fileOut = new FileOutputStream(context.getFilesDir()+fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            for(NotificationFilter filter:filters){
                objectOut.writeObject(filter);
            }

            objectOut.close();
        }
        catch (EOFException ex){
            Log.d("SaveDebug","End of filter list");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public static void replaceFilter(NotificationFilter old, NotificationFilter replacement, Context context){
        removeFilter(old, context);
        saveFilter(replacement, context);
    }
}
