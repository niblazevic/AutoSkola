package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class VjezbePropisiPrometniZnakovi extends AppCompatActivity {

    Button btnZnakoviIzricitihNaredbi, btnZnakoviOpasnosti, btnZnakoviObavijesti, btnZnakoviObavijestiItd, btnDopunskePloce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vjezbe_propisi_prometni_znakovi);

        btnZnakoviIzricitihNaredbi = findViewById(R.id.btnZnakoviIzricitihNaredbi);
        btnZnakoviOpasnosti = findViewById(R.id.btnZnakoviOpasnosti);
        btnZnakoviObavijesti = findViewById(R.id.btnZnakoviObavijesti);
        btnZnakoviObavijestiItd = findViewById(R.id.btnZnakoviObavijestiItd);
        btnDopunskePloce = findViewById(R.id.btnDopunskePloce);

        btnZnakoviIzricitihNaredbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePropisiPrometniZnakovi.this, VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "Prometni znakovi");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PrometniZnakovi");
                intent.putExtra("IDDETALJNO","ZnakoviIzricitihNaredbi");
                intent.putExtra("LOGO","logo_image_znakovi");
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnZnakoviIzricitihNaredbi, "logo_image_znakovi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePropisiPrometniZnakovi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnZnakoviOpasnosti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePropisiPrometniZnakovi.this, VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "Prometni znakovi");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PrometniZnakovi");
                intent.putExtra("IDDETALJNO","ZnakoviOpasnosti");
                intent.putExtra("LOGO","logo_image_znakovi");
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnZnakoviOpasnosti, "logo_image_znakovi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePropisiPrometniZnakovi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnZnakoviObavijesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePropisiPrometniZnakovi.this, VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "Prometni znakovi");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PrometniZnakovi");
                intent.putExtra("IDDETALJNO","ZnakoviObavijesti");
                intent.putExtra("LOGO","logo_image_znakovi");
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnZnakoviObavijesti, "logo_image_znakovi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePropisiPrometniZnakovi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnZnakoviObavijestiItd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePropisiPrometniZnakovi.this, VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "Prometni znakovi");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PrometniZnakovi");
                intent.putExtra("IDDETALJNO","ZnakoviObavijestiItd");
                intent.putExtra("LOGO","logo_image_znakovi");
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnZnakoviObavijestiItd, "logo_image_znakovi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePropisiPrometniZnakovi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnDopunskePloce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePropisiPrometniZnakovi.this, VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "Prometni znakovi");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PrometniZnakovi");
                intent.putExtra("IDDETALJNO","DopunskePloce");
                intent.putExtra("LOGO","logo_image_znakovi");
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnDopunskePloce, "logo_image_znakovi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePropisiPrometniZnakovi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

    }
}
