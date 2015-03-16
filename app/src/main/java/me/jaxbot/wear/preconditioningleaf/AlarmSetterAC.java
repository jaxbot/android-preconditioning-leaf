package me.jaxbot.wear.preconditioningleaf;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jonathan on 1/10/15.
 */
public class AlarmSetterAC {
    final static String TAG = "AlarmSetterAC";
    final static int REQUEST_SET_ALARMS = 1;
    final static int REQUEST_ACSTART_BOUNDARY = 10;

    public static void setAlarm(Context context, long startTime, String eventName, int i)
    {
        Intent intent = new Intent(context, AlarmReceiverAC.class);
        intent.putExtra("eventName", eventName);
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_ACSTART_BOUNDARY + i, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, startTime, sender);
    }

    public static void setAlarmsForToday(Context ctx) {
        ArrayList<ScheduledStart> events = CalendarSchedule.getStartEndTimes(ctx);
        String timesStr = "";
        Long currentTime = System.currentTimeMillis();

        int i = 0;
        for (ScheduledStart event : events) {
            if (event.time > currentTime) {
                timesStr += event.name + ": " + CalendarSchedule.longToDate(event.time) + "\n";
                AlarmSetterAC.setAlarm(ctx, event.time, event.name, i);
                Log.i(TAG, "Setting: " + CalendarSchedule.longToDate(event.time) + " - " + event.name);
            } else {
                Log.i(TAG, "Not setting: " + CalendarSchedule.longToDate(event.time) + " - " + event.name);
            }
            i++;
        }
        System.out.println(timesStr);
    }

    public static void setAlarmSetterAlarm(Context context) {
        // Get tomorrow's 12am
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + 86400 * 1000);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 4);

        Intent intent = new Intent(context, AlarmReceiverSetAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_SET_ALARMS, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 86400 * 1000, sender);
    }
}
