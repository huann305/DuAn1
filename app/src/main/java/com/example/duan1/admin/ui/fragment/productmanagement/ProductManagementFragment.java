package com.example.duan1.admin.ui.fragment.productmanagement;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.duan1.R;
import com.example.duan1.admin.ui.fragment.BaseFragment;
import com.example.duan1.dao.ProductDAO;
import com.example.duan1.dao.ProductDetailDAO;
import com.example.duan1.databinding.FragmentProductManagementBinding;
import com.example.duan1.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManagementFragment extends BaseFragment<FragmentProductManagementBinding> {
    ProductDAO productDAO;
    List<Product> list;
    AdapterProductManagement adapter;
    String filePath = "";
    ImageView ivImagePro;
    TextView tvStatusImage;
    String linkImage = "";


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

        loadData();
        addProduct();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        configCloudinary();
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
            Spinner spnRole = view.findViewById(R.id.spn_addpro);
            Button btnThem = view.findViewById(R.id.btn_submit_addpro);
            Button btnHuy = view.findViewById(R.id.btn_canaddpro);
            ivImagePro = view.findViewById(R.id.ivImageProduct);
            tvStatusImage = view.findViewById(R.id.tvStatusImage);

            // Tạo danh sách dữ liệu
            List<String> data = new ArrayList<>();
            data.add("Đồ ăn");
            data.add("Nước Uống");

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
                String statusImage = tvStatusImage.getText().toString();

                if( name.isEmpty() || price.isEmpty() || !statusImage.equals("Upload thành công!")) {
                    Toast.makeText(getContext(), "Các trường không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                product.setName(name);
                product.setPrice(Integer.parseInt(price));
                product.setStatus(status);
                product.setImage("" + linkImage);
                Log.i("TAG", "Link ảnh đây nàyyyy: " + linkImage);
                    if(productDAO.insert(product)){;
                        ProductDetailDAO productDetailDAO = new ProductDetailDAO(getContext());
                        productDetailDAO.insert(list.size()+1, "description", product.getImage());
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        loadData();
                    }else{
                        Toast.makeText(getContext(), "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                    }

            });
            ivImagePro.setOnClickListener(v1 -> {
                accessTheGallery();
            });

            btnHuy.setOnClickListener(v1 -> alertDialog.dismiss());
            alertDialog.show();
        });
    }

    public void accessTheGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        myLauncher.launch(i);
    }

    private ActivityResultLauncher<Intent> myLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result == null){
                return;
            }
            if(result.getData() == null){
                return;
            }
            //get the image's file location
            filePath = getRealPathFromUri(result.getData().getData(), getActivity());

            if (result.getResultCode() == RESULT_OK) {
                try {
                    //set picked image to the mProfile
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getData().getData());
                    //hiển thị hình ảnh lên imgview
                    ivImagePro.setImageBitmap(bitmap);

                    uploadToCloudinary(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    private String getRealPathFromUri(Uri imageUri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if (cursor == null) {
            return imageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    HashMap<String, String> config = new HashMap<>();
    private void configCloudinary() {
        try {
            config.put("cloud_name", "diqi1klub");
            config.put("api_key", "712862419224312");
            config.put("api_secret", "XfHe0kvB7wBt4vgIfbHcoXP8L3M");
            MediaManager.init(getContext(), config);
        }catch (Exception e){
            Log.i("TAG", "configCloudinary: " + e);
        }
    }

    private void uploadToCloudinary(String filePath) {
        Log.d("A", "sign up uploadToCloudinary- ");
        MediaManager.get().upload(filePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                tvStatusImage.setText("Bắt đầu upload");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                tvStatusImage.setText("Đang upload... ");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                tvStatusImage.setText("Upload thành công!");
                //tvStatusImage.setText(":Upload ảnh thành công");
                linkImage = resultData.get("url").toString();
                Log.i("TAG", "linkImage nhaa: " + linkImage);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                tvStatusImage.setText("Lỗi " + error.getDescription());
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                tvStatusImage.setText("Reshedule " + error.getDescription());
            }
        }).dispatch();
    }

}
