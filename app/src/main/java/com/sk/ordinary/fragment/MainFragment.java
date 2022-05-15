package com.sk.ordinary.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sk.ordinary.R;
import com.sk.ordinary.activity.RecordActivity;
import com.sk.ordinary.adapter.ListBillParentAdapter;
import com.sk.ordinary.db.DBManager;
import com.sk.ordinary.dialog.BottomAreaDialog;
import com.sk.ordinary.entity.BillRecord;
import com.sk.ordinary.entity.ParenBilltRecord;
import com.sk.ordinary.utils.AutoBillUtils;
import com.sk.ordinary.utils.DataDealUtils;
import com.sk.ordinary.utils.TimeUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener{

    private ImageView imageView;
    ImageButton imageButton;
    int year, month, day;
    List<ParenBilltRecord> pDatas;
    ListBillParentAdapter listBillParentAdapter;
    ListView listBill;

    //头部信息的声明
    TextView monthOut, monthIn, monthBalance, monthBudget;
    View headerView;
    ImageView imageViewHide;

    boolean isShow = true;
    //存储预算
    SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initView(view);

        initTime();
        sharedPreferences = getActivity().getSharedPreferences("budget", Context.MODE_PRIVATE);

        // 添加头部信息
        addHeader();
        AutoBillUtils.parseParams(getActivity().getIntent().getData());

        pDatas = new ArrayList<>();
        listBillParentAdapter = new ListBillParentAdapter(getContext(), R.layout.item_list_bill, pDatas);
        listBill.setAdapter(listBillParentAdapter);
        return view;

    }

    /**
     * 添加主页的头部信息
     */
    private void addHeader() {
        headerView = getLayoutInflater().inflate(R.layout.main_top_header, null);
        listBill.addHeaderView(headerView);

        monthOut = headerView.findViewById(R.id.month_out_money_tv);
        monthIn = headerView.findViewById(R.id.month_in_money_tv);
        monthBalance = headerView.findViewById(R.id.month_balance_money_tv);
        monthBudget = headerView.findViewById(R.id.budget_remain_money_tv);
        imageViewHide = headerView.findViewById(R.id.main_show_iv);

        //给隐藏按钮添加点击事件
        imageViewHide.setOnClickListener(this);
        monthBudget.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDBData();

        //给头部的header添加数据
        loadHeaderData();
    }

    /**
     * 记载头部的数据
     */
    private void loadHeaderData() {
        float income_month = DBManager.getTotalMonthMoney(year, month, 1);
        float outcome_month = DBManager.getTotalMonthMoney(year, month, 0);
        float balance_month = income_month - outcome_month;
        String income_month_str = "￥:"+"<span style='color:#00c4cc'>"+income_month+"</span>";
        String oucome_month_str = "￥:"+"<span style='color:#e04646'>"+outcome_month+"</span>";
        String balance_month_str = "￥:"+balance_month;
        monthIn.setText(Html.fromHtml(income_month_str));
        monthOut.setText(Html.fromHtml(oucome_month_str));
        monthBalance.setText(balance_month_str);

        // 预算剩余

        float bmoney = sharedPreferences.getFloat("bmoney", 0);
        if( bmoney == 0) {
            monthBudget.setText("￥:0");
        }else{
            float syMoney = bmoney - outcome_month;
            monthBudget.setText("￥:"+syMoney);
        }
    }

    /**
     * 记载数据库的数据
     */
    private void loadDBData() {
        List<ParenBilltRecord> currentMonthBill = DataDealUtils.getCurrentMonthBill(year, month, day);

        if(pDatas != null) {
            pDatas.clear();
            pDatas.addAll(currentMonthBill);
            listBillParentAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取时间
     */
    private void initTime() {
        year = TimeUtils.year();
        month = TimeUtils.month();
        day = TimeUtils.day();
    }

    /**
     * 初始化页面控件
     * @param view
     */
    private void initView(View view) {
        imageButton = view.findViewById(R.id.add_bill_ib);
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true);
        imageButton.setColorFilter(typedValue.data);
        imageButton.setOnClickListener(this);

        listBill = view.findViewById(R.id.main_lv_bill_page);
    }


    /**
     * 点击事件的监听
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_bill_ib:

                startActivity(new Intent(getContext(), RecordActivity.class));
                break;
            case R.id.main_show_iv:
                // 切换明文和密文
                toggleShow();
                break;
            case R.id.budget_remain_money_tv:
                budgetShow();
                break;
        }
    }

    /**
     * 设置与显示预算
     */
    private void budgetShow() {

        BottomAreaDialog.input(getContext(), "设置预算", null, "确定", "取消", new BottomAreaDialog.InputCallback() {
            @Override
            public void input(String data) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("bmoney", Float.parseFloat(data));
                editor.commit();
                float budgets = Float.parseFloat(data);
                //计算剩余金额
                monthBudget.setText("￥:"+(budgets-DBManager.getTotalMonthMoney(year, month, 0)));
            }

            @Override
            public void cancel() {

            }
        });
    }

    /**
     * 显示显示和隐藏的
     */
    private void toggleShow() {
        if(isShow) {
            PasswordTransformationMethod instance = PasswordTransformationMethod.getInstance();
            monthIn.setTransformationMethod(instance);
            monthOut.setTransformationMethod(instance);
            monthBalance.setTransformationMethod(instance);
            monthBudget.setTransformationMethod(instance);
        }else {
            HideReturnsTransformationMethod instance = HideReturnsTransformationMethod.getInstance();
            monthIn.setTransformationMethod(instance);
            monthOut.setTransformationMethod(instance);
            monthBalance.setTransformationMethod(instance);
            monthBudget.setTransformationMethod(instance);
        }

        isShow = !isShow;
    }
}