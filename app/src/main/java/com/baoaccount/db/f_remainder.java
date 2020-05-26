package com.baoaccount.db;

import cn.bmob.v3.BmobObject;

public class f_remainder extends BmobObject {
    private Double f_money;
    private Accunt_type account;
    private family_name f_name;

    public family_name getF_name() {
        return f_name;
    }

    public void setF_name(family_name f_name) {
        this.f_name = f_name;
    }

    public Accunt_type getAccount() {
        return account;
    }

    public void setAccount(Accunt_type account) {
        this.account = account;
    }

    public Double getF_money() {
        return f_money;
    }

    public void setF_money(Double f_money) {
        this.f_money = f_money;
    }

}
