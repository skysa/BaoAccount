package com.baoaccount.db;

import cn.bmob.v3.BmobObject;

public class Flow_type extends BmobObject {
    private Integer t_z;
    private Integer t_type;
    private String type_name;

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Integer getT_type() {
        return t_type;
    }

    public void setT_type(Integer t_type) {
        this.t_type = t_type;
    }

    public Integer getT_z() {
        return t_z;
    }

    public void setT_z(Integer t_z) {
        this.t_z = t_z;
    }
}
