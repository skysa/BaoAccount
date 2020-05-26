package com.baoaccount.db;

import cn.bmob.v3.BmobObject;

public class Flow_direction extends BmobObject {
    private Integer direction;
    private String d_name;

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }
}
