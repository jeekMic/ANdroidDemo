package app.bxvip.com.myandroid.thread;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyAsyn extends AsyncTask<String, Integer, String>{
    TextView tv_2;
    TextView tv_4;
    TextView tv_6;
    ProgressBar p1;
    ProgressBar p2;
    ProgressBar p3;
    ProgressBar p4;
    int total =0;
    int perEach = 0;
    int last = 0;
    final MyUpdateUi handler = new MyUpdateUi(this);

    public MyAsyn(TextView tv_2, TextView tv_4, TextView tv_6,ProgressBar p1,ProgressBar p2,ProgressBar p3,ProgressBar p4) {
        this.tv_2 = tv_2;
        this.tv_4 = tv_4;
        this.tv_6 = tv_6;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;

    }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //the method is call when the Asynchronous is invoke usually is to
            // mark ui to prepare
        }

        @Override
        protected String doInBackground(String... strings) {
            //do in the backgorund and string is array
            String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/main.apk";
            String path = strings[0];
            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                // get the length of file
                int fileLength = connection.getContentLength();
                total = fileLength;
                Log.i("length==", fileLength + "");
                // generate a file in local which is same size of need download
                RandomAccessFile file = new RandomAccessFile(filename, "rwd");
                file.setLength(fileLength);
                file.close();
                connection.disconnect();
                //set the number of thread
                int threadSize = 3;
                //calculate the load per thread
                int threadlength = fileLength % 3 == 0 ? fileLength / 3 : fileLength + 1;

                perEach = threadlength;

                for (int i = 0; i < threadSize; i++) {
                    publishProgress(i * 30);
                    int startposition = i * threadlength;
                    RandomAccessFile threadFile = new RandomAccessFile(filename, "rwd");
                    threadFile.seek(startposition);
                    new DownLoadThread(i, startposition, threadFile, threadlength, path, "1000" + i).start();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * when the doInBackground method is call publishProgress(Progress... values) (value is array)
         * this method will be invoke and to update ui
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("hb---------------", values[0] + "'");
            super.onProgressUpdate(values);
        }

        /**
         * after all background task has done completely this method will invoke and in main Thread
         *
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            Log.i("hb---------------", "complete");
            super.onPostExecute(s);
        }


    private  class DownLoadThread extends Thread implements Runnable {
        private int threadId;
        private int startPosition;
        private RandomAccessFile threadFile;
        private int threadLength;
        private String path;
        private String threadName;


        public DownLoadThread(int threadId, int startPosition, RandomAccessFile threadFile, int threadLength, String path, String threadName) {
            this.threadId = threadId;
            this.startPosition = startPosition;
            this.threadFile = threadFile;
            this.threadLength = threadLength;
            this.path = path;
            this.threadName = threadName;
        }

        //null construct
        public DownLoadThread() {
        }

        @Override
        public void run() {
            try {
                Thread.currentThread().setName(this.threadName);
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Range", "bytes=" + startPosition + "-");
                if (conn.getResponseCode() == 206) {
                    InputStream is = conn.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    int length = 0;
                    while (length < threadLength && (len = is.read(buffer)) != -1) {
                        threadFile.write(buffer, 0, len);
                        Log.i("load----------", length + "");
                        length += len;
                        Message message = Message.obtain();
                        if (Thread.currentThread().getName().equals("10001")) {
                            message.what = 1;


                        } else if (Thread.currentThread().getName().equals("10002")) {
                            message.what = 2;
                        } else {
                            message.what = 3;
                        }
                        message.arg2 = (int) (length*100.0/perEach);
                        message.arg1 = length;

                        handler.sendMessage(message);


                    }
                    threadFile.close();
                    is.close();
                    //all is done
                }
            } catch (Exception e) {
                e.printStackTrace();
                //线程下载出错了
            }
        }
    }

    class MyUpdateUi extends Handler {
        WeakReference<MyAsyn> myAsynWeakReference = null;

        public MyUpdateUi(MyAsyn myAsyn) {
            this.myAsynWeakReference = new WeakReference<>(myAsyn);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.i("============",msg.arg1+"");
            MyAsyn fragment = myAsynWeakReference.get();
            if (fragment != null) {
                // do something
                switch (msg.what) {
                    case 1:
                        tv_2.setText(msg.arg1+"");
                        Log.i("====================",msg.arg2+"");
                        p1.setProgress(msg.arg2);
                        break;
                    case 2:
                        tv_4.setText(msg.arg1+"");
                        p2.setProgress(msg.arg2);
                        break;
                    case 3:
                        tv_6.setText(msg.arg1+"");
                        p3.setProgress(msg.arg2);
                        break;
                    default:
                        break;
                }

            }
        }
    }

}
