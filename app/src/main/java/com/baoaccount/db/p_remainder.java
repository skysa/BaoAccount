package com.baoaccount.db;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class p_remainder extends BmobObject {
    private Double p_money;
    private Accunt_type p_account;
    private BmobUser p_name;

    public BmobUser getP_name() {
        return p_name;
    }

    public void setP_name(BmobUser p_name) {
        this.p_name = p_name;
    }

    public Accunt_type getP_account() {
        return p_account;
    }

    public void setP_account(Accunt_type p_account) {
        this.p_account = p_account;
    }

    public Double getP_money() {
        return p_money;
    }

    public void setP_money(Double p_money) {
        this.p_money = p_money;
    }

}
