package com.baoaccount.db;

import cn.bmob.v3.BmobObject;

public class suggestion extends BmobObject {
    private String suggestion;
    private Integer s_type;

    public Integer getS_type() {
        return s_type;
    }

    public void setS_type(Integer s_type) {
        this.s_type = s_type;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
