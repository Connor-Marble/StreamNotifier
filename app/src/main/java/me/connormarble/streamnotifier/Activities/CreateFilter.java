package me.connormarble.streamnotifier.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import me.connormarble.streamnotifier.R;

/**
 * Created by connoc on 5/23/2015.
 */
public class CreateFilter extends Activity {

    Button saveBtn;
    Button cancelBtn;

    Switch monSwitch, tueSwitch,
            wedSwitch, thuSwitch,
            friSwitch, satSwitch, sunSwitch;

    

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        saveBtn = (Button)findViewById(R.id.save_btn);
    }
}
