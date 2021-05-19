package com.sldevs.mainmenu.ui.garbage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sldevs.mainmenu.R;
import com.sldevs.mainmenu.ui.craft.CraftViewModel;

public class GarbageFragment extends Fragment {

    private CraftViewModel craftViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        craftViewModel =
                new ViewModelProvider(this).get(CraftViewModel.class);
        View root = inflater.inflate(R.layout.fragment_garbage, container, false);

        return root;
    }
}