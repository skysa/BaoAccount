package com.baoaccount.db;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {
    private family_name family;

    public family_name getFamily() {
        return family;
    }

    public void setFamily(family_name family) {
        this.family = family;
    }
}
