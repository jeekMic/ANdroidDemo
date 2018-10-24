package app.bxvip.com.myandroid.mynotification;

import android.app.Notification;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import app.bxvip.com.myandroid.R;

public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = NotificationActivity.class.getSimpleName();

    private static final int NOTI_PRIMARY1 = 1100;
    private static final int NOTI_PRIMARY2 = 1101;
    private static final int NOTI_SECONDARY1 = 1200;
    private static final int NOTI_SECONDARY2 = 1201;


    private MainUi ui;
    private NotificationHelper noti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        noti = new NotificationHelper(this);
        ui = new MainUi(findViewById(R.id.activity_main));
    }

    public void sendNotification(int id, String title) {
        Notification.Builder nb = null;
        switch (id) {
            case NOTI_PRIMARY1:
                nb = noti.getNotification1(title, getString(R.string.primary1_body));
                break;

            case NOTI_PRIMARY2:
                nb = noti.getNotification1(title, getString(R.string.primary2_body));
                break;

            case NOTI_SECONDARY1:
                nb = noti.getNotification2(title, getString(R.string.secondary1_body));
                break;

            case NOTI_SECONDARY2:
                nb = noti.getNotification2(title, getString(R.string.secondary2_body));
                break;
            default:
                break;
        }
        if (nb != null) {
            //显示的调用 noti.cancel(id)也可以取消
            noti.notify(id, nb);
        }
    }

    /**
     * 进入通知设置页面进行设置
     */
    public void goToNotificationSettings() {
        Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(i);
    }
    //对具体某个通道的通知进行设置
    public void goToNotificationSettings(String channel) {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
        startActivity(i);
    }


    class MainUi implements View.OnClickListener{
        final TextView titlePrimary;
        final TextView titleSecondary;

        public MainUi(View root) {
            titlePrimary = (TextView) root.findViewById(R.id.main_primary_title);
            ((Button) root.findViewById(R.id.main_primary_send1)).setOnClickListener(this);
            ((Button) root.findViewById(R.id.main_primary_send2)).setOnClickListener(this);
            ((ImageButton) root.findViewById(R.id.main_primary_config)).setOnClickListener(this);

            titleSecondary = (TextView) root.findViewById(R.id.main_secondary_title);
            ((Button) root.findViewById(R.id.main_secondary_send1)).setOnClickListener(this);
            ((Button) root.findViewById(R.id.main_secondary_send2)).setOnClickListener(this);
            ((ImageButton) root.findViewById(R.id.main_secondary_config)).setOnClickListener(this);

            ((Button) root.findViewById(R.id.btnA)).setOnClickListener(this);
        }

        private String getTitlePrimaryText() {
            if (titlePrimary != null) {
                return titlePrimary.getText().toString();
            }
            return "";
        }

        private String getTitleSecondaryText() {
            if (titlePrimary != null) {
                return titleSecondary.getText().toString();
            }
            return "";
        }

        @Override
        public void onClick(View root) {
            switch (root.getId()) {
                case R.id.main_primary_send1:
                    sendNotification(NOTI_PRIMARY1, getTitlePrimaryText());
                    break;
                case R.id.main_primary_send2:
                    sendNotification(NOTI_PRIMARY2, getTitlePrimaryText());
                    break;
                case R.id.main_primary_config:
                    goToNotificationSettings(NotificationHelper.PRIMARY_CHANNEL);
                    break;

                case R.id.main_secondary_send1:
                    sendNotification(NOTI_SECONDARY1, getTitleSecondaryText());
                    break;
                case R.id.main_secondary_send2:
                    sendNotification(NOTI_SECONDARY2, getTitleSecondaryText());
                    break;
                case R.id.main_secondary_config:
                    goToNotificationSettings(NotificationHelper.SECONDARY_CHANNEL);
                    break;
                case R.id.btnA:
                    goToNotificationSettings();
                    break;
                default:
                    Log.e(TAG, "Unknown click event.");
                    break;
            }
        }

    }
}
