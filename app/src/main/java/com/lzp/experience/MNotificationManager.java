package com.lzp.experience;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

/**
 * Created by Li Xiaopeng on 18/7/23.
 */
public class MNotificationManager {
    public static final String NOTIFICATION_GROUP_ID_AD = "ad_group_id";//广告推送的分组
    public static final String NOTIFICATION_GROUP_NAME_AD = "广告";//广告推送的分组name
    public static final String NOTIFICATION_CHANNEL_ID_AD = "channel_id_ad";//广告推送的渠道id


    public static final String NOTIFICATION_GROUP_ID_APP_MSG = "app_msg_group_id";//广告推送的分组
    public static final String NOTIFICATION_GROUP_NAME_APP_MSG = "内部通知";//广告推送的分组name
    public static final String NOTIFICATION_CHANNEL_ID_APP_MSG = "channel_id_app_msg";//广告推送的渠道id

    private static MNotificationManager instance;


    public static MNotificationManager getInstance() {
        if (instance==null){
            synchronized (MNotificationManager.class){
                instance = new MNotificationManager();
            }
        }
        return instance;
    }

    public void init(Context context){
        initGroup(context);
        initChannel(context);
    }

    /**
     * 初始化推送分组
     * @param context
     */
    public void initGroup(Context context){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            //广告分组
            NotificationChannelGroup adChannelGroup = new NotificationChannelGroup(NOTIFICATION_GROUP_ID_AD,NOTIFICATION_GROUP_NAME_AD);
            notificationManager.createNotificationChannelGroup(adChannelGroup);

            NotificationChannelGroup channelGroup_1 = new NotificationChannelGroup("下载","下载");
            notificationManager.createNotificationChannelGroup(channelGroup_1);

            NotificationChannelGroup appMSG = new NotificationChannelGroup(NOTIFICATION_GROUP_ID_APP_MSG,NOTIFICATION_GROUP_NAME_APP_MSG);
            notificationManager.createNotificationChannelGroup(appMSG);
        }

    }

    /**
     * 初始化渠道
     * @param context
     */
    public void initChannel(Context context){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            //渠道
            NotificationChannel adChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID_AD,"广告推送",NotificationManager.IMPORTANCE_DEFAULT);
            adChannel.setDescription("这是补充channel的描述，可以没有");
            //将该渠道添加到分组
            adChannel.setGroup(NOTIFICATION_GROUP_ID_AD);
            notificationManager.createNotificationChannel(adChannel);


            NotificationChannel appMSG = new NotificationChannel(NOTIFICATION_CHANNEL_ID_APP_MSG,"app内部通知",NotificationManager.IMPORTANCE_DEFAULT);
            //将该渠道添加到分组
            appMSG.setGroup(NOTIFICATION_GROUP_ID_APP_MSG);
            notificationManager.createNotificationChannel(appMSG);

        }

    }

    public void showAPPMSGNotification(Context context){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            //创建通知时，标记你的渠道id
            Notification notification = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID_APP_MSG)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("一条新通知")
                    .setContentText("这是一条测试消息")
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(3,notification);
        }
    }

    public void showAdNotification(Context context){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            //创建通知时，标记你的渠道id
            Notification notification = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID_AD)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("一条新通知")
                    .setContentText("这是一条测试消息")
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(1,notification);
        }
    }

    /**
     * 删除某个渠道的通知
     * @param context
     * @param channelId
     */
    private void deleteNotificationChannel(Context context,String channelId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.deleteNotificationChannel(channelId);
        }

    }
}
