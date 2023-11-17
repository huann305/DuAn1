package com.example.duan1.admin.ui.fragment.employeemanagement;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.dao.AccountDAO;
import com.example.duan1.dao.EmployeeDAO;
import com.example.duan1.databinding.FragmentEmployeeManagementBinding;
import com.example.duan1.model.Account;
import com.example.duan1.model.Employee;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.text.Regex;

public class EmployeeManagementFragment extends BaseFragment<FragmentEmployeeManagementBinding> {
    public static String TAG = "Quản lý nhân viên";
    EmployeeDAO employeeDAO;
    List<Employee> list;
    EmployeeAdapter adapter;

    public static EmployeeManagementFragment newInstance() {

        Bundle args = new Bundle();

        EmployeeManagementFragment fragment = new EmployeeManagementFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_employee_management;
    }

    @Override
    protected void initView() {
    }
    @Override
    protected void initData() {
        loadData();
        addEmployee();
    }
    @Override
    public String getTAG() {
        return TAG;
    }
    public  void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rccEmployee.setLayoutManager(linearLayoutManager);
        employeeDAO = new EmployeeDAO(getContext());
        list = employeeDAO.getAll();
        adapter = new EmployeeAdapter(getContext(), list);
        binding.rccEmployee.setAdapter(adapter);
    }
    public void addEmployee() {
        binding.fltAddEm.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.dialog_add_employee, null);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();

            EditText edtEmail = view.findViewById(R.id.edtEmail_add_em);
            Spinner spnRole = view.findViewById(R.id.spnRole_add_em);
            Button btnThem = view.findViewById(R.id.btnLuu_add_em);
            Button btnHuy = view.findViewById(R.id.btnHuy_add_em);

            // Tạo danh sách dữ liệu
            List<String> data = new ArrayList<>();
            data.add("admin");
            data.add("employeeSp");
            data.add("employeeChef");

            // Tạo Adapter để đổ dữ liệu vào Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);

            // Định dạng Spinner
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnRole.setAdapter(adapter);

            btnThem.setOnClickListener(v1 -> {
                Employee employee = new Employee();
                String email = edtEmail.getText().toString();
                String role = spnRole.getSelectedItem().toString();

                 if(email.isEmpty()) {
                     Toast.makeText(getContext(), "Email khong duoc de trong", Toast.LENGTH_SHORT).show();
                     return;
                 }
                AccountDAO accountDAO = new AccountDAO(getContext());
                if(accountDAO.insertEmployee(new Account(email, "chicken", role))) {
                    employee.setEmail(email);
                    //get current date
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(currentDate);

                    employee.setDate(formattedDate);

                    if(employeeDAO.insert(employee)){;
                        Toast.makeText(getContext(), "Add employee succ", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        loadData();
                    }else{
                        Toast.makeText(getContext(), "Add employee fail", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Email is exist", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            });
            btnHuy.setOnClickListener(v1 -> alertDialog.dismiss());
            alertDialog.show();
        });

    }
}
