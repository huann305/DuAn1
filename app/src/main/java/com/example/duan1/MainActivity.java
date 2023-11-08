package com.example.duan1;

import android.content.Intent;
import android.view.View;

import com.example.duan1.databinding.ActivityMainBinding;
import com.example.duan1.qrcode.QRCodeGenerator;
import com.example.duan1.qrcode.QRScanActivity;
import com.example.duan1.ui.activity.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvent() {
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QRScanActivity.class));
            }
        });
    }

    @Override
    protected void initData() {

    }
}