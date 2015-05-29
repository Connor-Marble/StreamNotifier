package me.connormarble.streamnotifier.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import me.connormarble.streamnotifier.Data.NotificationFilter;
import me.connormarble.streamnotifier.R;
import me.connormarble.streamnotifier.Utils.FileHelper;
import org.apache.commons.lang.ArrayUtils;


import java.util.ArrayList;

/**
 * Created by connoc on 5/23/2015.
 */
public class CreateFilter extends Activity implements View.OnClickListener {

    Button saveBtn;
    Button cancelBtn;

    Switch monSwitch, tueSwitch,
            wedSwitch, thuSwitch,
            friSwitch, satSwitch, sunSwitch;

    TimePicker startPicker, endPicker;

    EditText channelName, streamTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        saveBtn = (Button)findViewById(R.id.save_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        monSwitch = (Switch)findViewById(R.id.monswitch);
        tueSwitch = (Switch)findViewById(R.id.tueswitch);
        wedSwitch = (Switch)findViewById(R.id.wedswitch);
        thuSwitch = (Switch)findViewById(R.id.thuswitch);
        friSwitch = (Switch)findViewById(R.id.friswitch);
        satSwitch = (Switch)findViewById(R.id.satswitch);
        sunSwitch = (Switch)findViewById(R.id.sunswitch);

        startPicker = (TimePicker)findViewById(R.id.startPicker);
        endPicker = (TimePicker)findViewById(R.id.endPicker);

        channelName = (EditText)findViewById(R.id.channelInput);
        streamTitle = (EditText)findViewById(R.id.streamInput);

        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.save_btn:
                if(validateForm()){
                    FileHelper.saveFilter(getNotificationFilter(), getApplicationContext());
                    finish();
                }
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }
    }

    private NotificationFilter getNotificationFilter(){

        return new NotificationFilter(
                channelName.getText().toString(),
                streamTitle.getText().toString(),
                startPicker.getCurrentHour(),
                startPicker.getCurrentMinute(),
                endPicker.getCurrentHour(),
                endPicker.getCurrentMinute(),
                getSelectedDays()
        );

    }

    private int[] getSelectedDays(){
        Switch[] daySwitches = new Switch[]{monSwitch, tueSwitch, wedSwitch,
                thuSwitch, friSwitch, satSwitch, sunSwitch};

        ArrayList<Integer> result = new ArrayList<Integer>();

        for(int i =0;i<daySwitches.length; i++){
            if(daySwitches[i].isChecked()){
                result.add(Integer.valueOf(i));
            }
        }
        Integer[] castResult = new Integer[result.size()];
        result.toArray(castResult);
        return ArrayUtils.toPrimitive(castResult);
    }

    private boolean validateForm(){
        return true;
    }
}
