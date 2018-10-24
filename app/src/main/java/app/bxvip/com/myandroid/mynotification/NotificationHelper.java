package app.bxvip.com.myandroid.mynotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import app.bxvip.com.myandroid.R;


/**
 * 通知帮助类，管理通知的频道和创建通知
 */
public class NotificationHelper extends ContextWrapper{
    private NotificationManager  manager;
    public static final String PRIMARY_CHANNEL = "default";
    public static final String SECONDARY_CHANNEL = "second";


    public NotificationHelper(Context base) {
        super(base);
        //android-o上对通知的管理更加细粒度，通过”管道“对通知分类，用户可以根据自己对通知的喜好选择接收特定管道的通知
        /**
         * 参数1 表示的是通道的id
         * 参数2 表示的通道的名字，不能超过40个字符否则会被截断
         * 参数3,是通道的重要程度，决定了通道被中断的方式
         */
        NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL,getString(R.string.noti_channel_default),NotificationManager.IMPORTANCE_DEFAULT);
        chan1.setLightColor(Color.GREEN);
        //在锁屏时是否发送此通知
        chan1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(chan1);
        // 设置通知出现时的震动（如果 android 设备支持的话）
        // chanl.enableVibration(true);

        NotificationChannel chan2 = new NotificationChannel(SECONDARY_CHANNEL,getString(R.string.noti_channel_second),NotificationManager.IMPORTANCE_HIGH);
        chan2.setLightColor(Color.BLUE);
        chan2.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(chan2);
    }

    public  Notification.Builder  getNotification1(String title, String body){
        return new Notification.Builder(getApplicationContext(),PRIMARY_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallcon())
                //设置大文本信息
                //.setStyle(new Notification.BigTextStyle().bigText("ajhgdasd"))
                //设置大图片
//                .setStyle(new Notification.BigPictureStyle().bigLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.p1)))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.p1))
                .setAutoCancel(true);
    }


    public  Notification.Builder getNotification2(String title, String body){
        return  new Notification.Builder(getApplicationContext(), SECONDARY_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallcon())
                //表示点击这个通知后就会自动取消这个通知，另外就是显示的是取消
                .setAutoCancel(true);
    }
    private int getSmallcon(){
        return R.drawable.ic_launcher_background;
    }

    public void notify(int id, Notification.Builder notifycation){
        getManager().notify(id,notifycation.build());
    }
    private NotificationManager  getManager(){
        if(manager == null){
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
