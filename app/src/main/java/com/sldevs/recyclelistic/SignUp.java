package com.sldevs.recyclelistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class SignUp extends AppCompatActivity {
    Button btnCreate,btnBackSignUp,btnGenerate;
    EditText etFullname,etEmailSignUp,etPasswordSignUp,etNumber;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    FirebaseStorage storage;
    CheckBox checkTerms;
    private Uri imageUri;
    StorageReference storageRef;
    ImageView imageQRCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        checkTerms = findViewById(R.id.checkTerms);
        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        etEmailSignUp = findViewById(R.id.etEmailSignUp);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(SignUp.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(SignUp.this,R.color.green));
        }
        btnBackSignUp = findViewById(R.id.btnBackSignUp);
        btnCreate = findViewById(R.id.btnCreate);
        etFullname = findViewById(R.id.etFullaname);
        etEmailSignUp = findViewById(R.id.etEmailSignUp);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);
        etNumber = findViewById(R.id.etNumber);
        imageQRCode = findViewById(R.id.imageQRCode);

        progressBar = findViewById(R.id.progressbar_signup);

        btnBackSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    registerUser();
            }
        });
//        btnGenerate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                generateQRCode();
//            }
//        });
    }
    private void registerUser() {


        String fullname = etFullname.getText().toString().trim();
        String email = etEmailSignUp.getText().toString().trim();
        String password = etPasswordSignUp.getText().toString().trim();
        String number = etNumber.getText().toString().trim();

        if (fullname.isEmpty()) {
            etFullname.setError("Name is required");
            etFullname.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmailSignUp.setError("Email is required");
            etEmailSignUp.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailSignUp.setError("Please enter a valid email");
            etEmailSignUp.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPasswordSignUp.setError("Password is required");
            etPasswordSignUp.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPasswordSignUp.setError("Minimum length of password should be 6");
            etPasswordSignUp.requestFocus();
            return;
        }

        if (number.isEmpty()) {
            etNumber.setError("Number is required");
            etNumber.requestFocus();
            return;
        }
        if (number.length() != 11 ) {
            etNumber.setError("Invalid Number");
            etNumber.requestFocus();
            return;
        }
        if(!checkTerms.isChecked()){
            new android.app.AlertDialog.Builder(SignUp.this).setTitle("Terms and Agreements").setMessage("Please check Terms and Agreements").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            return;
        }
//        if(){
//            new AlertDialog.Builder(SignUp.this).setTitle("Please generate QR Code").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    return;
//                }
//            });
//        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    UserDetails user = new UserDetails(fullname,email,password,number,"0");

                    FirebaseDatabase.getInstance().getReference("Recyclelistic")
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                generateQRCode();
                                storeImage();
                                storeProfile();
//                                sendVerification();
                                Toast.makeText(getApplicationContext(), "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(SignUp.this, UserSide.class));
                            }else{
                                etFullname.setText("");
                                etEmailSignUp.setText("");
                                etPasswordSignUp.setText("");
                                etNumber.setText("");
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Email is already been registered", Toast.LENGTH_SHORT).show();

                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
    public void storeProfile(){
        String id = FirebaseAuth.getInstance().getUid();
        StorageReference qrcode = storageRef.child(id + ".png");
        StorageReference qrcodeimageref = storageRef.child("ProfilePicture/" + id + "profile.png");
        qrcode.getName().equals(qrcodeimageref.getName());
        qrcode.getPath().equals(qrcodeimageref.getPath());


        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.default_avatar_logo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = qrcodeimageref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

    }
    public void storeImage(){
        String id = FirebaseAuth.getInstance().getUid();
        StorageReference qrcode = storageRef.child(id + ".png");
        StorageReference qrcodeimageref = storageRef.child("QRCode/" + id + "qrcode.png");
        qrcode.getName().equals(qrcodeimageref.getName());
        qrcode.getPath().equals(qrcodeimageref.getPath());

        imageQRCode.setDrawingCacheEnabled(true);
        imageQRCode.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageQRCode.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = qrcodeimageref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

    }
    public void sendVerification(){
        String userID = mAuth.getCurrentUser().getUid();
        FirebaseUser user = mAuth.getCurrentUser();

        if(!user.isEmailVerified()){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    new AlertDialog.Builder(SignUp.this).setTitle("Verification has been sent.").setMessage("Please check your email.").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }

                });
                }
            });
        }
    }
    public void generateQRCode(){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        String email = etEmailSignUp.getText().toString().trim();

        if (email.isEmpty()) {
            etEmailSignUp.setError("Email is required");
            etEmailSignUp.requestFocus();
            return;
        }else{
            try{
                BitMatrix bitMatrix = multiFormatWriter.encode("ID: "+mAuth.getCurrentUser().getUid()+"\nEmail: " + etEmailSignUp.getText().toString(), BarcodeFormat.QR_CODE,500,500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageQRCode.setImageBitmap(bitmap);


            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


}