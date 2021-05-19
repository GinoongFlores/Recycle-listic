package com.sldevs.mainmenu.ui.craft;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.sldevs.mainmenu.R;

public class CraftFragment extends Fragment {

    private CraftViewModel craftViewModel;
    Button btnPlay;
    YouTubePlayerView youTubePlayerView;
            YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        craftViewModel =
                new ViewModelProvider(this).get(CraftViewModel.class);

        View root = inflater.inflate(R.layout.fragment_craft, container, false);

//        YouTubePlayerSupportFragment youTubePlayerSupportFragment= YouTubePlayerSupportFragment.newInstance();
//
//        btnPlay = root.findViewById(R.id.btnPlay);
//        youTubePlayerView = root.findViewById(R.id.youtubePlayerView);
//
//        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                youTubePlayer.loadVideo("w9xfXsqIGKk&t=649s");
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//            }
//        };
//
//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                youTubePlayerSupportFragment.initialize("AIzaSyDwlwKiEYPkipah8ix7qDF8X4exQpDGQnM", onInitializedListener);
//            }
//        });
        return root;



    }
}