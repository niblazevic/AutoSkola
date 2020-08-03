package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class TeorijaPropisi extends AppCompatActivity {

    Button btnOpcaPitanjaTeorija, btnPrometniZnakoviTeorija, btnPropustanjeVozilaItdTeorija, btnOpasneSituacijeTeorija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_teorija_propisi);

        btnOpcaPitanjaTeorija = findViewById(R.id.btnOpcaPitanjaTeorija);
        btnPrometniZnakoviTeorija = findViewById(R.id.btnPrometniZnakoviTeorija);
        btnPropustanjeVozilaItdTeorija = findViewById(R.id.btnPropustanjeVozilaItdTeorija);
        btnOpasneSituacijeTeorija = findViewById(R.id.btnOpasneSituacijeTeorija);



        btnOpcaPitanjaTeorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent().getStringExtra("ADMIN")!=null){
                    Intent intent = new Intent(TeorijaPropisi.this,EditiranjePitanja.class);
                    intent.putExtra("KATEGORIJA", "Opća pitanja");
                    intent.putExtra("IDKATEGORIJE", "Kat1");
                    intent.putExtra("IDSETA","OpcaPitanja");
                    intent.putExtra("IDDETALJNO","0");
                    intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));

                    startActivity(intent);
                }else{
                    Intent intent = new Intent(TeorijaPropisi.this,TeorijaPitanja.class);
                    intent.putExtra("KATEGORIJA", "Opća pitanja");
                    intent.putExtra("IDKATEGORIJE", "Kat1");
                    intent.putExtra("IDSETA","OpcaPitanja");
                    intent.putExtra("IDDETALJNO","0");
                    intent.putExtra("LOGO","logo_image_opca");

                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(btnOpcaPitanjaTeorija, "logo_image_opca");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPropisi.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        btnPrometniZnakoviTeorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent().getStringExtra("ADMIN")!=null){
                    Intent intent = new Intent(TeorijaPropisi.this, TeorijaPropisiPrometniZnakovi.class);
                    intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(TeorijaPropisi.this, TeorijaPropisiPrometniZnakovi.class);
                    startActivity(intent);
                }
            }
        });

        btnPropustanjeVozilaItdTeorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("ADMIN")!=null){
                    Intent intent = new Intent(TeorijaPropisi.this, EditiranjePitanja.class);
                    intent.putExtra("KATEGORIJA", "Propuštanje vozila i prednost prolaska");
                    intent.putExtra("IDKATEGORIJE", "Kat1");
                    intent.putExtra("IDSETA","PropustanjeVozilaItd");
                    intent.putExtra("IDDETALJNO","0");
                    intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));

                    startActivity(intent);
                }else{
                    Intent intent = new Intent(TeorijaPropisi.this, TeorijaPitanja.class);
                    intent.putExtra("KATEGORIJA", "Propuštanje vozila i prednost prolaska");
                    intent.putExtra("IDKATEGORIJE", "Kat1");
                    intent.putExtra("IDSETA","PropustanjeVozilaItd");
                    intent.putExtra("IDDETALJNO","0");
                    intent.putExtra("LOGO","logo_image_prop");

                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(btnPropustanjeVozilaItdTeorija, "logo_image_prop");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPropisi.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        btnOpasneSituacijeTeorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("ADMIN")!=null){
                    Intent intent = new Intent(TeorijaPropisi.this, EditiranjePitanja.class);
                    intent.putExtra("KATEGORIJA", "Opasne situacije");
                    intent.putExtra("IDKATEGORIJE", "Kat1");
                    intent.putExtra("IDSETA","OpasneSituacije");
                    intent.putExtra("IDDETALJNO","0");
                    intent.putExtra("ADMIN", getIntent().getStringExtra("ADMIN"));

                    startActivity(intent);
                }else{
                    Intent intent = new Intent(TeorijaPropisi.this, TeorijaPitanja.class);
                    intent.putExtra("KATEGORIJA", "Opasne situacije");
                    intent.putExtra("IDKATEGORIJE", "Kat1");
                    intent.putExtra("IDSETA","OpasneSituacije");
                    intent.putExtra("IDDETALJNO","0");
                    intent.putExtra("LOGO","logo_image_opasne");

                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(btnOpasneSituacijeTeorija, "logo_image_opasne");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeorijaPropisi.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }
}
