package com.example.duan1.activity;



import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.example.duan1.R;
import com.example.duan1.database.DBHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ChooseImage extends AppCompatActivity {

    int REQUEST_CODE_CAMERA = 1;
    int REQUEST_CODE_GALLERY = 2;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_background_main);
        TextView btncamera = findViewById(R.id.btncamera);
        TextView btngallery = findViewById(R.id.gallery);
        iv = findViewById(R.id.test);
        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        ChooseImage.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA
                );
            }
        });
        btngallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        ChooseImage.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Bitmap bitmap = null;
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            bitmap = (Bitmap) data.getExtras().get("data");
        }
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            } else {
                Toast.makeText(ChooseImage.this, "Bạn không cho phép mở camera!", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(ChooseImage.this, "Bạn không cho phép truy cập kho ảnh", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public Bitmap cropBitmapToScreenRatio(Bitmap bitmap, Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        float screenRatio = (float) screenWidth / (float) screenHeight;
        float bitmapRatio = (float) bitmapWidth / (float) bitmapHeight;

        int x, y, width, height;
        if (bitmapRatio > screenRatio) {
            width = (int) ((float) bitmapHeight * screenRatio);
            height = bitmapHeight;
            x = (bitmapWidth - width) / 2;
            y = 0;
        } else {
            width = bitmapWidth;
            height = (int) ((float) bitmapWidth / screenRatio);
            x = 0;
            y = (bitmapHeight - height) / 2;
        }

        return Bitmap.createBitmap(bitmap, x, y, width, height);
    }

//    private byte[] bitmapToByte(Bitmap bitmap) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        if (bitmap.getByteCount() <= 8000000) {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        } else if (bitmap.getByteCount() <= 10000000) {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
//        } else {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
//        }
//
//        byte[] bytes = byteArrayOutputStream.toByteArray();
//
//        return bytes;
//    }
}