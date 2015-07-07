package me.connormarble.streamnotifier.Data;

import android.util.Log;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by connoc on 5/24/2015.
 */
public class NotificationFilter implements Serializable {

    private int startHr, endHr;
    private int startMin, endMin;

    private HashMap<Integer, Boolean> daysActive;

    private String channelName;
    private String streamName;

    private boolean enabled;

    /**
     * Empty constructor will ensure filter is always true
     */
    public NotificationFilter(String streamName){
        this.streamName = streamName;
        channelName = "";

        startHr = 0;
        startMin = 0;

        endHr = 23;
        endMin = 59;

        initDaysHashMap(true);
        enabled = true;

        Log.d("notification time", getTimeString());
    }

    public NotificationFilter(String channelName, String streamName, int startHr,
                              int startMin, int endHr, int endMin, int[] daysActive){
        this.startMin = startMin;
        this.startHr = startHr;

        this.endMin = endMin;
        this.endHr = endHr;

        this.streamName = streamName;
        this.channelName = channelName;

        initDaysHashMap(false);

        for(int day:daysActive){
            this.daysActive.put(day, true);
        }
    }

    private void initDaysHashMap(boolean startValue){
        daysActive = new HashMap<Integer, Boolean>();
        daysActive.put(Calendar.MONDAY, startValue);
        daysActive.put(Calendar.TUESDAY, startValue);
        daysActive.put(Calendar.WEDNESDAY, startValue);
        daysActive.put(Calendar.THURSDAY, startValue);
        daysActive.put(Calendar.FRIDAY, startValue);
        daysActive.put(Calendar.SATURDAY, startValue);
        daysActive.put(Calendar.SUNDAY, startValue);
    }

    public boolean isActive(){
        boolean active = true;

        Calendar cal = Calendar.getInstance();

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        active&=daysActive.get(cal.get(Calendar.DAY_OF_WEEK));

        active&= hour > startHr;
        active&= hour < endHr;

        active &= minute > startMin;
        active &= minute < startMin;

        return enabled&active;
    }

    public String getChannelName() {
        return channelName;
    }

    public int getStartMin() {
        return startMin;
    }

    public int getEndMin() {
        return endMin;
    }

    public String getStreamName() {
        return streamName;
    }

    public int getStartHr() {
        return startHr;
    }

    public int getEndHr() {
        return endHr;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Notification Filter:\n");
        stringBuilder.append("    Channel: " + channelName + "\n");
        stringBuilder.append("    stream filter: " + streamName + "\n");

        return stringBuilder.toString();
    }

    public String getTimeString(){
        StringBuilder timeString = new StringBuilder();
        timeString.append(startHr);
        timeString.append(':');
        timeString.append(StringUtils.leftPad(Integer.toString(startMin), 2, '0'));
        timeString.append(" - ");
        timeString.append(endHr);
        timeString.append(':');
        timeString.append(StringUtils.leftPad(Integer.toString(endMin), 2, '0'));

        return timeString.toString();
    }

    public boolean[] getDaysActive(){
        boolean[] result = new boolean[7];

        result[0] = daysActive.get(Calendar.SUNDAY);
        result[1] = daysActive.get(Calendar.MONDAY);
        result[2] = daysActive.get(Calendar.TUESDAY);
        result[3] = daysActive.get(Calendar.WEDNESDAY);
        result[4] = daysActive.get(Calendar.THURSDAY);
        result[5] = daysActive.get(Calendar.FRIDAY);
        result[6] = daysActive.get(Calendar.SATURDAY);

        return result;
    }

    @Override
    public boolean equals(Object obj){
        NotificationFilter other = (NotificationFilter)obj;
        if(other == null)
            return false;

        if(!StringUtils.equals(streamName, other.getStreamName()))
            return false;

        if(!StringUtils.equals(channelName, other.getChannelName()))
            return false;

        if(!StringUtils.equals(getTimeString(), other.getTimeString()))
            return false;

        boolean[] otherDays = other.getDaysActive();
        boolean[] daysActive = getDaysActive();

        for(int i=0;i<daysActive.length;i++){
            if(otherDays[i]!=daysActive[i])
                return false;
        }

        return true;
    }
}
