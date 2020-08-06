package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class AdminPitanje extends AppCompatActivity {

    TextView txtAdminOpcija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_pitanje);

        txtAdminOpcija = findViewById(R.id.txtAdminOpcija);

        txtAdminOpcija.setText(getIntent().getStringExtra("KATEGORIJA") + " - " + getIntent().getStringExtra("ADMIN"));

        Toast.makeText(this, getIntent().getStringExtra("TOCANODGOVOR"), Toast.LENGTH_SHORT).show();
    }
}