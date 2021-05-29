package com.sldevs.recyclelistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminSide extends AppCompatActivity {
    ImageView btnDataCollection,btnScan;
    private String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_side);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(AdminSide.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(AdminSide.this,R.color.green));
        }
        btnDataCollection = findViewById(R.id.btnDataCollection);

        btnScan = findViewById(R.id.btnScan);
        String adminCity = getIntent().getExtras().getString("City");
        TextView tvCity = findViewById(R.id.tvCity);
        tvCity.setText(adminCity.toUpperCase() + " ADMIN SIDE");

        btnDataCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminSide.this,DataCollection.class);
                i.putExtra("toDataCollection" , adminCity);
                startActivity(i);
            }
        });


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminSide.this,ScanQRCode.class);
                i.putExtra("toScanQRCode" , adminCity);
                startActivity(i);
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(AdminSide.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(AdminSide.this,R.color.green));
        }





    }
}