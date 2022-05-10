package com.example.mojfax;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FavoritesFragment extends Fragment {

Button btnChat,btnChat2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);

    }

    public void chat1(View view) {
        Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/muhamed.presi.dent/"));
        startActivity(i2);
    }
}
