package com.example.duan1.admin.ui.fragment.customermanagement;

import android.os.Bundle;

import com.example.duan1.R;
import com.example.duan1.databinding.FragmentCustomerManagementBinding;
import com.example.duan1.admin.ui.fragment.BaseFragment;

public class CustomerManagementFragment extends BaseFragment<FragmentCustomerManagementBinding> {
    public static String TAG = "Quản lý khách hàng";

    public static CustomerManagementFragment newInstance() {

        Bundle args = new Bundle();

        CustomerManagementFragment fragment = new CustomerManagementFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_customer_management;
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
