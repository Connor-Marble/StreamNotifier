package me.connormarble.streamnotifier.Utils;

import android.content.Context;
import android.widget.Toast;
import me.connormarble.streamnotifier.Data.NotificationFilter;
import me.connormarble.streamnotifier.R;

import java.io.*;

/**
 * Created by connor on 5/28/15.
 */
public class FileHelper {
    public static void saveFilter(NotificationFilter filter, Context context ){
        String fileName = context.getString(R.string.save_location);

        try {
            FileOutputStream stream = new FileOutputStream(context.getFilesDir()+fileName, true);
            ObjectOutputStream objectOut = new ObjectOutputStream(stream);
            objectOut.writeObject(filter);
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

        try {
            FileInputStream fileIn = new FileInputStream(context.getFilesDir() + fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }

        return null;
    }
}
