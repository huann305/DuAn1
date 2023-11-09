package com.example.duan1.activity;

import android.content.Intent;

import com.example.duan1.R;
import com.example.duan1.admin.ui.activity.BaseActivity;
import com.example.duan1.databinding.ActivitySignUpBinding;

public class SignUpActivity extends BaseActivity<ActivitySignUpBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initEvent() {
        binding.btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    @Override
    protected void initData() {

    }
}
