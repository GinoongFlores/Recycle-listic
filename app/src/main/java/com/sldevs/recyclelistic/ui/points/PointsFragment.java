package com.sldevs.recyclelistic.ui.points;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sldevs.recyclelistic.EditProfile;
import com.sldevs.recyclelistic.R;
import com.sldevs.recyclelistic.ShowLocations;
import com.sldevs.recyclelistic.ShowPoints;
import com.sldevs.recyclelistic.ShowQRCode;

import java.text.NumberFormat;
import java.util.Currency;


public class PointsFragment extends Fragment {
    TextView tvPoints;
    TextView btnShowQRCode,btnShowJunkLocations,btnShowMaterials;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("EUR"));

        String number = format.format(1000000);
        mAuth = FirebaseAuth.getInstance();

        View root = inflater.inflate(R.layout.fragment_points, container, false);
        tvPoints = root.findViewById(R.id.tvPoints);
        btnShowQRCode = root.findViewById(R.id.btnShowQRCode);
        btnShowJunkLocations = root.findViewById(R.id.btnShowJunkLocations);
        loadPoints();
        btnShowJunkLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();

                if(!user.isEmailVerified()){
                    new android.app.AlertDialog.Builder(getContext()).setTitle("EMAIL IS NOY YET VERIFIED").setMessage("Check you profile to verify.").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }

                    }).show();
                }else{
                    Intent i = new Intent(getContext(), ShowLocations.class);
                    startActivity(i);
                }

            }
        });
        btnShowMaterials = root.findViewById(R.id.btnShowMaterials);
        btnShowMaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ShowPoints.class);
                startActivity(i);
            }
        });
        btnShowQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ShowQRCode.class);
                startActivity(i);
            }
        });



        return root;

    }
    public void loadPoints(){
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
                tvPoints.setText(getPoints);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
}
    }