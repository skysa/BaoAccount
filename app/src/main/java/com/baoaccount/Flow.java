package com.baoaccount;

public class Flow {
    private String flow_type;
    private String flow_account;
    private Double flow_money;
    private String user_name;
    public Flow(String flow_type,String flow_account,Double flow_money,String user_name){
        this.flow_type = flow_type;
        this.flow_account = flow_account;
        this.flow_money = flow_money;
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getFlow_type() {
        return flow_type;
    }

    public String getFlow_account() {
        return flow_account;
    }

    public String getFlow_money() {
        return String.valueOf(flow_money);
    }
}
