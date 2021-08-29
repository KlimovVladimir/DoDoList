package com.example.dodolist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.dodolist.model.Note;
import com.example.dodolist.ui.AddNote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TimeNotification extends BroadcastReceiver {
    final String LOG_TAG = "myLogs";

    public void onReceive(Context context, Intent intent) {


        int notif = intent.getIntExtra("NOTIFY_ID", 0);
        String text = Integer.toString(notif);
        String textNote = "Note";
        textNote = intent.getStringExtra("TEXT");

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, text)
                        .setSmallIcon(R.drawable.ic_notific)
                        .setColor(context.getResources().getColor(R.color.yellow))
                        .setContentTitle("StudentList")
                        .setContentText(textNote);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notif, notification);

    }
}
