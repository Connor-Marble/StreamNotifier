package me.connormarble.streamnotifier.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Filter;
import android.widget.Toast;
import me.connormarble.streamnotifier.Data.NotificationFilter;
import me.connormarble.streamnotifier.Interfaces.FilterChangeListener;
import me.connormarble.streamnotifier.R;

import java.io.*;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

/**
 * Created by connor on 5/28/15.
 */
public class FilterManager {

    Context context;
    FilterChangeListener changeListener;

    public FilterManager(Context context, FilterChangeListener changeListener){
        this.context = context;
        this.changeListener = changeListener;
    }

    public void saveFilter(NotificationFilter filter){

        NotificationFilter[] oldFilters = getSavedFilters();
        overwriteFilters((NotificationFilter[])ArrayUtils.add(oldFilters, filter));

    }

    public NotificationFilter[] getSavedFilters(){
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
            return new NotificationFilter[0];
        }

        return filterList.toArray(new NotificationFilter[filterList.size()]);
    }

    public void removeFilter(NotificationFilter filter){
        NotificationFilter[] filters = getSavedFilters();
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

        overwriteFilters(newFilters);
    }

    private void overwriteFilters(NotificationFilter[] filters){
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
            Log.i("SaveDebug","End of filter list");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        changeListener.onFilterChange();

    }

    private void swapFilter(NotificationFilter original, NotificationFilter updated){
        NotificationFilter[] filters = getSavedFilters();

        for(int i =0;i<filters.length; i++){
            if(filters[i].equals(original)){
                filters[i]=updated;
                overwriteFilters(filters);
                return;
            }
        }

        Log.e("FilterManager", "Tried to replace non-existent filter");
    }

    public void replaceFilter(NotificationFilter old, NotificationFilter replacement){
        swapFilter(old, replacement);
    }
}
