package com.sldevs.recyclelistic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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
    Button btnSearch;
    private PieChart pieChart;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<String> materials,values;

    ArrayList<PieEntry> entries;
    Float f,s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);

        materials = new ArrayList<>();
        materials.add("Plastic");
        materials.add("Paper");
        materials.add("Metals");



        searchMonth = findViewById(R.id.searchMonth);
        searchDay = findViewById(R.id.searchDay);
        searchYear = findViewById(R.id.searchYear);
        btnSearch = findViewById(R.id.btnSearch);
        database =FirebaseDatabase.getInstance();
        pieChart = findViewById(R.id.activity_main_piechart);
        myRef = database.getReference("DataCollection");

        setupPieChart();
        total();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

    }

//    }

    public void total(){

        entries = new ArrayList<>();
//        values = new ArrayList<>();
//        Query paperQuery = myRef.child("cdo").orderByChild("item").equalTo("Paper");
//        paperQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int totalPaper = 0;
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String valuePaper = dataSnapshot.child("value").getValue().toString();
//                    totalPaper += Integer.parseInt(valuePaper);
//                    values.add(String.valueOf(totalPaper));
////                    Log.d("Value: ", String.valueOf(totalPaper));
//                }
//                String paper = String.valueOf(totalPaper);
//                entries.add(new PieEntry(Float.parseFloat(paper), "Paper"));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        Query plasticQuery = myRef.child("cdo").orderByChild("item").equalTo("Plastic");
//        plasticQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int totalPlastic = 0;
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String valuePlastic = dataSnapshot.child("value").getValue().toString();
//                    totalPlastic += Integer.parseInt(valuePlastic);
//                    plasticValue.setText(totalPlastic);
//                    values.add(plasticValue.getText().toString());
////                    Log.d("Value: ", String.valueOf(totalPlastic));
//                }
//                String plastic = String.valueOf(totalPlastic);
//                entries.add(new PieEntry(Float.parseFloat(plastic), "Plastic"));
//
//                Log.d("Final Paper Value: ", values.get(0));
//                Log.d("Final Plastic Value: ", values.get(1));
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        Float first = Float.parseFloat(values.get(0));
//        Float second = Float.parseFloat(values.get(1));
//        entries.add(new PieEntry(, materials.get(0)));
//        entries.add(new PieEntry(s, materials.get(1)));
        entries.add(new PieEntry(13f, "Plastic"));
        entries.add(new PieEntry(10f, "Metal"));
        entries.add(new PieEntry(9f, "Paper"));
        entries.add(new PieEntry(4f, "Gas"));


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
//                for(int i = 0; i < materials.size();i++){
//                    Log.d("Valuess: ", values.get(i));
//                    Log.d("Materialss: ", materials.get(i));
//                    entries.add(new PieEntry(Float.parseFloat(values.get(i)), materials.get(i)));
//                }

    }
}



