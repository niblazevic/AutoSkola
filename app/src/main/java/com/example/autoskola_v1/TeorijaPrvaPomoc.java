package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class TeorijaPrvaPomoc extends AppCompatActivity {

    Button btn1_22Teorija, btn23_44Teorija, btn45_66Teorija;
    TextView txtOpis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_teorija_prva_pomoc);

        btn1_22Teorija = findViewById(R.id.btn1_22Teorija);
        btn23_44Teorija = findViewById(R.id.btn23_44Teorija);
        btn45_66Teorija = findViewById(R.id.btn45_66Teorija);
        txtOpis = findViewById(R.id.txtOpis);

        if(getIntent().getStringExtra("ADMIN")!=null) {
            txtOpis.setText("Odaberi kategoriju za " + getIntent().getStringExtra("ADMIN") + ":");
        }

        btn1_22Teorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("ADMIN")!=null){

                    if(Objects.equals(getIntent().getStringExtra("ADMIN"), "Dodavanje")){
                        Intent intent = new Intent(TeorijaPrvaPomoc.this,AdminPitanje.class);
                        intent.putExtra("KATEGORIJA", "1. set pitanja");
                        intent.putExtra("IDKATEGORIJE", "Kat2");
                        intent.putExtra("IDSETA","1_22");
                        intent.putExtra("IDDETALJNO","0");
                        intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(TeorijaPrvaPomoc.this,EditiranjePitanja.class);
                        intent.putExtra("KATEGORIJA", "1. set pitanja");
                        intent.putExtra("IDKATEGORIJE", "Kat2");
                        intent.putExtra("IDSETA","1_22");
                        intent.putExtra("IDDETALJNO","0");
                        intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));
                        startActivity(intent);
                    }

                }else{
                    Intent intent = new Intent(TeorijaPrvaPomoc.this,TeorijaPitanja.class);
                    intent.putExtra("KATEGORIJA", "1. set pitanja");
                    intent.putExtra("IDKATEGORIJE", "Kat2");
                    intent.putExtra("IDSETA","1_22");
                    intent.putExtra("IDDETALJNO","0");
                    intent.putExtra("LOGO","logo_image_set_1");

                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(btn1_22Teorija, "logo_image_set_1");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPrvaPomoc.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        btn23_44Teorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("ADMIN")!=null){

                    if(Objects.equals(getIntent().getStringExtra("ADMIN"), "Dodavanje")){
                        Intent intent = new Intent(TeorijaPrvaPomoc.this,AdminPitanje.class);
                        intent.putExtra("KATEGORIJA", "2. set pitanja");
                        intent.putExtra("IDKATEGORIJE", "Kat2");
                        intent.putExtra("IDSETA","23_44");
                        intent.putExtra("IDDETALJNO","0");
                        intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(TeorijaPrvaPomoc.this,EditiranjePitanja.class);
                        intent.putExtra("KATEGORIJA", "2. set pitanja");
                        intent.putExtra("IDKATEGORIJE", "Kat2");
                        intent.putExtra("IDSETA","23_44");
                        intent.putExtra("IDDETALJNO","0");
                        intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));
                        startActivity(intent);
                    }

                }else{
                    Intent intent = new Intent(TeorijaPrvaPomoc.this,TeorijaPitanja.class);
                    intent.putExtra("KATEGORIJA", "2. set pitanja");
                    intent.putExtra("IDKATEGORIJE", "Kat2");
                    intent.putExtra("IDSETA","23_44");
                    intent.putExtra("IDDETALJNO","0");
                    intent.putExtra("LOGO","logo_image_set_2");

                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(btn23_44Teorija, "logo_image_set_2");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPrvaPomoc.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        btn45_66Teorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("ADMIN")!=null){

                    if(Objects.equals(getIntent().getStringExtra("ADMIN"), "Dodavanje")){
                        Intent intent = new Intent(TeorijaPrvaPomoc.this,AdminPitanje.class);
                        intent.putExtra("KATEGORIJA", "3. set pitanja");
                        intent.putExtra("IDKATEGORIJE", "Kat2");
                        intent.putExtra("IDSETA","45_66");
                        intent.putExtra("IDDETALJNO","0");
                        intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(TeorijaPrvaPomoc.this,EditiranjePitanja.class);
                        intent.putExtra("KATEGORIJA", "3. set pitanja");
                        intent.putExtra("IDKATEGORIJE", "Kat2");
                        intent.putExtra("IDSETA","45_66");
                        intent.putExtra("IDDETALJNO","0");
                        intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));
                        startActivity(intent);
                    }

                }else{
                    Intent intent = new Intent(TeorijaPrvaPomoc.this,TeorijaPitanja.class);
                    intent.putExtra("KATEGORIJA", "3. set pitanja");
                    intent.putExtra("IDKATEGORIJE", "Kat2");
                    intent.putExtra("IDSETA","45_66");
                    intent.putExtra("IDDETALJNO","0");
                    intent.putExtra("LOGO","logo_image_set_3");

                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(btn45_66Teorija, "logo_image_set_3");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPrvaPomoc.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }
}
