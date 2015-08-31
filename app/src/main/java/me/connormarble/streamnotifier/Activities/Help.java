package me.connormarble.streamnotifier.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import me.connormarble.streamnotifier.R;

/**
 * Created by connor on 8/22/15.
 */
public class Help extends Activity implements View.OnClickListener {

    Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        okBtn = (Button)findViewById(R.id.ok_button);
        okBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ok_button){
            finish();
        }
    }
}
