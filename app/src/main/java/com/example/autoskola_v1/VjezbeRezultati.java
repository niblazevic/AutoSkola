package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VjezbeRezultati extends AppCompatActivity {

    LinearLayout linearLayout8;
    TextView txtRezultatVjezbeRezultati, txtKategorijaRezultati;
    Button btnIzlazVjezbeRezultati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vjezbe_rezultati);

        txtRezultatVjezbeRezultati = findViewById(R.id.txtRezultatVjezbeRezultati);
        txtKategorijaRezultati = findViewById(R.id.txtKategorijaRezultati);
        btnIzlazVjezbeRezultati = findViewById(R.id.btnIzlazVjezbeRezultati);
        linearLayout8 = findViewById(R.id.linearLayout8);

        linearLayout8.setTransitionName(getIntent().getStringExtra("LOGO"));

        String rezultatStr = getIntent().getStringExtra("REZULTAT");
        txtRezultatVjezbeRezultati.setText(rezultatStr);

        rezultatStr = getIntent().getStringExtra("KATEGORIJA");
        txtKategorijaRezultati.setText(rezultatStr);

        btnIzlazVjezbeRezultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VjezbeRezultati.this,GlavniIzbornik.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
