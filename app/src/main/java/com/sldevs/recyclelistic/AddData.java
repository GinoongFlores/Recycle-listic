package com.sldevs.recyclelistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class AddData extends AppCompatActivity {
    EditText etDay,etYear,etPaperAddData,etPlasticAddData,etMetalAddData;
    Button addNewData,btnBackAddData;
    Spinner spinMonth;
    private FirebaseAuth mAuth;
    FirebaseStorage storage;
    DatabaseReference myRef;
    private String addDataCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        btnBackAddData = findViewById(R.id.btnBackAddData);
        btnBackAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(AddData.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(AddData.this,R.color.green));
        }
        addDataCity = getIntent().getExtras().getString("toAddData");
        etYear = findViewById(R.id.year);
        etPaperAddData = findViewById(R.id.etPaperAddData);
        etPlasticAddData = findViewById(R.id.etPlasticAddData);
        etMetalAddData = findViewById(R.id.etMetalAddData);
        spinMonth = findViewById(R.id.month);
        etDay = findViewById(R.id.day);

        addNewData = findViewById(R.id.btnAddNewData);

        addNewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });



    }
    public void addData() {
        new android.app.AlertDialog.Builder(AddData.this).setTitle("Are you sure you want to add this data?").setMessage("Double check it to make it sure").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String month = spinMonth.getSelectedItem().toString();
                String day = etDay.getText().toString().trim();
                String year = etYear.getText().toString().trim();
                String paper = etPaperAddData.getText().toString().trim();
                String plastic = etPlasticAddData.getText().toString().trim();
                String metal = etMetalAddData.getText().toString().trim();
                String mmdd = month + day;
                String mmyy = month + year;
                String mmddyy = month + day + "," +year;



//        progressBar.setVisibility(View.VISIBLE);
                DataAdd data = new DataAdd(addDataCity, mmdd,mmyy,mmddyy,year,paper, plastic,metal);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference uniqueKeyRef = ref.child("DataCollection").child(addDataCity).child(mmddyy);
                uniqueKeyRef.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddData.this,"Successfully Added!",Toast.LENGTH_LONG).show();
                    }
                });


            }
        }).setNegativeButton("Check", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }
}
