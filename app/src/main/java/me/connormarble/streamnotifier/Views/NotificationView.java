package me.connormarble.streamnotifier.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import android.widget.LinearLayout;
import me.connormarble.streamnotifier.Data.NotificationFilter;

/**
 * Created by connor on 5/26/15.
 */
public class NotificationView extends View {

    NotificationFilter filter;
    int outline = 2;

    int backgroundColor;

    public NotificationView(Context context, NotificationFilter filter, int backgroundColor) {
        super(context);

        this.filter = filter;
        this.backgroundColor = backgroundColor;
    }



    @Override
    public void onDraw(Canvas canvas){

        drawBackground(canvas);
        drawContent(canvas);

    }

    private void drawContent(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        paint.setTextSize(54f);

        canvas.drawText(" " + filter.getChannelName(), 0, getHeight()/2f, paint);

    }

    private void drawBackground(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        paint.setStrokeWidth(10f);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        paint.setColor(backgroundColor);

        canvas.drawRect(outline, outline, getWidth()-outline, getHeight()-outline, paint);
    }


}
