package com.sk.ordinary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sk.ordinary.R;
import com.sk.ordinary.activity.BaseActivity;
import com.sk.ordinary.entity.Cate;

import java.util.List;

/**
 * 支出和收入的界面的GridView的适配器
 */
public class GridAdapter extends BaseAdapter {

    public int selectPos = 0;
    Context context;
    List<Cate> datas;
    String type;

    public GridAdapter(Context context, List<Cate> datas, String type) {
        this.context = context;
        this.datas = datas;
        this.type = type;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_type, viewGroup, false);

        ImageView imageView = view.findViewById(R.id.item_type_iv);
        TextView textView = view.findViewById(R.id.item_type_tv);

        //获取指定位置的数据源  判断是否被选中
        Cate cate = datas.get(i);
        textView.setText(cate.getCateName());
        imageView.setImageResource(cate.getImageId());

//            //选中  那么需要改变颜色
//            imageView.setColorFilter(R.color.red);

/*        Drawable drawable = view.getResources().getDrawable(R.drawable.ic_qita);
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor("#30A069"),
                PorterDuff.Mode.SRC_ATOP);
        drawable.setColorFilter(porterDuffColorFilter);
        imageView.setImageDrawable(drawable);*/
        if( "outcome".equals(type)) {
            int checkColor = context.getResources().getColor(R.color.red_ff4f81);
            imageView.setColorFilter(selectPos==i? checkColor :Color.TRANSPARENT);
            //imageView.setColorFilter(selectPos==i? Color.RED :Color.TRANSPARENT);
        }else if("income".equals(type)) {
            int checkColor = context.getResources().getColor(R.color.edit_text);
            imageView.setColorFilter(selectPos==i? checkColor :Color.TRANSPARENT);
        }
        return view;
    }
}
