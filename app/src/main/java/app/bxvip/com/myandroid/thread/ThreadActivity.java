package app.bxvip.com.myandroid.thread;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.bxvip.com.myandroid.R;

public class ThreadActivity extends AppCompatActivity {
    final int REQUEST_CODE_CONTACT = 101;
    TextView tv_2;
    TextView tv_4;
    TextView tv_6;
    ProgressBar p1;
    ProgressBar p2;
    ProgressBar p3;
    ProgressBar p4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        tv_2 = findViewById(R.id.tv_2);
        tv_4 = findViewById(R.id.tv_4);
        tv_6 = findViewById(R.id.tv_6);
        p1 =findViewById(R.id.progressBar_1);
        p2 =findViewById(R.id.progressBar_2);
        p3 =findViewById(R.id.progressBar_3);
        p4 =findViewById(R.id.progressBar_4);
        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }else {
                    MyAsyn threadTask = new MyAsyn(tv_2, tv_4, tv_6,p1,p2,p3,p4);
                    threadTask.execute("http://gdown.baidu.com/data/wisegame/3d4de3ae1d2dc7d5/weixin_1360.apk");
                }
            }
        }

    }

    //     public  void  onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<String>, grantResults: IntArray) {
//        when (requestCode) {
//            REQUEST_CODE_CONTACT -> {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return
//            }
//        }
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    MyAsyn threadTask = new MyAsyn(tv_2, tv_4, tv_6,p1,p2,p3,p4);
                    threadTask.execute("http://gdown.baidu.com/data/wisegame/3d4de3ae1d2dc7d5/weixin_1360.apk");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                return;

            }
            default:
                break;
        }
    }


}
