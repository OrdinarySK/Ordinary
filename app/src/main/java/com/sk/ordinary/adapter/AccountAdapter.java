package com.sk.ordinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sk.ordinary.R;
import com.sk.ordinary.entity.AccountBean;

import java.util.Calendar;
import java.util.List;

/**
 * 记账记录的适配器
 */
public class AccountAdapter extends BaseAdapter {
    Context context;
    List<AccountBean> mDatas;
    LayoutInflater layoutInflater;

    int year, month, day;

    /**
     * 通过构造方法进行传递
     * @param context
     * @param mDatas
     */
    public AccountAdapter(Context context, List<AccountBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(context);
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        day = cal.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.item_mainlv, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        AccountBean bean = mDatas.get(i);
        holder.typeIv.setImageResource(bean.getsImageId());
        holder.typeTv.setText(bean.getTypeName());

        holder.descTv.setText(bean.getDescs());
        if(bean.getKind() == 0) {
            holder.moneyTv.setTextColor(context.getResources().getColor(R.color.out));
            //holder.moneyTv.setTextColor(Color.rgb(0, 255, 0));
            holder.moneyTv.setText("-"+bean.getMoney());
        }else{
            holder.moneyTv.setTextColor(context.getResources().getColor(R.color.in));
            //holder.moneyTv.setTextColor(Color.rgb(255, 0, 0));
            holder.moneyTv.setText("+"+bean.getMoney());
        }

        holder.timeTv.setText(bean.getTime());
        //显示格式
        if (bean.getYear() == year && bean.getMonth() == month && bean.getDay() == day) {
            String s = bean.getTime().split(" ")[1];
            holder.timeTv.setText("今天 "+s);
        }else{
            holder.timeTv.setText(bean.getTime());
        }
        return view;
    }
}

/**
 * 用户装载所有控件对象的类
 */
class ViewHolder {
    ImageView typeIv;
    TextView typeTv, descTv, timeTv, moneyTv;

    public ViewHolder(View view) {
        typeIv = view.findViewById(R.id.item_mainlv_iv);
        typeTv = view.findViewById(R.id.item_mainlv_tv_title);
        timeTv = view.findViewById(R.id.item_mainlv_tv_time);
        descTv = view.findViewById(R.id.item_mainlv_tv_desc);
        moneyTv = view.findViewById(R.id.item_mainlv_tv_money);
    }
}
