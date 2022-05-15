package com.sk.ordinary.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sk.ordinary.R;
import com.sk.ordinary.adapter.GridAdapter;
import com.sk.ordinary.db.DBManager;
import com.sk.ordinary.entity.Cate;

import java.util.ArrayList;
import java.util.List;

public class IncomeFragment extends BaseRecordFragment{


    @Override
    public void loadGridData() {
        cateList = new ArrayList<>();
        gridAdapter = new GridAdapter(getContext(), cateList, "income");
        gridView.setAdapter(gridAdapter);
        super.loadGridData();
        List<Cate> list = DBManager.getCateList(1);
        cateList.addAll(list);
        gridAdapter.notifyDataSetChanged();
        topTv.setText("其他");
        topIv.setImageResource(R.drawable.in_qita);
    }

    @Override
    public void setGVListener() {
        super.setGVListener();
        int checkColor = getContext().getResources().getColor(R.color.edit_text);
        topIv.setColorFilter(checkColor);
    }

    /**
     * 重写数据保存
     */
    @Override
    public void saveBillToDB() {
        billRecord.setKind(1);
        DBManager.insertBillRecord(billRecord);
        getActivity().finish();
    }
}
