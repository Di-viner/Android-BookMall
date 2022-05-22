package com.example.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_username, et_pwd1, et_pwd2;
    private Button iv_back;
    private DBOpenHelper mDBopenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止横屏
        setContentView(R.layout.activity_register);
        init();
        mDBopenHelper = new DBOpenHelper(this,"db_test.db",null,1);
        mDBopenHelper.getWritableDatabase();
    }
    private void init(){
        et_username = findViewById(R.id.username);
        et_pwd1 = findViewById(R.id.pwd);
        et_pwd2 = findViewById(R.id.pwd2);
        Button btn_register = findViewById(R.id.registerBtn);
        iv_back = findViewById(R.id.iv_back);
        btn_register.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SQLiteDatabase db = mDBopenHelper.getWritableDatabase();
        switch (view.getId()){
            case R.id.registerBtn:
                String username = et_username.getText().toString().trim();
                String pwd1 = et_pwd1.getText().toString().trim();
                String pwd2 = et_pwd2.getText().toString().trim();
                if(!TextUtils.isEmpty(username)){
                    Cursor cursor = db.query("user",null,"username = ?", new String[]{username},null,null,null);
                    if(cursor.moveToFirst())
                    {
                        Toast.makeText(this,"用户名已存在",Toast.LENGTH_SHORT).show();
                        cursor.close();
                    }
                }else{
                    Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                }
                if(!TextUtils.isEmpty(pwd1)&&!TextUtils.isEmpty(pwd2)){
                    if(pwd1.equals(pwd2)){
                        //mDBopenHelper.add(username, pwd1);
                        ContentValues values = new ContentValues();
                        values.put("username", username);
                        values.put("password", pwd1);
                        db.insert("user",null,values);
                        db.close();
                        Toast.makeText(this, "注册中...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "两次密码不一致，注册失败",Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(this,"输入密码不能为空", Toast.LENGTH_SHORT).show();
                }
                if(null != db){
                    db.close();
                }
                break;
            case R.id.iv_back:
                if(db!=null) {
                    db.close();
                }
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}