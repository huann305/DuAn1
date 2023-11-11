package com.example.duan1.user.fragment.changepass;

import android.os.Bundle;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.databinding.FragmentChangePasswordBinding;

public class ChangePasswordFragment extends BaseFragment<FragmentChangePasswordBinding> {
    public static final String TAG = "Đổi mật khẩu";
    public static ChangePasswordFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_password;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
