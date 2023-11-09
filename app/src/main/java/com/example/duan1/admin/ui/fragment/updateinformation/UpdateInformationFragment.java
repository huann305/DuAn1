package com.example.duan1.admin.ui.fragment.updateinformation;

import android.os.Bundle;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.databinding.FragmentUpdateInformationBinding;

public class UpdateInformationFragment extends BaseFragment<FragmentUpdateInformationBinding> {
    public static String TAG = "Cập nhật thông tin";

    public static UpdateInformationFragment newInstance() {

        Bundle args = new Bundle();

        UpdateInformationFragment fragment = new UpdateInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_information;
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
