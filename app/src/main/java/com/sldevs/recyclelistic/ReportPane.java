package com.sldevs.recyclelistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReportPane extends AppCompatActivity {
    EditText etLocation,etDescription;
    TextView btnGoReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pane);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(ReportPane.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(ReportPane.this,R.color.green));
        }
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);
        btnGoReport = findViewById(R.id.btnGoReport);

        btnGoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLocation.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
                    new AlertDialog.Builder(ReportPane.this).setTitle("Please enter your location and description.").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                } else {

                    final String appPackageName = "com.google.android.gm"; // getPackageName() from Context or Activity object
                    if (isAppInstalled("com.google.android.gm") == true) {

                        etDescription.setText("");
                        etLocation.setText("");
                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"recycelistic@gmail.com"});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Solid Waste Materials");
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Location: " + etLocation.getText() + "\n" + "Description: " + etDescription.getText() + "\n\n" + "(DON'T FORGET TO ATTACH IMAGE)");
                        emailIntent.setType("image/png");

                        ArrayList<Uri> uris = new ArrayList<Uri>();

//                uris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ic_menu_gallery));
//                uris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ic_menu_camera));

                        emailIntent.putExtra(Intent.EXTRA_STREAM, uris);

                        startActivity(Intent.createChooser(emailIntent, "Choose Gmail:"));
                    } else {
                        etDescription.setText("");
                        etLocation.setText("");
                        new AlertDialog.Builder(ReportPane.this).setTitle("You don't have Gmail App.").setMessage("Are you going to install it?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ReportPane.this, "Install Gmail App to use this feature.", Toast.LENGTH_LONG).show();
                            }
                        }).show();
                    }
                }
            }
        });

    }
    private boolean isAppInstalled(String packageName) {
        PackageManager pm = ReportPane.this.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
            Toast.makeText(ReportPane.this,"Gmail is installed.", Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
            Toast.makeText(ReportPane.this,"Gmail is not installed.", Toast.LENGTH_LONG).show();
        }
        return installed;
    }
}