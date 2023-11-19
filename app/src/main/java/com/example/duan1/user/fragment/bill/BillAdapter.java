package com.example.duan1.user.fragment.bill;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.dao.BillDAO;
import com.example.duan1.dao.BillDetailDAO;
import com.example.duan1.dao.EmployeeDAO;
import com.example.duan1.dao.ProductDAO;
import com.example.duan1.model.Bill;
import com.example.duan1.model.BillDetail;
import com.example.duan1.model.Product;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    List<Bill> list;
    BillDAO billDAO;
    ProductDAO productDAO;
    EmployeeDAO employeeDAO;
    BillDetailDAO billDetailDAO;

    public BillAdapter(List<Bill> list, Context context) {
        this.list = list;
        billDAO = new BillDAO(context);
        productDAO = new ProductDAO(context);
        employeeDAO = new EmployeeDAO(context);
        billDetailDAO = new BillDetailDAO(context);
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_placed, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = list.get(position);
        holder.tvIdBill.setText("Mã hóa đơn: " + String.valueOf(bill.getId()));
        //holder.tvNameEmployee.setText("Tên nhân viên: " + employeeDAO.getID(bill.getIdEmployee()).getName());
        holder.tvTotalPrice.setText("Tổng tiền: " + billDetailDAO.getTotalPrice(bill.getId()));
        holder.tvDate.setText(bill.getDate());
        holder.tvAddress.setText(bill.getShippingAddress());
        holder.tvStatus.setText("Trạng thái: " + bill.getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BillViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tvIdBill, tvNameEmployee, tvTotalPrice, tvDate, tvAddress, tvStatus;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvIdBill = itemView.findViewById(R.id.tvIdBill);
            tvNameEmployee = itemView.findViewById(R.id.tvNameEmployee);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
