package me.connormarble.streamnotifier.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import me.connormarble.streamnotifier.Data.NotificationFilter;
import me.connormarble.streamnotifier.R;
import me.connormarble.streamnotifier.Utils.FileHelper;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;

/**
 * Created by connor on 6/8/15.
 */
public class EditFilter extends Activity implements View.OnClickListener {

    Button saveBtn;
    Button cancelBtn;

    Switch monSwitch, tueSwitch,
            wedSwitch, thuSwitch,
            friSwitch, satSwitch, sunSwitch;

    TimePicker startPicker, endPicker;

    EditText channelName, streamTitle;

    NotificationFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        saveBtn = (Button)findViewById(R.id.edit_save_btn);
        cancelBtn = (Button)findViewById(R.id.edit_cancel_btn);

        monSwitch = (Switch)findViewById(R.id.edit_monswitch);
        tueSwitch = (Switch)findViewById(R.id.edit_tueswitch);
        wedSwitch = (Switch)findViewById(R.id.edit_wedswitch);
        thuSwitch = (Switch)findViewById(R.id.edit_thuswitch);
        friSwitch = (Switch)findViewById(R.id.edit_friswitch);
        satSwitch = (Switch)findViewById(R.id.edit_satswitch);
        sunSwitch = (Switch)findViewById(R.id.edit_sunswitch);

        startPicker = (TimePicker)findViewById(R.id.edit_startPicker);
        endPicker = (TimePicker)findViewById(R.id.edit_endPicker);

        channelName = (EditText)findViewById(R.id.edit_channelInput);
        streamTitle = (EditText)findViewById(R.id.edit_streamInput);

        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);


        filter = (NotificationFilter)getIntent().getExtras().getSerializable(
                getApplicationContext().getString(R.string.filter_pass_id));

        updateFields();
    }


    private void updateFields(){
        channelName.setText(filter.getChannelName());
        streamTitle.setText(filter.getStreamName());

        boolean[] daysActive = filter.getDaysActive();

        sunSwitch.setChecked(daysActive[0]);
        monSwitch.setChecked(daysActive[1]);
        tueSwitch.setChecked(daysActive[2]);
        wedSwitch.setChecked(daysActive[3]);
        thuSwitch.setChecked(daysActive[4]);
        friSwitch.setChecked(daysActive[5]);
        satSwitch.setChecked(daysActive[6]);

        startPicker.setCurrentHour(filter.getStartHr());
        startPicker.setCurrentMinute(filter.getStartMin());

        endPicker.setCurrentHour(filter.getEndHr());
        endPicker.setCurrentMinute(filter.getEndMin());

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.edit_cancel_btn:
                finish();
                return;
            case R.id.edit_save_btn:
                FileHelper.replaceFilter(filter, getNewFilter(), getApplicationContext());
                finish();
                return;
        }
    }

    public NotificationFilter getNewFilter() {
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
        Switch[] daySwitches = new Switch[]{sunSwitch, monSwitch, tueSwitch, wedSwitch,
                thuSwitch, friSwitch, satSwitch};

        ArrayList<Integer> result = new ArrayList<Integer>();

        for(int i =0;i<daySwitches.length; i++){
            if(daySwitches[i].isChecked()){
                result.add(Integer.valueOf(i+1));
            }
        }
        Integer[] castResult = new Integer[result.size()];
        result.toArray(castResult);
        return ArrayUtils.toPrimitive(castResult);
    }

}
