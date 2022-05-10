package com.example.mojfax;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    Button btnLogout,btnChat,btnName;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    //FirebaseDatabase mDatabase;
    private DatabaseReference mDatabase;
    private String mUIDsender;
    private String fakultet1;
    private String odsjek1;
    private String godina1;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLogout = findViewById(R.id.logout);
        btnChat = findViewById(R.id.chating1);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUIDsender=mFirebaseAuth.getCurrentUser().getUid();


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fakultet1 = (dataSnapshot.child("user").child(mUIDsender).child("fakultet").getValue(String.class)).replaceAll(" ","");
                odsjek1 = (dataSnapshot.child("user").child(mUIDsender).child("odsjek").getValue(String.class)).replaceAll(" ","");
                godina1 = (dataSnapshot.child("user").child(mUIDsender).child("godina").getValue(String.class));

                Toast.makeText(HomeActivity.this, odsjek1, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        mDatabase.addValueEventListener(postListener);


/*        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });*/

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment2()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment2();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new FavoritesFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void chat1(View view) {
        Intent i = new Intent(HomeActivity.this, Chat1.class);
        //FavoritesFragment fragment = new FavoritesFragment();
        //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuff", fakultet1);

        //Add the bundle to the intent
        i.putExtras(bundle);

//Fire that second activity
        startActivity(i);


    }

    public void chat2(View view) {
        Intent i = new Intent(HomeActivity.this, Chat1.class);
        //FavoritesFragment fragment = new FavoritesFragment();
        //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuff", fakultet1);
        bundle.putString("stuff1", odsjek1);
//Add the bundle to the intent
        i.putExtras(bundle);

//Fire that second activity
        startActivity(i);


    }

    public void chat3(View view) {
        Intent i = new Intent(HomeActivity.this, Chat1.class);
        //FavoritesFragment fragment = new FavoritesFragment();
        //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuff", fakultet1);
        bundle.putString("stuff1", odsjek1);
        bundle.putString("stuff2",godina1);
//Add the bundle to the intent
        i.putExtras(bundle);

//Fire that second activity
        startActivity(i);


    }
    public void chat4(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intToMain);


    }

}

