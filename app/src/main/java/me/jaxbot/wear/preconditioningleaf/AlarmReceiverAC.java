package me.jaxbot.wear.preconditioningleaf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AlarmReceiverAC extends WakefulBroadcastReceiver {
    private static final String TAG = "AlarmReceiverAC";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received intent, starting StartACService");

        Intent service = new Intent(context, StartACService.class);
        service.putExtra("eventName", intent.getStringExtra("eventName"));
        startWakefulService(context, service);
    }
}
