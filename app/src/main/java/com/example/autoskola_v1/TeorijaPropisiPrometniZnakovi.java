package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class TeorijaPropisiPrometniZnakovi extends AppCompatActivity {

    Button btnZnakoviIzricitihNaredbiTeorija, btnZnakoviOpasnostiTeorija, btnZnakoviObavijestiTeorija, btnZnakoviObavijestiItdTeorija, btnDopunskePloceTeorija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_teorija_propisi_prometni_znakovi);

        btnZnakoviIzricitihNaredbiTeorija = findViewById(R.id.btnZnakoviIzricitihNaredbiTeorija);
        btnZnakoviOpasnostiTeorija = findViewById(R.id.btnZnakoviOpasnostiTeorija);
        btnZnakoviObavijestiTeorija = findViewById(R.id.btnZnakoviObavijestiTeorija);
        btnZnakoviObavijestiItdTeorija = findViewById(R.id.btnZnakoviObavijestiItdTeorija);
        btnDopunskePloceTeorija = findViewById(R.id.btnDopunskePloceTeorija);

        btnZnakoviIzricitihNaredbiTeorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeorijaPropisiPrometniZnakovi.this, TeorijaPitanja.class);
                intent.putExtra("KATEGORIJA", "Prometni znakovi");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PrometniZnakovi");
                intent.putExtra("IDDETALJNO","ZnakoviIzricitihNaredbi");
                intent.putExtra("LOGO","logo_image_znakovi");
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnZnakoviIzricitihNaredbiTeorija, "logo_image_znakovi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPropisiPrometniZnakovi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnZnakoviOpasnostiTeorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeorijaPropisiPrometniZnakovi.this, TeorijaPitanja.class);
                intent.putExtra("KATEGORIJA", "Prometni znakovi");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PrometniZnakovi");
                intent.putExtra("IDDETALJNO","ZnakoviOpasnosti");
                intent.putExtra("LOGO","logo_image_znakovi");
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnZnakoviIzricitihNaredbiTeorija, "logo_image_znakovi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPropisiPrometniZnakovi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnZnakoviObavijestiTeorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeorijaPropisiPrometniZnakovi.this, TeorijaPitanja.class);
                intent.putExtra("KATEGORIJA", "Prometni znakovi");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PrometniZnakovi");
                intent.putExtra("IDDETALJNO","ZnakoviObavijesti");
                intent.putExtra("LOGO","logo_image_znakovi");
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnZnakoviIzricitihNaredbiTeorija, "logo_image_znakovi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPropisiPrometniZnakovi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnZnakoviObavijestiItdTeorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeorijaPropisiPrometniZnakovi.this, TeorijaPitanja.class);
                intent.putExtra("KATEGORIJA", "Prometni znakovi");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PrometniZnakovi");
                intent.putExtra("IDDETALJNO","ZnakoviObavijestiItd");
                intent.putExtra("LOGO","logo_image_znakovi");
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnZnakoviIzricitihNaredbiTeorija, "logo_image_znakovi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPropisiPrometniZnakovi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnDopunskePloceTeorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeorijaPropisiPrometniZnakovi.this, TeorijaPitanja.class);
                intent.putExtra("KATEGORIJA", "Prometni znakovi");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PrometniZnakovi");
                intent.putExtra("IDDETALJNO","DopunskePloce");
                intent.putExtra("LOGO","logo_image_znakovi");
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnZnakoviIzricitihNaredbiTeorija, "logo_image_znakovi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPropisiPrometniZnakovi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
