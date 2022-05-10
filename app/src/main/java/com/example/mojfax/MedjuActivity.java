package com.example.mojfax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;


public class MedjuActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medju);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        mFirebaseAuth = FirebaseAuth.getInstance();
        String slika = mFirebaseAuth.getCurrentUser().getPhotoUrl().toString();
        String ime = mFirebaseAuth.getCurrentUser().getDisplayName();
        editor.putString("jedan", slika);
        editor.putString("dva",ime);

        if(slika!=null){
            Intent i = new Intent(MedjuActivity.this,TestActivity.class);
            startActivity(i);
            }

    }
}