package com.sldevs.recyclelistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.format.DateFormat;
import android.widget.Toast;

public class AddData extends AppCompatActivity {
    EditText etDay,etYear,etValue;
    Button addNewData;
    Spinner spinMonth,spinMaterial;
    private FirebaseAuth mAuth;
    FirebaseStorage storage;
    private String addDataCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        Calendar calendar = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(AddData.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(AddData.this,R.color.green));
        }
        addDataCity = getIntent().getExtras().getString("toAddData");
        etYear = findViewById(R.id.etYear);
        etValue = findViewById(R.id.etValue);
        spinMonth = findViewById(R.id.spinMonth);
        spinMaterial = findViewById(R.id.spinnerMaterial);
        etDay = findViewById(R.id.etDay);

        addNewData = findViewById(R.id.btnAddNewData);

        addNewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });



    }
    public void addData() {
        String month = spinMonth.getSelectedItem().toString();
        String day = etDay.getText().toString().trim();
        String year = etYear.getText().toString().trim();
        String item = spinMaterial.getSelectedItem().toString();
        String value = etValue.getText().toString().trim();
        int dayy = Integer.parseInt(day);
        if ((month == "January") || (month == "March") || (month == "May") || (month == "July") || (month == "August") || (month == "October") || (month == "December")) {
            if (dayy > 31) {
                etDay.setError("There is only 31 days in " + month);
                etDay.requestFocus();
                return;
            }
        }
        if ((month == "April") || (month == "June") || (month == "September") || (month == "November")) {
            if (dayy > 30) {
                etDay.setError("There is only 30 days in " + month);
                etDay.requestFocus();
                return;
            }
        }
        if (month == "Febuary") {
            if (dayy > 29) {
                etDay.setError("There is only 28/29 days in " + month);
                etDay.requestFocus();
                return;
            }
        }
//        progressBar.setVisibility(View.VISIBLE);
        DataAdd data = new DataAdd(addDataCity, month, day, year, item, value);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String key = ref.child("DataCollection").push().getKey();
        DatabaseReference uniqueKeyRef = ref.child("DataCollection").child(addDataCity).child(key);
        uniqueKeyRef.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddData.this,"Successfully Added!",Toast.LENGTH_LONG).show();
            }
        });


    }
}