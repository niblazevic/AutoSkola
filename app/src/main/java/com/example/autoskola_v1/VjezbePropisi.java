package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class VjezbePropisi extends AppCompatActivity {

    Button btnOpcaPitanja, btnPrometniZnakovi, btnPropustanjeVozilaitd, btnOpasneSituacije;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vjezbe_propisi);

        btnOpcaPitanja = findViewById(R.id.btnOpcaPitanja);
        btnPrometniZnakovi = findViewById(R.id.btnPrometniZnakovi);
        btnPropustanjeVozilaitd = findViewById(R.id.btnPropustanjeVozilaitd);
        btnOpasneSituacije = findViewById(R.id.btnOpasneSituacije);



        btnOpcaPitanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePropisi.this, VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "Opća pitanja");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","OpcaPitanja");
                intent.putExtra("IDDETALJNO","0");
                intent.putExtra("LOGO","logo_image_opca");

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnOpcaPitanja, "logo_image_opca");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePropisi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
        btnPrometniZnakovi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePropisi.this, VjezbePropisiPrometniZnakovi.class);
                startActivity(intent);
            }
        });
        btnPropustanjeVozilaitd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePropisi.this, VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "Propuštanje vozila i prednost prolaska");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","PropustanjeVozilaItd");
                intent.putExtra("IDDETALJNO","0");
                intent.putExtra("LOGO","logo_image_prop");

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnPropustanjeVozilaitd, "logo_image_prop");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePropisi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
        btnOpasneSituacije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VjezbePropisi.this, VjezbePitanja.class);
                intent.putExtra("KATEGORIJA", "Opasne situacije");
                intent.putExtra("IDKATEGORIJE", "Kat1");
                intent.putExtra("IDSETA","OpasneSituacije");
                intent.putExtra("IDDETALJNO","0");
                intent.putExtra("LOGO","logo_image_opasne");

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(btnOpasneSituacije, "logo_image_opasne");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePropisi.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
