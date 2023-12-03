package com.example.duan1.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;

import com.bumptech.glide.Glide;
import com.example.duan1.R;
import com.example.duan1.activity.ChooseActivity;
import com.example.duan1.admin.ui.activity.BaseActivity;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.dao.CustomerDAO;
import com.example.duan1.databinding.ActivityMainCustomerBinding;
import com.example.duan1.eventbus.Search;
import com.example.duan1.eventbus.UpdateInfo;
import com.example.duan1.model.Customer;
import com.example.duan1.user.fragment.Cart.CartFragment;
import com.example.duan1.user.fragment.changepass.ChangePasswordFragment;
import com.example.duan1.user.fragment.home.HomeFragment;
import com.example.duan1.user.fragment.bill.BillFragment;
import com.example.duan1.user.fragment.updateinfo.UpdateInfoCustomerFragment;
import com.google.android.material.imageview.ShapeableImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainCustomer extends BaseActivity<ActivityMainCustomerBinding> {
    private Boolean isExit = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_customer;
    }

    @Override
    protected void initEvent() {
        BaseFragment.add(this, HomeFragment.newInstance());
        binding.tvTitle.setText(HomeFragment.newInstance().getTAG());
        binding.navView.setCheckedItem(R.id.nav_home_page);
        binding.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(binding.navView);
            }
        });
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rltSearch.setVisibility(View.VISIBLE);
            }
        });
        binding.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rltSearch.setVisibility(View.GONE);
            }
        });

        binding.bottomNav.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bottomNav.btnCart.setBackgroundColor(getResources().getColor(R.color.main));
                binding.bottomNav.btnHome.setBackgroundColor(getResources().getColor(R.color.bgr_btn));
                binding.bottomNav.btnInvoice.setBackgroundColor(getResources().getColor(R.color.bgr_btn));
                binding.bottomNav.btnCartText.setVisibility(View.VISIBLE);
                binding.bottomNav.btnHomeText.setVisibility(View.GONE);
                binding.bottomNav.btnInvoiceText.setVisibility(View.GONE);
                binding.tvTitle.setText(CartFragment.newInstance().getTAG());
                BaseFragment.add(MainCustomer.this, CartFragment.newInstance());
            }
        });

        binding.bottomNav.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bottomNav.btnCart.setBackgroundColor(getResources().getColor(R.color.bgr_btn));
                binding.bottomNav.btnHome.setBackgroundColor(getResources().getColor(R.color.main));
                binding.bottomNav.btnInvoice.setBackgroundColor(getResources().getColor(R.color.bgr_btn));
                binding.bottomNav.btnCartText.setVisibility(View.GONE);
                binding.bottomNav.btnHomeText.setVisibility(View.VISIBLE);
                binding.bottomNav.btnInvoiceText.setVisibility(View.GONE);
                binding.tvTitle.setText(HomeFragment.newInstance().getTAG());
                BaseFragment.add(MainCustomer.this, HomeFragment.newInstance());
            }
        });
        binding.bottomNav.btnInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bottomNav.btnCart.setBackgroundColor(getResources().getColor(R.color.bgr_btn));
                binding.bottomNav.btnHome.setBackgroundColor(getResources().getColor(R.color.bgr_btn));
                binding.bottomNav.btnInvoice.setBackgroundColor(getResources().getColor(R.color.main));
                binding.bottomNav.btnCartText.setVisibility(View.GONE);
                binding.bottomNav.btnHomeText.setVisibility(View.GONE);
                binding.bottomNav.btnInvoiceText.setVisibility(View.VISIBLE);
                binding.tvTitle.setText(BillFragment.newInstance().getTAG());
                BaseFragment.add(MainCustomer.this, BillFragment.newInstance());
            }
        });

        binding.edtSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                EventBus.getDefault().post(new Search(String.valueOf(charSequence)));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.navView.setNavigationItemSelectedListener(item -> {
            binding.bottomNav.bottomNav.setVisibility(View.VISIBLE);
            binding.btnSearch.setVisibility(View.GONE);
            BaseFragment fragment = null;
            if (item.getItemId() == R.id.nav_home_page) {
                fragment = HomeFragment.newInstance();
                binding.bottomNav.btnHome.callOnClick();
                binding.btnSearch.setVisibility(View.VISIBLE);
            } else if (item.getItemId() == R.id.nav_update_profile) {
                fragment = UpdateInfoCustomerFragment.newInstance();
                binding.bottomNav.bottomNav.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.nav_change_password) {
                fragment = ChangePasswordFragment.newInstance();
                binding.bottomNav.bottomNav.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.nav_logout) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainCustomer.this);
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn có muốn đăng xuất ?");
                builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainCustomer.this, ChooseActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            } else if (item.getItemId() == R.id.nav_order_placed) {
                fragment = BillFragment.newInstance();
                binding.bottomNav.btnInvoice.callOnClick();
            } else if (item.getItemId() == R.id.nav_cart) {
                fragment = CartFragment.newInstance();
                binding.bottomNav.btnCart.callOnClick();
            }

            if (fragment != null) {
                BaseFragment.add(MainCustomer.this, fragment);
                binding.tvTitle.setText(fragment.getTAG());
            }
//            item.setCheckable(true);
            binding.drawerLayout.closeDrawer(binding.navView);
            return true;
        });
    }

    @Override
    protected void initData() {
        loadData();

    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else if (backStackEntryCount > 0) {
            super.onBackPressed();
        } else if (this.isExit.booleanValue()) {
            finish();
        } else {
            Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();
            this.isExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public final void run() {
                    isExit = false;
                }
            }, 3000L);
        }
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        CustomerDAO customerDAO = new CustomerDAO(this);
        Customer customer = customerDAO.getByEmail(email);
        ShapeableImageView imgAccount = binding.navView.getHeaderView(0).findViewById(R.id.imgAccount);
        TextView tvFullName = binding.navView.getHeaderView(0).findViewById(R.id.tvFullName);
        TextView tvAc = binding.navView.getHeaderView(0).findViewById(R.id.tvAc);
        imgAccount.setImageResource(R.drawable.baseline_person_24_fff);
        tvFullName.setText(customer.getName());
        tvAc.setText(customer.getEmail());
        if (customer.getImage() != null) {
            Glide.with(this).load(customer.getImage()).into(imgAccount);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UpdateInfo event) {
        loadData();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
