package com.baoaccount.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baoaccount.MyAccount;
import com.baoaccount.MyChart;
import com.baoaccount.MyFamily;
import com.baoaccount.MySetting;
import com.baoaccount.R;
import com.baoaccount.app_main;
import com.baoaccount.user_setting;
import com.leon.lib.settingview.LSettingItem;

import cn.bmob.v3.BmobUser;

public class MineFragment extends Fragment {
    private TextView user_name;
    private TextView family_name;
    private ImageView setting;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        user_name = root.findViewById(R.id.m_name);
        family_name = root.findViewById(R.id.m_family);
        String name = (String) BmobUser.getObjectByKey("username");
        String family = (String) BmobUser.getObjectByKey("family_name");
        user_name.setText(name);
        family_name.setText(family);

        LinearLayout m_family = root.findViewById(R.id.set_family);
        LinearLayout m_chart = root.findViewById(R.id.set_chart);
        LinearLayout m_account = root.findViewById(R.id.set_account);
        LinearLayout m_setting = root.findViewById(R.id.set_setting);
        LinearLayout m_sign_out = root.findViewById(R.id.set_so);

        m_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStart(MyFamily.class);
            }
        });
        m_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStart(MyChart.class);
            }
        });
        m_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStart(MyAccount.class);
            }
        });
        m_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStart(MySetting.class);
            }
        });
        m_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                getActivity().finish();
            }
        });

        return root;
    }
    public void ActivityStart(Class tag){
        Intent intent = new Intent(getActivity(),tag);
        startActivity(intent);
    }

}
