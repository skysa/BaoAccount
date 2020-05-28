package com.baoaccount.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baoaccount.R;
import com.baoaccount.db.Day_flow;
import com.baoaccount.db.MyUser;
import com.baoaccount.db.d_jieyu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeFragment extends Fragment {
    private TextView home_title;  //设置时间标题
    private TextView home_sum_out; //设置总支出
    private TextView home_sum_in; //设置总收入
    private TextView home_suggestion; //设置小贴士
    private int year;
    private int month;
    private int day;
    private ScrollView scrollView; //是指home页面的滚动布局
    private LinearLayout in_layout; //收入展示页布局
    private LinearLayout out_layout; //支出展示页布局


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //scrollView = (ScrollView) root.findViewById(R.id.home_layout);  //获取滚动布局的id
        out_layout = (LinearLayout) root.findViewById(R.id.account_out);
        in_layout = (LinearLayout)root.findViewById(R.id.account_in);

        home_title = root.findViewById(R.id.title_home);
        setDate(home_title);
        setSum(root);
        try {
            setAccountOut(root);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return root;
    }


    @SuppressLint("SetTextI18n")
    public void setDate(TextView t){   //设置进入的时间
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        t.setText(year+"年"+month+"月"+day+"日");
    }

    public void setSum(final View root){
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<d_jieyu> jyq1 = new BmobQuery<>();
        jyq1.addWhereEqualTo("j_user",user);
        BmobQuery<d_jieyu> jyq2 = new BmobQuery<>();
        jyq2.addWhereEqualTo("j_year",year);
        BmobQuery<d_jieyu> jyq3 = new BmobQuery<>();
        jyq3.addWhereEqualTo("j_month",month);
        BmobQuery<d_jieyu> jyq4 = new BmobQuery<>();
        jyq4.addWhereEqualTo("j_day",day);

        List<BmobQuery<d_jieyu>> bmobQueries = new ArrayList<>();
        bmobQueries.add(jyq1);
        bmobQueries.add(jyq2);
        bmobQueries.add(jyq3);
        bmobQueries.add(jyq4);

        BmobQuery<d_jieyu> query = new BmobQuery<>();
        query.include("j_suggestion");
        query.and(bmobQueries);
        query.findObjects(new FindListener<d_jieyu>() {
            @Override
            public void done(List<d_jieyu> list, BmobException e) {
                if (e==null){
                    home_sum_out = root.findViewById(R.id.home_sum_out);
                    home_sum_in = root.findViewById(R.id.home_sum_in);
                    home_suggestion = root.findViewById(R.id.home_suggestion);
                    if (list.size()==0){   //如果当前无流水，记录suggestion，设置支出收入为零
                        home_sum_out.setText(String.valueOf(0));
                        home_sum_in.setText(String.valueOf(0));
                        home_suggestion.setText("今日还没有任何账单记录哦，快去记一笔吧！");
                    }else {
                        Double moneyIn = list.get(0).getJ_money_in();
                        Double moneyOut = list.get(0).getJ_money_out();
                        String suggestion = list.get(0).getJ_suggestion().getSuggestion();



                        home_sum_in.setText(String.valueOf(moneyIn));
                        home_sum_out.setText(String.valueOf(moneyOut));
                        home_suggestion.setText(suggestion);
                    }


                }
            }
        });
    }
    public void setAccountOut(final View root) throws ParseException {
        final MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Day_flow> dfq1 = new BmobQuery<>(); //设置查询年月日相同的账单
        dfq1.addWhereEqualTo("df_year",year);

        BmobQuery<Day_flow> dfq2 = new BmobQuery<>();
        dfq2.addWhereEqualTo("df_month",month);

        BmobQuery<Day_flow> dfq3 = new BmobQuery<>();
        dfq3.addWhereEqualTo("df_date",day);

        BmobQuery<Day_flow> dfq4 = new BmobQuery<>();
        dfq4.addWhereEqualTo("user_name",user);

        List<BmobQuery<Day_flow>> bmobQueryList = new ArrayList<>();
        bmobQueryList.add(dfq1);
        bmobQueryList.add(dfq2);
        bmobQueryList.add(dfq3);
        bmobQueryList.add(dfq4);


        BmobQuery<Day_flow> query = new BmobQuery<>();
        query.include("account_type,m_direction,flow_type");
        query.order("-createdAt");
        query.and(bmobQueryList);
        query.findObjects(new FindListener<Day_flow>() {
            @Override
            public void done(List<Day_flow> list, BmobException e) {
                if (e==null){
                    in_layout.removeAllViews();
                    out_layout.removeAllViews();
                    for (int i=0;i<list.size();i++){
                        if (list.get(i).getM_direction().getD_name().equals("支出")){
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_account_out_item,out_layout,false);
                            setItem(out_layout,view,list,i);

                        }else if (list.get(i).getM_direction().getD_name().equals("收入")){
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_account_out_item,in_layout,false);
                            setItem(in_layout,view,list,i);
                        }
                    }

                }
            }
        });
    }

    public void setItem(LinearLayout layout,View view,List<Day_flow> list,int i){
        TextView out_type = (TextView)view.findViewById(R.id.account_out_type);
        TextView out_account = (TextView)view.findViewById(R.id.account_out_used);
        TextView out_money = (TextView)view.findViewById(R.id.account_out_number);
        out_type.setText(list.get(i).getFlow_type().getType_name());
        out_account.setText(list.get(i).getAccount_type().getAccount_name());
        out_money.setText(String.valueOf(list.get(i).getN_money()));
        layout.addView(view);
    }
}
