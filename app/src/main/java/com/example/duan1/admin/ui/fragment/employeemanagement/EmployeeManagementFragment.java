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
import com.example.duan1.dao.EmployeeDAO;
import com.example.duan1.databinding.FragmentEmployeeManagementBinding;
import com.example.duan1.model.Employee;

import java.util.ArrayList;
import java.util.List;

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

            EditText tvHoTen = view.findViewById(R.id.tvHoTen_add_em);
            EditText tvEmail = view.findViewById(R.id.tvTaiKhoan_add_em);
            EditText tvSDT = view.findViewById(R.id.tvSDT_add_em);
            EditText tvDiaChi = view.findViewById(R.id.tvDiaChi_add_em);
            Spinner spinnerTrangThai = view.findViewById(R.id.spnTrangThai_add_em);
            EditText tvCCCD = view.findViewById(R.id.tvCCCD_add_em);
            Button btnThem = view.findViewById(R.id.btnLuu_add_em);
            Button btnHuy = view.findViewById(R.id.btnHuy_add_em);

            // Tạo danh sách dữ liệu
            List<String> data = new ArrayList<>();
            data.add("active");
            data.add("inactive");

            // Tạo Adapter để đổ dữ liệu vào Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);

            // Định dạng Spinner
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Gán Adapter cho Spinner
            spinnerTrangThai.setAdapter(adapter);

            btnThem.setOnClickListener(v1 -> {
                Employee employee = new Employee();

                String hoTen = tvHoTen.getText().toString();
                String email = tvEmail.getText().toString();
                String sdt = tvSDT.getText().toString();
                String diaChi = tvDiaChi.getText().toString();
                String trangThai = spinnerTrangThai.getSelectedItem().toString();
                String cccd = tvCCCD.getText().toString();

                if(hoTen.equals("") || email.equals("") || sdt.equals("") || diaChi.equals("") || trangThai.equals("") || cccd.equals("")){
                    Toast.makeText(getContext(), "Please enter complete information", Toast.LENGTH_SHORT).show();
                    return;
                }

                employee.setName(hoTen);
                employee.setEmail(email);
                employee.setPhone(sdt);
                employee.setAddress(diaChi);
                employee.setStatus(trangThai);
                employee.setCitizenshipID(cccd);

                if(employeeDAO.insert(employee)){;
                    Toast.makeText(getContext(), "Add employee succ", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    loadData();
                }else{
                    Toast.makeText(getContext(), "Add employee fail", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialog.show();
        });

    }
}
