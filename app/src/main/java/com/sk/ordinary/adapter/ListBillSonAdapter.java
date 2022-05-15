package com.sk.ordinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sk.ordinary.R;
import com.sk.ordinary.entity.BillRecord;
import com.sk.ordinary.entity.ListBill;
import com.sk.ordinary.utils.TimeUtils;

import java.util.List;

/**
 * 内部的单项的子适配器
 */
public class ListBillSonAdapter extends BaseAdapter {
    Context context;
    List<ListBill> sonDatas;
    LayoutInflater layoutInflater;
    int year, month, day;
    @Override
    public int getCount() {
        return sonDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return sonDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SonViewHolder holder;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.item_single_item, viewGroup, false);
            holder = new SonViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (SonViewHolder) view.getTag();
        }

        holder.cateName.setText(sonDatas.get(i).getCateName());
        holder.remark.setText(sonDatas.get(i).getRemark());
        //根据判断类型来识别支出 还是收入
        if(sonDatas.get(i).getKind() == 0) {
            holder.money.setTextColor(context.getResources().getColor(R.color.out));
            holder.money.setText("-"+sonDatas.get(i).getMoney());
            holder.inOut.setImageResource(R.drawable.ic_out);
        }else{
            holder.money.setTextColor(context.getResources().getColor(R.color.in));
            holder.money.setText("+"+sonDatas.get(i).getMoney());
            holder.inOut.setImageResource(R.drawable.ic_in);
        }
        holder.billTime.setText(sonDatas.get(i).getTimes().substring(11, sonDatas.get(i).getTimes().length()));
        holder.pay.setText(sonDatas.get(i).getPay());



        return view;

    }

    /**
     * 子listview的适配器的构造函数
     * @param context
     * @param sonDatas
     */
   public ListBillSonAdapter(Context context, List<ListBill> sonDatas) {
        this.context = context;
        this.sonDatas = sonDatas;
        layoutInflater = LayoutInflater.from(context);
        year = TimeUtils.year();
        month = TimeUtils.month();
        day = TimeUtils.day();
   }


}

class SonViewHolder{
    TextView cateName, remark, money, pay, billTime;
    ImageView inOut;
    public SonViewHolder(View view) {
        cateName = view.findViewById(R.id.item_cate);
        remark = view.findViewById(R.id.item_remark);
        money = view.findViewById(R.id.item_money);
        pay = view.findViewById(R.id.item_pay);
        inOut = view.findViewById(R.id.in_out);
        billTime = view.findViewById(R.id.bill_time);
    }
}
