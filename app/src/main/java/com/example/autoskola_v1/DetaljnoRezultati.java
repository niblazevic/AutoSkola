package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.Objects;

public class DetaljnoRezultati extends AppCompatActivity {

    LinearLayout linearLayout26, txtBrojZavrsenihLayout, txtPostotakTocnihOdgovoraUVjezbamaLayout, txtNajboljeRjesenaVjezbaLayout, txtBrojPolozenihIspitaLayout;
    ImageView imgSlikaDetaljnoRezultati;
    TextView txtKategorijaDetaljnoRezultati, txtBrojZavrsenih, txtPostotakTocnihOdgovoraUVjezbama, txtNajboljeRjesenaVjezba, txtBrojPolozenihIspita, txtBrojZavrsenihNesto;
    Button btnIspitDetaljnoRezultati;
    int prolaznost = 0;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detaljno_rezultati);

        linearLayout26 = findViewById(R.id.linearLayout26);
        txtBrojZavrsenihLayout = findViewById(R.id.txtBrojZavrsenihLayout);
        txtPostotakTocnihOdgovoraUVjezbamaLayout = findViewById(R.id.txtPostotakTocnihOdgovoraUVjezbamaLayout);
        txtNajboljeRjesenaVjezbaLayout = findViewById(R.id.txtNajboljeRjesenaVjezbaLayout);
        txtBrojPolozenihIspitaLayout = findViewById(R.id.txtBrojPolozenihIspitaLayout);

        imgSlikaDetaljnoRezultati = findViewById(R.id.imgSlikaDetaljnoRezultati);
        txtKategorijaDetaljnoRezultati = findViewById(R.id.txtKategorijaDetaljnoRezultati);
        txtBrojZavrsenih = findViewById(R.id.txtBrojZavrsenih);
        txtPostotakTocnihOdgovoraUVjezbama = findViewById(R.id.txtPostotakTocnihOdgovoraUVjezbama);
        txtNajboljeRjesenaVjezba = findViewById(R.id.txtNajboljeRjesenaVjezba);
        txtBrojPolozenihIspita = findViewById(R.id.txtBrojPolozenihIspita);
        txtBrojZavrsenihNesto = findViewById(R.id.txtBrojZavrsenihNesto);
        btnIspitDetaljnoRezultati = findViewById(R.id.btnIspitDetaljnoRezultati);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        linearLayout26.setTransitionName(getIntent().getStringExtra("LOGO"));
        txtKategorijaDetaljnoRezultati.setText(getIntent().getStringExtra("KATEGORIJA"));

        btnIspitDetaljnoRezultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetaljnoRezultati.this, ListaIspita.class);
                startActivity(intent);
            }
        });

        if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Propisi")){
            imgSlikaDetaljnoRezultati.setImageResource(R.drawable.vjezba_propisi_icon);
            getPropisiRezultati();
        }

        if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Prva pomoć")){
            imgSlikaDetaljnoRezultati.setImageResource(R.drawable.vjezba_prva_pomoc_icon);
            getPrvaPomocRezultati();
        }

        if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Ispiti")){
            imgSlikaDetaljnoRezultati.setImageResource(R.drawable.ispit_propisi_icon);
            getIspitiRezultati();
        }
    }

    private void getPropisiRezultati() {
        txtBrojPolozenihIspitaLayout.setVisibility(View.GONE);
        btnIspitDetaljnoRezultati.setVisibility(View.GONE);

        CollectionReference collectionReference = fStore.collection("users").document(userID).collection("Kat1");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int brojacZavrsenihVjezbi = 0;
                    double tocniOdgovori = 0;
                    double brojPitanja = 0;
                    double postotak;
                    double najboljeRjesen = 0;

                    for(QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())){
                        tocniOdgovori = tocniOdgovori + snapshot.getDouble("BrojTocnihOdgovora");
                        brojPitanja = brojPitanja + snapshot.getDouble("BrojPitanja");
                        if(snapshot.getDouble("BrojTocnihOdgovora") / snapshot.getDouble("BrojPitanja") > najboljeRjesen){
                            najboljeRjesen = snapshot.getDouble("BrojTocnihOdgovora") / snapshot.getDouble("BrojPitanja");
                        }
                        brojacZavrsenihVjezbi++;
                    }

                    if(brojacZavrsenihVjezbi == 0){

                        txtBrojZavrsenih.setText(String.valueOf(brojacZavrsenihVjezbi));
                        txtPostotakTocnihOdgovoraUVjezbama.setText(String.valueOf(0));
                        txtNajboljeRjesenaVjezba.setText(String.valueOf(0));

                    }else{
                        DecimalFormat df = new DecimalFormat("##.##%");
                        postotak = (tocniOdgovori / brojPitanja);
                        String formattedPercent = df.format(postotak);
                        String formattedPercent1 = df.format(najboljeRjesen);

                        txtBrojZavrsenih.setText(String.valueOf(brojacZavrsenihVjezbi));
                        txtPostotakTocnihOdgovoraUVjezbama.setText(formattedPercent);
                        txtNajboljeRjesenaVjezba.setText(formattedPercent1);
                    }
                }
            }
        });
    }

    private void getPrvaPomocRezultati() {
        txtBrojPolozenihIspitaLayout.setVisibility(View.GONE);
        btnIspitDetaljnoRezultati.setVisibility(View.GONE);

        CollectionReference collectionReference1 = fStore.collection("users").document(userID).collection("Kat2");
        collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int brojacZavrsenihVjezbi = 0;
                    double tocniOdgovori = 0;
                    double brojPitanja = 0;
                    double postotak;
                    double najboljeRjesen = 0;

                    for(QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())){
                        tocniOdgovori = tocniOdgovori + snapshot.getDouble("BrojTocnihOdgovora");
                        brojPitanja = brojPitanja + snapshot.getDouble("BrojPitanja");
                        if(snapshot.getDouble("BrojTocnihOdgovora") / snapshot.getDouble("BrojPitanja") > najboljeRjesen){
                            najboljeRjesen = snapshot.getDouble("BrojTocnihOdgovora") / snapshot.getDouble("BrojPitanja");
                        }
                        brojacZavrsenihVjezbi++;
                    }

                    if(brojacZavrsenihVjezbi == 0){

                        txtBrojZavrsenih.setText(String.valueOf(brojacZavrsenihVjezbi));
                        txtPostotakTocnihOdgovoraUVjezbama.setText(String.valueOf(0));
                        txtNajboljeRjesenaVjezba.setText(String.valueOf(0));

                    }else{
                        DecimalFormat df = new DecimalFormat("##.##%");
                        postotak = (tocniOdgovori / brojPitanja);
                        String formattedPercent = df.format(postotak);
                        String formattedPercent1 = df.format(najboljeRjesen);

                        txtBrojZavrsenih.setText(String.valueOf(brojacZavrsenihVjezbi));
                        txtPostotakTocnihOdgovoraUVjezbama.setText(formattedPercent);
                        txtNajboljeRjesenaVjezba.setText(formattedPercent1);
                    }
                }
            }
        });
    }

    private void getIspitiRezultati() {
        txtPostotakTocnihOdgovoraUVjezbamaLayout.setVisibility(View.GONE);
        txtNajboljeRjesenaVjezbaLayout.setVisibility(View.GONE);

        DocumentReference documentReference = fStore.collection("users").document(userID).collection("BrojRjesenihIspita").document("Broj");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    assert snapshot != null;

                    if(snapshot.getLong("Broj") == null){
                        txtBrojZavrsenih.setText("0");
                        btnIspitDetaljnoRezultati.setVisibility(View.GONE);
                    }else{
                        txtBrojZavrsenih.setText(String.valueOf(snapshot.getLong("Broj")));
                    }
                    txtBrojZavrsenihNesto.setText("Broj završenih ispita:");


                    if(snapshot.getLong("Broj") != null) {
                        CollectionReference collectionReference = fStore.collection("users").document(userID).collection("RjeseniIspiti");
                        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                        if(Objects.equals(document.getData().get("Prolaznost"), "Ispit je položen!")){
                                            prolaznost++;
                                        }
                                    }
                                    txtBrojPolozenihIspita.setText(String.valueOf(prolaznost));
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}