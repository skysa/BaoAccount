package com.baoaccount.ui.Account;

import android.accounts.Account;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoaccount.Flow;
import com.baoaccount.FlowAdapter;
import com.baoaccount.R;
import com.baoaccount.db.Day_flow;
import com.baoaccount.db.MyUser;
import com.baoaccount.db.family_name;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AccountFragment extends Fragment {



    private View view;  //设置fragment中的layout
    public RecyclerView recyclerView;  //定义RecycleView

    private FlowAdapter flowAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_account, container, false);
        Button person_flow= root.findViewById(R.id.person_flow);
        Button family_flow= root.findViewById(R.id.family_flow);
        showPersonFlow(root);

        person_flow.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                showPersonFlow(root);

            }
        });
        family_flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFamilyFlow(root);
                Toast.makeText(root.getContext(),"click",Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
    public void showPersonFlow(final View root){
        final MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Day_flow> day_flowBmobQuery = new BmobQuery<>();
        day_flowBmobQuery.include("account_type,m_direction,flow_type");
        day_flowBmobQuery.order("-createdAt");
        day_flowBmobQuery.addWhereEqualTo("user_name",user);
        day_flowBmobQuery.findObjects(new FindListener<Day_flow>() {
            @Override
            public void done(List<Day_flow> list, BmobException e) {
                ArrayList<Flow> flowArrayList = new ArrayList<>();
                if (e==null){
                    Log.d("My",String.valueOf(list.size()));
                    for (int i=0;i<list.size();i++){  //查询bmob数据库列表，并将其添加至list中
                        String type = list.get(i).getFlow_type().getType_name();
                        String account = list.get(i).getAccount_type().getAccount_name();
                        String direction = list.get(i).getM_direction().getD_name();
                        Double money = list.get(i).getN_money();
                        if (direction.equals("支出")){
                            money = -money;
                        }
                        String user_name = user.getUsername();
                        Log.d("Mine",user_name);
                        Flow flow = new Flow(type,account,money,user_name);
                        flowArrayList.add(flow);

                    }
                    initRecycleView(flowArrayList,root);


                }
            }
        });
    }



    public void showFamilyFlow(final View root){
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        String username = user.getUsername();
       BmobQuery<MyUser> myUserBmobQuery = new BmobQuery<>();
       myUserBmobQuery.addWhereEqualTo("username",username);
       myUserBmobQuery.include("family");
       myUserBmobQuery.findObjects(new FindListener<MyUser>() {
           @Override
           public void done(List<MyUser> list, BmobException e) {
               if (e==null){
                   family_name f_name = list.get(0).getFamily();
                   //Log.d("My",f_name.getFamily_name());
                   BmobQuery<MyUser> userBmobQuery = new BmobQuery<>();
                   userBmobQuery.addWhereEqualTo("family",f_name);
                   userBmobQuery.findObjects(new FindListener<MyUser>() {
                       @Override
                       public void done(List<MyUser> list, BmobException e) {
                           //Log.d("My","user"+ list.size());
                           if (e==null){
                               fShowFlow(list,root);
                           }
                       }
                   });
               }
           }
       });

    }
    public void fShowFlow(List<MyUser> list, final View root){  //展示对应用户的账单列表
        List<BmobQuery<Day_flow>> queryList = new ArrayList<>();
        for (int i=0;i<list.size();i++){    //添加查询的或条件
            BmobQuery<Day_flow>  dfq = new BmobQuery<>();
            dfq.addWhereEqualTo("user_name",list.get(i));
            //Log.d("My","user23e"+list.size());
            queryList.add(dfq);
        }

        BmobQuery<Day_flow> query = new BmobQuery<>();
        query.or(queryList);
        query.include("account_type,m_direction,flow_type,user_name");
        query.order("-createdAt");
        query.findObjects(new FindListener<Day_flow>() {
            @Override
            public void done(List<Day_flow> list, BmobException e) {
                ArrayList<Flow> flowArrayList = new ArrayList<>();
                if (e==null){
                    //Log.d("My",String.valueOf(list.size()));
                    for (int i=0;i<list.size();i++){  //查询bmob数据库列表，并将其添加至list中
                        String type = list.get(i).getFlow_type().getType_name();
                        String account = list.get(i).getAccount_type().getAccount_name();
                        String direction = list.get(i).getM_direction().getD_name();
                        Double money = list.get(i).getN_money();
                        if (direction.equals("支出")){
                            money = -money;
                        }
                        //Log.d("My",list.get(i).getUser_name().getUsername());
                        String user_name = list.get(i).getUser_name().getUsername();
                        Flow flow = new Flow(type,account,money,user_name);
                        flowArrayList.add(flow);

                    }
                    initRecycleView(flowArrayList,root);


                }
            }
        });
    }
    private void initRecycleView(ArrayList<Flow> flowArrayList,View root){
        Log.d("My","12345");
        recyclerView = (RecyclerView)root.findViewById(R.id.recycle_view);  //找到recycleview
        flowAdapter = new FlowAdapter(getActivity(),flowArrayList);  //设置对应的adapter

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(flowAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));//设置分割线
        /*flowAdapter.setOnItemClickListener(new FlowAdapter.OnItemClickListener(){
            @Override
            public void OnItemClick(View view,Flow data){
                Toast.makeText(getActivity(),"我是item",Toast.LENGTH_LONG).show();
            }
        });*/
    }


}
