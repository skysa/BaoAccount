package com.baoaccount.db;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class budget extends BmobObject {
    private BmobUser b_user;

    public BmobUser getB_user() {
        return b_user;
    }

    public void setB_user(BmobUser b_user) {
        this.b_user = b_user;
    }

    private Double b_money;
    private Integer b_finish;

    public Integer getB_finish() {
        return b_finish;
    }

    public void setB_finish(Integer b_finish) {
        this.b_finish = b_finish;
    }

    public Double getB_money() {
        return b_money;
    }

    public void setB_money(Double b_money) {
        this.b_money = b_money;
    }


}
