package com.example.myevents;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelId="channelID";
    public static final String channelName="channel";
    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        createChannel();
    }
    public void createChannel(){
        NotificationChannel channel = new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(com.google.android.material.R.color.design_default_color_primary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager(){
        if(mManager==null){
            mManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification(String title , String message){
        return new NotificationCompat.Builder(getApplicationContext(),channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_alarm);
    }
}
