package com.hg39.helleng;

import android.app.Application;
import android.os.Build;

public class App extends Application {

    public static final String DEFAULT_NOTIFICATION_CHANNEL = "default_notification_channel";
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    @Override
    public void onCreate() {
        super.onCreate();

        //createNotificationChannels();


    }

    @Override
    public void onTerminate() {
        super.onTerminate();


    }

    private void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /*NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "channel2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is channel 2");*/

            /*NotificationChannel defaultChannel = new NotificationChannel(
                    DEFAULT_CHANNEL_ID,
                    "defaultChannel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            defaultChannel.setDescription("Default Channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(defaultChannel);*/
            //manager.createNotificationChannel(channel1);
            //manager.createNotificationChannel(channel2);

        }
    }
}
