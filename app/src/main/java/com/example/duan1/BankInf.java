package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.duan1.user.MainCustomer;

public class BankInf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_inf);
        TextView idBill = findViewById(R.id.tvIdBill);
        Button btnBack = findViewById(R.id.btnBack);
        Intent intent = getIntent();
        int i = intent.getIntExtra("idBill", 1);
        idBill.setText("Nội dung chuyển khoản: " + i);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BankInf.this, MainCustomer.class));
            }
        });
    }
}