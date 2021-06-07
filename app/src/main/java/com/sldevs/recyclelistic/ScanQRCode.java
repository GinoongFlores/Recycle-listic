package com.sldevs.recyclelistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class ScanQRCode extends AppCompatActivity {

    public static TextView btnScan,addPoints,totalPointss;
    public static EditText etEmailAdd,etPoints;
    FirebaseStorage firebaseStorage;
    DatabaseReference myRef;
    String getPoints;
    String inputPoints;
    Button btnBackScanQRCode;
    String uid;
    String qrCity;
    String acquiredPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r_code);
        totalPointss = findViewById(R.id.totalPoints);
        btnBackScanQRCode = findViewById(R.id.btnBackScanQRCode);
        btnBackScanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(ScanQRCode.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(ScanQRCode.this,R.color.green));
        }
        qrCity = getIntent().getExtras().getString("toScanQRCode");
        etEmailAdd = findViewById(R.id.etEmailAdd);
        addPoints = findViewById(R.id.addPoints);
        etPoints = findViewById(R.id.etPoints);
        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScanQRCode.this, ScanningPane.class);
                startActivity(i);

            }
        });

        addPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmailAdd.getText().toString().isEmpty()){
                    etEmailAdd.setError("Invalid User");
                    etEmailAdd.setFocusable(true);
                    return;
                }
                if(etPoints.getText().toString().isEmpty()){
                    etPoints.setError("Please Input Points");
                    etPoints.setFocusable(true);
                    return;
                }

                uid = etEmailAdd.getText().toString().substring(4, 32);
                Log.d("UID:", uid);
                int totalPoints = Integer.parseInt(etPoints.getText().toString()) + Integer.parseInt(totalPointss.getText().toString());

                FirebaseDatabase.getInstance().getReference("Recyclelistic")
                        .child("Users")
                        .child(uid)
                        .child("points")
                        .setValue(totalPoints).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        Intent i = new Intent(ScanQRCode.this,AdminSide.class);
                        i.putExtra("City", qrCity);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
     });


    }

}