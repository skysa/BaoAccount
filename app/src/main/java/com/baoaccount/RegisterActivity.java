package com.baoaccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baoaccount.db.user;
import com.baoaccount.db.userManager;

import org.litepal.LitePal;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText e_set_username = null;
    private EditText e_set_password = null;
    private EditText e_set_email = null;
    private Button e_set_finish = null;
    private ImageView e_back;
    private Activity tag = RegisterActivity.this;
    userManager userManager = new userManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                String username = e_set_username.getText().toString();
                String password = e_set_password.getText().toString();
                String email = e_set_email.getText().toString();
                //boolean fl = userManager.findUserByName(username);
                if (username.equals("")){
                    Toast.makeText(tag,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if(userManager.findUserByName(username)){
                    Toast.makeText(tag,"该用户名已存在！",Toast.LENGTH_SHORT).show();
                }else if (password.equals("")){
                    Toast.makeText(tag,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if (password.length()<=4){
                    Toast.makeText(tag,"密码不能小于四位",Toast.LENGTH_SHORT).show();
                }else if (email.equals("")){
                    Toast.makeText(tag,"请输入邮箱",Toast.LENGTH_SHORT).show();
                }else{
                    userManager.insertUser(username,password,email);
                    Toast.makeText(tag,"您已注册成功",Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(tag,LoginActivity.class);
                    startActivity(intent);
                }

            }
        });


    }
}
