package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Rezultati extends AppCompatActivity {

    Button btnPropisiRezultati, btnPrvaPomocRezultati, btnIspitiRezultati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rezultati);

        btnPropisiRezultati = findViewById(R.id.btnPropisiRezultati);
        btnPrvaPomocRezultati = findViewById(R.id.btnPrvaPomocRezultati);
        btnIspitiRezultati = findViewById(R.id.btnIspitiRezultati);

        btnPropisiRezultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rezultati.this, DetaljnoRezultati.class);
                intent.putExtra("KATEGORIJA", "Propisi");
                intent.putExtra("LOGO","logo_image_propisi");

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnPropisiRezultati, "logo_image_propisi");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Rezultati.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnPrvaPomocRezultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rezultati.this, DetaljnoRezultati.class);
                intent.putExtra("KATEGORIJA", "Prva pomoć");
                intent.putExtra("LOGO","logo_image_prvapomoc");

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnPrvaPomocRezultati, "logo_image_prvapomoc");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Rezultati.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnIspitiRezultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rezultati.this, DetaljnoRezultati.class);
                intent.putExtra("KATEGORIJA", "Ispiti");
                intent.putExtra("LOGO","logo_image_ispiti");

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnIspitiRezultati, "logo_image_ispiti");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Rezultati.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
