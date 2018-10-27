package app.bxvip.com.myandroid.litepal;

import org.litepal.crud.LitePalSupport;

/**
 * 这个类提供了增删查改的功能，在这里已经关联了相关的表格
 *  public class Person extends LitePalSupport {
 * 	private int id;
 * 	private String name;
 * 	private int age;
 * }
 *
 * The Person class is automatically mapped to the table named "person",
 * which might look like this:
 *
 * CREATE TABLE person (
 * 	id integer primary key autoincrement,
 * 	age integer,
 * 	name text
 * );
 */
public class Song extends LitePalSupport{
    private String name;
    private String duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

