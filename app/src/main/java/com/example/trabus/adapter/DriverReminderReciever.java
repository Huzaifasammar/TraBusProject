package com.example.trabus.adapter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.trabus.Main.Driver_Home;
import com.example.trabus.R;

public class DriverReminderReciever extends BroadcastReceiver {
    private static final String Id = "reminder_id";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationid = intent.getIntExtra("notificationid", 0);


        Intent mainintent = new Intent(context, Driver_Home.class);
        PendingIntent contentintent = PendingIntent.getActivity(context, 0, mainintent, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            CharSequence channelname = "TraBus Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Id, channelname, importance);
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Id).setContentText("Reminder! Your Scheduled time is arrived please do whatever you want ")
                    .setContentIntent(contentintent).setSmallIcon(R.drawable.bus).setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true).setVibrate(channel.getVibrationPattern());

            notificationManager.notify(notificationid, builder.build());

        }
    }
}
