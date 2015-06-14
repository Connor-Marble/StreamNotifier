package me.connormarble.streamnotifier.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import me.connormarble.streamnotifier.Data.NotificationFilter;
import me.connormarble.streamnotifier.Interfaces.FilterChangeListener;
import me.connormarble.streamnotifier.R;
import me.connormarble.streamnotifier.Utils.FilterManager;
import me.connormarble.streamnotifier.Views.NotificationView;


public class StreamNotifier extends ActionBarActivity implements View.OnClickListener, FilterChangeListener {

    Button addFilterBtn;
    LinearLayout listHolder;
    FilterManager filterManager;

    public static StreamNotifier instance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(me.connormarble.streamnotifier.R.layout.activity_main);
        addFilterBtn = (Button)findViewById(R.id.addFilter);
        addFilterBtn.setOnClickListener(this);

        listHolder = (LinearLayout)findViewById(R.id.list_holder);

        filterManager = new FilterManager(getApplicationContext(), this);

        instance = this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(me.connormarble.streamnotifier.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                return true;
            case R.id.action_global_filter:
                startActivity(new Intent(this, GlobalFilter.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, CreateFilter.class);

        startActivity(intent);

    }

    @Override
    protected void onResume(){
        super.onResume();

        NotificationFilter[] filters = filterManager.getSavedFilters();
        if(filters!=null) {
            buildNotificationList(filters);
        }
    }

    public void rebuildNotificationList(){
        NotificationFilter[] filters = filterManager.getSavedFilters();
        buildNotificationList(filters);
    }

    private void buildNotificationList(NotificationFilter[] filters){

        listHolder.removeAllViews();

        int oddColor = getResources().getColor(R.color.oddList);
        int evenColor = getResources().getColor(R.color.evenList);

        for(int i =0;i<filters.length;i++){

            int color = i%2==0?evenColor:oddColor;

            NotificationView notificationView = new NotificationView(getApplicationContext(), filters[i], color, this);

            notificationView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));

            listHolder.addView(notificationView);

        }

    }

    @Override
    public void onFilterChange() {
        Log.d("filterChange", "updating server");
    }

    public FilterManager getFilterManager(){
        return filterManager;
    }
}
