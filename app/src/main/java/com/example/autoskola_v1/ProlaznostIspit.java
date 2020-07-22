package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class ProlaznostIspit extends AppCompatActivity {

    TextView txtBodovi, txtTocnostPostotak, txtProlaznostRezultat, txtBrojBodovaDovoljno, txtTocnostRaskrizja;
    Button btnIzlaz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prolaznost_ispit);

        txtBodovi = findViewById(R.id.txtBodovi);
        txtTocnostPostotak = findViewById(R.id.txtTocnostPostotak);
        txtProlaznostRezultat = findViewById(R.id.txtProlaznostRezultat);
        txtBrojBodovaDovoljno = findViewById(R.id.txtBrojBodovaDovoljno);
        txtTocnostRaskrizja = findViewById(R.id.txtTocnostRaskrizja);
        btnIzlaz = findViewById(R.id.btnIzlaz);

        txtBodovi.setText(getIntent().getStringExtra("BODOVI"));
        txtBrojBodovaDovoljno.setText(getIntent().getStringExtra("BROJBODOVADOVOLJNO"));
        txtTocnostPostotak.setText(getIntent().getStringExtra("POSTOTAK"));
        txtProlaznostRezultat.setText(getIntent().getStringExtra("PROLAZNOST"));
        txtTocnostRaskrizja.setText(getIntent().getStringExtra("RASKRIZJA"));


        btnIzlaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProlaznostIspit.this, GlavniIzbornik.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}