package me.connormarble.streamnotifier.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TimePicker;
import me.connormarble.streamnotifier.R;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

/**
 * Created by connor on 6/4/15.
 */
public class GlobalFilter extends Activity implements View.OnClickListener {

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

        saveBtn = (Button)findViewById(R.id.global_filter_save);
        cancelBtn = (Button)findViewById(R.id.global_filter_cancel);

        startTime = (TimePicker)findViewById(R.id.global_start);
        endTime = (TimePicker)findViewById(R.id.global_end);

        preferences = getSharedPreferences("global_filter", Context.MODE_PRIVATE);

        loadExistingValues();

        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    private void loadExistingValues(){
        //retrieve day switch values
        monSwitch.setChecked(preferences.getBoolean("monday", true));
        tueSwitch.setChecked(preferences.getBoolean("tuesday", true));
        wedSwitch.setChecked(preferences.getBoolean("wednesday", true));
        thuSwitch.setChecked(preferences.getBoolean("thursday", true));
        friSwitch.setChecked(preferences.getBoolean("friday", true));
        satSwitch.setChecked(preferences.getBoolean("saturday", true));
        sunSwitch.setChecked(preferences.getBoolean("sunday", true));

        //retrieve time values and use the entire day by default
        startTime.setCurrentHour(preferences.getInt("start_hour", 0));
        startTime.setCurrentMinute(preferences.getInt("start_min", 0));

        endTime.setCurrentHour(preferences.getInt("end_hour", 23));
        endTime.setCurrentMinute(preferences.getInt("end_min", 59));

    }

    private void saveValues(){
        SharedPreferences.Editor prefEdit = preferences.edit();

        prefEdit.putBoolean("monday", monSwitch.isChecked());
        prefEdit.putBoolean("tuesday", tueSwitch.isChecked());
        prefEdit.putBoolean("wednesday", wedSwitch.isChecked());
        prefEdit.putBoolean("thursday", thuSwitch.isChecked());
        prefEdit.putBoolean("friday", friSwitch.isChecked());
        prefEdit.putBoolean("saturday", satSwitch.isChecked());
        prefEdit.putBoolean("sunday", sunSwitch.isChecked());

        prefEdit.putInt("start_hour", startTime.getCurrentHour());
        prefEdit.putInt("start_min", startTime.getCurrentMinute());

        prefEdit.putInt("end_hour", endTime.getCurrentHour());
        prefEdit.putInt("end_min", endTime.getCurrentMinute());

        prefEdit.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.global_filter_save:
                saveValues();
                finish();
                break;
            case R.id.global_filter_cancel:
                finish();
                break;
            default:
                break;
        }
    }

    public static boolean isActive(Context context){

        SharedPreferences prefs = context.getSharedPreferences("global_filter", Context.MODE_PRIVATE);

        boolean active=true;

        Calendar cal = Calendar.getInstance();
        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
        String day = days[cal.get(Calendar.DAY_OF_WEEK)-1];

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        //check if filter is active for this day
        active = active&&prefs.getBoolean(day, false);

        int startHour = prefs.getInt("start_hour", 0);
        int endHour = prefs.getInt("end_hour", 23);

        int startMinute = prefs.getInt("start_minute", 0);
        int endMinute = prefs.getInt("end_minute", 59);

        //check if time is within range
        active = active&&(hour>=startHour);
        active = active&&(hour<=endHour);

        if(hour == startHour)
            active = active&&minute>=startMinute;

        if(hour == endHour)
            active = active&&minute<=endMinute;

        return active;
    }
}
