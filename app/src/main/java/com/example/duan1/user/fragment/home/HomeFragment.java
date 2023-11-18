package com.example.duan1.user.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.duan1.R;
import com.example.duan1.activity.DetailProductActivity;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.dao.CustomerDAO;
import com.example.duan1.dao.ProductDAO;
import com.example.duan1.databinding.FragmentHomeCustomerBinding;
import com.example.duan1.model.Product;

import java.util.ArrayList;
import java.util.List;

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

    List<Product> list;
    ProductDAO productDAO;
    @Override
    protected void initView() {
        binding.rcvHomePage1.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        binding.rcvHomePage1.setAdapter(new HomeAdapter(list) {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), DetailProductActivity.class);
                intent.putExtra("product_id", list.get(position).getId());
                startActivity(intent);
                Toast.makeText(getContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        productDAO = new ProductDAO(getContext());

        list = productDAO.getAll();
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
