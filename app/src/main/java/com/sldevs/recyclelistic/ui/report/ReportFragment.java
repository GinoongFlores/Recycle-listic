package com.sldevs.recyclelistic.ui.report;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.sldevs.recyclelistic.LogIn;
import com.sldevs.recyclelistic.R;
import com.sldevs.recyclelistic.ReportPane;

public class ReportFragment extends Fragment {

    TextView btnGoReport;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_report, container, false);
        btnGoReport = root.findViewById(R.id.btnGoReport);

        btnGoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ReportPane.class);
                startActivity(i);
            }
        });
        return root;
    }
}