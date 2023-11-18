package com.example.duan1.user.fragment.Cart;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.dao.CartDAO;
import com.example.duan1.databinding.FragmentCartBinding;
import com.example.duan1.model.Cart;

import java.util.ArrayList;

public class CartFragment extends BaseFragment<FragmentCartBinding> {
    public static String TAG = "Giỏ hàng";
    CartAdapter adapter;
    CartDAO cartDAO;
    ArrayList<Cart> list;
    Button btnOrder;
    //TextView tvTotalPrice;

    public static CartFragment newInstance() {
        Bundle args = new Bundle();
        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        loatData();
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void loatData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rccCart.setLayoutManager(linearLayoutManager);

        cartDAO = new CartDAO(getContext());
        list = cartDAO.getAllCart();
        adapter = new CartAdapter(getContext(), list) {
            @Override
            public void click(int totalPrice, int totalQuantity) {
                binding.tvSum.setText("Tổng tiền: " + totalPrice + " Đ");
            }
        };
        binding.rccCart.setAdapter(adapter);

//        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//        });
    }
}