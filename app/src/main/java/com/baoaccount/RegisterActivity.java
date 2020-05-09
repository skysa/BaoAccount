package com.baoaccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {
    private EditText e_set_username = null;
    private EditText e_set_password = null;
    private EditText e_set_email = null;
    private Button e_set_finish = null;
    private ImageView e_back;
    private Context tag = RegisterActivity.this;
    toast t = new toast();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this,"0a45c49f2713b2838103e25fcf2474e6");

        e_set_username = (EditText) findViewById(R.id.user_set_name);
        e_set_password = (EditText) findViewById(R.id.user_set_password);
        e_set_email = (EditText) findViewById(R.id.user_set_email);
        e_set_finish = (Button) findViewById(R.id.set_finish);
        e_back = (ImageView) findViewById(R.id.register_back);

        e_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tag, LoginActivity.class);
                startActivity(intent);
            }
        });
        e_set_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = e_set_username.getText().toString();
                final String password = e_set_password.getText().toString();
                String email = e_set_email.getText().toString();
                //boolean fl = userManager.findUserByName(username);
                if (username.equals("")){
                    t.toast_short(tag,"请输入用户名");
                }else if (password.equals("")){
                    t.toast_short(tag,"请输入密码");
                }else if (email.equals("")){
                    t.toast_short(tag,"请输入邮箱");
                }else if (password.length()<6){
                    t.toast_short(tag,"密码不能少于6位数！");
                }else {
                    BmobUser user = new BmobUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.signUp(new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e==null){
                                t.toast_short(tag,"恭喜您，注册成功！");
                                Intent intent = new Intent(tag,LoginActivity.class);
                                intent.putExtra("user_name",username);
                                intent.putExtra("password",password);
                                startActivity(intent);
                            }else {
                                t.toast_long(tag,String.valueOf(e));

                            }
                        }
                    });
                }

            }
        });


    }

}
