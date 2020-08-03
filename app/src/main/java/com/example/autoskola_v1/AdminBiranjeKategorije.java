package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class AdminBiranjeKategorije extends AppCompatActivity {

    Button btnKategorija1, btnKategorija2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_biranje_kategorije);

        btnKategorija1 = findViewById(R.id.btnKategorija1);
        btnKategorija2 = findViewById(R.id.btnKategorija2);

        btnKategorija1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBiranjeKategorije.this, AdminIzbornik.class);
                intent.putExtra("KATEGORIJA", "Kat1");
                startActivity(intent);
            }
        });

        btnKategorija2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBiranjeKategorije.this, AdminIzbornik.class);
                intent.putExtra("KATEGORIJA", "Kat2");
                startActivity(intent);
            }
        });
    }
}