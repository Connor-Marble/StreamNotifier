package me.connormarble.streamnotifier.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import me.connormarble.streamnotifier.R;

/**
 * Created by connoc on 5/23/2015.
 */
public class CreateFilter extends Activity implements View.OnClickListener {

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
        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.save_btn:
                if(validateForm()){
                    saveNotification();
                    finish();
                }
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }
    }

    private void saveNotification(){
        Toast.makeText(getApplicationContext(),"New Notification Created", Toast.LENGTH_SHORT).show();

    }

    private boolean validateForm(){
        return true;
    }
}
