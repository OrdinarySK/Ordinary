package com.sk.ordinary;

import android.app.Application;

import com.sk.ordinary.db.DBManager;

/**
 * 全局应用的类
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());
    }


}
