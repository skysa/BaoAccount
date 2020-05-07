package com.baoaccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baoaccount.db.userManager;

public class LoginActivity extends AppCompatActivity {
    private EditText l_userName;
    private EditText l_userPassword;
    private Button l_button;
    private Button r_button;
    private Activity Tag = LoginActivity.this;

    userManager userManager = new userManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        l_userName = (EditText)findViewById(R.id.user_name);
        l_userPassword = (EditText)findViewById(R.id.user_password);
        l_button = (Button)findViewById(R.id.login);
        r_button = (Button)findViewById(R.id.register);

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
                String username = l_userName.getText().toString();
                String password = l_userPassword.getText().toString();
                if (username.equals("")){
                    Toast.makeText(Tag,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if (password.equals("")){
                    Toast.makeText(Tag,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if (userManager.findNameAndPassword(username,password)){
                    Toast.makeText(Tag,username+"欢迎回来",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Tag,MainActivity.class);
                    intent.putExtra("user_id",userManager.findId(username));
                    startActivity(intent);
                }else if (userManager.findUserByName(username)){
                    Toast.makeText(Tag,"该用户不存在",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Tag,"用户名与密码不匹配",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
