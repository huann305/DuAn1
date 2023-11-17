package com.example.duan1.user.fragment.changepass;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.duan1.MainActivity;
import com.example.duan1.R;
import com.example.duan1.activity.LoginActivity;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.dao.AccountDAO;
import com.example.duan1.dao.EmployeeDAO;
import com.example.duan1.databinding.FragmentChangePasswordBinding;
import com.example.duan1.model.Account;
import com.example.duan1.model.Employee;

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
