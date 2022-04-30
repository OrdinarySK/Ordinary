package com.sk.ordinary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;
import com.sk.ordinary.adapter.RecordPageAdapter;
import com.sk.ordinary.frag_record.IncomeFragment;
import com.sk.ordinary.frag_record.OutcomeFragment;
import com.sk.ordinary.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    /**
     * 创建布局的方法
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode

                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|

                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_record);

        //查找控件
        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);

        // 2.设置ViewPage加载的页面
        initPager();
    }

    /**
     * 初始化收入与支出页面
     */
    public void initPager() {
        //初始化ViewPage的页面的集合
        List<Fragment> fragmentList = new ArrayList<>();
        //创建收入和支出的页面，放置在Fragment上
        OutcomeFragment outcomeFragment = new OutcomeFragment(); //支出页面
        IncomeFragment incomeFragment = new IncomeFragment();  //收入页面
        fragmentList.add(outcomeFragment);
        fragmentList.add(incomeFragment);
        //创建适配器
        RecordPageAdapter recordPageAdapter = new RecordPageAdapter(getSupportFragmentManager(),fragmentList);

        //设置适配器对象

        viewPager.setAdapter(recordPageAdapter);
        //将Tabyout与viewPage关联
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 关闭按钮的点击事件
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_iv_back:
                finish();
                break;

        }
    }
}