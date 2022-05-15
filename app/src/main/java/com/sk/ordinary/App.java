package com.sk.ordinary;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Process;

import androidx.preference.PreferenceManager;

import com.sk.ordinary.db.DBManager;
import com.sk.ordinary.utils.ThemeUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rikka.material.app.DayNightDelegate;
import rikka.material.app.LocaleDelegate;

/**
 * 全局声明操作类
 */

public class App extends Application {
    private static App instance = null;
    private SharedPreferences pref;

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());
        instance = this;

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        // 获取设置主题
        DayNightDelegate.setApplicationContext(this);
        DayNightDelegate.setDefaultNightMode(ThemeUtils.getDarkTheme());
    }

    /**
     * 获取APP实例
     * @return
     */
    public static App getInstance() {
        return instance;
    }

    /**
     * 获取默认的主题
     */
    public static SharedPreferences getPreferences() {
        return instance.pref;
    }

    /**
     * 判断
     * @return
     */
    public static boolean isParasitic() {
        return !Process.isApplicationUid(Process.myUid());
    }

}
