package com.example.wordmaster.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.wordmaster.R;
import com.example.wordmaster.view.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

/**
 * @author seungho
 * @since 2021-07-14
 * class FireBaseMessage.java
 * project WordMaster
 * github devaspirant0510
 * email seungho020510@gmail.com
 * description 파베 메시지 서비스 받아 알림에 띄워줌
 **/
public class MyFirebaseMessageService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessageService";

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        Log.e(TAG, "onMessageReceived: " + remoteMessage.getFrom());
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            Log.e(TAG, "onMessageReceived: " + notification.getBody());
            Log.e(TAG, "onMessageReceived: " + notification.getTitle());
            sendNotification(notification.getTitle(), notification.getBody());
        }
    }

    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);
        Log.e(TAG, "onNewToken: " + s);
    }

    private void sendNotification(String title, String content) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //알림창 설정
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "myNoti")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setNumber(30)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{1, 1000});
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 오레오 버전부터 notification channel 을 만들어줘야됨
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myNoti", "myChannel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
//        String chId = "test";
//        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, chId)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(title)
//                .setContentText(content)
//                .setNumber(23)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        /* 안드로이드 오레오 버전 대응 */
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            String chName = "ch name";
//            NotificationChannel channel = new NotificationChannel(chId, chName, NotificationManager.IMPORTANCE_HIGH);
//            manager.createNotificationChannel(channel);
//        }
//        manager.notify(0, notiBuilder.build());

    }
}
