package com.example.duan1.user.fragment.Cart;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.BankInf;
import com.example.duan1.BillStatus;
import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.example.duan1.Utils;
import com.example.duan1.activity.DetailProductActivity;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.dao.BillDAO;
import com.example.duan1.dao.BillDetailDAO;
import com.example.duan1.dao.CartDAO;
import com.example.duan1.dao.CustomerDAO;
import com.example.duan1.dao.ProductDAO;
import com.example.duan1.databinding.FragmentCartBinding;
import com.example.duan1.eventbus.EventAdd;
import com.example.duan1.model.Bill;
import com.example.duan1.model.BillDetail;
import com.example.duan1.model.Cart;
import com.example.duan1.model.Customer;
import com.example.duan1.model.Product;
import com.example.duan1.qrcode.QRScanActivity;
import com.example.duan1.zalopay.Api.CreateOrder;
import com.example.duan1.zalopay.ZaloPayActivity;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class CartFragment extends BaseFragment<FragmentCartBinding> {
    public static String TAG = "Giỏ hàng";
    CartAdapter adapter;
    CartDAO cartDAO;
    ArrayList<Cart> list;
    String paymentMethod = "";
    //TextView tvTotalPrice;
    String email;
    int idBill = 0;

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
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);
        loadData();
    }

    @Override
    public String getTAG() {
        return TAG;
    }
    String totalPriceString = "";

    public void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rccCart.setLayoutManager(linearLayoutManager);

        cartDAO = new CartDAO(getContext());
        list = cartDAO.getAllCartCus(email);
        adapter = new CartAdapter(getContext(), list) {
            @Override
            public void click(int totalPrice) {
                totalPriceString = String.valueOf(totalPrice);
                binding.tvSum.setText(String.format("%,d", totalPrice) + "");
                Log.d("TAGG", "totalPrice" + totalPrice);
            }

            @Override
            public void clickBtnReduce() {
                list = cartDAO.getAllCartCus(email);
                cartEmpty();
            }

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), DetailProductActivity.class);
                intent.putExtra("product_id", list.get(position).getId());
                startActivity(intent);
            }
        };
        binding.rccCart.setAdapter(adapter);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.rccCart);


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
                if(!checkQuantity()) {
                    Toast.makeText(getContext(), "Giỏ hàng có sản phẩm không đủ số lượng hoặc hết hàng", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("TAGG", binding.tvAddress.getText().toString());
                if (binding.tvAddress.getText().toString().equals("Quét mã QR để nhập số bàn")) {
                    binding.btnQR.callOnClick();
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
                        paymentMethod = "Zalo Pay";
//                        Intent intent = new Intent(getContext(), ZaloPayActivity.class);
//                        intent.putExtra("price", binding.tvSum.getText().toString());
//                        startActivity(intent);
//                        onSuccess();
                        requestZaloPay();
                        alertDialog.dismiss();
                    }
                });
                tvCash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        paymentMethod = "Tiền mặt";
                        onSuccess();
                        alertDialog.dismiss();
                    }
                });
                tvBank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        paymentMethod = "Chuyển khoản";
                        onSuccess();
                        Intent intent = new Intent(getContext(), BankInf.class);
                        intent.putExtra("idBill", idBill);

                        startActivity(intent);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private boolean checkQuantity() {
        for (Cart cart : list) {
            if (cart.getQuantity() > cartDAO.getQuantity(cart.getIdProduct())) {
                return false;
            }
        }
        return true;
    }

    private void requestZaloPay() {
        CreateOrder orderApi = new CreateOrder();

        try {
            JSONObject data = orderApi.createOrder(totalPriceString);

            String code = data.getString("return_code");

            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(getActivity(), token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        Toast.makeText(getContext(), "Thanh toán thanh cong", Toast.LENGTH_SHORT).show();
                        onSuccess();
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {
                        Toast.makeText(getContext(), "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                        Toast.makeText(getContext(), "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Utils.QR, getContext().MODE_PRIVATE);
        String qr = sharedPreferences.getString(Utils.QR, "");
        if (!qr.equals("")) {
            binding.tvAddress.setText(qr);
        }
        adapter.notifyDataSetChanged();
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
            productDAO.updateQuantity(cart);
            cartDAO.deleteCart(cart.getId(), email);
        }
        //tạo đơn ra hóa đơn mới
        CustomerDAO customerDAO = new CustomerDAO(getContext());
        Customer customer = customerDAO.getByEmail(email);

        BillDAO billDAO = new BillDAO(getContext());

        Bill bill = new Bill();
        bill.setId(billDAO.getAll().size() + 1);
        idBill = billDAO.getAll().size() + 1;
        bill.setIdCustomer("" + customer.getId());
        bill.setShippingAddress(binding.tvAddress.getText().toString());

        bill.setEmail(email);
        bill.setStatus(BillStatus.CONFIRM);
        bill.setIdEmployee(null);
        bill.setPaymentMethod(paymentMethod);

        //lấy thời gian hiện tại
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = simpleDateFormat.format(date);
        bill.setDate(dateString);

        BillDetailDAO billDetailDAO = new BillDetailDAO(getContext());
        if (billDAO.insertCus(bill)) {
            //tạo hóa đơn chi tiết
            for (Cart cart : list) {
                BillDetail billDetail = new BillDetail();
                billDetail.setIdBill(bill.getId());
                billDetail.setIdProduct(cart.getIdProduct());
                billDetail.setQuantity(cart.getQuantity());
                billDetail.setPrice(cart.getPrice());

                // Lưu hóa đơn chi tiết vào cơ sở dữ liệu
                billDetailDAO.insert(billDetail);
            }
        }
        list.clear();
        list.addAll(cartDAO.getAllCartCus(email));
        cartEmpty();
        adapter.notifyDataSetChanged();
        binding.tvAddress.setText("Quét mã QR để nhập số bàn");
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
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.main))
                    .addActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftLabel("Delete")
                    .setSwipeLeftLabelColor(ContextCompat.getColor(getContext(), R.color.white)).setSwipeLeftLabelTypeface(Typeface.DEFAULT_BOLD)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position1 = viewHolder.getAdapterPosition();
            //
            ProductDAO productDAO = new ProductDAO(getContext());
            Product product = productDAO.getID(list.get(position1).getIdProduct());
            //
            CartDAO cartDAO = new CartDAO(getContext());
            Cart cart = list.get(position1);
            if (cartDAO.deleteCart(cart.getId(), email)) {
                list.clear();
                list.addAll(cartDAO.getAllCartCus(email));
                adapter.notifyDataSetChanged();

                Snackbar snackbar = Snackbar.make(binding.rccCart, "Xóa " + product.getName(), Snackbar.LENGTH_SHORT);
                snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cartDAO.insertToCart1(cart, product, email)) {
                            list.clear();
                            list.addAll(cartDAO.getAllCartCus(email));
                        }
                        adapter.notifyItemChanged(position1);
                    }
                });
                snackbar.show();
            }
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onEventAdd(EventAdd eventAdd) {
        loadData();
    }
}