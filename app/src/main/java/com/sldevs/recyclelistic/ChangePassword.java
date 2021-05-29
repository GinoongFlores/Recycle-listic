package com.sldevs.recyclelistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {
    EditText etCurrentPassword,etNewPassword;
    Button btnChangePassword;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(ChangePassword.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(ChangePassword.this,R.color.green));
        }
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Recyclelistic")
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etCurrentPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();

                if (password.isEmpty()) {
                    etCurrentPassword.setError("Password is required");
                    etCurrentPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    etCurrentPassword.setError("Minimum length of password should be 6");
                    etCurrentPassword.requestFocus();
                    return;
                }

                if (newPassword.isEmpty()) {
                    etNewPassword.setError("Password is required");
                    etNewPassword.requestFocus();
                    return;
                }

                if (newPassword.length() < 6) {
                    etNewPassword.setError("Minimum length of password should be 6");
                    etNewPassword.requestFocus();
                    return;
                }

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                            AccountNumber accountNumber = snapshot1.getValue(AccountNumber.class);
//                            String txt = accountNumber.getNumber();
//                            list.add(txt);
//                            etEmail.setText(list.toString());
//                        }
                        try{
                            String name = snapshot.child("password").getValue().toString();
                            Log.d("Catched Password:  ", name);

                            if(etCurrentPassword.getText().toString().equalsIgnoreCase(name)){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        new AlertDialog.Builder(ChangePassword.this).setTitle("Password Successfully Changed").setMessage("You will be logged out after clicking okay...").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                FirebaseAuth.getInstance().signOut();
                                                finish();
                                                Intent i = new Intent(ChangePassword.this, LogIn.class);
                                                startActivity(i);
                                            }
                                        }).show();
                                        FirebaseAuth.getInstance().signOut();
                                        finish();
                                        Intent i = new Intent(ChangePassword.this, LogIn.class);
                                        startActivity(i);
                                    }
                                });

                            }else{
                                new AlertDialog.Builder(ChangePassword.this).setTitle("Wrong Password").setMessage("Please type the correct password.").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();

                            }
                        }catch (NullPointerException e){

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });


    }
}