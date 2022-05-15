package com.sk.ordinary.fragment;

import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sk.ordinary.R;
import com.sk.ordinary.adapter.GridAdapter;
import com.sk.ordinary.db.DBManager;
import com.sk.ordinary.dialog.SelectDateDialog;
import com.sk.ordinary.entity.BillRecord;
import com.sk.ordinary.entity.Cate;
import com.sk.ordinary.utils.KeyBoardUtils;
import com.sk.ordinary.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 支出和收入的公共fragment类
 */
public abstract class BaseRecordFragment extends BaseFragment implements View.OnClickListener{
    ImageView topIv;
    EditText topEt;
    TextView topTv;
    KeyboardView keyboardView;
    TextView timeTv;
    EditText remarkEt;
    GridView gridView;
    KeyBoardUtils keyBoardUtils;

    List<Cate> cateList;
    GridAdapter gridAdapter;
    BillRecord billRecord;
    /**
     * 创建对象
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        billRecord = new BillRecord();
        billRecord.setCateName("其他");
        billRecord.setCateId1(10);
        billRecord.setCateId2(2);
    }

    /**
     * 视图的初始化
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outcome, container, false);

        initView(view);
        //给gridview填充数据
        initTime();
        loadGridData();
        setGVListener();
        return view;
    }

    /**
     * 初始化时间
     */
    public void initTime(){
        String times = TimeUtils.getSdfTime();
        Log.i("tag------------------------", times);
        timeTv.setText(times);
        billRecord.setTime(times);

        billRecord.setYear(TimeUtils.year());
       billRecord.setMonth(TimeUtils.month());
        billRecord.setDay(TimeUtils.day());
    };

    /**
     * 加载数据
     */
    public void loadGridData() {
    }

    public void setGVListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gridAdapter.selectPos = position;
                gridAdapter.notifyDataSetInvalidated();  //提示绘制发生变化了
                Cate cate = cateList.get(position);
                String typename = cate.getCateName();
                topTv.setText(typename);
                //accountBean.setTypename(typename);
                int simageId = cate.getImageId();
                topIv.setImageResource(simageId);

                billRecord.setCateId1(cate.getCateId1());
                billRecord.setCateId2(cate.getCateId2());
                billRecord.setCateName(typename);

            }
        });
    }


    /**
     * 初始化控件
     * @param view
     */
    public void initView(View view) {
        keyboardView = view.findViewById(R.id.frag_keyboard);
        topEt = view.findViewById(R.id.frag_top_et);
        topIv = view.findViewById(R.id.frag_top_iv);
        topTv = view.findViewById(R.id.frag_top_tv);

        timeTv = view.findViewById(R.id.frag_record_time_tv);
        remarkEt = view.findViewById(R.id.frag_record_remark_et);

        gridView = view.findViewById(R.id.frag_record_gv);
        //给备注和时间设置时间监听
       timeTv.setOnClickListener(this);

        //让自定义键盘显示
        keyBoardUtils = new KeyBoardUtils(keyboardView, topEt);
        keyBoardUtils.showKeyboard();
        //设置接口监听
        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //点击了确定按钮
                String str = topEt.getText().toString();
                if("".equals(str) || str.length() == 0 || "0".equals(str)) {
                    getActivity().finish();
                    return;
                }
                float money  = Float.parseFloat(str.toString());
                billRecord.setMoney(money);

                billRecord.setRemark(remarkEt.getText().toString());
                billRecord.setPay("微信");
                billRecord.setAccount("零钱");

                //然后让子类重写以下方法
                saveBillToDB();
            }
        });
    }

    /**
     * 让子类重写该方法进行传值
     */
    public abstract void saveBillToDB();

    /**
     * 时间点击事件的监听
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_record_remark_et:

                break;
            case R.id.frag_record_time_tv:

                showDateDialog();
                break;
        }
    }

    /**
     * 显示弹出事件修改的界面
     */
    public void showDateDialog() {
        SelectDateDialog selectDateDialog = new SelectDateDialog(getContext());
        selectDateDialog.show();
        selectDateDialog.setOnEnsureListener(new SelectDateDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);

                billRecord.setTime(time);
                billRecord.setYear(year);
                billRecord.setMonth(month);
                billRecord.setDay(day);
            }
        });
    }
}
