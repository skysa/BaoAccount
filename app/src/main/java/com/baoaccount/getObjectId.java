package com.baoaccount;

import android.util.Log;

import com.baoaccount.db.Accunt_type;
import com.baoaccount.db.Day_flow;
import com.baoaccount.db.Flow_direction;
import com.baoaccount.db.Flow_type;
import com.baoaccount.db.MyUser;
import com.baoaccount.db.budget;
import com.baoaccount.db.d_jieyu;
import com.baoaccount.db.f_remainder;
import com.baoaccount.db.family_name;
import com.baoaccount.db.p_remainder;
import com.baoaccount.db.suggestion;

import java.net.IDN;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class getObjectId {
    private Accunt_type accunt_type;
    private budget m_budget;
    private d_jieyu jieyu;
    private Day_flow day_flow;
    private f_remainder remainder;
    private family_name f_name;
    private Flow_direction flow_direction;
    private Flow_type flow_type;
    private MyUser myUser;
    private p_remainder p_remainder;
    private suggestion mSuggestion;

    public getObjectId(Accunt_type accunt_type) {
        this.accunt_type = accunt_type;
    }

    public getObjectId(budget m_budget) {
        this.m_budget = m_budget;
    }

    public getObjectId(d_jieyu jieyu) {
        this.jieyu = jieyu;
    }

    public getObjectId(Day_flow day_flow) {
        this.day_flow = day_flow;
    }

    public getObjectId(f_remainder remainder) {
        this.remainder = remainder;
    }

    public getObjectId(Flow_direction flow_direction) {
        this.flow_direction = flow_direction;
    }

    public getObjectId(Flow_type flow_type) {
        this.flow_type = flow_type;
    }

    public getObjectId(MyUser myUser) {
        this.myUser = myUser;
    }

    public getObjectId(com.baoaccount.db.p_remainder p_remainder) {
        this.p_remainder = p_remainder;
    }

    public getObjectId(suggestion mSuggestion) {
        this.mSuggestion = mSuggestion;
    }

    public String getAccountTypeId(String account_name){
        final String[] Id = new String[1];
        BmobQuery<Accunt_type> accunt_typeBmobQuery = new BmobQuery<Accunt_type>();
        accunt_typeBmobQuery.addWhereEqualTo("account_name",account_name);
        accunt_typeBmobQuery.addQueryKeys("objectId");
        accunt_typeBmobQuery.setLimit(15).findObjects(new FindListener<Accunt_type>() {
            @Override
            public void done(List<Accunt_type> list, BmobException e) {
                if (e==null){
                    Id[0] = list.get(0).getObjectId();
                }
            }
        });
        return Id[0];
    }

}
