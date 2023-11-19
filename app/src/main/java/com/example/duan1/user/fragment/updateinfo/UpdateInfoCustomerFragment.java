package com.example.duan1.user.fragment.updateinfo;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.admin.ui.fragment.updateinformation.UpdateInformationFragment;
import com.example.duan1.dao.CustomerDAO;
import com.example.duan1.dao.EmployeeDAO;
import com.example.duan1.databinding.FragmentUpdateInfoCustomerBinding;
import com.example.duan1.model.Customer;
import com.example.duan1.model.Employee;

public class UpdateInfoCustomerFragment extends BaseFragment<FragmentUpdateInfoCustomerBinding> {

    public static UpdateInfoCustomerFragment newInstance() {

        Bundle args = new Bundle();

        UpdateInfoCustomerFragment fragment = new UpdateInfoCustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_info_customer;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
loatData();
    }

    @Override
    public String getTAG() {
        return null;
    }
    public void loatData() {
        CustomerDAO customerDAO = new CustomerDAO(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER", MODE_PRIVATE);
        String emailData = sharedPreferences.getString("email", "");
        Customer customer = customerDAO.getByEmail(emailData);
        binding.edtNameUpEm.setText(customer.getName());
        binding.edtPhone.setText(customer.getPhone());
        binding.edtEmail.setText(customer.getEmail());
        binding.edtAddress.setText(customer.getAddress());
        binding.edtBirthday.setText(customer.getBirthday());
        binding.edtStatus.setText(customer.getStatus());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer.setName(binding.edtNameUpEm.getText().toString());
                customer.setPhone(binding.edtPhone.getText().toString());
                customer.setAddress(binding.edtAddress.getText().toString());
                customer.setBirthday(binding.edtBirthday.getText().toString());

                if (customerDAO.getByEmail(emailData) != null) {
                    if (customerDAO.updateInfo(customer, emailData)) {
                        Toast.makeText(getContext(), "Update information successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Update information failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}