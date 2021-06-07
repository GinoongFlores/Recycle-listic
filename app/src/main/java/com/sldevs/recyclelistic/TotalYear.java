package com.sldevs.recyclelistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TotalYear extends AppCompatActivity {
    Button btnSearchYear, btnBackViewRecords;
    EditText etSearchYear;
    private PieChart pieChart;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<PieEntry> entries;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_year);
        btnBackViewRecords = findViewById(R.id.btnBackViewRecords);
        btnBackViewRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSearchYear = findViewById(R.id.btnSearchYear);
        etSearchYear = findViewById(R.id.etSearchYear);
        city = getIntent().getExtras().getString("toYY");

        database = FirebaseDatabase.getInstance();
        pieChart = findViewById(R.id.activity_main_piechart);
        myRef = database.getReference("DataCollection");

        setupPieChart();
        btnSearchYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entries = new ArrayList<>();
                Query paperQuery = myRef.child(city).orderByChild("year").equalTo(etSearchYear.getText().toString());
                paperQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int totalPaper = 0;
                        int totalPlastic = 0;
                        int totalMetal = 0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String valuePaper = dataSnapshot.child("paper").getValue().toString();
                            String valuePlastic = dataSnapshot.child("plastic").getValue().toString();
                            String valueMetal = dataSnapshot.child("metal").getValue().toString();
                            totalPaper += Integer.parseInt(valuePaper);
                            totalPlastic += Integer.parseInt(valuePlastic);
                            totalMetal += Integer.parseInt(valueMetal);

//                    Log.d("Value: ", String.valueOf(totalPaper));
                        }
                        String paper = String.valueOf(totalPaper);
                        String plastic = String.valueOf(totalPlastic);
                        String metal = String.valueOf(totalMetal);
                        entries.add(new PieEntry(Float.parseFloat(paper), "Paper: " + paper));
                        entries.add(new PieEntry(Float.parseFloat(plastic), "Plastic: " + plastic));
                        entries.add(new PieEntry(Float.parseFloat(metal), "Metal: " + metal));
                        setupPieChart();
                        loadPieChartData();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
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
}