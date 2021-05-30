package com.sldevs.recyclelistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.tomer.fadingtextview.FadingTextView;

import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class MainActivity extends AppCompatActivity {
    private FadingTextView fadingTextView;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final KonfettiView konfettiView = findViewById(R.id.konfettiView);

        konfettiView.build()
                .addColors(Color.WHITE, Color.GREEN)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this,R.color.green));
        }
//        fadingTextView = findViewById(R.id.fading_text_view);
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