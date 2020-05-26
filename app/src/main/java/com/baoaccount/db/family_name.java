package com.baoaccount.db;

import cn.bmob.v3.BmobObject;

public class family_name extends BmobObject {
    private Integer family_member;
    private String family_name;

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public Integer getFamily_member() {
        return family_member;
    }

    public void setFamily_member(Integer family_member) {
        this.family_member = family_member;
    }
}
