package com.example.mojfax;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class AfterRegistration extends AppCompatActivity {
    Button btnName;
    EditText Nameid;
    EditText City;
    private String mPhotoUrl;
    private DatabaseReference mDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private ImageView mAddMessageImageView;
    private String mUIDsender;
    private AdView mAdView;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    CircleImageView imageView;
    ProgressBar progressBar;
    private static final int REQUEST_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_registration);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUIDsender = mFirebaseAuth.getCurrentUser().getUid();
        btnName = (Button) findViewById(R.id.aj);
        Nameid = findViewById(R.id.names);
        City = findViewById(R.id.username);
        progressBar = findViewById(R.id.progressBar4);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        imageView = (CircleImageView) findViewById(R.id.imageView2);
        imageView.setImageDrawable(ContextCompat.getDrawable(AfterRegistration.this,
                R.drawable.userslika));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
                progressBar.setVisibility(ProgressBar.VISIBLE);

            }
        });

        if (progressBar.getVisibility() == ProgressBar.INVISIBLE) {


            btnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sto = Nameid.getText().toString();
                    final String town = City.getText().toString();
                    if (sto.isEmpty()) {
                        Nameid.setError("Molimo dodajte ime!");
                        Nameid.requestFocus();
                    }


                    if (!sto.isEmpty()) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(sto)
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        if (task.isSuccessful()) {

                                            ValueEventListener postListener = new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    if ((dataSnapshot.child("user").child(mUIDsender).child("fakultet").getValue(String.class) != null) || ((dataSnapshot.child("user").child(mUIDsender).child("odsjek").getValue(String.class)) == null) || (dataSnapshot.child("user").child(mUIDsender).child("godina").getValue(String.class) == null)) {
                                                        Intent a = new Intent(AfterRegistration.this, TestActivity.class);
                                                        startActivity(a);
                                                    } else {
                                                        startActivity(new Intent(AfterRegistration.this, Nastavak.class));
                                                        if (!(town.isEmpty())) {
                                                            mDatabase.child("user").child(mFirebaseAuth.getCurrentUser().getUid()).setValue(town);
                                                        }


                                                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                                                    }


                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    // Getting Post failed, log a message
                                                }
                                            };
                                            mDatabase.addValueEventListener(postListener);


                                            //Toast.makeText(TestActivity.this, odsjek1, Toast.LENGTH_SHORT).show();


                                            // Toast.makeText(AfterRegistration.this, "Dodo!", Toast.LENGTH_SHORT).show();


                                        }
                                    }
                                });
                    }

                }
            });
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ",resultCode=" + resultCode);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    final Uri uri = data.getData();
                    Log.d(TAG, "Uri: " + uri.toString());

                    StorageReference storageReference = FirebaseStorage.getInstance()
                            .getReference(mFirebaseUser.getUid())
                            .child(mUIDsender)
                            .child(uri.getLastPathSegment());

                    putImageInStorage(storageReference, uri);


                } else {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            }
        }
    }


    private void putImageInStorage(StorageReference storageReference, Uri uri) {
        storageReference.putFile(uri).addOnCompleteListener(AfterRegistration.this,
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            task.getResult().getMetadata().getReference().getDownloadUrl()
                                    .addOnCompleteListener(AfterRegistration.this, new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()) {
                                                setProfilePic(Uri.parse(task.getResult().toString()),uri);
                                                // Toast.makeText(AfterRegistration.this,task.getResult().toString(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Log.w(TAG, "Image upload task was not successful.", task.getException());
                        }
                    }
                });
    }


    public void setProfilePic(Uri uri , Uri uri1) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AfterRegistration.this, "Slika je dodana", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AfterRegistration.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("kljuc",uri1.toString());
                        Glide.with(AfterRegistration.this)
                                .load(mFirebaseAuth.getCurrentUser().getPhotoUrl())
                                .into(imageView);
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }
                });


        //imageView.setImageURI(uri);

        //Toast.makeText(AfterRegistration.this,uri.toString(),Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {
            ///super.onBackPressed();
    }
}

