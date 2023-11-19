package com.example.duan1.admin.ui.fragment.productmanagement;

import android.os.Bundle;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.databinding.FragmentProductManagementBinding;

public class ProductManagementFragment extends BaseFragment<FragmentProductManagementBinding> {
    public static String TAG = "Quản lý sản phẩm";

    public static ProductManagementFragment newInstance() {
        Bundle args = new Bundle();

        ProductManagementFragment fragment = new ProductManagementFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_management;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }
    @Override
    public String getTAG() {
        return TAG;
    }
}
