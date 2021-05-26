package com.sldevs.recyclelistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.tomer.fadingtextview.FadingTextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private FadingTextView fadingTextView;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this,R.color.green));
        }
        fadingTextView = findViewById(R.id.fading_text_view);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,AfterSplash.class);
                startActivity(intent);
                finish();
            }
        },5500);
    }
//    public void startExample2(View v) {
//        String[] example2 = {"And", "this", "is", "example 2"};
//        fadingTextView.setTexts(example2);
//        fadingTextView.setTimeout(300, TimeUnit.MILLISECONDS);
//        fadingTextView.forceRefresh();
//    }
}