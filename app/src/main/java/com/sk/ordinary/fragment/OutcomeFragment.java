package com.sk.ordinary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sk.ordinary.R;
import com.sk.ordinary.adapter.GridAdapter;
import com.sk.ordinary.db.DBManager;
import com.sk.ordinary.dialog.SelectDateDialog;
import com.sk.ordinary.entity.Cate;

import java.util.ArrayList;
import java.util.List;

public class OutcomeFragment extends BaseRecordFragment{

    @Override
    public void loadGridData() {
        cateList = new ArrayList<>();
        gridAdapter = new GridAdapter(getContext(), cateList, "outcome");
        gridView.setAdapter(gridAdapter);
        super.loadGridData();
        List<Cate> list = DBManager.getCateList(0);
        cateList.addAll(list);
        gridAdapter.notifyDataSetChanged();
        topTv.setText("其他");
        topIv.setImageResource(R.drawable.ic_qita);
    }


    @Override
    public void setGVListener() {
        super.setGVListener();
        int checkColor = getContext().getResources().getColor(R.color.red_ff4f81);
        topIv.setColorFilter(checkColor);
    }

    @Override
    public void saveBillToDB() {
        billRecord.setKind(0);
        DBManager.insertBillRecord(billRecord);
        getActivity().finish();
    }


}
