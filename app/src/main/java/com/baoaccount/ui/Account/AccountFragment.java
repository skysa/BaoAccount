package com.baoaccount.ui.Account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baoaccount.R;
import com.baoaccount.db.Day_flow;
import com.baoaccount.db.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AccountFragment extends Fragment {

    private Button person_flow;
    private Button family_flow;
    private ListView account;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        person_flow = root.findViewById(R.id.person_flow);
        family_flow = root.findViewById(R.id.family_flow);
        person_flow.setOnClickListener(new View.OnClickListener() {
           // showPersonFlow(account,root);
            @Override
            public void onClick(View v) {
                //showPersonFlow(account,root);
            }
        });
        family_flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showFamilyFlow(account,root);
            }
        });

        return root;
    }
    public void showPersonFlow(ListView account, final View root){
        BmobQuery<Day_flow> day_flow = new  BmobQuery<Day_flow>();
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        day_flow.addWhereEqualTo("user_name",user);
        day_flow.findObjects(new FindListener<Day_flow>() {
            @Override
            public void done(List<Day_flow> list, BmobException e) {
                if (e==null){
                    for (int i=0;i<list.size();i++){

                    }
                }
            }
        });
    }
}
