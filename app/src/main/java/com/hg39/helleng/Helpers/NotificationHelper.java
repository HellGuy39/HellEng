package com.hg39.helleng.Helpers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.hg39.helleng.DialogActivity;
import com.hg39.helleng.R;

import static com.hg39.helleng.MainActivity.CHANNEL_ID;

public class NotificationHelper {

    public static void displayNotification(Context context, String title, String body) {

        Intent intent = new Intent(context, DialogActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_chat_24)
                .setContentTitle(title)
                .setContentText(body)
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(1, builder.build());

    }

}
