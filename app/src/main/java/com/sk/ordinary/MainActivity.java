package com.sk.ordinary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.core.os.BuildCompat;

import android.util.Log;
import com.sk.ordinary.activity.BaseActivity;
import com.sk.ordinary.activity.SettingsActivity;
import com.sk.ordinary.dialog.BottomAreaDialog;
import com.sk.ordinary.fragment.MainFragment;
import com.sk.ordinary.utils.AutoBillUtils;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.util.Arrays;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 自动化记账的处理
      }

    /**
     * 设置menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        Log.i("TAG", "onCreateOptionsMenu: start");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 当菜单键被选中时候的事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.toolbar_statistics:

                break;
            case R.id.toolbar_wallet:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}