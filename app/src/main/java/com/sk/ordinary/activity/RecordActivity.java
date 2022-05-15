package com.sk.ordinary.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sk.ordinary.R;
import com.sk.ordinary.adapter.RecordPageAdapter;
import com.sk.ordinary.fragment.IncomeFragment;
import com.sk.ordinary.fragment.OutcomeFragment;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends BaseActivity{
    TabLayout tabLayout;
    ViewPager viewPager;

    /**
     * 创建布局
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode
        (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|

                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_record);
        setContentView(R.layout.activity_record);

//        View viewById = findViewById(R.id.tab_header);
//        String theme_color = App.getPreferences().getString("theme_color", "COLOR_BLUE");
//        System.out.println("------------"+theme_color);
//
//        //viewById.setBackgroundColor(ColorUtils.getCurrentColor(theme_color));

        //查找控件
        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);
        // 2.设置Viewpage记载的页面
        initPager();
    }

    /**
     * 初始化收入与支出的页面
     */
    private void initPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        OutcomeFragment outcomeFragment = new OutcomeFragment();
        IncomeFragment incomeFragment = new IncomeFragment();
        fragmentList.add(outcomeFragment);
        fragmentList.add(incomeFragment);

        //创建适配器
        RecordPageAdapter recordPageAdapter = new RecordPageAdapter(getSupportFragmentManager(), fragmentList);
        //设置适配器对象
        viewPager.setAdapter(recordPageAdapter);
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
