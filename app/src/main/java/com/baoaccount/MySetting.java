package com.baoaccount;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baoaccount.db.MyUser;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.util.V;

public class MySetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        LinearLayout setPassword = (LinearLayout)findViewById(R.id.setting_mima); //获取修改密码布局
        LinearLayout setUsername = (LinearLayout)findViewById(R.id.setting_name); //获取修改用户名布局
        LinearLayout setEmain = (LinearLayout)findViewById(R.id.setting_email);  //获取修改邮箱布局
        setPassword.setOnClickListener(new View.OnClickListener() {  //点击后修改密码
            @Override
            public void onClick(View v) {
                l1();
            }
        });
        setUsername.setOnClickListener(new View.OnClickListener() { //点击修改用户名
            @Override
            public void onClick(View v) {
                l2();
            }
        });
        setEmain.setOnClickListener(new View.OnClickListener() { //点击修改密码
            @Override
            public void onClick(View v) {
                l3();
            }
        });

        ImageView back = (ImageView) findViewById(R.id.setting_back);  //点击返回按钮返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySetting.this.finish();
            }
        });

        ImageView finish = (ImageView)findViewById(R.id.finish);  //点击完成按钮返回
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySetting.this.finish();
            }
        });


    }

    public void l1(){   //点击修改密码跳出输入框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 = View.inflate(this,R.layout.setting_mima,null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(view1);
        alertDialog.show();

        final EditText origin = (EditText) view1.findViewById(R.id.origin_mima);
        final EditText newMima = (EditText) view1.findViewById(R.id.new_mima);
        final EditText sureMima = (EditText) view1.findViewById(R.id.queren_mima);

        Button sure = (Button) view1.findViewById(R.id.bsure);
        Button cancel = (Button) view1.findViewById(R.id.bcam);

        sure.setOnClickListener(new View.OnClickListener() {  //单击确定后判断是否合法，并且修改密码
            String or_pas;
            String new_pas;
            String sure_pas;
            @Override
            public void onClick(View v) {
                or_pas = origin.getText().toString(); //获取旧密码
                new_pas = newMima.getText().toString(); //获取输入新密码
                sure_pas = sureMima.getText().toString(); //获取第二次输入新密码
                if (!new_pas.equals(sure_pas)){
                    Toast.makeText(MySetting.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                }else if (new_pas.length()<6){
                    Toast.makeText(MySetting.this,"密码长度不能少于6位",Toast.LENGTH_SHORT).show();
                } else{
                    //修改密码
                    BmobUser.updateCurrentUserPassword(or_pas, new_pas, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Toast.makeText(MySetting.this,"修改密码成功！",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MySetting.this,"旧密码输入错误！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {  //点击取消按钮，退出弹框
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void l2(){
        AlertDialog .Builder builder = new  AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.setting_name,null);
        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.show();

        final EditText user_name = (EditText)view.findViewById(R.id.new_name);

        Button sureButton = (Button)view.findViewById(R.id.name_sure);
        Button cancelButton = (Button)view.findViewById(R.id.name_cancel);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = user_name.getText().toString();
                if (name.length()==0){
                    Toast.makeText(MySetting.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else {
                    name = user_name.getText().toString();
                    MyUser user = BmobUser.getCurrentUser(MyUser.class);
                    user.setUsername(name);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Toast.makeText(MySetting.this,"修改用户名成功",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(MySetting.this,"修改用户名失败",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
    public void l3(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.setting_email,null);
        final AlertDialog alertDialog1 = builder.create();
        alertDialog1.setView(view);
        alertDialog1.show();
        final EditText email = (EditText) view.findViewById(R.id.new_mail);

        Button sureButton = (Button) view.findViewById(R.id.mail_sure);
        Button cancelButton = (Button)view.findViewById(R.id.mail_cancel);

        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final String mail = email.getText().toString();
                    if (mail.length()==0){
                        Toast.makeText(MySetting.this,"请输入邮箱",Toast.LENGTH_SHORT).show();
                    }else {
                        MyUser user = BmobUser.getCurrentUser(MyUser.class);
                        user.setEmail(mail);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(MySetting.this,"修改邮箱成功",Toast.LENGTH_SHORT).show();
                                    alertDialog1.dismiss();
                                }else {
                                    Toast.makeText(MySetting.this,"邮箱格式错误",Toast.LENGTH_SHORT).show();
                                    alertDialog1.dismiss();
                                }
                            }
                        });
                    }

                }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });

    }
}
