package com.sk.ordinary.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.sk.ordinary.R;

/**
 * @Descrpition: 预算得dialog的处理文件
 */
public class BudgetDialog extends Dialog implements View.OnClickListener {
    //  定义需要获取得控件
    Button budgetBt;
    EditText budgetEt;
    OnEnsureListener onEnsureListener;

    /**
     * 设置自定义样式的dialog
     * @param context
     * @param themeResId
     */
    public BudgetDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_budget,null);
        //获得dialog的window窗口
        Window window = this.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        setContentView(dialogView);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.dialog_budget_bt_yes:
                // 点击确定的时候触发回调函数传值给MainActivity
                String s = budgetEt.getText().toString();
                if(TextUtils.isEmpty(s)) {
                    Toast.makeText(getContext(), "输入数据不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                float money = Float.parseFloat(s);

                if(money <= 0) {
                    Toast.makeText(getContext(), "预算金额必须大于0", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(onEnsureListener != null) {
                    onEnsureListener.onEnsure(money);
                }
                cancel();
                break;

        }
    }

    /**
     * 定义回调的接口
     */
    public interface OnEnsureListener{
        /**
         * 事件点击的回调函数
         * @param money
         */
        public void onEnsure(float money);
    }
    /**
     * 设置GET函数
     * @param onEnsureListener
     */
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_budget);
        //初始化获取组件
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        budgetEt = findViewById(R.id.dialog_set_budget);
        budgetBt = findViewById(R.id.dialog_budget_bt_yes);
        //给按钮设置事件监听
        budgetBt.setOnClickListener(this);
    }

    /**
     * 构造函数
     * @param context
     */
    public BudgetDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * 点击事件
     * @param view
     */


}
