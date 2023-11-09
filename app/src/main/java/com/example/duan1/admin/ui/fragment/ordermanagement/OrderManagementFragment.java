package com.example.duan1.admin.ui.fragment.ordermanagement;

import android.os.Bundle;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.databinding.FragmentOrderManagementBinding;

public class OrderManagementFragment extends BaseFragment<FragmentOrderManagementBinding> {
    public static String TAG = "Quản lý đơn hàng";

    public static OrderManagementFragment newInstance() {

        Bundle args = new Bundle();

        OrderManagementFragment fragment = new OrderManagementFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_management;
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
