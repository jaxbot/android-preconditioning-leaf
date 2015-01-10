package me.jaxbot.wear.preconditioningleaf;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StartACService extends Service {
    public StartACService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("We startrd!!");
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
