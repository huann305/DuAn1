package com.example.duan1.admin.ui.fragment.statistics;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.admin.ui.fragment.ordermanagement.OrderManagerAdapter;
import com.example.duan1.dao.BillDAO;
import com.example.duan1.databinding.FragmentStatisticsBinding;
import com.example.duan1.model.Bill;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatisticsFragment extends BaseFragment<FragmentStatisticsBinding> {
    public static String TAG = "Thống kê";

    public static StatisticsFragment newInstance() {

        Bundle args = new Bundle();

        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics;
    }

    @Override
    protected void initEvent() {
        chooseDate(binding.btntuNgay, binding.edttuNgay);
        chooseDate(binding.btndenNgay, binding.edtdenNgay);
        chooseDate(binding.edtdenNgay, binding.edtdenNgay);
        chooseDate(binding.edttuNgay, binding.edttuNgay);

        binding.btndoanhThu.setOnClickListener(v -> {
            if (binding.edttuNgay.getText().toString().equals("") || binding.edtdenNgay.getText().toString().equals("")) {
                binding.edttuNgay.setError("Không được để trống");
                binding.edtdenNgay.setError("Không được để trống");
                Toast.makeText(getContext(), "Chưa chọn ngày", Toast.LENGTH_SHORT).show();
                return;
            }
            String tuNgay = binding.edttuNgay.getText().toString();
            String denNgay = binding.edtdenNgay.getText().toString();

            int result = tuNgay.compareTo(denNgay);

            if (result > 0) {
                Toast.makeText(getContext(), "Ngày bắt đầu phải trước hoặc cùng ngày kết thúc", Toast.LENGTH_SHORT).show();
                return;
            }

            BillDAO billDAO = new BillDAO(getContext());

            List<Bill> list = billDAO.getTotalOrder(tuNgay, denNgay);
            binding.tvTongtien.setText("Tổng doanh thu: "+ String.format("%,d", billDAO.getTotal(tuNgay, denNgay)) + "VNĐ");
            binding.tvTongdonhang.setText("Tổng đơn hàng: " + list.size() + "");

            if (list.size() == 0) {
                binding.noS.setVisibility(View.VISIBLE);
            }else {
                binding.noS.setVisibility(View.GONE);
            }

            binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rcv.setAdapter(new OrderManagerAdapter(list, getContext()) {
                @Override
                public void onItemClick(int position) {

                }
            });
        });
    }

    private void chooseDate(View view1, EditText editText) {
        view1.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {

                if (dayOfMonth < 10) {
                    editText.setText(year + "-" + (month + 1) + "-0" + dayOfMonth);
                }
                if (month + 1 < 10) {
                    editText.setText(year + "-0" + (month + 1) + "-" + dayOfMonth);
                }
                if (dayOfMonth < 10 && month + 1 < 10) {
                    editText.setText(year + "-0" + (month + 1) + "-0" + dayOfMonth);
                }
                if (dayOfMonth >= 10 && month + 1 >= 10) {
                    editText.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
