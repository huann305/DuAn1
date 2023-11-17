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
import com.example.duan1.dao.ProductDAO;
import com.example.duan1.model.Bill;
import com.example.duan1.model.BillDetail;
import com.example.duan1.model.Product;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    List<BillDetail> list;

    public BillAdapter(List<BillDetail> list, Context context) {
        this.list = list;
        billDAO = new BillDAO(context);
        productDAO = new ProductDAO(context);
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_detail, parent, false));
    }

    BillDAO billDAO;
    ProductDAO productDAO;
    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        BillDetail billDetail = list.get(position);


        Bill bill = billDAO.getID(billDetail.getIdBill());
        Product product = productDAO.getID(billDetail.getIdProduct());

        Log.d("TAG", "onBindViewHolder: "+billDetail.toString());
        Log.d("TAG", "onBindViewHolder: "+product.toString());

        holder.tvName.setText(product.getName() + "");
        holder.tvTime.setText(bill.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BillViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView tvName;
        TextView tvTime;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
