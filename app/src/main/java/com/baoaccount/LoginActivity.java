package com.baoaccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baoaccount.db.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {
    private EditText l_userName;
    private EditText l_userPassword;
    private Button l_button;
    private Button r_button;
    private Activity Tag = LoginActivity.this;
    private String user_name;
    private String u_password;
    private Context TAG = LoginActivity.this;
    toast t = new toast();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bmob.initialize(this,"0a45c49f2713b2838103e25fcf2474e6"); //初始化bmob数据库

        l_userName = (EditText)findViewById(R.id.user_name);
        l_userPassword = (EditText)findViewById(R.id.user_password);
        l_button = (Button)findViewById(R.id.login);
        r_button = (Button)findViewById(R.id.register);

        user_name = getIntent().getStringExtra("user_name");
        u_password = getIntent().getStringExtra("password");
        l_userName.setText(user_name);
        l_userPassword.setText(u_password);

        r_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tag,RegisterActivity.class);
                startActivity(intent);
            }
        });
        l_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = l_userName.getText().toString();
                String password = l_userPassword.getText().toString();
                if (username.equals("")){
                    Toast.makeText(Tag,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (password.equals("")){
                    Toast.makeText(Tag,"请输入密码",Toast.LENGTH_SHORT).show();
                }else {
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser bmobUser, BmobException e) {
                            if(e == null){
                                t.toast_long(TAG,username+"欢迎回来!");
                                Intent intent = new Intent(TAG,MainActivity.class);
                                startActivity(intent);
                            }else {
                                t.toast_long(TAG,String.valueOf(e));
                            }
                        }
                    });
                }
            }
        });
    }
}
