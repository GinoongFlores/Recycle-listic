package com.sldevs.recyclelistic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViewRecords extends AppCompatActivity {
    Spinner searchMonth;
    EditText searchDay,searchYear;
    Button btnBackViewRecords,btnYY,btnMMDD,btnMMYY,btnMMDDYY;
    private PieChart pieChart;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<String> materials,values;

    ArrayList<PieEntry> entries;
    int getPaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);
        String viewRecordsCity = getIntent().getExtras().getString("toViewRecords");


        materials = new ArrayList<>();
        materials.add("Plastic");
        materials.add("Paper");
        materials.add("Metals");
        btnBackViewRecords = findViewById(R.id.btnBackViewRecords);
        btnBackViewRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(ViewRecords.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(ViewRecords.this,R.color.green));
        }


        database = FirebaseDatabase.getInstance();
        pieChart = findViewById(R.id.activity_main_piechart);
        myRef = database.getReference("DataCollection");

        setupPieChart();
        total();

        btnYY = findViewById(R.id.btnYY);
        btnYY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewRecords.this, TotalYear.class);
                i.putExtra("toYY",viewRecordsCity);
                startActivity(i);
            }
        });

        btnMMYY = findViewById(R.id.btnMMYY);
        btnMMYY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewRecords.this, TotalMonthYear.class);
                i.putExtra("toMMYY",viewRecordsCity);
                startActivity(i);
            }
        });

        btnMMDDYY = findViewById(R.id.btnMMDDYY);
        btnMMDDYY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewRecords.this, TotalMonthDayYear.class);
                i.putExtra("toMMDDYY",viewRecordsCity);
                startActivity(i);
            }
        });
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Total Data Collection");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

    }

    private void loadPieChartData() {
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Material Legend");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

//    }

    public void total(){
        String viewRecordsCity = getIntent().getExtras().getString("toViewRecords");
        entries = new ArrayList<>();
        values = new ArrayList<>();

        Query paperQuery = myRef.child(viewRecordsCity).orderByChild("city").equalTo(viewRecordsCity);
        paperQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalPaper = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String valuePaper = dataSnapshot.child("paper").getValue().toString();
                    totalPaper += Integer.parseInt(valuePaper);
                    Log.d("Value: ", String.valueOf(totalPaper));
                }
                Float paper = (float) totalPaper;
                entries.add(new PieEntry(paper, "Paper: " + paper));
                loadPieChartData();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Query plasticQuery = myRef.child(viewRecordsCity).orderByChild("city").equalTo(viewRecordsCity);
        plasticQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalPlastic = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String valuePlastic = dataSnapshot.child("plastic").getValue().toString();
                    totalPlastic += Integer.parseInt(valuePlastic);
                    Log.d("Value: ", String.valueOf(totalPlastic));
                }
                Float plastic = (float) totalPlastic;
                entries.add(new PieEntry(plastic, "Plastic: " + plastic));
                loadPieChartData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query metalQuery = myRef.child(viewRecordsCity).orderByChild("city").equalTo(viewRecordsCity);
        metalQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalMetal = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String valueMetal = dataSnapshot.child("metal").getValue().toString();
                    totalMetal += Integer.parseInt(valueMetal);
                    Log.d("Value: ", String.valueOf(totalMetal));
                }
                Float metal = (float) totalMetal;
                entries.add(new PieEntry(metal, "Metal: " + metal));
                loadPieChartData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}



