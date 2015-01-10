package me.jaxbot.wear.preconditioningleaf;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jonathan on 1/10/15.
 */
public class AlarmSetterAC {
    public static void setAlarm(Context context, long startTime, String eventName)
    {
        Intent intent = new Intent(context, AlarmReceiverAC.class);
        intent.putExtra("eventName", eventName);
        PendingIntent sender = PendingIntent.getBroadcast(context, 4, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, startTime, sender);
    }
}
