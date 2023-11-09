package com.example.duan1.admin.ui.fragment.employeemanagement;

import android.os.Bundle;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.databinding.FragmentEmployeeManagementBinding;

public class EmployeeManagementFragment extends BaseFragment<FragmentEmployeeManagementBinding> {
    public static String TAG = "Quản lý nhân viên";

    public static EmployeeManagementFragment newInstance() {

        Bundle args = new Bundle();

        EmployeeManagementFragment fragment = new EmployeeManagementFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_employee_management;
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
