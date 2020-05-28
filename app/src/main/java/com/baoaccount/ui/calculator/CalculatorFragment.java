package com.baoaccount.ui.calculator;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoaccount.CustomRadioGroup;
import com.baoaccount.R;
import com.baoaccount.db.Accunt_type;
import com.baoaccount.db.Day_flow;
import com.baoaccount.db.Flow_direction;
import com.baoaccount.db.Flow_type;
import com.baoaccount.db.MyUser;
import com.baoaccount.db.d_jieyu;
import com.baoaccount.db.f_remainder;
import com.baoaccount.db.family_name;
import com.baoaccount.db.p_remainder;
import com.baoaccount.db.suggestion;
import com.baoaccount.getObjectId;
import com.baoaccount.toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.StatementEvent;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CalculatorFragment extends Fragment {

    private String typename;
    private String MyDirection;
    private String MyAccount;
    private Double MyMoney ;
    private Button MyFinish;
    private toast t;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_calculator, container, false);


        getType(root); //获取用户选择的类型方向

        getDirection(root);//获取用户选择的流水方向

        getAccount(root);//获取用户选择的流水账户


        MyFinish = root.findViewById(R.id.c_finish);
        MyFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText money = root.findViewById(R.id.money);
                    MyMoney=Double.parseDouble(money.getText().toString());
                    if (IsNotEmpty(root)){
                        setFlow(root);  //按照用户设置增加流水数据
                        Toast.makeText(root.getContext(),"您已成功添加一条流水",Toast.LENGTH_SHORT).show();
                        updateD_jieyu();  //更新当日结余表
                        updatep_remainder(); //更新个人余额表
                        MyUser user = BmobUser.getCurrentUser(MyUser.class);
                        BmobQuery<MyUser> userBmobQuery = new BmobQuery<>();
                        userBmobQuery.include("family");
                        userBmobQuery.addWhereEqualTo("username",user.getUsername());
                        userBmobQuery.findObjects(new FindListener<MyUser>() {
                            @Override
                            public void done(List<MyUser> list, BmobException e) {
                                if (e==null){
                                    if (list.get(0).getFamily().getFamily_name().equals("")){  //如果用户加入了家庭则更新家庭余额

                                    }else {
                                        updatef_remainder(list.get(0).getFamily());
                                    }
                                }
                            }
                        });
                    }

                }
            });


        return root;
    }
    private void setSpacing(CustomRadioGroup cg,int widthdp,int heightdp){  //设置各按钮之间的间距大小
        cg.setHorizontalSpacing(widthdp);
        cg.setVerticalSpacing(heightdp);

    }

    public boolean IsNotEmpty(View root){
        if (MyMoney == null){
            t.toast_short(root.getContext(),"输入金额不能为空");
            return false;
        }else if (typename==null){
            t.toast_short(root.getContext(),"请选择收支类型");
            return false;
        }else if (MyDirection==null){
            t.toast_short(root.getContext(),"请选择收支方向");
            return false;
        }else if (MyAccount==null){
            t.toast_short(root.getContext(),"请选择关联账户");
            return false;
        }
        return true;
    }

    private void getType(View root){
        final CustomRadioGroup customRadioGroup = root.findViewById(R.id.customRadioGroup);
        setSpacing(customRadioGroup,25,25);
        customRadioGroup.setListener(new CustomRadioGroup.OnclickListener() {
            @Override
            public void OnText(String text) {
                typename = text;
            }
        });
        BmobQuery<Flow_type> type = new BmobQuery<Flow_type>();
        type.addQueryKeys("type_name");
        type.findObjects(new FindListener<Flow_type>() {
            @Override
            public void done(List<Flow_type> list, BmobException e) {
                if (e==null){
                    for (int i=0;i<list.size();i++){
                        RadioButton radioButton = (RadioButton)getActivity().getLayoutInflater().inflate(R.layout.redio_button, null);
                        radioButton.setText(list.get(i).getType_name());
                        customRadioGroup.addView(radioButton);
                    }
                }
            }
        });
    }

    public void getDirection(View root){
        final CustomRadioGroup customRadioGroup1 = root.findViewById(R.id.customRadioGroup_direction);
        setSpacing(customRadioGroup1,60,20);
        customRadioGroup1.setListener(new CustomRadioGroup.OnclickListener() {
            @Override
            public void OnText(String text) {
                MyDirection = text;
            }
        });
        BmobQuery<Flow_direction> dir = new BmobQuery<Flow_direction>();
        dir.addQueryKeys("d_name");
        dir.findObjects(new FindListener<Flow_direction>() {
            @Override
            public void done(List<Flow_direction> list, BmobException e) {
                if (e==null){
                    for (int i=0;i<list.size();i++){
                        RadioButton radioButton = (RadioButton)getActivity().getLayoutInflater().inflate(R.layout.redio_button,null);
                        radioButton.setText(list.get(i).getD_name());
                        customRadioGroup1.addView(radioButton);
                    }
                }
            }
        });
    }

    public void getAccount(View root){  //获取数据库账户，呈现出单选框
        final CustomRadioGroup customRadioGroup = root.findViewById(R.id.customRadioGroup_account);
        setSpacing(customRadioGroup,60,20);
        customRadioGroup.setListener(new CustomRadioGroup.OnclickListener() {
            @Override
            public void OnText(String text) {
                MyAccount = text;
            }
        });
        final BmobQuery<Accunt_type> account = new BmobQuery<Accunt_type>();
        account.addQueryKeys("account_name");
        account.findObjects(new FindListener<Accunt_type>() {
            @Override
            public void done(List<Accunt_type> list, BmobException e) {
                if (e==null){
                    for (int i=0;i<list.size();i++){
                        RadioButton radioButton = (RadioButton)getActivity().getLayoutInflater().inflate(R.layout.redio_button,null);
                        radioButton.setText(list.get(i).getAccount_name());
                        customRadioGroup.addView(radioButton);
                    }
                }
            }
        });
    }

    public void setFlow(final View root){  //在数据库中添加流水记录
        Calendar calendar = Calendar.getInstance();  //获取当前年月日
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH)+1;
        final int day = calendar.get(Calendar.DATE);
       MyUser user = BmobUser.getCurrentUser(MyUser.class);   //获取当前登录用户

        final Day_flow day_flow = new Day_flow();
        day_flow.setUser_name(user);
        day_flow.setN_money(MyMoney);
        day_flow.setDf_date(day);
        day_flow.setDf_year(year);
        day_flow.setDf_month(month);
        day_flow.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    final String id = day_flow.getObjectId();
                    updateType(root,id);
                    updateDirection(root,id);
                    updateAccount(root,id);

                }else {
                    t.toast_short(root.getContext(),e.toString());
                }
            }
        });

    }
    public void updateType(final View root, final String id){   //添加输入的flowtype
        BmobQuery<Flow_type> flow_typeBmobQuery = new BmobQuery<>(); //查询type的id
        flow_typeBmobQuery.addWhereEqualTo("type_name", typename);
        flow_typeBmobQuery.findObjects(new FindListener<Flow_type>() {
            @Override
            public void done(List<Flow_type> list, BmobException e) {
                if (e == null) {
                    String type_id = list.get(0).getObjectId();
                    Flow_type flow_type = new Flow_type();
                    flow_type.setObjectId(type_id);
                    Day_flow day_flow1 = new Day_flow();
                    day_flow1.setObjectId(id);
                    day_flow1.setFlow_type(flow_type);
                    day_flow1.update(new UpdateListener() {  //更新day_flow表
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                            }else {
                                t.toast_short(root.getContext(),e.toString());
                            }
                        }
                    });

                }
            }
        });
    }
    public void updateDirection(final View root, final String id){  //更新一对多流水方向关联
        BmobQuery<Flow_direction> flow_directionBmobQuery = new BmobQuery<>();
        flow_directionBmobQuery.addWhereEqualTo("d_name",MyDirection);
        flow_directionBmobQuery.findObjects(new FindListener<Flow_direction>() {
            @Override
            public void done(List<Flow_direction> list, BmobException e) {
                if (e==null){
                    String direction_id = list.get(0).getObjectId();
                    Flow_direction flow_direction = new Flow_direction();
                    flow_direction.setObjectId(direction_id);
                    Day_flow day_flow = new Day_flow();
                    day_flow.setObjectId(id);
                    day_flow.setM_direction(flow_direction);
                    day_flow.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                t.toast_short(root.getContext(),e.toString());
                            }
                        }
                    });
                }
            }
        });
    }
    public void updateAccount(final View root,final String id){
        BmobQuery<Accunt_type>  accunt_typeBmobQuery = new BmobQuery<>();
        accunt_typeBmobQuery.addWhereEqualTo("account_name",MyAccount);
        accunt_typeBmobQuery.findObjects(new FindListener<Accunt_type>() {
            @Override
            public void done(List<Accunt_type> list, BmobException e) {
                if (e==null){
                    String account_id = list.get(0).getObjectId();
                    Accunt_type accunt_type = new Accunt_type();
                    accunt_type.setObjectId(account_id);
                    Day_flow day_flow = new Day_flow();
                    day_flow.setObjectId(id);
                    day_flow.setAccount_type(accunt_type);
                    day_flow.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){

                            }else {
                                t.toast_short(root.getContext(),e.toString());
                            }
                        }
                    });
                }
            }
        });
    }
    public void updateD_jieyu(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH)+1;
        final int day = calendar.get(Calendar.DATE);
        BmobQuery<d_jieyu> dbq1 = new BmobQuery<>();//添加查找条件
        dbq1.addWhereEqualTo("j_year",year);
        BmobQuery<d_jieyu> dbq2 = new BmobQuery<>();
        dbq2.addWhereEqualTo("j_month",month);
        BmobQuery<d_jieyu> dbq3 = new BmobQuery<>();
        dbq3.addWhereEqualTo("j_day",day);
        BmobQuery<d_jieyu> dbq4 = new BmobQuery<>();
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        dbq4.addWhereEqualTo("j_user",user);

        List<BmobQuery<d_jieyu>> andQuerys = new ArrayList<>();  //将所有and查询条件添加入队列
        andQuerys.add(dbq1);
        andQuerys.add(dbq2);
        andQuerys.add(dbq3);
        andQuerys.add(dbq4);

        BmobQuery<d_jieyu> Query = new BmobQuery<>();
        Query.and(andQuerys);
        Query.findObjects(new FindListener<d_jieyu>() {
            @Override
            public void done(List<d_jieyu> list, BmobException e) {
                if (e==null){
                    if (list.size()==0){
                        Log.d("My",String.valueOf(list.size()));
                        addJieyu(year,month,day);
                    }else {
                        updateJieyu(year,month,day,list);
                    }
                }
            }
        });
    }

    private void addJieyu(int year, int month, int day){ //当当天没有结余时，添加结余信息

        final d_jieyu jieyu = new d_jieyu();
        jieyu.setJ_year(year);
        jieyu.setJ_month(month);
        jieyu.setJ_day(day);
        if (MyDirection.equals("支出")){
            jieyu.setJ_money_out(MyMoney);
            jieyu.setJ_money_in(0.00);
        }else if (MyDirection.equals("收入")){
            jieyu.setJ_money_in(MyMoney);
            jieyu.setJ_money_out(0.00);
        }
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        jieyu.setJ_user(user);
        jieyu.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    if (MyDirection.equals("支出")){
                        String id = jieyu.getObjectId();
                        setOutSuggestion(id);
                        Log.d("My","ehuwof");
                    }else {
                        String id = jieyu.getObjectId();
                       setInSuggestion(id);
                    }
                }
            }
        });
    }
    public void updateJieyu(int year,int month,int day,List<d_jieyu> list){
        Double inMoney = list.get(0).getJ_money_in();
        Double outMoney = list.get(0).getJ_money_out();
        if (inMoney>outMoney){//当原支出小于收入时
            if (MyDirection.equals("支出")){
                outMoney = outMoney+MyMoney;
                updateOutMoney(outMoney,list.get(0).getObjectId()); //增加支出后需判断suggestion是否变动
                if (outMoney.equals(inMoney)){
                    Log.d("My","equal");
                    setEqual(list.get(0).getObjectId());
                }else if(outMoney>inMoney){
                    setOutSuggestion(list.get(0).getObjectId());
                }
            }else if (MyDirection.equals("收入")){  //增加收入后无需判断是否需要变动
                inMoney = inMoney + MyMoney;
                updateInMoney(inMoney,list.get(0).getObjectId());

            }
        }else if (inMoney.equals(outMoney)){  //当原支出与收入相等时
            if (MyDirection.equals("支出")){  //支出增加后，需判断suggestion是否变动
                outMoney = outMoney + MyMoney;
                updateOutMoney(outMoney,list.get(0).getObjectId());
                if (outMoney>inMoney){
                    setOutSuggestion(list.get(0).getObjectId());
                }
            }else if (MyDirection.equals("收入")){ //收入增加后，需判断suggestion是否变动
                inMoney = inMoney + MyMoney;
                updateInMoney(inMoney,list.get(0).getObjectId());
                if (inMoney>outMoney){
                    setInSuggestion(list.get(0).getObjectId());
                }
            }
        }else {
            if (MyDirection.equals("支出")){ //当原支出大于收入时，增加收入无需考虑
                outMoney = outMoney + MyMoney;
                updateOutMoney(outMoney,list.get(0).getObjectId());

            }else if (MyDirection.equals("收入")){ //当原支出大于收入，需判断现收入是否等于或者大于支出
                inMoney = inMoney + MyMoney;
                updateInMoney(inMoney,list.get(0).getObjectId());
                if (outMoney.equals(inMoney)){
                    Log.d("My","equal2");
                    setEqual(list.get(0).getObjectId());
                }else if(inMoney>outMoney){
                    setInSuggestion(list.get(0).getObjectId());
                }
            }
        }
    }

    public void updateInMoney(Double inMoney,String id){
        d_jieyu jieyu = new d_jieyu();
        jieyu.setJ_money_in(inMoney);
        jieyu.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                }else {
                    Log.d("My",e.toString());
                }
            }
        });
    }

    public void updateOutMoney(Double outMoney,String id){
        d_jieyu jieyu = new d_jieyu();
        jieyu.setJ_money_out(outMoney);
        jieyu.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                }else {
                    Log.d("My",e.toString());
                }
            }
        });
    }

    public void setInSuggestion( final String id){  //设置支出大于收入的建议
        final BmobQuery<suggestion> m_suggestion = new BmobQuery<>();
        m_suggestion.addWhereEqualTo("s_type",1); //查找数据库中suggestion表中的收入大于支出建议
        m_suggestion.findObjects(new FindListener<suggestion>() {
            @Override
            public void done(List<suggestion> list, BmobException e) {
                if (e==null){
                    String suggestionId = list.get(0).getObjectId();
                    suggestion mysuggestion = new suggestion();
                    mysuggestion.setObjectId(suggestionId);

                    d_jieyu myJieyu = new d_jieyu();
                    myJieyu.setObjectId(id);
                    myJieyu.setJ_suggestion(mysuggestion);
                    myJieyu.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                            }else {
                                Log.d("My",e.toString());
                            }
                        }
                    });
                }
            }
        });
    }

    public void setEqual( final String id){  //设置支出大于收入的建议
        final BmobQuery<suggestion> m_suggestion = new BmobQuery<>();
        m_suggestion.addWhereEqualTo("s_type",3); //查找数据库中suggestion表中的收入等于支出建议
        m_suggestion.findObjects(new FindListener<suggestion>() {
            @Override
            public void done(List<suggestion> list, BmobException e) {
                if (e==null){
                    String suggestionId = list.get(0).getObjectId();
                    suggestion mysuggestion = new suggestion();
                    mysuggestion.setObjectId(suggestionId);

                    d_jieyu myJieyu = new d_jieyu();
                    myJieyu.setObjectId(id);
                    myJieyu.setJ_suggestion(mysuggestion);
                    myJieyu.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                            }else {
                                Log.d("My",e.toString());
                            }
                        }
                    });
                }
            }
        });
    }
    public void setOutSuggestion(final String id){  //设置支出大于收入的建议
        final BmobQuery<suggestion> m_suggestion = new BmobQuery<>();
        m_suggestion.addWhereEqualTo("s_type",2); //查找数据库中suggestion表中的收入大于支出建议
        m_suggestion.findObjects(new FindListener<suggestion>() {
            @Override
            public void done(List<suggestion> list, BmobException e) {
                if (e==null){
                    String suggestionId = list.get(0).getObjectId();
                    suggestion mysuggestion = new suggestion();
                    mysuggestion.setObjectId(suggestionId);

                    d_jieyu myJieyu = new d_jieyu();
                    myJieyu.setObjectId(id);
                    myJieyu.setJ_suggestion(mysuggestion);
                    myJieyu.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                            }else {
                                Log.d("My",e.toString());
                            }
                        }
                    });
                }
            }
        });
    }

    public void updatep_remainder(){  //根据流水修改p_remainder
        BmobQuery<Accunt_type> queryAccount = new BmobQuery<>();
        queryAccount.addWhereEqualTo("account_name",MyAccount);
        queryAccount.findObjects(new FindListener<Accunt_type>() {
            @Override
            public void done(List<Accunt_type> list, BmobException e) {
                if (e==null){
                    final String accountId = list.get(0).getObjectId();  //获取输入账户的账户id
                    MyUser user = BmobUser.getCurrentUser(MyUser.class);
                    BmobQuery<p_remainder> bpr1 = new BmobQuery<>();  //添加查询当前用户账户余额
                    bpr1.addWhereEqualTo("p_name",user);

                    BmobQuery<p_remainder> bpr2 = new BmobQuery<>(); //添加查询当前账户条件
                    Accunt_type accunt_type = new Accunt_type();
                    accunt_type.setObjectId(accountId);
                    bpr2.addWhereEqualTo("p_account",accunt_type);

                    List<BmobQuery<p_remainder>> bmobQueryList = new ArrayList<>();
                    bmobQueryList.add(bpr1);
                    bmobQueryList.add(bpr2);

                    BmobQuery<p_remainder>  bmobQuery = new BmobQuery<>();
                    bmobQuery.and(bmobQueryList);
                    bmobQuery.findObjects(new FindListener<p_remainder>() {
                        @Override
                        public void done(List<p_remainder> list, BmobException e) {
                            if (list.size()==0){
                                addP_Remainder(accountId);
                            }else {
                                updateRemainder(list);
                            }
                        }
                    });

                }
            }
        });

    }

    public void addP_Remainder(String accountId){
        Accunt_type accunt_type = new Accunt_type();
        accunt_type.setObjectId(accountId);
        MyUser user = BmobUser.getCurrentUser(MyUser.class);

        p_remainder pRemainder = new p_remainder();
        pRemainder.setP_name(user);
        pRemainder.setP_account(accunt_type);
        if (MyDirection.equals("支出")){
            pRemainder.setP_money(-MyMoney);
        }else {
            pRemainder.setP_money(MyMoney);
        }
        pRemainder.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){

                }else {
                    Log.d("My",e.toString());
                }
            }
        });
    }
    public void updateRemainder(List<p_remainder> list){ //更新p_remainder表中的money信息
        p_remainder pRemainder = new p_remainder();
        Double money = list.get(0).getP_money();
        if (MyDirection.equals("支出")){
            money = money - MyMoney;
        }else {
            money = money + MyMoney;
        }
        pRemainder.setP_money(money);
        pRemainder.update(list.get(0).getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    Log.d("My","修改余额成功");
                }
            }
        });

    }

    public void updatef_remainder(final family_name family){  //根据流水更新家庭账户表中的信息
        final BmobQuery<Accunt_type> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("account_name",MyAccount);
        bmobQuery.findObjects(new FindListener<Accunt_type>() {
            @Override
            public void done(List<Accunt_type> list, BmobException e) {
                final String account_id = list.get(0).getObjectId();
                final Accunt_type accunt_type = new Accunt_type();
                accunt_type.setObjectId(account_id);
                BmobQuery<f_remainder> bmobQuery1 = new BmobQuery<>();
                bmobQuery1.addWhereEqualTo("account",accunt_type); //添加账户查询信息

                BmobQuery<f_remainder> bmobQuery2 = new BmobQuery<>();
                bmobQuery2.addWhereEqualTo("f_name",family);

                List<BmobQuery<f_remainder>> bmobQueryList = new ArrayList<>();
                bmobQueryList.add(bmobQuery1);
                bmobQueryList.add(bmobQuery2);

                BmobQuery<f_remainder> query = new BmobQuery<>();
                query.and(bmobQueryList);
                query.findObjects(new FindListener<f_remainder>() {
                    @Override
                    public void done(List<f_remainder> list, BmobException e) {
                        if (list.size()==0){
                            addfremainder(family,accunt_type);
                        }else {
                            updatefRemainder(list);
                        }
                    }
                });
            }
        });
    }

    public void addfremainder(family_name family,Accunt_type accunt_type){
        f_remainder fRemainder = new f_remainder();
        fRemainder.setF_name(family);
        fRemainder.setAccount(accunt_type);
        if (MyDirection.equals("支出")){
            fRemainder.setF_money(-MyMoney);
        }else {
            fRemainder.setF_money(MyMoney);
        }
        fRemainder.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    Log.d("My","添加账户成功");
                }
            }
        });

    }
    public void updatefRemainder(List<f_remainder> list){  //更新数据库中已存在的家庭账户余额
        f_remainder fRemainder = new f_remainder();
        Double money = list.get(0).getF_money();
        if (MyDirection.equals("支出")){
            money = money - MyMoney;
        }else {
            money = money + MyMoney;
        }
        fRemainder.setF_money(money);
        fRemainder.update(list.get(0).getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    Log.d("My","修改家庭余额成功");
                }
            }
        });
    }

}
