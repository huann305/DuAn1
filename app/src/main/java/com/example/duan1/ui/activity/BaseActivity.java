package com.example.duan1.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.duan1.MainActivity;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {
    protected B binding;
    private MainActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initData();
        initEvent();
    }
    protected abstract int getLayoutId();
    protected abstract void initEvent();
    protected abstract void initData();
}
