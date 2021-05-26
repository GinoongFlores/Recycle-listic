package com.sldevs.recyclelistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AfterSplash extends AppCompatActivity {
    Button splashLogIn,splashSignUp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_splash);
        mAuth = FirebaseAuth.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(AfterSplash.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(AfterSplash.this,R.color.green));
        }

        splashLogIn = findViewById(R.id.splashLogIn);
        splashSignUp = findViewById(R.id.splashSignUp);

        splashLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterSplash.this, LogIn.class);
                startActivity(i);
            }
        });
        splashSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterSplash.this, SignUp.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, UserSide.class));
        }
    }
}