package com.sldevs.recyclelistic;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ShowQRCode extends AppCompatActivity {
    Button btnBackQRCode;
    FirebaseStorage storage;
    ImageView displayQRCode;
    FirebaseAuth mAuth;
    TextView displayEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_q_r_code);
        btnBackQRCode = findViewById(R.id.btnBackQRCode);
        btnBackQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        displayQRCode = findViewById(R.id.displayQRCode);
        displayEmail = findViewById(R.id.displayEmail);
        mAuth = FirebaseAuth.getInstance();
        displayEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        try {
            loadQRCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnBackQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void loadQRCode() throws IOException {
        String id = mAuth.getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference getQR = storageRef.child("QRCode/"+ mAuth.getUid()+ "qrcode.png");
        final long ONE_MEGABYTE = 1024 * 1024;
        getQR.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                displayQRCode.setImageBitmap(bitmap);
            }
        });
    }
}