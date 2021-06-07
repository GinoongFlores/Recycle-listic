package com.sldevs.recyclelistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AccessController;

public class EditProfile extends AppCompatActivity {
    Button btnBackEditProfile;
    TextView tvNotVerified,tvEditProfilePicture,editName,editEmail,editPassword,editNumber,editNumberr,editNamee,editEmaill;
    EditText etNameProfile,etEmailProfile,etNumberProfle;
    FirebaseAuth mAuth;
    ImageView ivImage;
    StorageReference storageRef;
    FirebaseStorage storage;
    DatabaseReference myRef;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(EditProfile.this,R.color.green));
            getWindow().setNavigationBarColor(ContextCompat.getColor(EditProfile.this,R.color.green));
        }

        etNameProfile = findViewById(R.id.etNameProfile);
        etEmailProfile = findViewById(R.id.etEmailProfile);
        etNumberProfle = findViewById(R.id.etNumberProfile);
        tvNotVerified = findViewById(R.id.tvNotVerified);
        mAuth = FirebaseAuth.getInstance();

        editNamee = findViewById(R.id.editNamee);
        editNamee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                if(etNameProfile.getText().toString().isEmpty()){
                    etNumberProfle.setError("Name is empty");
                    etNumberProfle.requestFocus();
                    return;
                }

                    myRef.child("name").setValue(etNameProfile.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(EditProfile.this,"Name successfully updated!",Toast.LENGTH_LONG).show();
                            editNamee.setVisibility(View.INVISIBLE);
                            editName.setVisibility(View.VISIBLE);
                            etNameProfile.setEnabled(false);

                            loadData();
                        }
                    });



            }
        });

        editName = findViewById(R.id.editName);
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editNamee.setVisibility(View.VISIBLE);
                editName.setVisibility(View.INVISIBLE);
                etNameProfile.setEnabled(true);
                showKeyboard();
                etNameProfile.requestFocus();


            }
        });
        editEmaill = findViewById(R.id.editEmaill);
        editEmaill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                if (etEmailProfile.getText().toString().isEmpty()) {
                    etEmailProfile.setError("Email is required");
                    etEmailProfile.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(etEmailProfile.getText().toString()).matches()) {
                    etEmailProfile.setError("Please enter a valid email");
                    etEmailProfile.requestFocus();
                    return;
                }
                EditText currentEmail = new EditText(EditProfile.this);
                EditText currentPassword = new EditText(EditProfile.this);
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
                        try{
                            String email = snapshot.child("email").getValue().toString();
                            String password = snapshot.child("password").getValue().toString();
                            currentEmail.setText(email);
                            currentPassword.setText(password);
                        }catch (NullPointerException e){

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential(currentEmail.getText().toString(), currentPassword.getText().toString()); // Current Login Credentials \\
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(etEmailProfile.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                    Toast.makeText(getApplicationContext(), "Email is already been registered", Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                if (task.isSuccessful()) {
                                    myRef.child("email").setValue(etEmailProfile.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(EditProfile.this,"Email successfully updated!",Toast.LENGTH_LONG).show();
                                            editEmaill.setVisibility(View.INVISIBLE);
                                            editEmail.setVisibility(View.VISIBLE);
                                            etEmailProfile.setEnabled(false);

                                            loadData();
                                        }
                                    });

                                }
                                if (!task.isSuccessful()){
                                    Toast.makeText(EditProfile.this,"Something went wrong",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

            }
        });
        editEmail = findViewById(R.id.editEmail);
        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editEmaill.setVisibility(View.VISIBLE);
                editEmail.setVisibility(View.INVISIBLE);
                etEmailProfile.setEnabled(true);
                showKeyboard();
                etEmailProfile.requestFocus();
                loadData();
            }
        });

        editPassword = findViewById(R.id.editPassword);
        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(EditProfile.this,ChangePassword.class);
               startActivity(i);
                loadData();
            }
        });
        editNumberr = findViewById(R.id.editNumberr);
        editNumberr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                if(etNumberProfle.getText().toString().isEmpty()){
                    etNumberProfle.setError("Number is empty");
                    etNumberProfle.requestFocus();
                    return;
                }
                if(etNumberProfle.length() != 11){
                    etNumberProfle.setError("Number should not be greater or lower than 11");
                    etNumberProfle.requestFocus();
                    return;
                }

                    myRef.child("number").setValue(etNumberProfle.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(EditProfile.this,"Number successfully updated!",Toast.LENGTH_LONG).show();
                            editNumberr.setVisibility(View.INVISIBLE);
                            editNumber.setVisibility(View.VISIBLE);
                            etNumberProfle.setEnabled(false);
                            loadData();
                        }
                    });


            }
        });
        editNumber = findViewById(R.id.editNumber);
        editNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNumberr.setVisibility(View.VISIBLE);
                editNumber.setVisibility(View.INVISIBLE);
                etNumberProfle.setEnabled(true);
                showKeyboard();
                etNumberProfle.requestFocus();
                loadData();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Recyclelistic")
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        isVerify();

        tvNotVerified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String userID = mAuth.getCurrentUser().getUid();
                    FirebaseUser user = mAuth.getCurrentUser();

                    if(!user.isEmailVerified()){
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                new android.app.AlertDialog.Builder(EditProfile.this).setTitle("Verification has been sent.").setMessage("Please check your email.").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }

                                }).show();
                            }
                        });
                    }
                }

        });
        try {
            loadProfile();
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tvEditProfilePicture = findViewById(R.id.tvEditProfilePicture);
        tvEditProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

                try {
                    loadProfile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        ivImage = findViewById(R.id.profile_picture);

        btnBackEditProfile = findViewById(R.id.btnBackEditProfile);
        btnBackEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeImage();
                finish();
                Intent i = new Intent(EditProfile.this,UserSide.class);
                startActivity(i);
            }
        });


    }
    @Override
    public void onBackPressed() {
        storeImage();
        finish();
        Intent i = new Intent(EditProfile.this,UserSide.class);
        startActivity(i);
    }

    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
    public void isVerify(){

        FirebaseUser user = mAuth.getCurrentUser();
        if(!user.isEmailVerified()){
            tvNotVerified.setVisibility(View.VISIBLE);
        }else{
            tvNotVerified.setVisibility(View.GONE);
        }
    }
    public void loadProfile() throws IOException {
        String id = mAuth.getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference getQR = storageRef.child("ProfilePicture/"+ mAuth.getUid()+ "profile.png");
        final long ONE_MEGABYTE = 1024 * 1024;
        getQR.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                ivImage.setImageBitmap(bitmap);
            }
        });
    }
    public void storeImage(){
        String id = FirebaseAuth.getInstance().getUid();
        StorageReference qrcode = storageRef.child(id + ".png");
        StorageReference qrcodeimageref = storageRef.child("ProfilePicture/" + id + "profile.png");
        qrcode.getName().equals(qrcodeimageref.getName());
        qrcode.getPath().equals(qrcodeimageref.getPath());

        ivImage.setDrawingCacheEnabled(true);
        ivImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = qrcodeimageref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utils.checkPermission(EditProfile.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".png");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
        ivImage.setMaxHeight(265);
        ivImage.setMaxWidth(265);
        Toast.makeText(EditProfile.this,"Profile Successfully Changed!",Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivImage.setImageBitmap(bm);
        ivImage.setMaxHeight(265);
        ivImage.setMaxWidth(265);
        Toast.makeText(EditProfile.this,"Profile Successfully Changed!",Toast.LENGTH_LONG).show();
    }

    public void loadData(){
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
                try{
                    String name = snapshot.child("name").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String number = snapshot.child("number").getValue().toString();

                    etNameProfile.setText(name);
                    etEmailProfile.setText(email);
                    etNumberProfle.setText(number);
                }catch (NullPointerException e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void editName(){
//        EditText name = new EditText(this);
//        name.setText(etNameProfile.getText().toString());
//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle("Change name:")
//                .setMessage("Updating your name")
//                .setView(name)
//                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        myRef.child("name").setValue(name.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(EditProfile.this,"Name successfully updated!",Toast.LENGTH_LONG).show();
//                            }
//                        });
//
//                    }
//                })
//                .setNegativeButton("Cancel", null)
//                .create();
//        dialog.show();

    }
    public void editEmail(){
        EditText currentEmail = new EditText(this);
        EditText currentPassword = new EditText(this);
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
                try{
                    String email = snapshot.child("email").getValue().toString();
                    String password = snapshot.child("password").getValue().toString();
                    currentEmail.setText(email);
                    currentPassword.setText(password);
                }catch (NullPointerException e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        EditText email = new EditText(this);
        email.setText(etEmailProfile.getText().toString());
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Change email:")
                .setMessage("Updating your email")
                .setView(email)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential credential = EmailAuthProvider.getCredential(currentEmail.getText().toString(), currentPassword.getText().toString()); // Current Login Credentials \\
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updateEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                            Toast.makeText(getApplicationContext(), "Email is already been registered", Toast.LENGTH_SHORT).show();

                                        } else {

                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        if (task.isSuccessful()) {
                                            myRef.child("email").setValue(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Toast.makeText(EditProfile.this,"Email successfully updated!",Toast.LENGTH_LONG).show();
                                                                }
                                                            });

                                                        }
                                        if (!task.isSuccessful()){
                                            Toast.makeText(EditProfile.this,"Email successfully updated!",Toast.LENGTH_LONG).show();
                                        }
                                            }
                                        });
                                    }
                                });

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
    public void editPassword(){
//        EditText current = new EditText(this);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                        list.clear();
////                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
////                            AccountNumber accountNumber = snapshot1.getValue(AccountNumber.class);
////                            String txt = accountNumber.getNumber();
////                            list.add(txt);
////                            etEmail.setText(list.toString());
////                        }
//                try{
//                    String password = snapshot.child("password").getValue().toString();
//                    current.setText(password);
//                }catch (NullPointerException e){
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
////        LayoutInflater mInflater = LayoutInflater.from(this);
////        final View textEntryView = mInflater.inflate(
////                R.layout.alert_text_entry, null);
//
//        EditText password = new EditText(this);
//        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        password.setHint("Current Password");
//        layout.addView(password);
//
//        EditText newpassword = new EditText(this);
//        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        newpassword.setHint("New Password");
//        layout.addView(newpassword);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Email sent.")
                .setMessage("Check your email to change password.")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        dialog.show();
    }
    public void editNumber(){
        EditText number = new EditText(this);
        number.setText(etNumberProfle.getText().toString());
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Change number:")
                .setMessage("Updating your number")
                .setView(number)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(number.getText().toString().isEmpty()){
                            number.setError("Empty number");
                            number.requestFocus();

                        }
//                        if(Integer.parseInt(number.getText().toString()) > 11){
//                            number.setError("Please use a correct mobile number");
//                            number.requestFocus();
//                            return;
//                        }
//                        if(Integer.parseInt(number.getText().toString()) < 10){
//                            number.setError("Please use a correct mobile number");
//                            number.requestFocus();
//                            return;
//                        }



                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}