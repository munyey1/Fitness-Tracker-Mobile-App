package com.example.fitlog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Toast;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createNotificationChannel();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SwitchCompat notificationSwitch = findViewById(R.id.notificationSwitch);
        boolean isNotificationEnabled = sharedPreferences.getBoolean("notification_enabled", false);
        notificationSwitch.setChecked(isNotificationEnabled);

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle the switch state change
                sharedPreferences.edit().putBoolean("notification_enabled", isChecked).apply();
                if (isChecked) {
                    enableNotifications();
                } else {
                    cancelNotifications();
                }
            }
        });
    }

    private void enableNotifications() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Set the time for your notification
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12); // Set the hour of day for your notification
        calendar.set(Calendar.MINUTE, 0); // Set the minute of your notification

        // Create an intent for your notification receiver
        Intent intent = new Intent(this, NotificationReceiver.class);

        // Create a pending intent for your notification receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm using the alarm manager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void cancelNotifications() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Create an intent for your notification receiver
        Intent intent = new Intent(this, NotificationReceiver.class);

        // Create a pending intent for your notification receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Cancel the alarm using the alarm manager
        alarmManager.cancel(pendingIntent);
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel
            NotificationChannel channel = new NotificationChannel("notifyWorkout",
                    "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel Description");

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }
}