package com.example.duan1.admin.ui.fragment.productmanagement;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.duan1.R;
import com.example.duan1.dao.ProductDAO;
import com.example.duan1.databinding.ItemProductManagerBinding;
import com.example.duan1.model.Cart;
import com.example.duan1.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AdapterProductManagement extends RecyclerView.Adapter<AdapterProductManagement.ViewHolder> {
    private Context context;
    private List<Product> list;
    TextView tvStatusImage_up;
    public abstract void click(int position);
    public AdapterProductManagement(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductManagerBinding binding = ItemProductManagerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = list.get(position);
        holder.binding.tvTitleProduct.setText(product.getName());
        holder.binding.tvPriceProduct.setText("Đơn giá: " + product.getPrice() + " VND");
        holder.binding.tvQuantityProduct.setText("Số lượng: " + product.getQuantitySold() + "");
        holder.binding.tvStatusProduct.setText("Trạng thái: " + product.getStatus());
        if(product.getImage() != null){
            Glide.with(context).load(product.getImage()).into(holder.binding.ivImageProduct);
        }else {
            Glide.with(context).load(R.drawable.improduct1).into(holder.binding.ivImageProduct);
        }

        //chọn lại ảnh
        holder.binding.ivImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        holder.binding.ivUpdateProduct.setOnClickListener(v -> {
            click(position);
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_products, null);
//            builder.setView(view);
//            builder.create();
//            AlertDialog alertDialog = builder.create();
//
//            EditText edtTenSP = view.findViewById(R.id.edt_title_updatepro);
//            EditText edtDonGia = view.findViewById(R.id.edt_price_updatepro);
//            Spinner spinnerTrangThai = view.findViewById(R.id.spn_updatepro);
//            Button btnUpdate = view.findViewById(R.id.btn_submit_updatepro);
//            Button btnCancel = view.findViewById(R.id.btn_canupdatepro);
//            ivUpdate = view.findViewById(R.id.iv_update_product);
//            tvStatusImage_up = view.findViewById(R.id.tvStatusImage_up);
//
//            List<String> data = new ArrayList<>();
//            data.add("Còn hàng");
//            data.add("Hết hàng");
//
//            // Tạo Adapter để đổ dữ liệu vào Spinner
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, data);
//
//            // Định dạng Spinner
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//            // Gán Adapter cho Spinner
//            spinnerTrangThai.setAdapter(adapter);
//            edtTenSP.setText(product.getName());
//            edtDonGia.setText(String.valueOf(product.getPrice()));
//            spinnerTrangThai.setSelection(data.indexOf(product.getStatus()));
//
//            if(product.getImage() != null){
//                Glide.with(context).load(product.getImage()).into(ivUpdate);
//            }else {
//                Glide.with(context).load(R.drawable.improduct1).into(ivUpdate);
//            }
//
//            if (product.getStatus().equals("Còn hàng")) {
//                spinnerTrangThai.setSelection(0);
//            } else {
//                spinnerTrangThai.setSelection(1);
//            }
//
//            ////
//
//            btnUpdate.setOnClickListener(v1 -> {
//                ProductDAO productDAO = new ProductDAO(context);
//                String status = spinnerTrangThai.getSelectedItem().toString();
//                String name = edtTenSP.getText().toString();
//                String gia = edtDonGia.getText().toString();
//                product.setStatus(status);
//                product.setName(name);
//                product.setPrice(Integer.parseInt(gia));
//
//
//                if (productDAO.updatee(product, product.getId())) {
//                    Toast.makeText(context, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
//                    alertDialog.dismiss();
//                } else {
//                    Toast.makeText(context, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
//                }
//            });
//            btnCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    alertDialog.dismiss();
//                }
//            });
//
//            alertDialog.show();
//
        });
    }
    public void sort2(){
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (o1.getPrice() > o2.getPrice()){
//                    Toast.makeText(context, "Đã sắp xếp tăng dần", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    return 1;
                }else {
                    if (o1.getPrice() == o2.getPrice()){
                        return 0;
                    }else return -1;
                }
            }
        });
    }
    public void sort1(){
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (o1.getPrice() < o2.getPrice()){
//                    Toast.makeText(context, "Đã sắp xếp giảm dần", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    return 1;
                }else {
                    if (o1.getPrice() == o2.getPrice()){
                        return 0;
                    }else return -1;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemProductManagerBinding binding;

        public ViewHolder(@NonNull ItemProductManagerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
