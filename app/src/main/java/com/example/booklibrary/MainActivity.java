package com.example.booklibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
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
    //private ArrayList<Book> datas = new ArrayList<>();


    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    private static final int DEFAULT_OFFSCREEN_PAGES = 0;
    private int mOffscreenPageLimit = DEFAULT_OFFSCREEN_PAGES;


    private void init(){
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_booklist = (RadioButton) findViewById(R.id.rb_booklist);
        rb_shoppingcart = (RadioButton) findViewById(R.id.rb_shoppingcart);
        rb_history = (RadioButton) findViewById(R.id.rb_history);
        rg_tab_bar.setOnCheckedChangeListener(this);
        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setOffscreenPageLimit(0);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),username);

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