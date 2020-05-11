package com.baoaccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.leon.lib.settingview.LSettingItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class app_main extends AppCompatActivity {
    private Context TAG = app_main.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Bmob.initialize(this, "0a45c49f2713b2838103e25fcf2474e6");
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


        //mineFragment
    }

}
