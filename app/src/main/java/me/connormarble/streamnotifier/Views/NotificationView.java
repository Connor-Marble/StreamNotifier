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

    public NotificationView(Context context, NotificationFilter filter) {
        super(context);

        this.filter = filter;

    }



    @Override
    public void onDraw(Canvas canvas){

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10f);

        canvas.drawLine(getLeft(), getTop(), getRight(), getTop(), paint);
        canvas.drawLine(getLeft(), getBottom(), getRight(), getBottom(), paint);
        canvas.drawLine(getLeft(), getTop(), getLeft(), getBottom(), paint);
        canvas.drawLine(getRight(), getTop(), getRight(), getBottom(), paint);
    }


}
