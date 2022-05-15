package com.sk.ordinary.utils;

import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.sk.ordinary.R;


public class KeyBoardUtils {
    private final Keyboard k1;  //自定义的键盘
    private KeyboardView keyboardView;
    //设置过程中的变化
    private EditText editText;
    /**
     * 完成之后的跳转的监听的接口
     */
    public interface OnEnsureListener {
        public void onEnsure();
    }

    /**
     * 用于数据回调的set方法
     * @param onEnsureListener
     */
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    OnEnsureListener onEnsureListener;

    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        //给编辑的文本设置事件  绑定我们自带的键盘
        //取消弹出系统键盘
        this.editText.setInputType(InputType.TYPE_NULL);
        k1 = new Keyboard(this.editText.getContext(), R.xml.keyboard);
        this.keyboardView.setKeyboard(k1); //设置新的键盘为键盘k1
        this.keyboardView.setEnabled(true); //显示键盘
        this.keyboardView.setPreviewEnabled(false);  //不进行预览
        this.keyboardView.setOnKeyboardActionListener(listener);  //设置键盘按钮被点击之后的监听
    }

    /**
     * 键盘的各类点击事件
     */
    KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {
            //点击之后的事件

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int i, int[] ints) {
            Editable text = editText.getText();
            int selectionStart = editText.getSelectionStart();
            switch (i) {
                case Keyboard.KEYCODE_DELETE:  //点击了删除键
                    if (text != null && text.length() > 0) {
                        if(selectionStart == 1) {
                            editText.setText("0.0");
                        }
                        if (selectionStart > 0) {
                            text.delete(selectionStart - 1, selectionStart);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL:   //点击了清零
                    text.clear();
                    editText.setText("0.0");
                    break;
                case Keyboard.KEYCODE_DONE:  //点击了确定
                    onEnsureListener.onEnsure();   //通过接口回调的方法，当点击确定，可以调用这个方法
                    break;
                default:
                    if(Float.parseFloat(text.toString()) == 0.0) {
                        text.clear();
                    }
                    text.insert(selectionStart, Character.toString((char) i));    //其他的数字的直接插入
            }
        }

        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if(visibility == View.INVISIBLE || visibility == View.GONE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if(visibility == View.VISIBLE || visibility == View.INVISIBLE){
            keyboardView.setVisibility(View.GONE);
        }
    }
}
