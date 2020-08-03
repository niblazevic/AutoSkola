package com.example.autoskola_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Objects;

public class AdminIzbornik extends AppCompatActivity {

    Button btnDodaj, btnIzmjeni, btnIzbrisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_izbornik);

        btnDodaj = findViewById(R.id.btnDodaj);
        btnIzmjeni = findViewById(R.id.btnIzmjeni);
        btnIzbrisi = findViewById(R.id.btnIzbrisi);

        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Kat1")){
                    Intent intent = new Intent(AdminIzbornik.this, TeorijaPropisi.class);
                    intent.putExtra("ADMIN", "Dodavanje");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(AdminIzbornik.this, TeorijaPrvaPomoc.class);
                    intent.putExtra("ADMIN", "Dodavanje");
                    startActivity(intent);
                }
            }
        });
        btnIzmjeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Kat1")){
                    Intent intent = new Intent(AdminIzbornik.this, TeorijaPropisi.class);
                    intent.putExtra("ADMIN", "Izmjena");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(AdminIzbornik.this, TeorijaPrvaPomoc.class);
                    intent.putExtra("ADMIN", "Izmjena");
                    startActivity(intent);
                }
            }
        });
        btnIzbrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Kat1")){
                    Intent intent = new Intent(AdminIzbornik.this, TeorijaPropisi.class);
                    intent.putExtra("ADMIN", "Brisanje");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(AdminIzbornik.this, TeorijaPrvaPomoc.class);
                    intent.putExtra("ADMIN", "Brisanje");
                    startActivity(intent);
                }
            }
        });

        /*
        fAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if(task.isSuccessful()){

                    OkHttpClient client = new OkHttpClient();

                    String url = "https://firestore.googleapis.com/v1/projects/autoskolav1/databases/(default)/documents/Pitanja/Kat1/BrojPitanja/OpcaPitanja";
                    String auth = "Bearer " + task.getResult().getToken();

                    Request request = new Request.Builder().header("Authorization", auth).url(url).build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                final String myResponse = Objects.requireNonNull(response.body()).string();

                                AdminIzbornik.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AdminIzbornik.this, myResponse, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });*/
    }
}