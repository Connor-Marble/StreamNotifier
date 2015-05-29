package me.connormarble.streamnotifier.Data;

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
    }

    public NotificationFilter(String channelName, String streamName, int startHr,
                              int endHr, int startMin, int endMin, int[] daysActive){
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
        boolean active = false;

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
}
