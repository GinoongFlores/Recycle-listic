package com.sldevs.recyclelistic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ShowPoints extends AppCompatActivity {
    public static TextView tvCurrentPoints;
    ImageView firstRedeem,secondRedeem;
    ListView listViewPoints;
    ArrayList<String> arrayList = new ArrayList<>();
    DatabaseReference mRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_points);
        mAuth = FirebaseAuth.getInstance();
        firstRedeem = findViewById(R.id.firstRedeem);
        tvCurrentPoints = findViewById(R.id.tvCurrentPoints);
        loadPoints();

            firstRedeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(ShowPoints.this).setTitle("Are you sure you want to redeem?").setMessage("100 points for 50 GCash Load").setPositiveButton("Redeem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int current = Integer.parseInt(tvCurrentPoints.getText().toString());
                            if(current < 100){
                                Toast.makeText(ShowPoints.this,"Your points is not enough!",Toast.LENGTH_LONG).show();
                            }else{
                                int totalPoints =  current - 100;

                                FirebaseDatabase.getInstance().getReference("Recyclelistic")
                                        .child("Users")
                                        .child(mAuth.getCurrentUser().getUid())
                                        .child("points")
                                        .setValue(totalPoints).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        finish();
                                        Toast.makeText(ShowPoints.this,"Successfully Redeemed!",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            }

                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ShowPoints.this,"Cancelled",Toast.LENGTH_LONG).show();
                        }
                    }).show();

                }
            });


        secondRedeem = findViewById(R.id.secondRedeem);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ShowPoints.this, android.R.layout.simple_list_item_1,arrayList);

        listViewPoints = findViewById(R.id.listViewPoints);
        listViewPoints.setAdapter(arrayAdapter);

        mRef = FirebaseDatabase.getInstance().getReference("PointsMenu/List");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                getPoints a = new getPoints("Category");
//                String name = snapshot.child("Category").getValue(String.class);
//                arrayList.add("Category: " + name);
                String name = snapshot.child("Category").getValue(String.class);
                arrayList.add(name);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void loadPoints(){
        DatabaseReference myRef;
        myRef = FirebaseDatabase.getInstance().getReference("Recyclelistic")
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                            AccountNumber accountNumber = snapshot1.getValue(AccountNumber.class);
//                            String txt = accountNumber.getNumber();
//                            list.add(txt);
//                            etEmail.setText(list.toString());
//                        }
                String getPoints = snapshot.child("points").getValue().toString();
                tvCurrentPoints.setText("Current Points: " + getPoints);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}