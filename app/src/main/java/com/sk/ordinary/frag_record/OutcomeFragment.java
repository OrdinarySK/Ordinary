package com.sk.ordinary.frag_record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sk.ordinary.R;
import com.sk.ordinary.entity.AccountBean;
import com.sk.ordinary.db.DBManager;
import com.sk.ordinary.entity.TypeBean;
import com.sk.ordinary.utils.KeyBoardUtils;
import com.sk.ordinary.utils.SelectDateDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 记录页面的支出模块
 */
public class OutcomeFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean flag = false;

    KeyboardView keyboardView;  //键盘view
    EditText moneyText;
    ImageView typeIv;
    TextView typeTv, descTv, timeTv;
    GridView typeGv;
    List<TypeBean> typeBeanList;
    TypeBase typeBaseAdapter;
    KeyBoardUtils keyBoardUtils;
    EditText descEd;
    AccountBean accountBean;  //将需要插入到记账本中的数据保存到对象中

    public OutcomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OutcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OutcomeFragment newInstance(String param1, String param2) {
        OutcomeFragment fragment = new OutcomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean = new AccountBean();
        accountBean.setTypeName("其他");
        accountBean.setsImageId(R.mipmap.ic_qita_fs);
    }

    /**
     * 给fragement动态i添加实体元素
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outcome, container, false);
        //初始化view
        initView(view);
        //给FridView填充的方法
        //设置初始化时间
        initTime();
        loadDataToGv();
        setGvListener();
        return view;
    }

    /**
     * 初始化时间的方法（这部分可以优化-提取到公共实现类）
     */
    private void initTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = sdf.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);

        Calendar calendar = Calendar.getInstance();
        accountBean.setYear(calendar.get(Calendar.YEAR));
        accountBean.setMonth(calendar.get(Calendar.MONTH)+1);
        accountBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
    }

    //设置GridView每一项的点击事件
    private void setGvListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                typeBaseAdapter.selectPos = i;
                typeBaseAdapter.notifyDataSetChanged();  //提示绘制

                //点击后上方的被选中的图标和文字的变化
                typeIv.setImageResource(typeBeanList.get(i).getSImageId());
                accountBean.setsImageId(typeBeanList.get(i).getSImageId());
                typeTv.setText(typeBeanList.get(i).getTypeName());
                accountBean.setTypeName(typeBeanList.get(i).getTypeName());

            }
        });
    }

    /**
     * 加载数据到GridView
     */
    private void loadDataToGv() {
        typeBeanList = new ArrayList<>();
        typeBaseAdapter = new TypeBase(getContext(), typeBeanList);
        typeGv.setAdapter(typeBaseAdapter);
        //获取数据中的数据源
        List<TypeBean> list = DBManager.getTypeList(0);
        typeBeanList.addAll(list);
        typeBaseAdapter.notifyDataSetChanged();

    }

    /**
     * View 初始化
     * @param view
     */
    public void initView(View view) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyText = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        //descTv = view.findViewById(R.id.frag_record_tv_desc);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        typeGv = view.findViewById(R.id.frag_record_gv);
        descEd = view.findViewById(R.id.frag_record_et_desc);
        timeTv.setOnClickListener(this::onClick);
        descEd.setOnClickListener(this::onClick);
        //让自定义软键盘显示出来
        keyBoardUtils = new KeyBoardUtils(keyboardView, moneyText);
        keyBoardUtils.showKeyboard();
        //设置接口监听
        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                // 点击了确定按钮
                // 获取输入的钱数目
                String s = moneyText.getText().toString();
                if(s.equals("") || s.length() == 0|| s.equals("0")) {
                    getActivity().finish();
                    return;
                }
                float money = Float.parseFloat(s.toString());
                accountBean.setMoney(money);
                //accountBean.setDescs("备注");
                //获取记录的信息  保存在数据库当中，还要去返回上一级页面
                accountBean.setKind(0);
                accountBean.setDescs(descEd.getText().toString());
                //开始调用保存数据的操作
                DBManager.insertMoneyRecord(accountBean);

                getActivity().finish();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;
            case R.id.frag_record_et_desc:
                break;
        }
    }

    /**
     * 弹出显示时间的对话框
     */
    private void showTimeDialog() {
        SelectDateDialog selectDateDialog = new SelectDateDialog(getContext());
        selectDateDialog.show();
        //设定按钮被点击之后的监听器
        selectDateDialog.setOnEnsureListener(new SelectDateDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }
}