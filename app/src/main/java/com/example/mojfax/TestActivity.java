package com.example.mojfax;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BitmapCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestActivity extends AppCompatActivity {
    private String fakultet1;
    private String odsjek1;
    private String uni1;
    private String godina1;
    private String mUIDsender;
    private ImageView imageView;
    private TextView editText;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private ProgressBar progressBar;
    private AdView mAdView;
    private AppBarConfiguration mAppBarConfiguration;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUIDsender=mFirebaseAuth.getCurrentUser().getUid();
        progressBar=findViewById(R.id.progressBar6);
        imageView = findViewById(R.id.imageViewslika);
        editText = findViewById(R.id.textViewime);
        if(mFirebaseAuth.getCurrentUser().getPhotoUrl()!=null) {
            String ja = mFirebaseUser.getPhotoUrl().toString();
        }
      //  editText.setText(mFirebaseAuth.getCurrentUser().getDisplayName());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView4);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if((dataSnapshot.child("user").child(mUIDsender).child("fakultet").getValue(String.class)==null)||((dataSnapshot.child("user").child(mUIDsender).child("odsjek").getValue(String.class))==null)||(dataSnapshot.child("user").child(mUIDsender).child("godina").getValue(String.class)==null)){
                    Intent a = new Intent(TestActivity.this, Nastavak.class);
                    startActivity(a);
                }
                else {
                  if(!mFirebaseUser.isEmailVerified()){
                      Intent i = new Intent(TestActivity.this,AfterRegistation2.class);
                      startActivity(i);
                    }
                    uni1 = (dataSnapshot.child("user").child(mUIDsender).child("univerzitet").getValue(String.class)).replaceAll(" ", "");
                    fakultet1 = (dataSnapshot.child("user").child(mUIDsender).child("fakultet").getValue(String.class)).replaceAll(" ", "");
                    odsjek1 = (dataSnapshot.child("user").child(mUIDsender).child("odsjek").getValue(String.class)).replaceAll(" ", "");
                    godina1 = (dataSnapshot.child("user").child(mUIDsender).child("godina").getValue(String.class));
                    long broj1 =(dataSnapshot.child("messages").child("_").child("_").child("_____").getChildrenCount());
                    long broj2 =(dataSnapshot.child("messages").child(uni1).child("_").child("_____").getChildrenCount());
                    long broj3 =(dataSnapshot.child("messages").child(uni1).child(fakultet1).child(fakultet1+ "  ").getChildrenCount());
                    long broj4 =(dataSnapshot.child("messages").child(uni1).child(fakultet1).child(fakultet1 +"_"+ odsjek1 + " ").getChildrenCount());
                    long broj5 =(dataSnapshot.child("messages").child(uni1).child(fakultet1).child(fakultet1 +"_"+ odsjek1 + "_"+godina1).getChildrenCount());
                    long broj6 =(dataSnapshot.child("messages").child("_").child(fakultet1).child(fakultet1 + "____").getChildrenCount());

                    String ime1 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child(fakultet1).child(fakultet1 +"_"+ odsjek1 + "_"+godina1).child("name").getValue(String.class));
                    String poruka1 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child(fakultet1).child(fakultet1 +"_"+ odsjek1 + "_"+godina1).child("text").getValue(String.class));
                    String slika1 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child(fakultet1).child(fakultet1 +"_"+ odsjek1 + "_"+godina1).child("photoUrl").getValue(String.class));
                    String ime2 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child(fakultet1).child(fakultet1 +"_"+ odsjek1 + " ").child("name").getValue(String.class));
                    String poruka2 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child(fakultet1).child(fakultet1 +"_"+ odsjek1 + " ").child("text").getValue(String.class));
                    String slika2 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child(fakultet1).child(fakultet1 +"_"+ odsjek1 + " ").child("photoUrl").getValue(String.class));
                    String ime3 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child(fakultet1).child(fakultet1 +"  ").child("name").getValue(String.class));
                    String poruka3 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child(fakultet1).child(fakultet1+ "  ").child("text").getValue(String.class));
                    String slika3 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child(fakultet1).child(fakultet1+ "  ").child("photoUrl").getValue(String.class));
                    String ime4 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child("_").child("_____").child("name").getValue(String.class));
                    String poruka4 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child("_").child( "_____").child("text").getValue(String.class));
                    String slika4 =(dataSnapshot.child("messages").child(uni1).child("zadnja").child("_").child("_____").child("photoUrl").getValue(String.class));
                    String ime5 =(dataSnapshot.child("messages").child("_").child("zadnja").child("_").child("_____").child("name").getValue(String.class));
                    String ime6 =(dataSnapshot.child("messages").child("_").child("zadnja").child(fakultet1).child(fakultet1 + "____").child("name").getValue(String.class));
                    String poruka6 =(dataSnapshot.child("messages").child("_").child("zadnja").child(fakultet1).child(fakultet1 + "____").child("text").getValue(String.class));
                    String slika6 =(dataSnapshot.child("messages").child("_").child("zadnja").child(fakultet1).child(fakultet1 + "____").child("photoUrl").getValue(String.class));

                    String poruka5 =(dataSnapshot.child("messages").child("_").child("zadnja").child("_").child("_____").child("text").getValue(String.class));
                    String slika5 =(dataSnapshot.child("messages").child("_").child("zadnja").child("_").child("_____").child("photoUrl").getValue(String.class));

                    editor.putString("ime1",ime1);
                    editor.putString("poruka1", poruka1);
                    editor.putString("slika1", slika1);
                    editor.putString("ime2",ime2);
                    editor.putString("poruka2", poruka2);
                    editor.putString("slika2", slika2);
                    editor.putString("ime3",ime3);
                    editor.putString("poruka3", poruka3);
                    editor.putString("slika3", slika3);
                    editor.putString("ime4",ime4);
                    editor.putString("poruka4", poruka4);
                    editor.putString("slika4", slika4);
                    editor.putString("ime5",ime5);
                    editor.putString("ime6",ime6);
                    editor.putString("poruka5", poruka5);
                    editor.putString("poruka6",poruka6);
                    editor.putString("slika5", slika5);
                    editor.putString("slika6",slika6);
                    editor.putLong("broj1",broj1);
                    editor.putLong("broj2",broj2);
                    editor.putLong("broj3",broj3);
                    editor.putLong("broj4",broj4);
                    editor.putLong("broj5",broj5);
                    editor.putLong("broj6",broj6);
                    editor.putString("fax", (dataSnapshot.child("user").child(mUIDsender).child("fakultet").getValue(String.class)));
                    editor.putString("ods", (dataSnapshot.child("user").child(mUIDsender).child("odsjek").getValue(String.class)));
                    editor.putString("god", godina1);
                    editor.apply();
                   // Toast.makeText(TestActivity.this,ime5,Toast.LENGTH_SHORT).show();


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

        //FloatingActionButton fab = findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TestActivity.this, Chat1.class);
                startActivity(i);
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        View headerView = navigationView.getHeaderView(0);
        TextView usern = (TextView) headerView.findViewById(R.id.textViewime);
        usern.setText(mFirebaseAuth.getCurrentUser().getDisplayName());
        CircleImageView iV;
        iV = (CircleImageView) headerView.findViewById(R.id.imageViewslika);

        if(mFirebaseAuth.getCurrentUser().getPhotoUrl() != null) {
            Glide.with(TestActivity.this)
                    .load(mFirebaseAuth.getCurrentUser().getPhotoUrl())
                    .into(iV);
        }else{
            iV.setImageDrawable(ContextCompat.getDrawable(TestActivity.this,
                    R.drawable.logo1));
        }
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_gallery, R.id.nav_home, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        Intent i = new Intent(TestActivity.this,SifraActivity.class);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent i = new Intent(TestActivity.this,SifraActivity.class);
                this.startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void chat1(View view) {
        Intent i = new Intent(TestActivity.this, Chat1.class);
        //FavoritesFragment fragment = new FavoritesFragment();
        //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuf",uni1);
        bundle.putString("stuff", fakultet1);

//Add the bundle to the intent
        i.putExtras(bundle);

//Fire that second activity
        startActivity(i);


    }

    public void chat2(View view) {
        Intent i = new Intent(TestActivity.this, Chat1.class);
        //FavoritesFragment fragment = new FavoritesFragment();
        //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuf",uni1);
        bundle.putString("stuff", fakultet1);
        bundle.putString("stuff1", odsjek1);
//Add the bundle to the intent
        i.putExtras(bundle);

//Fire that second activity
        startActivity(i);


    }

    public void chat3(View view) {
        Intent i = new Intent(TestActivity.this, Chat1.class);
        //FavoritesFragment fragment = new FavoritesFragment();
        //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuf",uni1);
        bundle.putString("stuff", fakultet1);
        bundle.putString("stuff1", odsjek1);
        bundle.putString("stuff2",godina1);
//Add the bundle to the intent
        i.putExtras(bundle);

//Fire that second activity
        startActivity(i);


    }
    public void chat4(View view) {
        Intent i = new Intent(TestActivity.this, Chat1.class);
        //FavoritesFragment fragment = new FavoritesFragment();
        //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuf","_");
        bundle.putString("stuff", fakultet1);
        bundle.putString("stuff1", "_");
        bundle.putString("stuff2","_");
//Add the bundle to the intent
        i.putExtras(bundle);

//Fire that second activity
        startActivity(i);


    }
    public void chatstudenti(View view) {
        Intent i = new Intent(TestActivity.this, Chat1.class);
        //FavoritesFragment fragment = new FavoritesFragment();
        //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuf", "_");
        bundle.putString("stuff", "_");
        bundle.putString("stuff1", "_");
        bundle.putString("stuff2","_");
//Add the bundle to the intent
        i.putExtras(bundle);

//Fire that second activity
        startActivity(i);


    }
    public void chatuni(View view) {
        Intent i = new Intent(TestActivity.this, Chat1.class);
        //FavoritesFragment fragment = new FavoritesFragment();
        //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuf",uni1);
        bundle.putString("stuff", "_");
        bundle.putString("stuff1", "_");
        bundle.putString("stuff2","_");
//Add the bundle to the intent
        i.putExtras(bundle);

//Fire that second activity
        startActivity(i);


    }

    public void sign(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intToMain = new Intent(TestActivity.this, MainActivity.class);
        startActivity(intToMain);


    }
    public void obavijesti1(View view) {
        Intent i = new Intent(TestActivity.this, ObavijestiActivity.class);
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
    public void obavijest2(View view) {
        Intent i = new Intent(TestActivity.this, ObavijestiActivity.class);
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
    public void obavijest3(View view) {
        Intent i = new Intent(TestActivity.this, ObavijestiActivity.class);
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

    public void promjeniime(View view){
        startActivity(new Intent(TestActivity.this, AfterRegistration.class));
    }
    public  void promjenafax(View view){
        startActivity(new Intent(TestActivity.this,Nastavak.class));
    }
    public void promjenasifre(View view){
        startActivity(new Intent(TestActivity.this,PrijeMjenjanjaSifre.class));
    }

}