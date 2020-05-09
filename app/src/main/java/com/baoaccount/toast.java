package com.baoaccount;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class toast {
    public void toast_short(Context tag, String message){
        Toast.makeText(tag,message,Toast.LENGTH_SHORT).show();
    }
    public void toast_long(Context tag,String message){
        Toast.makeText(tag,message,Toast.LENGTH_LONG).show();
    }
}
