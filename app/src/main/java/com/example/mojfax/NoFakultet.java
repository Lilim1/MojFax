package com.example.mojfax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NoFakultet extends AppCompatActivity {
private TextView textView;
private TextView textView2;
private Button button;
private FirebaseAuth mFirebaseAuth;
private FirebaseUser mFirebaseUser;
FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
String jedan;
String dva;
private ProgressBar progressBar;
private static final String TAG= "NoFakultet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_fakultet);
        textView = findViewById(R.id.sifra);
        textView2 = findViewById(R.id.sifra1);
        button= findViewById(R.id.prs);
        progressBar = findViewById(R.id.progressBar7);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    jedan = textView.getText().toString();
                    dva = textView2.getText().toString();
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                if((textView.getText().toString()).isEmpty()){
                    textView.setError("Napiši Šifru");
                    textView.requestFocus();
                }
                else if((textView2.getText().toString()).isEmpty()){
                    textView2.setError("Potvrdi Šifru");
                    textView2.requestFocus();
                }

                else if ((!(jedan.isEmpty())) && (!(dva.isEmpty())) && jedan.equals(dva)){
                        Toast.makeText(NoFakultet.this,"Uso",Toast.LENGTH_SHORT).show();
                                     user.updatePassword(jedan)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override

                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(NoFakultet.this,"Uso2",Toast.LENGTH_SHORT).show();
                                            if (task.isSuccessful()) {
                                                Toast.makeText(NoFakultet.this, "Šifra je promjenjena", Toast.LENGTH_SHORT).show();
                                             Intent i = new Intent(NoFakultet.this,TestActivity.class);
                                             startActivity(i);
                                            }else{
                                                Toast.makeText(NoFakultet.this,"Nije uso",Toast.LENGTH_SHORT).show();
                                                Log.i(TAG,task.getException().getMessage().toString());
                                            }
                                        }
                                    });

                    }
                else{
                    Toast.makeText(NoFakultet.this,"Šifre se ne poklapaju",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}