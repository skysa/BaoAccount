package com.baoaccount.db;

import cn.bmob.v3.BmobObject;

public class Day_flow extends BmobObject {

    private Double n_money;
    private Flow_direction m_direction;
    private Flow_type flow_type;
    private Accunt_type account_type;
    private MyUser user_name;

    public MyUser getUser_name() {
        return user_name;
    }

    public void setUser_name(MyUser user_name) {
        this.user_name = user_name;
    }

    public Accunt_type getAccount_type() {
        return account_type;
    }

    public void setAccount_type(Accunt_type account_type) {
        this.account_type = account_type;
    }

    public Flow_type getFlow_type() {
        return flow_type;
    }

    public void setFlow_type(Flow_type flow_type) {
        this.flow_type = flow_type;
    }

    public Flow_direction getM_direction() {
        return m_direction;
    }

    public void setM_direction(Flow_direction m_direction) {
        this.m_direction = m_direction;
    }

    public Double getN_money() {
        return n_money;
    }

    public void setN_money(Double n_money) {
        this.n_money = n_money;
    }


}
