package com.example.mojfax;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Nastavak extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnName;
    EditText Odsjek;
    EditText Godina;
    Spinner spinner;
    Spinner spinner2;
    Spinner spinneruni;
    EditText Uni;
    String univ;
    String imefakulteta;
    String jedan;
    String dva;
    String un;
    int broj;
    private AdView mAdView;
    ProgressBar progressBar;
    private DatabaseReference mDatabase;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nastavak);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //String [] Akademijascenskihumjetnosti = {"Nastavnicki odsjek", "Odsjek za slikarstvo", "Odsjek kiparstvo", "Odsjek grafika", "Odsjek graficki dizajn", "Odsjek produkt dizajn"};
        btnName = (Button) findViewById(R.id.aj);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        spinneruni = findViewById(R.id.spinnerUN);
        Godina = findViewById(R.id.Godina);
        progressBar= findViewById(R.id.progressBar5);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.univerziteti, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneruni.setAdapter(adapter);
        spinneruni.setOnItemSelectedListener(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        String godina = Godina.getText().toString();
                        if (godina.isEmpty()) {
                                Godina.setError("Metni godinu");
                                Godina.requestFocus();
                        }
                                else{
                                    progressBar.setVisibility(ProgressBar.VISIBLE);
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    Toast.makeText(Nastavak.this, "Dodo!", Toast.LENGTH_SHORT).show();
                                    mDatabase.child("user").child(mFirebaseAuth.getCurrentUser().getUid()).child("univerzitet").setValue(un);
                                    mDatabase.child("user").child(mFirebaseAuth.getCurrentUser().getUid()).child("fakultet").setValue(jedan);
                                    mDatabase.child("user").child(mFirebaseAuth.getCurrentUser().getUid()).child("odsjek").setValue(dva);
                                    mDatabase.child("user").child(mFirebaseAuth.getCurrentUser().getUid()).child("godina").setValue("1");
                                    editor.putString("uni",un.replaceAll(" ",""));
                                    editor.putString("jedan", jedan.replaceAll(" ", ""));
                                    editor.putString("dva", dva.replaceAll(" ", ""));
                                    editor.putString("tri", godina);
                                    editor.apply();

                                       startActivity(new Intent(Nastavak.this, MainActivity.class));

                                }

            }




        });


}


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if(parent.getId() == R.id.spinnerUN) {
            un = parent.getItemAtPosition(position).toString();


            if (position == 0) {
               // un = parent.getItemAtPosition(position).toString();
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakulteti, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;


            }else if (position == 1) {
              //  un = parent.getItemAtPosition(position).toString();
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiIUS, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;

            } else if (position == 2) {
             //   un = parent.getItemAtPosition(position).toString();
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiBURCH, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 3) {
                un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetISarajevo, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 4) {
              //  un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiBL, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 5) {
             //   un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiPoslovnaskolaBL, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 6) {
             //   un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiBLKOledz, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 7) {
               // un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetNezBL, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 8) {
              //  un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetKAPAfIBl, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;

            } else if (position == 9) {
               // un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetSinBjeljina, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 10) {
              //  un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiSSST, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 11) {
             //   un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetbiznisIstocnosarajevo, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 12) {
              //  un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiZenica, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 13) {
               // un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiDzBijedic, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 14) {
             //   un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiTuzla, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 15) {
              //  un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetisveuMostar, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 16) {
              //  un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiLogos, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 17) {
               // un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiBihac, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 18) {
              //  un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiTravnik, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 19) {
              //  un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiINTravnik, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 20) {
                un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiCEPS, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 21) {
              //  un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetVitez, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 22) {
              //  un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiApeiron, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            } else if (position == 23) {
                //un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiprometej, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            }
            else if (position == 24) {
               // un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakutetiHercegovina, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter1);
            spinner.setOnItemSelectedListener(this);
                broj = position;
        } else if (position == 25) {
               // un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiPIMBL, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter1);
            spinner.setOnItemSelectedListener(this);
                broj = position;
        } else if (position == 26) {
                //un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiDOBOj, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter1);
            spinner.setOnItemSelectedListener(this);
                broj = position;
        } else if (position == 27) {
                //un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiCKM, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter1);
            spinner.setOnItemSelectedListener(this);
                broj = position;
        } else if (position == 28) {
                //un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fakultetiTuzlazadnja, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter1);
            spinner.setOnItemSelectedListener(this);
                broj = position;
        } else if (position == 29) {
                //un = parent.getItemAtPosition(position).toString();

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.beta, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                spinner.setOnItemSelectedListener(this);
                broj = position;
            }


        }

        if (parent.getId() == R.id.spinner && (broj==0)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Akademijalikovnihumjetnosti, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Akademijascenskihumjetnosti, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Arhitektonskifakultet, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Ekonomskifakultet, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Elektotehnickifakultet, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Fakultetislamskihnauka, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Fakultetkriminalistika, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 7) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Fakultetpolitickihnauka, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 8) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Fakultetsporta, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);

            } else if (position == 9) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Fakultetsaobracaj, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 10) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Farmacija, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 11) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Filozofski, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 12) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Gradjevinski, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 13) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Masinci, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 14) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Medicinari, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 15) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Muzicari, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 16) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Pedagogija, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 17) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Poljopr, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 18) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Pravnici, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 19) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.PMF, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 20) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Stomatologija, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 21) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Sumari, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 22) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Veterina, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 23) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Zdravstveni, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 24) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.beta, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }



        }

        if (parent.getId() == R.id.spinner && (broj==1)) {
            jedan = parent.getItemAtPosition(position).toString();

            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.iusFASS, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.iusFBA, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.iusfens, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.iusFlw, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.iusFEDU, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==2)) {
            jedan = parent.getItemAtPosition(position).toString();

            if (position == 0) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.burchfens, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.burchFess, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.burchFEH, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }

        if (parent.getId() == R.id.spinner && (broj==3)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.iSarajevoAkademijalik, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.iSarajevoPravoslavni, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevoekonomijapale, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevoekonomijabrcko, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevoElektro, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.iSarajevoFilozofija, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.iSarajevoSport, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 7) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevoposekonomija, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 8) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevoproizvodnjamenadz, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);

            } else if (position == 9) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevoMasinski, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 10) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevoMuzicka, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 11) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevomedicina, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 12) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevopedagoski, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 13) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevopoljoprivredni, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 14) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevopravo, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 15) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevosaobracajni, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 16) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.isarajevotehno, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }


        if (parent.getId() == R.id.spinner && (broj==4)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLAkademijaUmjetnosti, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLarhitektonskogr, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLekonomija, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLelektrotehnika, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLmasinski, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLmedicina, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLpoljoprivreda, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 7) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLpravo, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 8) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLPMF, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);

            } else if (position == 9) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLRudarstvo, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 10) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLTehnoloski, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 11) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLbezbjednost, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 12) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLsport, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 13) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLfiloloski, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 14) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLFilozofski, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 15) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLSumarski, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }

        if (parent.getId() == R.id.spinner && (broj==5)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLpos0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLpos1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLpos2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLpos3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLpos4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLpos5, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLpos6, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }

        if (parent.getId() == R.id.spinner && (broj==6)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLKOledz0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLKOledz0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLKOledz1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLKOledz2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLKOledz0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BLKOledz0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }

        if (parent.getId() == R.id.spinner && (broj==7)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Nez0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Nez1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Nez2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Nez3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Nez4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Nez5, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Nez6, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }

        if (parent.getId() == R.id.spinner && (broj==8)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Kapa1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Kapa0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Kapa2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==9)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bjelj0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bjelj1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bjelj1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bjelj2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bjelj3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }



        if (parent.getId() == R.id.spinner && (broj==10)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.SSSTP, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.SSSTP, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.SSSTP, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.SSSTP, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.SSSTP, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.SSSTP, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.SSSTP, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 7) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.SSSTP, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 8) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.SSSTP, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);

            } else if (position == 9) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.SSSTP, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }

        }
        if (parent.getId() == R.id.spinner && (broj==11)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ISTSa, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ISTSa, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ISTSa, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ISTSa, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ISTSa, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ISTSa, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }

        if (parent.getId() == R.id.spinner && (broj==12)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Zenica0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Zenica1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Zenica2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Zenica3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Zenica4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Zenica5, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Zenica6, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 7) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Zenica7, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==13)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bijedic0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bijedic1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.BIjedic2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bijedic3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bijedic4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bijedic5, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bijedic6, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 7) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Bijedic7, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }

        if (parent.getId() == R.id.spinner && (broj==14)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla5, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla6, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 7) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla7, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 8) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla8, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);

            } else if (position == 9) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla9, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 10) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla10, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 11) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla11, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 12) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Tuzla12, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==15)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc5, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc6, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 7) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc7, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 8) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc8, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);

            } else if (position == 9) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc9, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 10) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sveuc10, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==16)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.logos1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.logos1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.logos1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.logos1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.logos1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.logos1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.logos1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==17)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.bihac0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.bihac1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.bihac2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.bihac3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.bihac4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.bihac5, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==18)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ftravnik0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ftravnik1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ftravnik2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ftravnik3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ftravnik4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==19)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.intr0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.intr1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.intr2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.intr3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.intr4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.intr5, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.intr6, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }


        if (parent.getId() == R.id.spinner && (broj==20)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ceps0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ceps1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ceps2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ceps3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ceps4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ceps5, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ceps6, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==21)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.vitez0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.vitez1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.vitez2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.vitez3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==22)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ap0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ap1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ap2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ap3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ap4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ap5, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ap6, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==23)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pro0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pro0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pro0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pro0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==24)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 5) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 6) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 7) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 8) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);

            } else if (position == 9) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 10) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 11) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 12) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.herc0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==25)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pim0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pim1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pim2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pim3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pim4, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==26)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.doboj0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.doboj0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.doboj0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.doboj0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==27)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ckm0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ckm1, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ckm2, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ckm3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ckm3, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }
        if (parent.getId() == R.id.spinner && (broj==28)) {

            jedan = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), jedan, Toast.LENGTH_SHORT).show();
            String text2 = jedan.replaceAll(" ", "");
            if (position == 0) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.zadnji0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);

                spinner2.setOnItemSelectedListener(this);

            } else if (position == 1) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.zadnji0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);


            } else if (position == 2) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.zadnji0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 3) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.zadnji0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            } else if (position == 4) {
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.zadnji0, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter1);
                spinner2.setOnItemSelectedListener(this);
            }
        }





        else if(parent.getId() == R.id.spinner2)
        {
            dva = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), dva, Toast.LENGTH_SHORT).show();
            String text2 = dva.replaceAll(" ", "");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}