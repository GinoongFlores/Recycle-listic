package com.sldevs.mainmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {
    EditText emailET,passwordET;
    Button btnLogIn;
    TextView signupTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        btnLogIn = findViewById(R.id.btnLogIn);
        signupTV = findViewById(R.id.signupTV);


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogIn.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        });

//        //hide the nav bar
//        View decorView = getWindow().getDecorView();
//
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
    }
}