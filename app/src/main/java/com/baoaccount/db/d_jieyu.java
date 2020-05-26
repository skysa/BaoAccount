package com.baoaccount.db;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class d_jieyu extends BmobObject {
    private Double j_money_in;
    private Double j_money_out;

    public Double getJ_money_out() {
        return j_money_out;
    }

    public void setJ_money_out(Double j_money_out) {
        this.j_money_out = j_money_out;
    }

    public Double getJ_money_in() {
        return j_money_in;
    }

    public void setJ_money_in(Double j_money_in) {
        this.j_money_in = j_money_in;
    }

    private BmobUser j_user;
    private suggestion j_suggestion;
    private Integer j_year;
    private Integer j_month;
    private Integer j_day;

    public Integer getJ_day() {
        return j_day;
    }

    public void setJ_day(Integer j_day) {
        this.j_day = j_day;
    }

    public Integer getJ_month() {
        return j_month;
    }

    public void setJ_month(Integer j_month) {
        this.j_month = j_month;
    }

    public Integer getJ_year() {
        return j_year;
    }

    public void setJ_year(Integer j_year) {
        this.j_year = j_year;
    }

    public suggestion getJ_suggestion() {
        return j_suggestion;
    }

    public void setJ_suggestion(suggestion j_suggestion) {
        this.j_suggestion = j_suggestion;
    }

    public BmobUser getJ_user() {
        return j_user;
    }

    public void setJ_user(BmobUser j_user) {
        this.j_user = j_user;
    }


}
