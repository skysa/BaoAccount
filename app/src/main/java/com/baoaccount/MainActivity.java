package com.baoaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this,"0a45c49f2713b2838103e25fcf2474e6");
    }
}
