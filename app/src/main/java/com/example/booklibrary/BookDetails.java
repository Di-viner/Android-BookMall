package com.example.booklibrary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;

public class BookDetails extends Fragment implements View.OnClickListener {

    View view;
    TextView tv_name;
    TextView tv_intro;
    TextView tv_price;
    TextView tv_content;
    TextView tv_ID;
    TextView tv_author;
    ImageView iv_pic;
    Button iv_back;
    Button add_cars;

    String username;
    BookDetails(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.book_detail, container, false);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_intro = (TextView) view.findViewById(R.id.tv_intro);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_content = (TextView) view.findViewById(R.id.tv_content);

        iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        iv_back = (Button) view.findViewById(R.id.iv_back);
        tv_ID = (TextView) view.findViewById(R.id.tv_ID);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_author = (TextView) view.findViewById(R.id.tv_author);
        add_cars = (Button) view.findViewById(R.id.add_cars);
        //getArgument获取传递过来的Bundle对象

        tv_name.setText(getArguments().getString("name"));
        tv_intro.setText(getArguments().getString("intro"));
        tv_price.setText("¥" +getArguments().getString("price"));
        iv_pic.setImageResource(getArguments().getInt("pic"));

        ///
        tv_content.setText(getArguments().getString("content"));
        tv_ID.setText(getArguments().getString("ID"));
        tv_author.setText(getArguments().getString("author"));
        username = getArguments().getString("username");
        add_cars.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        switch (view.getId()) {

            case R.id.iv_back:
                Log.d("details", "返回");
                try {
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.add_cars:
                Log.d("details","添加购物车");
                addTocars();
                try {
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    private void addTocars() {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getActivity(), "db_test.db", null,1);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("cart",null,"username=? and name = ?",new String[]{username, tv_name.getText().toString()},null,null,null);
        if(cursor.moveToFirst()){
            Toast.makeText(getActivity(), "此商品已在购物车中", Toast.LENGTH_SHORT).show();
            cursor.close();
            db.close();         ////
        }
        else {
            ContentValues values = new ContentValues();
            values.put("username",username);
            values.put("ID",tv_ID.getText().toString());
            values.put("pic",getArguments().getInt("pic"));
            values.put("name",tv_name.getText().toString());
            values.put("price",getArguments().getString("price"));
            values.put("num","1");

            db.insert("cart",null,values);

            Toast.makeText(getActivity(), "添加购物车成功", Toast.LENGTH_SHORT).show();
            cursor.close();

            db.close();     ////
        }

    }
}
