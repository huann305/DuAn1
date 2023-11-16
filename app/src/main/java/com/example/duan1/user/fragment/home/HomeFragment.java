package com.example.duan1.user.fragment.home;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.databinding.FragmentHomeCustomerBinding;

public class HomeFragment extends BaseFragment<FragmentHomeCustomerBinding> {
    public static final String TAG = "Trang chủ";
    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_customer;
    }

    @Override
    protected void initView() {
        binding.rcvHomePage1.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rcvHomePage2.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.rcvHomePage1.setAdapter(new HomeAdapter());
        binding.rcvHomePage2.setAdapter(new HomeAdapter());
    }

    @Override
    protected void initData() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
