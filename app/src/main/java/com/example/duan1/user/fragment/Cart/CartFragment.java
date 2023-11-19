package com.example.duan1.user.fragment.Cart;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.Utils;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.dao.CartDAO;
import com.example.duan1.dao.ProductDAO;
import com.example.duan1.databinding.FragmentCartBinding;
import com.example.duan1.model.Bill;
import com.example.duan1.model.Cart;
import com.example.duan1.model.Product;
import com.example.duan1.qrcode.QRScanActivity;

import java.util.ArrayList;
import java.util.List;

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
            public void click(int totalPrice) {
                binding.tvSum.setText("Tổng tiền: " + totalPrice + " Đ");
            }
            @Override
            public void clickBtnReduce() {
                list = cartDAO.getAllCart();
                cartEmpty();
            }
        };
        binding.rccCart.setAdapter(adapter);

        //nếu giỏ hàng trống k cho đặt hàng
        cartEmpty();

        Log.d("TAGG", binding.tvAddress.getText().toString());
        if(binding.tvAddress.getText().toString().equals("Quét mã QR để nhập số bàn")){
            binding.btnOrder.setEnabled(false);
            Log.d("Clicked", "false");
        }else {
            binding.btnOrder.setEnabled(true);
            Log.d("Clicked", "true");
        }

        binding.btnQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), QRScanActivity.class));
            }
        });

        //đặt hàng
        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialog_choose_method, null);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();

                TextView tvZaloPay = view1.findViewById(R.id.tvZaloPay);
                TextView tvCash = view1.findViewById(R.id.tvCash);
                TextView tvBank = view1.findViewById(R.id.tvBank);
                tvZaloPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSuccess();
                        alertDialog.dismiss();
                    }
                });
                tvCash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSuccess();
                        alertDialog.dismiss();
                    }
                });
                tvBank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSuccess();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Utils.QR, getContext().MODE_PRIVATE);
        String qr = sharedPreferences.getString(Utils.QR, "");
        if (!qr.equals("")) {
            binding.tvAddress.setText(qr);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Utils.QR, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Utils.QR, "");
        editor.apply();
    }

    private void onSuccess() {
        //tăng số lượng đã bán
        ProductDAO productDAO = new ProductDAO(getContext());
        for (Cart cart : list) {
            productDAO.updateQuantitySold(cart);
            cartDAO.deleteCart(cart.getId());
        }
        list.clear();
        list.addAll(cartDAO.getAllCart());
        cartEmpty();
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
    }

    private void cartEmpty() {
        if (list.size() == 0) {
            binding.tvCartEmpty.setVisibility(View.VISIBLE);
            binding.llBottom.setVisibility(View.GONE);
        } else {
            binding.llBottom.setVisibility(View.VISIBLE);
            binding.tvCartEmpty.setVisibility(View.GONE);
        }
        binding.tvSum.setText("Tổng tiền: " + "0" + " Đ");
    }

}