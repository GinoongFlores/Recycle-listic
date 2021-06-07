package com.sldevs.recyclelistic.ui.garbage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sldevs.recyclelistic.R;

import java.util.ArrayList;

public class GarbageFragment extends Fragment {
    Spinner spinCity,spinBarangay;
    TextView dsunday,dmonday,dtuesday,dwednesday,dthursday,dfriday,dsaturday;

    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        View root = inflater.inflate(R.layout.fragment_garbage, container, false);
        dsunday = root.findViewById(R.id.sunday);
        dmonday = root.findViewById(R.id.monday);
        dtuesday = root.findViewById(R.id.tuesday);
        dwednesday = root.findViewById(R.id.wednesday);
        dthursday = root.findViewById(R.id.thursday);
        dfriday = root.findViewById(R.id.friday);
        dsaturday = root.findViewById(R.id.saturday);
        spinBarangay = root.findViewById(R.id.spinBarangay);
        spinCity = root.findViewById(R.id.spinCity);

        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city = spinCity.getSelectedItem().toString().trim();
                if(city.equalsIgnoreCase("CDO")){
                    populateCDOBarangay();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinBarangay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String barangay = spinBarangay.getSelectedItem().toString().trim();
                if(barangay.equalsIgnoreCase("Nazareth")){
                    myRef = FirebaseDatabase.getInstance().getReference("GarbageTruckSchedule")
                            .child(spinCity.getSelectedItem().toString())
                            .child("Barangay")
                            .child(spinBarangay.getSelectedItem().toString());

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
                            try{
                                String sunday = snapshot.child("Sunday").child("Time").getValue().toString();
                                String monday = snapshot.child("Monday").child("Time").getValue().toString();
                                String tuesday = snapshot.child("Tuesday").child("Time").getValue().toString();
                                String wednesday = snapshot.child("Wednesday").child("Time").getValue().toString();
                                String thursday = snapshot.child("Thursday").child("Time").getValue().toString();
                                String friday = snapshot.child("Friday").child("Time").getValue().toString();
                                String saturday = snapshot.child("Saturday").child("Time").getValue().toString();

                                dsunday.setText(sunday);
                                dmonday.setText(monday);
                                dtuesday.setText(tuesday);
                                dwednesday.setText(wednesday);
                                dthursday.setText(thursday);
                                dfriday.setText(friday);
                                dsaturday.setText(saturday);
                            }catch (NullPointerException e){
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(barangay.equalsIgnoreCase("Lumbia")){
                    myRef = FirebaseDatabase.getInstance().getReference("GarbageTruckSchedule")
                            .child(spinCity.getSelectedItem().toString())
                            .child("Barangay")
                            .child(spinBarangay.getSelectedItem().toString());

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
                            try{
                                String sunday = snapshot.child("Sunday").child("Time").getValue().toString();
                                String monday = snapshot.child("Monday").child("Time").getValue().toString();
                                String tuesday = snapshot.child("Tuesday").child("Time").getValue().toString();
                                String wednesday = snapshot.child("Wednesday").child("Time").getValue().toString();
                                String thursday = snapshot.child("Thursday").child("Time").getValue().toString();
                                String friday = snapshot.child("Friday").child("Time").getValue().toString();
                                String saturday = snapshot.child("Saturday").child("Time").getValue().toString();

                                dsunday.setText(sunday);
                                dmonday.setText(monday);
                                dtuesday.setText(tuesday);
                                dwednesday.setText(wednesday);
                                dthursday.setText(thursday);
                                dfriday.setText(friday);
                                dsaturday.setText(saturday);
                            }catch (NullPointerException e){
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        return root;
    }
    public void populateCDOBarangay(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Nazareth");
        arrayList.add("Bugo");
        arrayList.add("Lumbia");
        arrayList.add("Kauswagan");
        arrayList.add("Lumbia");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBarangay.setAdapter(arrayAdapter);
    }
}