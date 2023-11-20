package com.example.duan1.admin.ui.fragment.productmanagement;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.dao.ProductDAO;
import com.example.duan1.dao.ProductDetailDAO;
import com.example.duan1.databinding.FragmentProductManagementBinding;
import com.example.duan1.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductManagementFragment extends BaseFragment<FragmentProductManagementBinding> {
    ProductDAO productDAO;
    List<Product> list;
    AdapterProductManagement adapter;
    public static String TAG = "Quản lý sản phẩm";

    public static ProductManagementFragment newInstance() {
        Bundle args = new Bundle();

        ProductManagementFragment fragment = new ProductManagementFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_management;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    item();
        loadData();
        addProduct();

    }
    @Override
    public String getTAG() {
        return TAG;
    }
    public  void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rcvProduct.setLayoutManager(linearLayoutManager);
        productDAO = new ProductDAO(getContext());
        list = productDAO.getAll();
        adapter = new AdapterProductManagement(getContext(), list);
        binding.rcvProduct.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void addProduct(){
        binding.fltAddPro.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.dialog_add_products, null);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();

            EditText edtName = view.findViewById(R.id.edt_name_addpro);
            EditText edtPrice = view.findViewById(R.id.edt_price_addpro);
            EditText edtmota = view.findViewById(R.id.edt_mota_addpro);
            Spinner spnRole = view.findViewById(R.id.spn_addpro);
            Button btnThem = view.findViewById(R.id.btn_submit_addpro);
            Button btnHuy = view.findViewById(R.id.btn_canaddpro);

            // Tạo danh sách dữ liệu
            List<String> data = new ArrayList<>();
            data.add("Còn hàng");
            data.add("Hết hàng");

            // Tạo Adapter để đổ dữ liệu vào Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);

            // Định dạng Spinner
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnRole.setAdapter(adapter);

            btnThem.setOnClickListener(v1 -> {
                Product product = new Product();
                String name = edtName.getText().toString();
                String price = edtPrice.getText().toString();
                String status = spnRole.getSelectedItem().toString();
                String mota = edtmota.getText().toString();


                if( name.isEmpty() || price.isEmpty()|| mota.isEmpty()) {
                    Toast.makeText(getContext(), "Các trươờng không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                product.setName(name);
                product.setPrice(Integer.parseInt(price));
                product.setStatus(status);
                    if(productDAO.insert(product)){;

                        ProductDetailDAO productDetailDAO = new ProductDetailDAO(getContext());
                        productDetailDAO.insert(list.size()+1, mota);

                        Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        loadData();
                    }else{
                        Toast.makeText(getContext(), "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                    }

            });
            btnHuy.setOnClickListener(v1 -> alertDialog.dismiss());
            alertDialog.show();
        });

    }
    public void item() {
        List<String> data = new ArrayList<>();
        data.add("Chọn sắp xếp");
        data.add("Sắp xếp tăng dần");
        data.add("Sắp xếp giảm dần");

        // Tạo Adapter để đổ dữ liệu vào Spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);

        // Định dạng Spinner
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán Adapter cho Spinner
        binding.spinnerProduct.setAdapter(adapter1);
        binding.spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (data.get(i).equals("Sắp xếp tăng dần")){
                    adapter.sort2();
                    Toast.makeText(getContext(), "Đã sắp xếp tăng dần", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }else
                if (data.get(i).equals("Sắp xếp giảm dần")){
                    adapter.sort1();
                    Toast.makeText(getContext(), "Đã sắp xếp giảm dần", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
