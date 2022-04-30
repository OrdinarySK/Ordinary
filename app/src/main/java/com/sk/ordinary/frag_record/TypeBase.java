package com.sk.ordinary.frag_record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sk.ordinary.R;
import com.sk.ordinary.entity.TypeBean;

import java.util.List;

public class TypeBase extends BaseAdapter {


    Context context;
    List<TypeBean> mDatas;
    int selectPos = 0;

    public TypeBase(Context context, List<TypeBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
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
    //此适配器不考虑服用问题，所有的id都显示在界面上，不会因为滑动而消失，所没有多余的convertView
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv, viewGroup, false);
        // 查找布局当中的控件
        ImageView iv = view.findViewById(R.id.item_recordfrag_iv);
        TextView tv = view.findViewById(R.id.item_recordfrag_tv_desc);

        //获取指定位置的数据源  判断是否被选中
        TypeBean typeBean = mDatas.get(i);
        tv.setText(typeBean.getTypeName());
        //根据是否被选中的位置来设置不同颜色的icon
        if(selectPos == i) {
            iv.setImageResource(typeBean.getSImageId());
        }else{
            iv.setImageResource(typeBean.getImageId());
        }
        return view;
    }
}
