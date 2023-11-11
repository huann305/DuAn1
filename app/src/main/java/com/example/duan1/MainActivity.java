package com.example.duan1;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.GravityCompat;

import com.example.duan1.activity.ChooseActivity;
import com.example.duan1.databinding.ActivityMainBinding;
import com.example.duan1.admin.ui.activity.BaseActivity;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.admin.ui.fragment.customermanagement.CustomerManagementFragment;
import com.example.duan1.admin.ui.fragment.employeemanagement.EmployeeManagementFragment;
import com.example.duan1.admin.ui.fragment.ordermanagement.OrderManagementFragment;
import com.example.duan1.admin.ui.fragment.productmanagement.ProductManagementFragment;
import com.example.duan1.admin.ui.fragment.statistics.StatisticsFragment;
import com.example.duan1.admin.ui.fragment.updateinformation.UpdateInformationFragment;
import com.example.duan1.user.fragment.changepass.ChangePasswordFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private Boolean isExit = false;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvent() {
        BaseFragment.add(this, ProductManagementFragment.newInstance());
        binding.tvTitle.setText(ProductManagementFragment.newInstance().getTAG());
        binding.navView.setCheckedItem(R.id.nav_product_management);
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
            if(item.getItemId() == R.id.nav_statistics){
                 fragment = StatisticsFragment.newInstance();
            } else if (item.getItemId() == R.id.nav_product_management) {
                 fragment = ProductManagementFragment.newInstance();
            } else if (item.getItemId() == R.id.nav_employee_management) {
                 fragment = EmployeeManagementFragment.newInstance();
            } else if (item.getItemId() == R.id.nav_customer_management) {
                 fragment = CustomerManagementFragment.newInstance();
            } else if (item.getItemId() == R.id.nav_order_management) {
                 fragment = OrderManagementFragment.newInstance();
            } else if (item.getItemId() == R.id.nav_update_profile) {
                 fragment = UpdateInformationFragment.newInstance();
            } else if (item.getItemId() == R.id.nav_change_password) {
                fragment = ChangePasswordFragment.newInstance();
            } else if (item.getItemId() == R.id.nav_logout) {
                startActivity(new Intent(MainActivity.this, ChooseActivity.class));
                finish();
            }
            if (fragment != null) {
                BaseFragment.add(MainActivity.this, fragment);
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