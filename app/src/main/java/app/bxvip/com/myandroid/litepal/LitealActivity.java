package app.bxvip.com.myandroid.litepal;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.litepal.LitePal;

import java.util.List;

import app.bxvip.com.myandroid.R;

public class LitealActivity extends AppCompatActivity {
    /**
     *
     * LitePal2.0.0之后几乎所有的接口都改变了，但是并不影响原来接口的使用，是提供向下兼容的问题
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liteal);
        Song  song = new Song();
        song.setName("hello");
        song.setDuration("180");
        //保存
        Book book = new Book("new book", 635);
        book.save();
        Book book2 = new Book("new book2", 637);
        book2.save();
        //修改
        ContentValues cv = new ContentValues();
        cv.put("name", "第二行代码");
        cv.put("page", 570);
        LitePal.update(Book.class, cv, 1);

        List<Book> books = LitePal.findAll(Book.class);
        for (Book mybook :books){
            Log.e("数据库数据",mybook.getName()+"---"+mybook.getPage()+"---"+mybook.getId());
        }

    }
}
