package com.example.mojfax;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText emailId, password,  conpass;
    Button btnSignUp;
    CheckBox checkBox;
    CheckBox checkBox1;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;
    private FirebaseAuth mAuthListener;
    private AdView mAdView;
    ImageView showUserProfile;
    private final Integer PICK_IMAGE_REQUEST=1;
    Bitmap bitmap;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.Univerzitet);
        password = findViewById(R.id.username);
        btnSignUp = (Button) findViewById(R.id.muhamed);
        tvSignIn = findViewById(R.id.hodzic);
        conpass = findViewById(R.id.cfpassword);
        progressBar= findViewById(R.id.progressBar3);
        checkBox = findViewById(R.id.checkBox);
        checkBox1 = findViewById(R.id.checkBox2);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                String cfpass = conpass.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Morate unijeti email!");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Morate unijeti šifru!");
                    password.requestFocus();
                } else if (cfpass.isEmpty()) {
                    conpass.setError("Potvrdite šifru!");
                    conpass.requestFocus();
                }
                else if(!checkBox.isChecked()){
                    checkBox.setError("Morate prihvatiti uslove korištenja");
                    checkBox.requestFocus();
                }
                else if(!checkBox1.isChecked()){
                    checkBox1.setError("Morate prihvatiti politiku privatnosti");
                    checkBox1.requestFocus();
                }

                //else if (!pwd.equals(cfpass)) {
                // conpass.setError("Passwords don't match!");
                // conpass.requestFocus();
                //}
                else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } //else if (uri == null)
                //Toast.makeText(Singup_form.this, "Please upload picture", Toast.LENGTH_SHORT).show();


                else  if(!(email.isEmpty()) && (!(pwd.isEmpty())) && (!(cfpass.isEmpty())) && (pwd.equals(cfpass)) && (checkBox.isChecked() ) && (checkBox1.isChecked())) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    String emailfin = email.replaceAll(" ","");
                    mFirebaseAuth.createUserWithEmailAndPassword(emailfin, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                progressBar.setVisibility(ProgressBar.INVISIBLE);
                                Toast.makeText(RegisterActivity.this, "Prijavljivanje nije uspjelo, molimo probajte opet", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                startActivity(new Intent(RegisterActivity.this, AfterRegistration.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this,"Error!",Toast.LENGTH_SHORT).show();

                }
            }
        });







        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }


}