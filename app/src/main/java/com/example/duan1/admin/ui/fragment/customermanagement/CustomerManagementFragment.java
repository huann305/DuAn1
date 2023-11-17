package com.example.duan1.admin.ui.fragment.customermanagement;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.dao.CustomerDAO;
import com.example.duan1.databinding.FragmentCustomerManagementBinding;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerManagementFragment extends BaseFragment<FragmentCustomerManagementBinding> {
    public static String TAG = "Quản lý khách hàng";
    CustomerAdapter adapter;
    CustomerDAO customerDAO;
    List<Customer> list;

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
        loadData();
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rccCustomer.setLayoutManager(linearLayoutManager);
        customerDAO = new CustomerDAO(getContext());
        list = customerDAO.getAll();
        adapter = new CustomerAdapter(getContext(), list);
        binding.rccCustomer.setAdapter(adapter);
    }
}
