package com.sldevs.recyclelistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DataCollection extends AppCompatActivity {
    Button btnViewRecords,btnAddData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        String dataCity = getIntent().getExtras().getString("toDataCollection");
        btnViewRecords = findViewById(R.id.btnViewRecords);
        btnAddData = findViewById(R.id.btnAddData);
        TextView show = findViewById(R.id.showCity);
        show.setText(dataCity);
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