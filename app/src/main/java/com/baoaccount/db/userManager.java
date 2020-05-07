package com.baoaccount.db;

import org.litepal.LitePal;

import java.util.List;

public class userManager {
    //注册时插入用户数据
    public boolean insertUser(String name,String password,String e_mail){
        user us1 = new user();
        us1.setU_name(name);
        us1.setU_password(password);
        us1.setU_email(e_mail);
        LitePal.getDatabase();
        return us1.save();
    }
    //查看用户名是否存在
    public boolean findUserByName(String name){
        List<user> users = LitePal.findAll(user.class);
        for(user us1:users){
            if(name.trim().equals(us1.getU_name())){
                return true;
            }
        }
        return false;
    }
    //查找用户名密码是否匹配
    public boolean findNameAndPassword(String name,String password){
        List<user> users = LitePal.findAll(user.class);
        for (user us1:users){
            if (name.trim().equals(us1.getU_name())&&password.trim().equals(us1.getU_password())){
                return true;
            }
        }
        return false;
    }
    public int findId(String name){
        List<user> users = LitePal.findAll(user.class);
        for (user us1:users){
            if (name.trim().equals(us1.getU_name())){
                return us1.getU_id();
            }
        }
        return 0;
    }
}
