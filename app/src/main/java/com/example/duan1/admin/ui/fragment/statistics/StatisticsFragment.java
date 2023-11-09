package com.example.duan1.admin.ui.fragment.statistics;

import android.os.Bundle;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.databinding.FragmentStatisticsBinding;

public class StatisticsFragment extends BaseFragment<FragmentStatisticsBinding> {
    public static String TAG = "Thống kê";

    public static StatisticsFragment newInstance() {

        Bundle args = new Bundle();

        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
    @Override
    public String getTAG() {
        return TAG;
    }
}
