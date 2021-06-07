package com.sldevs.recyclelistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DataCollection extends AppCompatActivity {
    Button btnViewRecords,btnAddData,btnBackDataCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        btnBackDataCollection = findViewById(R.id.btnBackDataCollection);
        btnBackDataCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(DataCollection.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(DataCollection.this,R.color.green));
        }
        String dataCity = getIntent().getExtras().getString("toDataCollection");
        btnViewRecords = findViewById(R.id.btnViewRecords);
        btnAddData = findViewById(R.id.btnAddData);
        TextView show = findViewById(R.id.showCity);
        show.setText(dataCity + " ADMIN SIDE");
        btnViewRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DataCollection.this,ViewRecords.class);
                i.putExtra("toViewRecords",dataCity);
                startActivity(i);
            }
        });

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DataCollection.this,AddData.class);
                i.putExtra("toAddData",dataCity);
                startActivity(i);
            }
        });


    }
}