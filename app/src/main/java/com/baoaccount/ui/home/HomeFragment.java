package com.baoaccount.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baoaccount.R;
import com.baoaccount.db.Day_flow;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.bmob.v3.BmobQuery;

public class HomeFragment extends Fragment {
    private TextView home_title;
    private TextView home_sum_out;
    private TextView home_sum_in;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        home_title = root.findViewById(R.id.title_home);
        home_sum_in = root.findViewById(R.id.home_sum_in);
        home_sum_out = root.findViewById(R.id.home_sum_out);
        setDate(home_title);

        return root;
    }

    public void setHome_sum_out(TextView t){
        BmobQuery<Day_flow> day_flowBmobQuery = new BmobQuery<>();

    }

    public void setDate(TextView t){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        t.setText(year+"年"+month+"月"+date+"日");
    }

}
