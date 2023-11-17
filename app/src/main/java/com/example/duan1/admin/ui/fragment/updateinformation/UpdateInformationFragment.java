package com.example.duan1.admin.ui.fragment.updateinformation;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.dao.EmployeeDAO;
import com.example.duan1.databinding.FragmentUpdateInformationBinding;

import com.example.duan1.model.Employee;


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
        loatData();
    }

    public void loatData() {
        EmployeeDAO employeeDAO = new EmployeeDAO(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER", MODE_PRIVATE);
        String emailData = sharedPreferences.getString("email", "");
        Employee employee = employeeDAO.getByEmail(emailData);
        binding.edtNameUpEm.setText(employee.getName());
        binding.edtPhone.setText(employee.getPhone());
        binding.edtEmail.setText(employee.getEmail());
        binding.edtAddress.setText(employee.getAddress());
        binding.edtCitizen.setText(employee.getCitizenshipID());
        binding.edtDate.setText(employee.getDate());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employee.setName(binding.edtNameUpEm.getText().toString());
                employee.setPhone(binding.edtPhone.getText().toString());
                employee.setAddress(binding.edtAddress.getText().toString());
                if (employeeDAO.getByEmail(emailData) != null) {
                    if (employeeDAO.updateInfo(employee, emailData)) {
                        Toast.makeText(getContext(), "Update information successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Update information failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}

