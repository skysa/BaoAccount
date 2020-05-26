package com.baoaccount.Util;

import android.util.Log;

import com.baoaccount.db.Flow_type;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FlowTypeUtil {
    private static IFlowType type;

    public static void queryTypeId(String type_name){
        BmobQuery<Flow_type> flow_typeBmobQuery = new BmobQuery<>();
        flow_typeBmobQuery.addWhereEqualTo("type_name", type_name);
        flow_typeBmobQuery.findObjects(new FindListener<Flow_type>() {
            @Override
            public void done(List<Flow_type> list, BmobException e) {
                if (e == null) {
                    String id = list.get(0).getObjectId();
                    type.getTypeId(id);
                }
            }
        });
    }

    public static void setIFlowTypeListener(IFlowType iFlowType){
        type = iFlowType;
    }

    public interface IFlowType{
        void getTypeId(String id);
    }
}
