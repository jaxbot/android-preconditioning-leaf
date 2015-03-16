package me.jaxbot.wear.preconditioningleaf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AlarmReceiverSetAlarm extends WakefulBroadcastReceiver {
    public final String TAG = "AlarmReceiverSetAlarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Wakeful broadcast received.");
        AlarmSetterAC.setAlarmsForToday(context);
        AlarmSetterAC.setAlarmSetterAlarm(context);
    }
}
