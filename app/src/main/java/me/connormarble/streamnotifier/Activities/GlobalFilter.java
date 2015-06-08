package me.connormarble.streamnotifier.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TimePicker;
import me.connormarble.streamnotifier.R;

/**
 * Created by connor on 6/4/15.
 */
public class GlobalFilter extends Activity {

    Button saveBtn;
    Button cancelBtn;

    Switch monSwitch, tueSwitch,
            wedSwitch, thuSwitch,
            friSwitch, satSwitch, sunSwitch;

    TimePicker startTime, endTime;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.global_filter);

        monSwitch = (Switch)findViewById(R.id.monswitch_global);
        tueSwitch = (Switch)findViewById(R.id.tueswitch_global);
        wedSwitch = (Switch)findViewById(R.id.wedswitch_global);
        thuSwitch = (Switch)findViewById(R.id.thuswitch_global);
        friSwitch = (Switch)findViewById(R.id.friswitch_global);
        satSwitch = (Switch)findViewById(R.id.satswitch_global);
        sunSwitch = (Switch)findViewById(R.id.sunswitch_global);

        saveBtn = (Button)findViewById(R.id.save_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        startTime = (TimePicker)findViewById(R.id.global_start);
        endTime = (TimePicker)findViewById(R.id.global_end);

        preferences = getSharedPreferences("global_filter", Context.MODE_PRIVATE);
    }

    private void loadExistingValues(){

        monSwitch.setChecked(preferences.getBoolean("monday", true));
        monSwitch.setChecked(preferences.getBoolean("tuseday", true));
        monSwitch.setChecked(preferences.getBoolean("wedneday", true));
        monSwitch.setChecked(preferences.getBoolean("thursday", true));
        monSwitch.setChecked(preferences.getBoolean("friday", true));
        monSwitch.setChecked(preferences.getBoolean("saturday", true));
        monSwitch.setChecked(preferences.getBoolean("sunday", true));

    }
}
