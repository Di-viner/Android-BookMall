package com.example.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements OnClickListener{
    private DBOpenHelper mDBOpenHelper;
    private Button login;
    private TextView tv_register;
    private CheckBox sav_pwd;
    private EditText et_username, et_pwd;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        //Objects.requireNonNull(getSupportActionBar()).hide();//隐藏标题栏
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止横屏
        mDBOpenHelper = new DBOpenHelper(this, "db_test.db", null,1);
        mDBOpenHelper.getWritableDatabase();

        init();

        SharedPreferences sp = getSharedPreferences("user_mes", MODE_PRIVATE);
        editor = sp.edit();
        if(sp.getBoolean("flag", false)){
            String user_read = sp.getString("username","");
            String psw_read = sp.getString("password","");
            sav_pwd.setChecked(true);
            et_username.setText(user_read);
            et_pwd.setText(psw_read);
        }
    }
    private void init(){
        et_username = (EditText) findViewById(R.id.username);
        et_pwd =(EditText) findViewById(R.id.pwd);
        sav_pwd = (CheckBox) findViewById(R.id.save_pwd);
        login = (Button) findViewById(R.id.loginBtn);
        tv_register = (TextView) findViewById(R.id.register);
        tv_register.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                Log.d("TAG", "onClick: ");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.loginBtn:
                String username = et_username.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
                Cursor cursor = db.query("user",null,"username=?",new String[]{username},null,null,null);
                boolean match = false;
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)){
                    if(cursor.moveToFirst()){
                        int i = cursor.getColumnIndex("password");
                        if(!pwd.equals(cursor.getString(i))){
                            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }else{
                            if(sav_pwd.isChecked()){
                                match = true;
                                editor.putBoolean("flag",true);
                                editor.putString("username", username);
                                editor.putString("password", pwd);
                                editor.apply();
                            }else{
                                editor.putBoolean("flag",false);
                                editor.putString("username",username);
                                editor.putString("password","");
                                editor.clear();
                                editor.apply();
                            }
                            if(match){
                                Toast.makeText(this,"成功记住密码",Toast.LENGTH_SHORT).show();
                                sav_pwd.setChecked(true);
                            }
                            Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                            Runnable target;
                            Thread thread = new Thread(){
                                @Override
                                public void run(){
                                    try{
                                        //sleep(2000);
                                        String user_name = username;
                                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                                        intent1.putExtra("username",user_name);
                                        startActivity(intent1);
                                        finish();
                                    }  catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            };
                            thread.start();
                        }
                    }else{
                        Toast.makeText(this, "用户名不存在", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            default:
                finish();
        }
    }
}