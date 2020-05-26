package com.baoaccount.db;

import cn.bmob.v3.BmobObject;

public class Accunt_type extends BmobObject {
    private Integer t_account;
    private String account_name;

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public Integer getT_account() {
        return t_account;
    }

    public void setT_account(Integer t_account) {
        this.t_account = t_account;
    }
}
