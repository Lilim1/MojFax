package com.example.mojfax;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignIn, tvSignUp;
    ProgressBar progressBar;
    ImageView oko;
    FirebaseAuth mFirebaseAuth;
    private AdView mAdView;
    private DatabaseReference mDatabase;
    private String fakultet1;
    private String odsjek1;
    private String godina1;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String mUIDsender;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private LinearLayoutManager mLinearLayoutManager;
    FirebaseUser mFirebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailId2);
        password = findViewById(R.id.password2);
        btnSignIn = findViewById(R.id.hamuha);
        tvSignUp = findViewById(R.id.mirela);
        progressBar= findViewById(R.id.progressBar2);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {

                            Intent i = new Intent(MainActivity.this, TestActivity.class);
                            startActivity(i);
                        }

                    // Toast.makeText(login_form.this, "You are logged in", Toast.LENGTH_SHORT).show();



                else {
                    Toast.makeText(MainActivity.this, "Prijavi se", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Morate unijeti email ");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Morate unijeti šifru");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Polja su prazna", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Error pri prijavljivanju", Toast.LENGTH_SHORT).show();
                            } else {

                                user = FirebaseAuth.getInstance().getCurrentUser();
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                ValueEventListener postListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        mUIDsender=user.getUid();
                                        if(dataSnapshot.child("user").child(mUIDsender).child("fakultet").getValue(String.class)==null){
                                            Intent a = new Intent(MainActivity.this, NoFakultet.class);
                                            startActivity(a);
                                        }
                                        else{
                                            if (user.isEmailVerified()){
                                                Intent intToHome = new Intent(MainActivity.this, MedjuActivity.class);
                                                startActivity(intToHome);
                                            }else{
                                                auth = FirebaseAuth.getInstance();
                                                user.sendEmailVerification()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    Intent i = new Intent(MainActivity.this,AfterRegistation2.class);
                                                                    startActivity(i);
                                                                }
                                                            }
                                                        });

                                            }

                                        }

                                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                                        //Toast.makeText(TestActivity.this, odsjek1, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        // Getting Post failed, log a message
                                    }
                                };
                                mDatabase.addValueEventListener(postListener);

                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error pri prijavljivanju!", Toast.LENGTH_SHORT).show();

                }

            }
        });


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intSignUp);
            }
        });

    }




    public void btnp_fb(View view) {
        Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/muhamed.presi.dent/"));
        startActivity(i2);
    }


    public void btnp_tw(View view) {
        Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ/"));
        startActivity(i2);
    }

    public void btnp_ig(View view) {
        Intent i2=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/haustor_/"));
        startActivity(i2);
    }

}
