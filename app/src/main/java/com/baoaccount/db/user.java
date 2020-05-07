package com.baoaccount.db;

import org.litepal.crud.LitePalSupport;

import cn.bmob.v3.BmobObject;

public class user extends LitePalSupport {
    private Integer u_id;
    private String u_name;
    private String u_password;
    private String u_email;
    private Integer u_familyId;

    public Integer getU_familyId() {
        return u_familyId;
    }

    public void setU_familyId(Integer u_familyId) {
        this.u_familyId = u_familyId;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }
}
