package com.example.duan1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.example.duan1.activity.ChooseActivity;
import com.example.duan1.admin.ui.fragment.ordermanagement.OrderAdminFragment;
import com.example.duan1.databinding.ActivityMainBinding;
import com.example.duan1.admin.ui.activity.BaseActivity;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.admin.ui.fragment.customermanagement.CustomerManagementFragment;
import com.example.duan1.admin.ui.fragment.employeemanagement.EmployeeManagementFragment;
import com.example.duan1.admin.ui.fragment.ordermanagement.OrderManagementFragment;
import com.example.duan1.admin.ui.fragment.productmanagement.ProductManagementFragment;
import com.example.duan1.admin.ui.fragment.statistics.StatisticsFragment;
import com.example.duan1.admin.ui.fragment.updateinformation.UpdateInformationFragment;
import com.example.duan1.database.DBHelper;
import com.example.duan1.user.fragment.changepass.ChangePasswordFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private Boolean isExit = false;
    int PERMISSION_CODE = 1;
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
            } else if (item.getItemId() == R.id.nav_order_management_admin) {
                fragment = OrderAdminFragment.newInstance();
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

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Cấp quyền thành công", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Cấp quyền thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}