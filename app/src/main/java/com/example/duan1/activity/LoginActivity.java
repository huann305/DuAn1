package com.example.duan1.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.example.duan1.Utils;
import com.example.duan1.admin.ui.activity.BaseActivity;
import com.example.duan1.databinding.ActivityLoginBinding;
import com.example.duan1.user.MainCustomer;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private String type = "custom";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEvent() {
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals(Utils.CUSTOM)){
                    if(binding.edtEmail.getText().toString().equals("custom") && binding.edtPassword.getText().toString().equals("custom")) {
                        startActivity(new Intent(LoginActivity.this, MainCustomer.class));
                        finishAffinity();
                    }else{
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else if (type.equals(Utils.SHOP)) {
                    if(binding.edtEmail.getText().toString().equals("shop") && binding.edtPassword.getText().toString().equals("shop")) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finishAffinity();
                    }else{
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra(Utils.TYPE);
        if(type == null){
            type = Utils.CUSTOM;
        }
        if (type.equals(Utils.SHOP)) {
            binding.llSignUp.setVisibility(View.GONE);
        }
        binding.edtEmail.setText(type);
        binding.edtPassword.setText(type);
        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
    }
}
