package com.sldevs.recyclelistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleObserver;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import org.jetbrains.annotations.NotNull;

public class ShowChannel extends YouTubeBaseActivity {
    private static final int RECOVERY_REQUEST = 1;
    private LifecycleObserver lifecycleOwner;
    ImageView channelLogo;
    TextView channelName,firstIntent,secondIntent;
    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView firstVideo,secondVideo, thirdVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_channel);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(ShowChannel.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(ShowChannel.this,R.color.green));
        }
        String channel = getIntent().getExtras().getString("channel").toString();
        switch (channel) {
            case "first":
                firstIntent = findViewById(R.id.firstIntent);
                firstIntent.setVisibility(View.VISIBLE);
                firstIntent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent  =new Intent(Intent.ACTION_VIEW);
                            intent.setPackage("com.google.android.youtube");
                            intent.setData(Uri.parse("https://www.youtube.com/channel/UC57XAjJ04TY8gNxOWf-Sy0Q"));
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Intent intent  =new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://www.youtube.com/channel/UC57XAjJ04TY8gNxOWf-Sy0Q"));
                            startActivity(intent);
                        }
                    }
                });
                channelName = findViewById(R.id.channelName);
                channelName.setText("5-MINUTE CRAFT PLAY");
                channelLogo = findViewById(R.id.channelLogo);
                channelLogo.setImageDrawable(ShowChannel.this.getResources().getDrawable(R.drawable.first_channel));
                String first_firstID = "JTa75n-iai8";
                String first_secondID = "uEDONaQHi38";
                String first_thirdID = "LlpArDpqPWM";
                firstVideo = findViewById(R.id.firstVideo);
                firstVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {

                        youTubePlayer.cueVideo(first_firstID, 0);
                        super.onReady(youTubePlayer);
                    }
                });

                secondVideo = findViewById(R.id.secondVideo);
                secondVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {

                        youTubePlayer.cueVideo(first_secondID, 0);
                        super.onReady(youTubePlayer);
                    }
                });

                thirdVideo = findViewById(R.id.thirdVideo);

                thirdVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {

                        youTubePlayer.cueVideo(first_thirdID, 0);

                        super.onReady(youTubePlayer);
                    }
                });
                break;
            case "second":
                secondIntent = findViewById(R.id.secondIntent);
                secondIntent.setVisibility(View.VISIBLE);
                secondIntent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent  =new Intent(Intent.ACTION_VIEW);
                            intent.setPackage("com.google.android.youtube");
                            intent.setData(Uri.parse("https://www.youtube.com/channel/UC9yVILEwmqgO5NyPjxj-Org"));
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Intent intent  =new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://www.youtube.com/channel/UC9yVILEwmqgO5NyPjxj-Org"));
                            startActivity(intent);
                        }
                    }
                });
                channelName = findViewById(R.id.channelName);
                channelName.setText("MISS DEBBIE DIY");
                channelLogo = findViewById(R.id.channelLogo);
                channelLogo.setImageDrawable(ShowChannel.this.getResources().getDrawable(R.drawable.second_channel));
                String second_firstID = "PTXTshcn9TU";
                String second_secondID = "xSe2asiPRKs";
                String second_thirdID = "yFrzjBcSbR4";
                secondVideo = findViewById(R.id.secondVideo);
                secondVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {

                        youTubePlayer.cueVideo(second_firstID, 0);
                        super.onReady(youTubePlayer);
                    }
                });

                secondVideo = findViewById(R.id.secondVideo);
                secondVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {

                        youTubePlayer.cueVideo(second_secondID, 0);
                        super.onReady(youTubePlayer);
                    }
                });

                thirdVideo = findViewById(R.id.thirdVideo);

                thirdVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {

                        youTubePlayer.cueVideo(second_thirdID, 0);

                        super.onReady(youTubePlayer);
                    }
                });
                break;
            case "third":
                String third_firstID = "HkHEJEzMKwc";
                String third_secondID = "HkHEJEzMKwc";
                String third_thirdID = "HkHEJEzMKwc";
                firstVideo = findViewById(R.id.firstVideo);
                firstVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {

                        youTubePlayer.cueVideo(third_firstID, 0);
                        super.onReady(youTubePlayer);
                    }
                });

                secondVideo = findViewById(R.id.secondVideo);
                secondVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.cueVideo(third_secondID, 0);
                        super.onReady(youTubePlayer);
                    }
                });

                thirdVideo = findViewById(R.id.thirdVideo);

                thirdVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {

                        youTubePlayer.cueVideo(third_thirdID, 0);

                        super.onReady(youTubePlayer);
                    }
                });
                break;
        }

//        String channel = getIntent().getExtras().getString("channel").toString();
//        switch (channel){
//            case "first":
//                firstVideo = findViewById(R.id.firstVideo);
//                firstVideo.initialize("AIzaSyCmWplVNnU_9vyZf9c3XAYtvpi8ZQH8wdw", new YouTubePlayer.OnInitializedListener() {
//                    @Override
//                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                        if (!b) {
//                            youTubePlayer.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
//                        }
//                    }
//
//                    @Override
//                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                        if (youTubeInitializationResult.isUserRecoverableError()) {
//                            youTubeInitializationResult.getErrorDialog(ShowChannel.this, RECOVERY_REQUEST).show();
//                        } else {
//                            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
//                            Toast.makeText(ShowChannel.this, "Error", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                secondVideo = findViewById(R.id.secondVideo);
//                secondVideo.initialize("AIzaSyCmWplVNnU_9vyZf9c3XAYtvpi8ZQH8wdw", new YouTubePlayer.OnInitializedListener() {
//                    @Override
//                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                        if (!b) {
//                            youTubePlayer.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
//                        }
//                    }
//
//                    @Override
//                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                        if (youTubeInitializationResult.isUserRecoverableError()) {
//                            youTubeInitializationResult.getErrorDialog(ShowChannel.this, RECOVERY_REQUEST).show();
//                        } else {
//                            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
//                            Toast.makeText(ShowChannel.this, "Error", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                break;
//            case "second":
//                break;
//
//        }
//
//
//
//
//
//        thirdVideo = findViewById(R.id.thirdVideo);





    }
}