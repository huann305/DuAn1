package com.example.duan1.user.fragment.order;

import android.os.Bundle;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.databinding.FragmentOrderCustomerBinding;

public class OrderFragment extends BaseFragment<FragmentOrderCustomerBinding> {
    public static final String TAG = "Đơn hàng";
    public static OrderFragment newInstance() {
        
        Bundle args = new Bundle();
        
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_customer;
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
