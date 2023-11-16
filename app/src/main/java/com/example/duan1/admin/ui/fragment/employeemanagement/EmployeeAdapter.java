package com.example.duan1.admin.ui.fragment.employeemanagement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.dao.EmployeeDAO;
import com.example.duan1.databinding.ItemEmployeeManagerBinding;
import com.example.duan1.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private Context context;
    private List<Employee> list;

    public EmployeeAdapter(Context context, List<Employee> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEmployeeManagerBinding binding = ItemEmployeeManagerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = list.get(position);
        holder.binding.tvTaiKhoanEm.setText("Email: " + employee.getEmail());
        holder.binding.tvHoTenEm.setText("Họ tên: " + employee.getName());
        holder.binding.tvSDTEm.setText("SĐT: " + employee.getPhone());
        holder.binding.tvDiaChiEm.setText("Địa chỉ: " + employee.getAddress());
        holder.binding.tvCCCDEm.setText("CCCD: " + employee.getCitizenshipID());
        holder.binding.tvTrangThaiEm.setText("Trạng thái: " + employee.getStatus());

        holder.binding.ivUpdate.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_employee, null);
            builder.setView(view);
            builder.create();
            AlertDialog alertDialog = builder.create();

            EditText edtEmail = view.findViewById(R.id.edtTaiKhoan_up_em);
            EditText edtHoTen = view.findViewById(R.id.edtHoTen_up_em);
            EditText edtSDT = view.findViewById(R.id.edtSDT_up_em);
            EditText edtDiaChi = view.findViewById(R.id.edtDiaChi_up_em);
            EditText edtCCCD = view.findViewById(R.id.edtCCCD_up_em);
            Spinner spinnerTrangThai = view.findViewById(R.id.spnTrangThai_up_em);
            Button btnUpdate = view.findViewById(R.id.btnLuu_up_em);

            edtHoTen.setText(employee.getName());
            edtEmail.setText(employee.getEmail());
            edtSDT.setText(employee.getPhone());
            edtDiaChi.setText(employee.getAddress());
            edtCCCD.setText(employee.getCitizenshipID());

            List<String> data = new ArrayList<>();
            data.add("Đang làm việc");
            data.add("Đã nghỉ việc");

            // Tạo Adapter để đổ dữ liệu vào Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, data);

            // Định dạng Spinner
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Gán Adapter cho Spinner
            spinnerTrangThai.setAdapter(adapter);

            if(employee.getStatus().equals("active")) {
                spinnerTrangThai.setSelection(0);
            } else {
                spinnerTrangThai.setSelection(1);
            }

            btnUpdate.setOnClickListener(v1 -> {
                EmployeeDAO employeeDAO = new EmployeeDAO(context);
                employee.setStatus(spinnerTrangThai.getSelectedItem().toString());
                if(employeeDAO.updateStatus(employee, String.valueOf(employee.getId()))) {
                    Toast.makeText(context, "Update employee succ", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(context, "Update employee fail", Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemEmployeeManagerBinding binding;
        public ViewHolder(@NonNull ItemEmployeeManagerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
