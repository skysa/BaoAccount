package com.baoaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoaccount.db.MyUser;
import com.baoaccount.db.family_name;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class create_family extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family);
        ImageView back = (ImageView)findViewById(R.id.home_create_back); //获取上端图标
        ImageView finish = (ImageView)findViewById(R.id.home_create_finish);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_family.this.finish();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_family.this.finish();
            }
        });

        final TextView family_name = (TextView)findViewById(R.id.family_name); //获取家庭名称
        final TextView user_name = (TextView)findViewById(R.id.family_member); //获取用户名
        final Button create = (Button)findViewById(R.id.create_family); //选择创建家庭按钮
        Button join = (Button)findViewById(R.id.join_family); //选择加入家庭按钮

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f_name = family_name.getText().toString();
                String u_name = user_name.getText().toString();
                if (f_name.length()==0){
                    Toast.makeText(create_family.this,"请输入创建的家庭名",Toast.LENGTH_SHORT).show();
                }else {
                    createfamily(f_name,u_name);  //创建新的家庭信息，如果username不为空，则添加该用户
                }

            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f_name = family_name.getText().toString();
                String u_name = user_name.getText().toString();
                if (f_name.length()==0){
                    Toast.makeText(create_family.this,"请输入创建的家庭名",Toast.LENGTH_SHORT).show();
                }else {
                    joinFamily(f_name,u_name);
                }
            }
        });
    }

    public void createfamily(String f_name, final String u_name){
            family_name family = new family_name();  //添加家庭数据
            family.setFamily_name(f_name);
            family.setFamily_member(1);
            family.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e==null){
                        BmobQuery<family_name> family_nameBmobQuery = new BmobQuery<>();
                        family_nameBmobQuery.getObject(s, new QueryListener<family_name>() {
                            @Override
                            public void done(final family_name familyName, BmobException e) {
                                MyUser user = BmobUser.getCurrentUser(MyUser.class);  //将新添加的数据加入当前用户数据中
                                user.setFamily(familyName);
                                Toast.makeText(create_family.this,"您已成功创建家庭",Toast.LENGTH_SHORT).show();
                                user.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e==null){
                                            adduser(familyName,u_name);
                                            Toast.makeText(create_family.this,"您已成功加入该家庭",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(create_family.this,MyFamily.class);
                                            startActivity(intent);
                                            create_family.this.finish();
                                        }else {
                                            Toast.makeText((create_family.this),"创建家庭失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }else {
                        Log.d("My",e.toString());
                    }


                }
            });


    }

    public void adduser(final family_name familyName, String u_name){  //将邀请用户加入该家庭
        if (u_name.length()>0){
            BmobQuery<MyUser> userBmobQuery = new BmobQuery<>();
            userBmobQuery.addWhereEqualTo("username",u_name);
            Log.d("Mine",u_name);
            userBmobQuery.findObjects(new FindListener<MyUser>() {
                @Override
                public void done(final List<MyUser> list, BmobException e) {
                    if (list.size()==0){
                        Toast.makeText(create_family.this,"该用户未注册,未能成功加入该家庭",Toast.LENGTH_SHORT).show();
                    }else {
                        if(list.get(0).getFamily()!=null){
                            Toast.makeText(create_family.this,"该用户已加入家庭,未能成功加入此家庭",Toast.LENGTH_SHORT).show();
                        }else {
                            MyUser user = new MyUser();
                            user.setFamily(familyName);
                            user.update(list.get(0).getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        family_name familyName1 = new family_name();
                                        familyName1.setFamily_member(familyName.getFamily_member()+1);
                                        familyName1.update(familyName.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e==null){

                                                }
                                            }
                                        });

                                        Toast.makeText(create_family.this,"您已成功邀请该用户加入该家庭",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                }
            });
        }
    }

    public void joinFamily(String f_name, final String u_name){
        BmobQuery<family_name> family_nameBmobQuery = new BmobQuery<>();
        family_nameBmobQuery.addWhereEqualTo("family_name",f_name);
        family_nameBmobQuery.findObjects(new FindListener<family_name>() {
            @Override
            public void done(final List<family_name> list, BmobException e) {
                if (e==null){
                    if (list.size()==0){
                        Toast.makeText(create_family.this,"该家庭不存在",Toast.LENGTH_SHORT).show(); //
                    }else {
                        MyUser user = BmobUser.getCurrentUser(MyUser.class);
                        user.setFamily(list.get(0));
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    family_name familyName1 = new family_name();
                                    familyName1.setFamily_member(list.get(0).getFamily_member()+1);
                                    familyName1.update(list.get(0).getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e==null){
                                                Toast.makeText(create_family.this,"您已成功加入该家庭",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        adduser(list.get(0),u_name);
                        Intent intent = new Intent(create_family.this,MyFamily.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
