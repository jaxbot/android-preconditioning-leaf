package me.jaxbot.wear.preconditioningleaf;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StartACService extends Service {
    private final static String TAG = "StartACService";
    public StartACService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Started service.");

        // Start_Sticky command, or other action without intent
        if (intent == null)
            return super.onStartCommand(intent, flags, startId);

        String eventName;
        eventName = intent.getStringExtra("eventName");

        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_leaf_notification)
                        .setContentTitle("Starting AC for " + eventName);

        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, mBuilder.build());

        return super.onStartCommand(intent, flags, startId);
    }
}
