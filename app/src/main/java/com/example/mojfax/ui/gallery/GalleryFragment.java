package com.example.mojfax.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DisplayContext;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mojfax.AfterRegistation2;
import com.example.mojfax.Chat1;
import com.example.mojfax.FriendlyMessage;
import com.example.mojfax.Nastavak;
import com.example.mojfax.R;
import com.example.mojfax.TestActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.mojfax.Chat1.MESSAGES_CHILD;

public class GalleryFragment extends Fragment {
    private DatabaseReference mFirebaseDatabaseReference;
    private static final String TAG = "Fragment";

    private GalleryViewModel galleryViewModel;
    private TextView kosaljegodina;
    private TextView porukagodina;
    private CircleImageView slikagodina;
    private TextView kosaljeodsjek;
    private TextView porukaodsjek;
    private CircleImageView slikaodsjek;
    private TextView kosaljefakultet;
    private TextView porukafakultet;
    private CircleImageView slikafakultet;
    private TextView kosaljeuni;
    private TextView porukauni;
    private CircleImageView slikauni;
    private TextView kosaljesvi;
    private TextView porukasvi;
    private TextView broj11;
    private TextView broj12;
    private TextView broj13;
    private TextView broj14;
    private TextView broj15;
    private TextView broj16;
    private TextView imefax;
    private CircleImageView slikasvi;
    private CircleImageView slikafax;
    private TextView porukafax;
    private TextView kosaljefax;
    private DatabaseReference mDatabase;
    private FirebaseUser mFirebase;
    private FirebaseRecyclerAdapter<FriendlyMessage, Chat1.MessageViewHolder> mFirebaseAdapter;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String ods = preferences.getString("ods", " ");
        String god = preferences.getString("god"," ");
        mFirebase= FirebaseAuth.getInstance().getCurrentUser();
        kosaljegodina = root.findViewById(R.id.slaoporukugodina);
        porukagodina = root.findViewById(R.id.porukagodina);
        slikagodina = root.findViewById(R.id.slikagodina);
        kosaljeodsjek = root.findViewById(R.id.slaoporukuodsjek);
        porukaodsjek = root.findViewById(R.id.porukaodsjek);
        slikaodsjek = root.findViewById(R.id.slikaodsjek);

        kosaljefakultet = root.findViewById(R.id.slaoporukufakultet);
        porukafakultet = root.findViewById(R.id.porukafakultet);
        slikafakultet = root.findViewById(R.id.slikafakultet);
        kosaljeuni = root.findViewById(R.id.slaoporukuuni);
        porukauni = root.findViewById(R.id.porukauni);
        slikauni = root.findViewById(R.id.slikauni);
        kosaljesvi = root.findViewById(R.id.slaoporukusvi);
        porukasvi = root.findViewById(R.id.porukasvi);
        slikasvi = root.findViewById(R.id.slikasvi);
        imefax = root.findViewById(R.id.textViewiiii);
        porukafax= root.findViewById(R.id.porukafaks);
        kosaljefax= root.findViewById(R.id.slaoporukufaks);
        slikafax = root.findViewById(R.id.slikafaks);
        broj11 = root.findViewById(R.id.broj1);
        broj12 = root.findViewById(R.id.broj2);
        broj13 = root.findViewById(R.id.broj3);
        broj14 = root.findViewById(R.id.broj4);
        broj15 = root.findViewById(R.id.broj5);
        broj16 = root.findViewById(R.id.brojfaks);

        String ime = preferences.getString("fax"," ");
        String kosaljegodinat = preferences.getString("ime1","");
        String porukagodinat = preferences.getString("poruka1","");
        String slikagodinat = preferences.getString("slika1","");
        String kosaljeodsjekt = preferences.getString("ime2","");
        String porukaodsjekt=preferences.getString("poruka2","");
        String slikaodsjekt= preferences.getString("slika2","");
        String kosaljeunit = preferences.getString("ime4","");
        String porukaunit=preferences.getString("poruka4","");
        String slikaunit= preferences.getString("slika4","");
        String kosaljefakultett = preferences.getString("ime3","");
        String porukafakultett=preferences.getString("poruka3","");
        String slikafakultett= preferences.getString("slika3","");
        String kosaljesvit = preferences.getString("ime5","");
        String porukasvit=preferences.getString("poruka5","");
        String slikasvit= preferences.getString("slika5","");
        String porukafaxt = preferences.getString("poruka6"," ");
        String kosaljefaxt = preferences.getString("ime6"," ");
        String slikafaxt = preferences.getString("slika6", " ");

        long broj1 = preferences.getLong("broj1",0);
        long broj2 = preferences.getLong("broj2",0);
        long broj3 = preferences.getLong("broj3",0);
        long broj4 = preferences.getLong("broj4",0);
        long broj5 = preferences.getLong("broj5",0);
        long broj6 = preferences.getLong("broj6", 0);

        long broj1c = preferences.getLong("broj1c",0);
        long broj2c = preferences.getLong("broj2c",0);
        long broj3c = preferences.getLong("broj3c",0);
        long broj4c = preferences.getLong("broj4c",0);
        long broj5c = preferences.getLong("broj5c",0);
        long broj6c = preferences.getLong("broj6c", 0);


        imefax.setText("Chat sa drugim "+ ime );


        if(broj1==broj1c || (broj1-broj1c)<0) {
            broj11.setVisibility(EditText.INVISIBLE);
        }else{
            broj11.setVisibility(EditText.VISIBLE);
            broj11.setText(Long.toString(broj1 - broj1c));
        }

        if(broj2==broj2c || (broj2-broj2c)<0){
            broj12.setVisibility(EditText.INVISIBLE);
        }else {
            broj12.setVisibility(EditText.VISIBLE);
            broj12.setText(Long.toString(broj2 - broj2c));
        }

        if(broj3==broj3c || (broj3-broj3c)<0){
            broj13.setVisibility(EditText.INVISIBLE);

        }
        else{
            broj13.setVisibility(EditText.VISIBLE);
            broj13.setText(Long.toString(broj3 - broj3c));
        }

        if(broj4==broj4c || (broj4-broj4c)<0){

            broj14.setVisibility(EditText.INVISIBLE);

        }else{
            broj14.setVisibility(EditText.VISIBLE);
            broj14.setText(Long.toString(broj4-broj4c));

        }
        if(broj5==broj5c || (broj5-broj5c)<0){
            broj15.setVisibility(EditText.INVISIBLE);

        }else {
            broj15.setVisibility(EditText.VISIBLE);
            broj15.setText(Long.toString(broj5 - broj5c));
        }
        if(broj6==broj6c || (broj6-broj6c)<0){
            broj16.setVisibility(EditText.INVISIBLE);

        }else {
            broj16.setVisibility(EditText.VISIBLE);

            broj16.setText(Long.toString(broj6 - broj6c));
        }



        kosaljegodina.setText(kosaljegodinat);
        porukagodina.setText(porukagodinat);
        Toast.makeText(getActivity(),kosaljeodsjekt,Toast.LENGTH_SHORT).show();
       kosaljeodsjek.setText(kosaljeodsjekt);
        porukaodsjek.setText(porukaodsjekt);

        Glide.with(getActivity())
                .load(slikagodinat)
                .into(slikagodina);
        Glide.with(getActivity())
                .load(slikaodsjekt)
                .into(slikaodsjek);
        kosaljefakultet.setText(kosaljefakultett);
        porukafakultet.setText(porukafakultett);

        Glide.with(getActivity())
                .load(slikafakultett)
                .into(slikafakultet);
        kosaljeuni.setText(kosaljeunit);
        porukauni.setText(porukaunit);
        kosaljeuni.setText(kosaljeunit);
        porukauni.setText(porukaunit);

        Glide.with(getActivity())
                .load(slikaunit)
                .into(slikauni);
        Glide.with(getActivity())
                .load(slikaunit)
                .into(slikauni);

        kosaljesvi.setText(kosaljesvit);
        porukasvi.setText(porukasvit);

        Glide.with(getActivity())
                .load(slikasvit)
                .into(slikasvi);

        kosaljefax.setText(kosaljefaxt);
        porukafax.setText(porukafaxt);

        Glide.with(getActivity())
                .load(slikafaxt)
                .into(slikafax);


        TextView jen,dva,tri;
        jen = root.findViewById(R.id.textViewi);
        dva = root.findViewById(R.id.textViewa);
        tri = root.findViewById(R.id.textView7);
        jen.setText("Chat sa " + ime);
        dva.setText("Chat sa " + ods);
        tri.setText("Chat sa " +god+ ". godinom " + ods);

        return root;

    }
}