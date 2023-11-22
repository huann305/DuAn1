package com.example.duan1.user;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.GravityCompat;

import com.example.duan1.R;
import com.example.duan1.activity.ChooseActivity;
import com.example.duan1.admin.ui.activity.BaseActivity;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.admin.ui.fragment.updateinformation.UpdateInformationFragment;
import com.example.duan1.databinding.ActivityMainCustomerBinding;
import com.example.duan1.user.fragment.Cart.CartFragment;
import com.example.duan1.user.fragment.changepass.ChangePasswordFragment;
import com.example.duan1.user.fragment.home.HomeFragment;
import com.example.duan1.user.fragment.bill.BillFragment;
import com.example.duan1.user.fragment.updateinfo.UpdateInfoCustomerFragment;

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

        binding.navView.setNavigationItemSelectedListener(item -> {
            BaseFragment fragment = null;
            if (item.getItemId() == R.id.nav_home_page) {
                fragment = HomeFragment.newInstance();
            }else if (item.getItemId() == R.id.nav_update_profile) {
                fragment = UpdateInfoCustomerFragment.newInstance();
            } else if (item.getItemId() == R.id.nav_change_password) {
                fragment = ChangePasswordFragment.newInstance();
            } else if (item.getItemId() == R.id.nav_logout) {
                startActivity(new Intent(MainCustomer.this, ChooseActivity.class));
                finish();
            } else if (item.getItemId() == R.id.nav_order_placed) {
                fragment = BillFragment.newInstance();
            } else if (item.getItemId() == R.id.nav_cart) {
                fragment = CartFragment.newInstance();
            }

            if (fragment != null) {
                BaseFragment.add(MainCustomer.this, fragment);
                binding.tvTitle.setText(fragment.getTAG());
            }
            item.setCheckable(true);
            binding.drawerLayout.closeDrawer(binding.navView);
            return true;
        });
    }

    @Override
    protected void initData() {

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
}
