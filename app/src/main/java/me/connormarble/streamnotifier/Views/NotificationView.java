package me.connormarble.streamnotifier.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import me.connormarble.streamnotifier.Data.NotificationFilter;

/**
 * Created by connor on 5/26/15.
 */
public class NotificationView extends View implements View.OnTouchListener {

    NotificationFilter filter;
    int outline = 2;

    int backgroundColor;
    boolean selected = false;

    public NotificationView(Context context, NotificationFilter filter, int backgroundColor) {
        super(context);

        this.filter = filter;
        this.backgroundColor = backgroundColor;

        setOnTouchListener(this);
    }



    @Override
    public void onDraw(Canvas canvas){

        drawBackground(canvas);
        drawContent(canvas);

    }

    private void drawContent(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        //draw channel name
        paint.setTextSize(54f);

        canvas.drawText(" " + filter.getChannelName(), 0, getHeight()/2f, paint);

        //draw stream name
        String streamName = filter.getStreamName().length()>0?"Streams Matching: "+filter.getStreamName():"All Streams";

        paint.setTextSize(36f);
        canvas.drawText("    " + streamName, 0, getHeight()/2f + 54f, paint);

        //draw time
        paint.setTextAlign(Paint.Align.RIGHT);

        canvas.drawText(filter.getTimeString(), getWidth(), 60f, paint);

        //draw days active
        char[] days = "SFTWTMS".toCharArray();
        boolean[] daysActive = filter.getDaysActive();

        String dayList = "";

        for(int i = daysActive.length-1;i>=0;i--){

            String day = daysActive[i]?String.valueOf(days[i]):"-";

            dayList+=day;

        }
        canvas.drawText(dayList, getWidth()-5f, getHeight()/2f + 54f, paint);
    }

    private void drawBackground(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        paint.setStrokeWidth(10f);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);



        paint.setColor(backgroundColor);

        canvas.drawRect(outline, outline, getWidth()-outline, getHeight()-outline, paint);
        if(selected){
            paint.setColor(Color.BLACK);
            paint.setAlpha(100);
            canvas.drawRect(outline, outline, getWidth()-outline, getHeight()-outline, paint);
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            selected = true;

        else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            selected = false;
            //open editing activity
        }

        else if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL){
            selected = false;
        }

        invalidate();

        return true;
    }
}
