package com.sldevs.recyclelistic.ui.craft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sldevs.recyclelistic.R;
import com.sldevs.recyclelistic.ShowChannel;

public class CraftFragment extends Fragment {

    ImageView firstChannel,secondChannel,thirdChannel,fourthChannel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_craft, container, false);
        firstChannel = root.findViewById(R.id.firstChannel);
        secondChannel = root.findViewById(R.id.secondChannel);
        thirdChannel = root.findViewById(R.id.thirdChannel);
        fourthChannel = root.findViewById(R.id.fourthChannel);

        firstChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ShowChannel.class);
                i.putExtra("channel", "first");
                startActivity(i);
            }
        });

        secondChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ShowChannel.class);
                i.putExtra("channel", "second");
                startActivity(i);
            }
        });

        thirdChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ShowChannel.class);
                i.putExtra("channel", "third");
                startActivity(i);
            }
        });

        fourthChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ShowChannel.class);
                i.putExtra("fourthChannel", "fourth");
                startActivity(i);
            }
        });

        return root;

    }
}