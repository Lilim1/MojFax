package com.example.mojfax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PrijeMjenjanjaSifre extends AppCompatActivity {
TextView textView;
TextView textView2;
Button button;
String email;
String pass;
ProgressBar progressBar;
FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prije_mjenjanja_sifre);
        textView=findViewById(R.id.signin1);
        textView2=findViewById(R.id.signin2);
        button= findViewById(R.id.butt);
        progressBar= findViewById(R.id.progressBar9);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                AuthCredential credential= EmailAuthProvider
                        .getCredential(textView2.getText().toString(),textView.getText().toString());

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               Intent i = new Intent(PrijeMjenjanjaSifre.this,NoFakultet.class);
                               startActivity(i);
                            }
                        });

            }
        });

    }
}