package com.example.booklibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBOpenHelper extends SQLiteOpenHelper {
    //public static final String name = "db_test.db";
    public static final String CREATE_USER = "CREATE TABLE user("
            + "username TEXT PRIMARY KEY,"
            + "password TEXT)";
    public static final String CREATE_BOOK = "CREATE TABLE book("
            + "name TEXT PRIMARY KEY,"
            + "pic INTEGER,"
            + "intro TEXT,"
            + "price TEXT)";
    private SQLiteDatabase db;
    private Context myContext;
    public DBOpenHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_BOOK);

        //初始化书库
        for(int i = 0; i < 10 ;i++){
            ContentValues values = new ContentValues();
            values.put("name", "Book" + i);
            values.put("intro", "哲理" + i + i);
            values.put("price", "100");
            values.put("pic", R.drawable.user);
            sqLiteDatabase.insert("book", null, values);
        }
        Toast.makeText(myContext, "Welcome you!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(sqLiteDatabase);
    }
    //void add(String name, String password){
        //db.execSQL("INSERT INTO user (username,password) VALUES(?,?)",new Object[]{name,password});
    //}


}
