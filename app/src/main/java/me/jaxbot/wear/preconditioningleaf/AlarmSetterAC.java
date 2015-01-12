package me.jaxbot.wear.preconditioningleaf;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jonathan on 1/10/15.
 */
public class AlarmSetterAC {
    final static int REQUEST_SET_ALARMS = 1;
    public static void setAlarm(Context context, long startTime, String eventName)
    {
        Intent intent = new Intent(context, AlarmReceiverAC.class);
        intent.putExtra("eventName", eventName);
        PendingIntent sender = PendingIntent.getBroadcast(context, 4, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, startTime, sender);
    }

    public static void setAlarmsForToday(Context ctx) {
        ArrayList<ScheduledStart> events = CalendarSchedule.getStartEndTimes(ctx);
        String timesStr = "";
        Long currentTime = System.currentTimeMillis();
        for (ScheduledStart event : events) {
            if (event.time > currentTime) {
                timesStr += event.name + ": " + CalendarSchedule.longToDate(event.time) + "\n";
                AlarmSetterAC.setAlarm(ctx, event.time, event.name);
            }
        }
        System.out.println(timesStr);
    }

    public static void setAlarmSetterAlarm(Context context) {
        // Get tomorrow's 12am
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + 86400 * 1000);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(context, AlarmReceiverSetAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_SET_ALARMS, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 86400 * 1000, sender);
    }
}
