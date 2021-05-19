package com.sldevs.mainmenu.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.sldevs.mainmenu.R;
import com.sldevs.mainmenu.ui.craft.CraftFragment;

public class HomeFragment extends Fragment {
    private FragmentActivity myContext;
    private HomeViewModel homeViewModel;
    YouTubePlayerView youTubePlayerView;
    ImageView craftit;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FragmentTransaction replace = transaction.replace(R.id.youtube_fragment, youTubePlayerFragment);
        transaction.commit();

        youTubePlayerFragment.initialize("AIzaSyDwlwKiEYPkipah8ix7qDF8X4exQpDGQnM", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTubePlayer.cueVideo("QGjPyPBpevY");
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });

        craftit = root.findViewById(R.id.craftit);
        craftit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  goToAttract(v);
//                transaction.replace(R.id.nav_craft,new CraftFragment());
//                transaction.commit();
            }
        });



        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        return root;
    }

    public void goToAttract(View v)
    {
        Intent intent = new Intent(getActivity(), CraftFragment.class);
        startActivity(intent);
    }
}