package com.example.duan1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.example.duan1.Utils;
import com.example.duan1.admin.ui.activity.BaseActivity;
import com.example.duan1.dao.AccountDAO;
import com.example.duan1.databinding.ActivityLoginBinding;

import com.example.duan1.model.Account;
import com.example.duan1.user.MainCustomer;
import com.example.duan1.database.DBHelper;

import java.util.List;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private String type = "custom";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    List<Account> list;
    @Override
    protected void initEvent() {
        AccountDAO accountDAO = new AccountDAO(this);
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
                    if(accountDAO.checkLogin(binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString(), DBHelper.TABLE_ACCOUNT_CUSTOMER)){
                        startActivity(new Intent(LoginActivity.this, MainCustomer.class));
                        finishAffinity();
                    }else{
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else if (type.equals(Utils.SHOP)) {
                    if(accountDAO.checkLogin(binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString(), DBHelper.TABLE_ACCOUNT_SHOP)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", binding.edtEmail.getText().toString());
                        editor.putString("password", binding.edtPassword.getText().toString());
                        editor.commit();

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
