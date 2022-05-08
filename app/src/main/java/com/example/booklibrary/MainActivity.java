package com.example.booklibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private TextView txt_topbar;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_booklist;
    private RadioButton rb_shoppingcart;
    private RadioButton rb_history;
    private ViewPager vpager;
    private MyFragmentPagerAdapter mAdapter;
    private DBOpenHelper mDBOpenHelper;
    private ArrayList<Book> datas = new ArrayList<>();


    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;

    private void init(){
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_booklist = (RadioButton) findViewById(R.id.rb_booklist);
        rb_shoppingcart = (RadioButton) findViewById(R.id.rb_shoppingcart);
        rb_history = (RadioButton) findViewById(R.id.rb_history);
        rg_tab_bar.setOnCheckedChangeListener(this);
        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBOpenHelper = new DBOpenHelper(this, "db_test.db", null,1);

        SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        //main函数在这里完成数据库到arraylist的转换，datas作为参数传给MyFragmentPagerAdapter
        Cursor cursor = db.query("book",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                int i = cursor.getColumnIndex("name"), j = cursor.getColumnIndex("intro"),
                        k = cursor.getColumnIndex("price"), p = cursor.getColumnIndex("pic");
                String name = cursor.getString(i);
                String intro = cursor.getString(j);
                String price = cursor.getString(k);
                int pic = cursor.getInt(p);
                Book book = new Book(name,price,intro,pic);
                datas.add(book);
            }while(cursor.moveToNext());
        }
        cursor.close();

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),datas);
        init();
        rb_booklist.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_booklist:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_shoppingcart:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_history:
                vpager.setCurrentItem(PAGE_THREE);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if(state == 2){
            switch (vpager.getCurrentItem()){
                case PAGE_ONE:
                    rb_booklist.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_shoppingcart.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_history.setChecked(true);
                    break;
            }
        }
    }
}