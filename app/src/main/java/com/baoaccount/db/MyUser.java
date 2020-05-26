package com.baoaccount.db;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {
    private family_name family_name;

    public com.baoaccount.db.family_name getFamily_name() {
        return family_name;
    }

    public void setFamily_name(com.baoaccount.db.family_name family_name) {
        this.family_name = family_name;
    }
}
