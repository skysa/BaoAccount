package com.baoaccount;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.baoaccount.db.Day_flow;
import com.baoaccount.db.Flow_type;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FlowAdapter extends RecyclerView.Adapter<FlowAdapter.ViewHolder> {

    private Context context;

    private List<Flow> mFlowList;

    public FlowAdapter(Context context, ArrayList<Flow> flowArrayList){
        this.context = context;
        mFlowList = flowArrayList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.fragment_account_item,parent,false);  //如果写成view = View.inflate(mContext, R.layout.item,null); 会导致item不能满屏

        return new ViewHolder(itemView);
    }



    //自定义ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView type;
        private TextView account;
        private TextView money;

        public ViewHolder(View itemView){
            super(itemView);
            type = (TextView)itemView.findViewById(R.id.account_type);
            account = (TextView)itemView.findViewById(R.id.account_used);
            money = (TextView) itemView.findViewById(R.id.account_money);

            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v,mFlowList.get(getLayoutPosition()));
                    }
                }
            });
        }

    }

    /*
      设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /*
          接口中的点击每一项的实现方法，参数自己定义

          @param view 点击的item的视图
          @param data 点击的item的数据
         */
        public void OnItemClick(View view, Flow data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Flow flow = mFlowList.get(position);
        holder.type.setText(flow.getFlow_type());
        holder.account.setText(flow.getFlow_account());
        holder.money.setText(flow.getFlow_money());


    }

    @Override
    public int getItemCount(){
        return mFlowList.size();

    }
}
