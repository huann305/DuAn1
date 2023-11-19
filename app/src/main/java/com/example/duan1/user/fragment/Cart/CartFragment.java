package com.example.duan1.user.fragment.Cart;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.Context;
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
import com.example.duan1.dao.BillDAO;
import com.example.duan1.dao.BillDetailDAO;
import com.example.duan1.dao.CartDAO;
import com.example.duan1.dao.CustomerDAO;
import com.example.duan1.dao.ProductDAO;
import com.example.duan1.databinding.FragmentCartBinding;
import com.example.duan1.model.Bill;
import com.example.duan1.model.BillDetail;
import com.example.duan1.model.Cart;
import com.example.duan1.model.Customer;
import com.example.duan1.model.Product;
import com.example.duan1.qrcode.QRScanActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    protected void initEvent() {

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
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rccCart.setLayoutManager(linearLayoutManager);

        cartDAO = new CartDAO(getContext());
        list = cartDAO.getAllCartCus(email);
        adapter = new CartAdapter(getContext(), list) {
            @Override
            public void click(int totalPrice) {
                binding.tvSum.setText("Tổng tiền: " + totalPrice + " Đ");
            }
            @Override
            public void clickBtnReduce() {
                list = cartDAO.getAllCartCus(email);
                cartEmpty();
            }
        };
        binding.rccCart.setAdapter(adapter);

        //nếu giỏ hàng trống k cho đặt hàng
        cartEmpty();

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
                Log.d("TAGG", binding.tvAddress.getText().toString());
                if(binding.tvAddress.getText().toString().equals("Quét mã QR để nhập số bàn")){
                    Toast.makeText(getContext(), "Quét mã QR", Toast.LENGTH_SHORT).show();
                    return;
                }
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
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        //tăng số lượng đã bán
        ProductDAO productDAO = new ProductDAO(getContext());
        for (Cart cart : list) {
            productDAO.updateQuantitySold(cart);
            cartDAO.deleteCart(cart.getId(), email);
        }
        //tạo đơn hàng
        CustomerDAO customerDAO = new CustomerDAO(getContext());
        Customer customer = customerDAO.getByEmail(email);

        BillDAO billDAO = new BillDAO(getContext());

        Bill bill = new Bill();
        bill.setId(billDAO.getAll().size() + 1);
        bill.setIdCustomer("" + customer.getId());
        bill.setShippingAddress("1");
        bill.setStatus("Đang chờ");
        bill.setEmail(email);

        //lấy thời gian hiện tại
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = simpleDateFormat.format(date);
        bill.setDate(dateString);

        BillDetailDAO billDetailDAO = new BillDetailDAO(getContext());
        if(billDAO.insertCus(bill)){
            //tạo hóa đơn chi tiết
            for (Cart cart : list) {
                BillDetail billDetail = new BillDetail();
                billDetail.setIdBill(bill.getId());
                Log.i("BillDetail", bill.getId() + "");
                billDetail.setIdProduct(cart.getIdProduct());
                billDetail.setQuantity(cart.getQuantity());
                billDetail.setPrice(cart.getPrice() * cart.getQuantity());


                // Lưu hóa đơn chi tiết vào cơ sở dữ liệu
                billDetailDAO.insert(billDetail);
            }
        }
        list.clear();
        list.addAll(cartDAO.getAllCartCus(email));
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