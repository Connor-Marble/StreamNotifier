package me.connormarble.streamnotifier.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import me.connormarble.streamnotifier.Data.NotificationFilter;

/**
 * Created by connor on 5/26/15.
 */
public class NotificationView extends ViewGroup {


    public NotificationView(Context context, AttributeSet attrs, NotificationFilter notification) {
        super(context, attrs);

        if(notification != null){
            TextView name = new TextView(null);
            name.setText( notification.getChannelName());
            addView(name);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
