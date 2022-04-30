package com.sk.ordinary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sk.ordinary.adapter.AccountAdapter;
import com.sk.ordinary.entity.AccountBean;
import com.sk.ordinary.db.DBManager;
import com.sk.ordinary.utils.AutoBillUtils;
import com.sk.ordinary.utils.BottomArea;
import com.sk.ordinary.utils.BudgetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * 主活动界面文件类
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ListView todayLv; //展示今日收支情况的listview
    //声明数据源
    List<AccountBean> mDatas;
    int year, month, day;
    AccountAdapter accountAdapter;
    Button editBtn;
    ImageButton moreBtn;
    ImageView searchIv;
    boolean isShow = true;
    View headerView;
    TextView topOutV, topInTv, topbudgetTv, topConTv;
    ImageView topShowIv;
    MainActivity mContext;

    SharedPreferences sharedPreferences;

    /**
     * 重写OnCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //对自动记账发送的数据进行处理
        AutoBillUtils.parseParams(getIntent().getData());
        initTime();
        initView();
        //初始化
        sharedPreferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
        addHeaderView();
        mDatas = new ArrayList<>();
        // 设置适配器
        accountAdapter = new AccountAdapter(this, mDatas);
        todayLv.setAdapter(accountAdapter);
    }


    /**
     * 添加头布局
     */
    private void addHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.item_main_top, null);
        todayLv.addHeaderView(headerView);
        topOutV = headerView.findViewById(R.id.item_mainlv_top_tv_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_today);
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_lv_hide);
        mContext = MainActivity.this;

        topShowIv.setOnClickListener(this);

        // 给弹出的dialog修改样式
        topbudgetTv.setOnClickListener(this);

    }
    /**
     * 初始化布局
     */
    private void initView() {
        todayLv = findViewById(R.id.main_lv);
        editBtn = findViewById(R.id.main_btn_edit);
        moreBtn = findViewById(R.id.main_button_more);
        searchIv = findViewById(R.id.main_iv_search);

        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchIv.setOnClickListener(this);
    }

    /**
     * 初始化时间
     */
    public void initTime() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取焦点时，执行的方法，在发生变动后，里面的方面也会同步执行
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadDBData();
        //设置顶部布局header得变化得显示
        setTopTvShow();
    }
    /**
     * 设置顶部显示得布局方法  文本内容得显示
     */
    private void setTopTvShow() {
        //获取今日总得支出和收入  并显示view中
        float income_today = DBManager.getTotalDayMoney(year, month, day, 1);
        float outcome_today = DBManager.getTotalDayMoney(year, month, day, 0);
        String infotodaDay = "今日收入：" + "<span style='color:#00c4cc'>" +income_today +"</span>"+ " 今日支出：" +"<span style='color:#e04646'>"+outcome_today+"</span>";
        topConTv.setText(Html.fromHtml(infotodaDay));

        //获取本月
        float income_month = DBManager.getTotalMonthMoney(year, month, 1);
        float outcome_month = DBManager.getTotalMonthMoney(year, month, 0);
        //给收入和支出设置不同的显示颜色
        topInTv.setText(Html.fromHtml("<span style='color:#00c4cc'>"+income_month+"</span>"));
        topOutV.setText(Html.fromHtml("<span style='color:#e04646'>"+outcome_month+"</span>"));

        //    设置显示运算剩余
        float bmoney = sharedPreferences.getFloat("bmoney", 0);//预算
        if (bmoney == 0) {
            topbudgetTv.setText("￥ 0");
        }else{
            float syMoney = bmoney-outcome_month;
            topbudgetTv.setText("￥"+syMoney);
        }
    }
    /**
     * 记载数据
     */
    public  void loadDBData() {
        List<AccountBean> listByDate = DBManager.getListByDate(year, month, day);
        if(mDatas!=null){
            mDatas.clear();
            mDatas.addAll(listByDate);
            accountAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 点击得监听方法
     * @param view
     */
    @Override
    public void onClick(View view) {
        //判断谁被点击了
        switch (view.getId()) {
            case R.id.main_iv_search:
                showDialogTest();
                break;
            case R.id.main_btn_edit:
                Intent intent = new Intent(this, RecordActivity.class);  //连接四大组件的   跳转界面
                startActivity(intent);

                break;
            case R.id.main_button_more:

                break;
            case R.id.item_mainlv_top_tv_budget:
                //点击后设置预算
                budgetShow();
                break;
            case R.id.item_mainlv_top_lv_hide:
                //点击后 切换明文和秘文
                toggleShow();
                break;
        }
    }

    /**
     * 测试卡片弹出框
     */
    private void showDialogTest() {
        BottomArea.list(this, "请进行操作", Arrays.asList("记账", "删除", "复制"), new BottomArea.ListCallback() {
            @Override
            public void onSelect(int position) {
                switch (position) {
                    case 0:
                        Log.i("0", "记账");

                        //goBillApp(billInfo, bundle.getInt("id"));
                        break;
                    case 1:
                        Log.i("1", "删除");
                        //del(bundle);
                        break;
                    case 2:
                        Log.i("2", "复制");
                        //copy(bundle);
                        break;
                }
            }
        });
    }

    /**
     * 显示预算
     */
    private void budgetShow() {
        BudgetDialog budgetDialog = new BudgetDialog(mContext, R.style.Theme_Light_Dialog);
        budgetDialog.show();
        //给回调函数设置值
        budgetDialog.setOnEnsureListener(new BudgetDialog.OnEnsureListener() {
            @Override
            public void onEnsure(float money) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("bmoney", money);
                editor.commit();
                // 计算剩余金额
                float outcomeMoney = DBManager.getTotalMonthMoney(year, month, 0);
                float sy = money - outcomeMoney;
                topbudgetTv.setText("¥ "+sy);
            }
        });

    }
    /**
     * 点击隐藏按钮  切换显示与隐藏
     */
    private void toggleShow() {
        if(isShow) {
            PasswordTransformationMethod instance = PasswordTransformationMethod.getInstance();
            topInTv.setTransformationMethod(instance);  //设置隐藏
            topOutV.setTransformationMethod(instance);  //
            topbudgetTv.setTransformationMethod(instance);
            topShowIv.setImageResource(R.mipmap.ih_hide);
        }else {
            HideReturnsTransformationMethod instance = HideReturnsTransformationMethod.getInstance();

            topInTv.setTransformationMethod(instance);  //设置隐藏
            topOutV.setTransformationMethod(instance);  //
            topbudgetTv.setTransformationMethod(instance);
            topShowIv.setImageResource(R.mipmap.ih_show);
        }
        isShow = !isShow;
    }

}