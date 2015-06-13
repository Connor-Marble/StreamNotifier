package me.connormarble.streamnotifier.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import me.connormarble.streamnotifier.Activities.EditFilter;
import me.connormarble.streamnotifier.Activities.StreamNotifier;
import me.connormarble.streamnotifier.Data.NotificationFilter;
import me.connormarble.streamnotifier.R;
import me.connormarble.streamnotifier.Utils.FileHelper;
import me.connormarble.streamnotifier.Views.NotificationView;

/**
 * Created by connor on 6/12/15.
 */
public class EditDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private NotificationFilter filter;

    public void setFilter(NotificationFilter filter){
        this.filter = filter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Notification Rule Selected");
        builder.setMessage("Choose an action");

        builder.setNegativeButton("Delete", this);
        builder.setNeutralButton("Cancel", this);
        builder.setPositiveButton("Modify", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int id){
        switch (id){
            case AlertDialog.BUTTON_NEGATIVE:
                //delete notification
                FileHelper.removeFilter(filter, getActivity().getApplicationContext());
                getActivity().recreate();
                break;

            case AlertDialog.BUTTON_NEUTRAL:
                //cancel action
                break;

            case AlertDialog.BUTTON_POSITIVE:
                //bring up edit activity
                Intent intent = new Intent(getActivity().getApplicationContext(), EditFilter.class);
                intent.putExtra(getActivity().getApplicationContext().getString(R.string.filter_pass_id), filter);

                getActivity().startActivity(intent);
                break;
        }


    }
}
