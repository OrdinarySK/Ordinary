package com.sk.ordinary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import com.sk.ordinary.R;

public class SelectTimeDialog extends Dialog implements View.OnClickListener{

    TimePicker timePicker;
    Button date;
//    Button cancel, yes;



    /**
     * 设置点击时间监听的回调接口
     */
    public interface OnEnsureListener{
        /**
         * 回调接口函数
         */
        public void onEnsure(int hour, int minute);
    }
    OnEnsureListener onEnsureListener;
    /**
     * 设置监听接口的Set方法
     * @param onEnsureListener
     */
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public SelectTimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.dialog_24time_cancel:  //点击取消的事件
//                cancel();
//                break;
//
//            case R.id.dialog_24time_yes:
//
//
//                break;
            case R.id.dialog_date_button:
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                if(onEnsureListener!=null) {
                    onEnsureListener.onEnsure(hour, minute);
                }
                //onEnsureListener.onEnsure(hour, minute);
                //timePicker.setVisibility(View.GONE);
                cancel();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);

        timePicker = findViewById(R.id.dialog_time_tp);
        timePicker.setIs24HourView(true);
        date = findViewById(R.id.dialog_date_button);
//        cancel = findViewById(R.id.dialog_24time_cancel);
//        yes = findViewById(R.id.dialog_24time_yes);
//
//        cancel.setOnClickListener(this);
//        yes.setOnClickListener(this);

        date.setOnClickListener(this);

    }
}
