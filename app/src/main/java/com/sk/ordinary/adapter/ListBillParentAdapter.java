package com.sk.ordinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sk.ordinary.R;
import com.sk.ordinary.entity.ListBill;
import com.sk.ordinary.entity.ParenBilltRecord;
import com.sk.ordinary.utils.ListViewUtils;

import java.util.List;

public class ListBillParentAdapter extends ArrayAdapter<ParenBilltRecord> {
    Context context;
    int res;
    List<ParenBilltRecord> parenBilltRecordList;
    public static int mParentItem = -1;
    List<ListBill> listBillList;

    ListView listView;

    @Override
    public int getCount() {
        return parenBilltRecordList.size();
    }

    @Nullable
    @Override
    public ParenBilltRecord getItem(int position) {
        return parenBilltRecordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 构造函数
     * @param context
     * @param resource
     * @param parenBilltRecordList
     */
    public ListBillParentAdapter(@NonNull Context context, int resource, List<ParenBilltRecord> parenBilltRecordList) {
        super(context, resource, parenBilltRecordList);
        this.context = context;
        this.res = resource;
        this.parenBilltRecordList = parenBilltRecordList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        ParenBilltRecord parenBilltRecord = getItem(position);
        ParentViewHolder holder;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(res, parent, false);
            holder = new ParentViewHolder(view);

            view.setTag(holder);
        }else {
            holder = (ParentViewHolder) view.getTag();
        }

        holder.dates.setText(parenBilltRecord.getDates());
        holder.week.setText(parenBilltRecord.getWeek());
        holder.income.setText("收:"+parenBilltRecord.getIncome());
        holder.outcome.setText("支:"+parenBilltRecord.getOutcome());


        //存储父亲listview的位置
        //if(mParentItem == position) {
            listBillList = parenBilltRecordList.get(position).getListBillList();
            // 实现子listview
            ListBillSonAdapter listBillSonAdapter = new ListBillSonAdapter(context, listBillList);
            holder.childList.setAdapter(listBillSonAdapter);
        ListViewUtils.setListViewHeightBasedOnChildren(holder.childList);
       // }
        return view;
    }

    /**
     * 父类的Handler处理类
     */

}

class ParentViewHolder{
    TextView dates, week, income, outcome;
    ListView childList;

    public ParentViewHolder(View view){
        dates = view.findViewById(R.id.card_date);
        week = view.findViewById(R.id.card_week);
        income = view.findViewById(R.id.day_in_money);
        outcome = view.findViewById(R.id.day_out_money);
        childList = view.findViewById(R.id.card_listview);
    }
}
