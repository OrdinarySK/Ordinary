package com.sk.ordinary.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sk.ordinary.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 在记录页面弹出时间对话框
 */
public class SelectDateDialog extends Dialog implements View.OnClickListener {

    TextView hourEt, minuteEt;
    DatePicker datePicker;
    Button cancel, yes, time;
    OnEnsureListener onEnsureListener;

    /**
     * 回调接口
     */
    public interface OnEnsureListener{
        public void onEnsure(String time, int year, int month, int day);
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public SelectDateDialog(@NonNull Context context) {
        super(context);
    }
    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_time_cancel:  //点击取消的事件
                cancel();
                break;

            case R.id.dialog_time_yes:
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int dayOfMonth = datePicker.getDayOfMonth();
                String monthStr = String.valueOf(month);
                if(month < 10) {
                    monthStr = "0"+month;
                }
                String dateStr = String.valueOf(dayOfMonth);
                if(dayOfMonth < 10) {
                    dateStr = "0"+dayOfMonth;
                }

                //获取小时
                String hourStr = hourEt.getText().toString();
                String minuteStr = minuteEt.getText().toString();

                int hour = 0;
                if(!TextUtils.isEmpty(hourStr)) {
                    hour = Integer.parseInt(hourStr);
                }
                int minute = 0;
                if(!TextUtils.isEmpty(minuteStr)) {
                    minute = Integer.parseInt(minuteStr);
                    minute = minute%60;
                }

                hourStr = String.valueOf(hour);
                minuteStr = String.valueOf(minute);

                if(hour < 10) {
                    hourStr = "0"+hour;
                }
                if(minute<10){
                    minuteStr="0"+minute;
                }
                String timeFordate = year+"-"+monthStr+"-"+dateStr+" "+hourStr+":"+minuteStr;

                if( onEnsureListener != null) {
                    //onEnsureListener.onEnsure();
                    //给回调函数传值
                    onEnsureListener.onEnsure(timeFordate, year, month, dayOfMonth);
                }
                cancel();
                break;
            case R.id.dialog_time_se_button:
                //弹出选择时间的dialog
                showTimeDialog();
                break;
        }
    }

    /**
     * 点击后显示选择时间的dialog
     */
    private void showTimeDialog() {
        SelectTimeDialog selectTimeDialog = new SelectTimeDialog(getContext());
        selectTimeDialog.show();
        selectTimeDialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(int hour, int minute) {
                hourEt.setText(String.valueOf(hour));
                minuteEt.setText(String.valueOf(minute));
            }
        });
    }

    /**
     * 创建原始
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar);
        //获取对象
        hourEt = findViewById(R.id.dialog_time_et_hour);
        Calendar instance = Calendar.getInstance();
        hourEt.setText(String.valueOf(instance.get(Calendar.HOUR_OF_DAY)));
        minuteEt = findViewById(R.id.dialog_time_et_minute);
        minuteEt.setText(String.valueOf(instance.get(Calendar.MINUTE)));
        cancel = findViewById(R.id.dialog_time_cancel);
        yes = findViewById(R.id.dialog_time_yes);
        datePicker = findViewById(R.id.dialog_time_dp);
        time = findViewById(R.id.dialog_time_se_button);

        cancel.setOnClickListener(this);
        yes.setOnClickListener(this);
        time.setOnClickListener(this);

        hideDatePickerHeader();
    }

    /**
     * 隐藏日期选择器头部
     */
    public void hideDatePickerHeader() {
        ViewGroup rootView = (ViewGroup) datePicker.getChildAt(0);
        if(rootView == null) {
            return;
        }
        View headerView = rootView.getChildAt(0);
        if(headerView == null) {
            return;
        }

        int headerId = getContext().getResources().getIdentifier("day_picker_selector_layout", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParamsRoot = rootView.getLayoutParams();
            layoutParamsRoot.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            rootView.setLayoutParams(layoutParamsRoot);

            ViewGroup animator = (ViewGroup) rootView.getChildAt(1);
            ViewGroup.LayoutParams layoutParamsAnimator = animator.getLayoutParams();
            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            animator.setLayoutParams(layoutParamsAnimator);

            View child = animator.getChildAt(0);
            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            child.setLayoutParams(layoutParamsChild);
            return;
        }
        //设置隐藏时钟的头部
        headerId = getContext().getResources().getIdentifier("date_picker_header","id","android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
        }
    }
}
