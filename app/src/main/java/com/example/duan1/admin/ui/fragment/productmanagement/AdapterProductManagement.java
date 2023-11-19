package com.example.duan1.admin.ui.fragment.productmanagement;

import android.app.AlertDialog;
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
import com.example.duan1.dao.ProductDAO;
import com.example.duan1.databinding.ItemProductManagerBinding;
import com.example.duan1.model.Cart;
import com.example.duan1.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdapterProductManagement extends RecyclerView.Adapter<AdapterProductManagement.ViewHolder>{
    private Context context;
    private List<Product> list;

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
        holder.binding.tvPriceProduct.setText("Đơn giá: "+product.getPrice()+" VND");
        holder.binding.tvQuantityProduct.setText("Số lượng: "+product.getQuantitySold()+"");
        holder.binding.tvStatusProduct.setText("Trạng thái: "+product.getStatus());

        holder.binding.ivUpdateProduct.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_products, null);
                builder.setView(view);
                builder.create();
                AlertDialog alertDialog = builder.create();

                EditText edtTenSP = view.findViewById(R.id.edt_title_updatepro);
                EditText edtDonGia = view.findViewById(R.id.edt_price_updatepro);
                Spinner spinnerTrangThai = view.findViewById(R.id.spn_updatepro);
                Button btnUpdate = view.findViewById(R.id.btn_submit_updatepro);
                Button btnCancel = view.findViewById(R.id.btn_canupdatepro);



                List<String> data = new ArrayList<>();
                data.add("Còn hàng");
                data.add("Hết hàng");

                // Tạo Adapter để đổ dữ liệu vào Spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, data);

                // Định dạng Spinner
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Gán Adapter cho Spinner
                spinnerTrangThai.setAdapter(adapter);
            edtTenSP.setText(product.getName());
            edtDonGia.setText(String.valueOf(product.getPrice()));
            spinnerTrangThai.setSelection(data.indexOf(product.getStatus()));

                if(product.getStatus().equals("Còn hàng")) {
                    spinnerTrangThai.setSelection(0);
                } else {
                    spinnerTrangThai.setSelection(1);
                }

                btnUpdate.setOnClickListener(v1 -> {
                    ProductDAO productDAO = new ProductDAO(context);
                    String status = spinnerTrangThai.getSelectedItem().toString();
                    String name = edtTenSP.getText().toString();
                    String gia = edtDonGia.getText().toString();
                    product.setStatus(status);
                    product.setName(name);
                    product.setPrice(Integer.parseInt(gia));

                    if(productDAO.updatee(product, product.getId())) {
                        Toast.makeText(context, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(context, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

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
