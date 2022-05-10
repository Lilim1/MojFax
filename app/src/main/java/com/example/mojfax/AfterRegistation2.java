package com.example.mojfax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AfterRegistation2 extends AppCompatActivity {
private Button button;
private Button button1;
private FirebaseAuth auth;
private FirebaseUser user;
private static final String TAG = "MainActivity2";
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_registation2);
        button = findViewById(R.id.buttonver);
        button1 = findViewById(R.id.ponpr);
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressBar=findViewById(R.id.progressBar8);
        auth = FirebaseAuth.getInstance();
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        if(user.isEmailVerified()){
            Intent i = new Intent(AfterRegistation2.this,TestActivity.class);
            startActivity(i);
        }

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                                    Toast.makeText(AfterRegistation2.this,"Email je poslan",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Log.i(TAG,task.getException().toString());
                                }
                            }
                        });
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(AfterRegistation2.this, MainActivity.class);
                startActivity(intToMain);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}