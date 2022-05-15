package com.sk.ordinary.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.color.DynamicColors;
import com.sk.ordinary.utils.ThemeUtils;

import rikka.material.app.MaterialActivity;

/**
 * 基础的活动页面
 */
public class BaseActivity extends MaterialActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否跟随系统主题
        if (ThemeUtils.isSystemAccent()) {
            DynamicColors.applyToActivityIfAvailable(this);
        }
    }

    /**
     * 获取主题资源
     * @param theme
     * @param isDecorView
     */
    @Override
    public void onApplyUserThemeResource(@NonNull Resources.Theme theme, boolean isDecorView) {
        if(!ThemeUtils.isSystemAccent()) {
            theme.applyStyle(ThemeUtils.getColorThemeStyleRes(), true);
        }
        theme.applyStyle(ThemeUtils.getNightThemeStyleRes(this), true);
        theme.applyStyle(rikka.material.preference.R.style.ThemeOverlay_Rikka_Material3_Preference, true);
    }

    /**
     * 获取用户的显示模式和主题颜色的组合关键字
     * @return
     */
    @Nullable
    @Override
    public String computeUserThemeKey() {
        return ThemeUtils.getColorTheme() + ThemeUtils.getNightTheme(this);
    }

    /**
     * 设置bar和navi的颜色
     */
    @Override
    public void onApplyTranslucentSystemBars() {
        super.onApplyTranslucentSystemBars();
        Window window = getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
    }
}
