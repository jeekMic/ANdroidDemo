package app.bxvip.com.myandroid.thread;

import android.support.annotation.NonNull;

import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Multithreading {

    public static void main(String[] args) throws SQLException {
        startThreadPool();
    }
    public static void  startThreadPool(){
        MyFactory factory = new MyFactory("mythread");
        ThreadPoolExecutor  threadPoolExecutor = new ThreadPoolExecutor(5,6,0, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(),factory);
        for (int i = 0; i <10 ; i++) {
            threadPoolExecutor.submit(new MyTask());
        }
    }

    static class MyFactory implements ThreadFactory {
        private String name;
        private int count = 0;
        @Override
        public Thread newThread(@NonNull Runnable r) {
            //create a new thread and name it
            Thread t = new Thread(r,name+"==="+count);
            count ++;
            return t;
        }

        public MyFactory(String name) {
            this.name = name;
        }
    }

    static class MyTask implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
