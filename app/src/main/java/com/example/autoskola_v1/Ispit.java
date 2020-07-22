package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.sax.EndElementListener;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

public class Ispit extends AppCompatActivity {

    TextView txtHeaderImePrezime, txtHeaderEmail;
    TextView txtTekstPitanjaIspit, txtVrijemeIspit, txtBrojPitanjaIspit;
    ImageView imgSlikaIspit;
    Button btnOdgovor1Ispit, btnOdgovor2Ispit, btnOdgovor3Ispit, btnPrethodnoPitanje, btnSljedecePitanje, btnZavrsiIspit;
    List<Pitanje> ListaPitanja;
    List<OznaceniOdgovoriIspit> ListaOdgovora;
    int velicinaSeta, redniBrojPitanja;
    CountDownTimer countDownTimer;
    String userID, brojBodovaDovoljno, formattedPercent, prolaznost, tocnostRaskrizja;
    int OznaceniOdgovor = 0;
    int BrojBodova = 0, raskrizja = 0;
    int brojRjesenihIspita = 1;
    int brojac = 0;
    double postotak, BrojBodova1;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View headerView;

    private static final String FORMAT = "%02d:%02d";

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    Ucitavanje ucitavanje = new Ucitavanje(Ispit.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ispit);

        ucitavanje.pokreniUcitavanje();

        drawerLayout = findViewById(R.id.drawer_layout_ispit);
        navigationView = findViewById(R.id.nav_viewIspit);
        toolbar = findViewById(R.id.toolbarmenuispit);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        txtTekstPitanjaIspit = findViewById(R.id.txtTekstPitanjaIspit);
        txtVrijemeIspit = findViewById(R.id.txtVrijemeIspit);
        txtBrojPitanjaIspit = findViewById(R.id.txtBrojPitanjaIspit);
        imgSlikaIspit = findViewById(R.id.imgSlikaIspit);
        btnOdgovor1Ispit = findViewById(R.id.btnOdgovor1Ispit);
        btnOdgovor2Ispit = findViewById(R.id.btnOdgovor2Ispit);
        btnOdgovor3Ispit = findViewById(R.id.btnOdgovor3Ispit);
        btnPrethodnoPitanje = findViewById(R.id.btnPrethodnoPitanje);
        btnSljedecePitanje = findViewById(R.id.btnSljedecePitanje);
        btnZavrsiIspit = findViewById(R.id.btnZavrsiIspit);
        txtHeaderImePrezime = findViewById(R.id.txtHeaderImePrezime);
        txtHeaderEmail = findViewById(R.id.txtHeaderEmail);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.getMenu().getItem(0).setTitle(Html.fromHtml("<font color='#ffcc00'>1. pitanje</font>"));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                                                             @Override
                                                             public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                                                                 switch (item.getItemId()){
                                                                     case R.id.navPitanje1:
                                                                         redniBrojPitanja = 1;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje2:
                                                                         redniBrojPitanja = 2;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje3:
                                                                         redniBrojPitanja = 3;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje4:
                                                                         redniBrojPitanja = 4;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje5:
                                                                         redniBrojPitanja = 5;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje6:
                                                                         redniBrojPitanja = 6;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje7:
                                                                         redniBrojPitanja = 7;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje8:
                                                                         redniBrojPitanja = 8;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje9:
                                                                         redniBrojPitanja = 9;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje10:
                                                                         redniBrojPitanja = 10;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje11:
                                                                         redniBrojPitanja = 11;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje12:
                                                                         redniBrojPitanja = 12;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje13:
                                                                         redniBrojPitanja = 13;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje14:
                                                                         redniBrojPitanja = 14;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje15:
                                                                         redniBrojPitanja = 15;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje16:
                                                                         redniBrojPitanja = 16;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje17:
                                                                         redniBrojPitanja = 17;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje18:
                                                                         redniBrojPitanja = 18;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje19:
                                                                         redniBrojPitanja = 19;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje20:
                                                                         redniBrojPitanja = 20;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje21:
                                                                         redniBrojPitanja = 21;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje22:
                                                                         redniBrojPitanja = 22;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje23:
                                                                         redniBrojPitanja = 23;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje24:
                                                                         redniBrojPitanja = 24;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje25:
                                                                         redniBrojPitanja = 25;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje26:
                                                                         redniBrojPitanja = 26;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje27:
                                                                         redniBrojPitanja = 27;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje28:
                                                                         redniBrojPitanja = 28;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje29:
                                                                         redniBrojPitanja = 29;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje30:
                                                                         redniBrojPitanja = 30;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje31:
                                                                         redniBrojPitanja = 31;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje32:
                                                                         redniBrojPitanja = 32;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje33:
                                                                         redniBrojPitanja = 33;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje34:
                                                                         redniBrojPitanja = 34;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje35:
                                                                         redniBrojPitanja = 35;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje36:
                                                                         redniBrojPitanja = 36;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje37:
                                                                         redniBrojPitanja = 37;
                                                                         promjeniPitanje(true);
                                                                         break;

                                                                     case R.id.navPitanje38:
                                                                         redniBrojPitanja = 36;
                                                                         promjeniPitanje(false);
                                                                         break;
                                                                 }
                                                                 drawerLayout.closeDrawer(GravityCompat.START);
                                                                 return true;
                                                             }
                                                         }


        );

        headerView = navigationView.getHeaderView(0);
        txtHeaderImePrezime = headerView.findViewById(R.id.txtHeaderImePrezime);
        txtHeaderEmail = headerView.findViewById(R.id.txtHeaderEmail);

        headerUcitavanje();

        btnOdgovor1Ispit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oznacavanjeOdgovora("1");
            }
        });

        btnOdgovor2Ispit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oznacavanjeOdgovora("2");
            }
        });

        btnOdgovor3Ispit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oznacavanjeOdgovora("3");
            }
        });

        btnPrethodnoPitanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPrethodnoPitanje.setEnabled(false);
                btnSljedecePitanje.setEnabled(false);
                btnZavrsiIspit.setEnabled(false);
                promjeniPitanje(true);
            }
        });

        btnSljedecePitanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPrethodnoPitanje.setEnabled(false);
                btnSljedecePitanje.setEnabled(false);
                btnZavrsiIspit.setEnabled(false);
                promjeniPitanje(false);
            }
        });

        btnZavrsiIspit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Jeste li sigurni da želite završiti ispit?").setPositiveButton("Da", dialogClickListener)
                        .setNegativeButton("Ne", dialogClickListener).show();
            }
        });

        getListaPitanja();
    }

    private void getListaPitanja() {
        ListaPitanja = new ArrayList<>();
        ListaOdgovora = new ArrayList<>();

        DocumentReference documentReference = fStore.collection("Pitanja").document("Kat1").collection("BrojPitanja").document("OpcaPitanja");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    velicinaSeta = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getLong("BrojPitanja")).intValue();

                    if (velicinaSeta < 18) {
                        Intent intent = new Intent(Ispit.this, VjezbeRezultati.class);
                        intent.putExtra("REZULTAT", "Trenutno ne postoji dovoljan broj pitanja za kreiranje ispita!");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        List<Integer> ListaBrojeva = new ArrayList<>();
                        Random random = new Random();
                        int temp;

                        for (int i = 0; i < 18; i++) {
                            temp = random.nextInt(velicinaSeta) + 1;
                            if (ListaBrojeva.contains(temp)) {
                                i--;
                            } else {
                                ListaBrojeva.add(temp);
                                DocumentReference documentReference1 = fStore.collection("Pitanja").document("Kat1").collection("OpcaPitanja").document("Pitanje" + ListaBrojeva.get(i));
                                final int finalI = i;
                                documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot snapshot = task.getResult();
                                            if(finalI < 14){
                                                Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje") + " (2 boda)", snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), snapshot.getLong("TocanOdgovor"), snapshot.getString("Slika"));
                                                ListaPitanja.add(pitanje);
                                                ListaOdgovora.add(new OznaceniOdgovoriIspit("0", "0", "0", 2));
                                            }else{

                                                Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje") + " (3 boda)", snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), snapshot.getLong("TocanOdgovor"), snapshot.getString("Slika"));
                                                ListaPitanja.add(pitanje);
                                                ListaOdgovora.add(new OznaceniOdgovoriIspit("0", "0", "0", 3));
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });


        documentReference = fStore.collection("Pitanja").document("Kat1").collection("BrojPitanja").document("PrometniZnakovi").collection("Pitanja").document("ZnakoviOpasnosti");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    velicinaSeta = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getLong("BrojPitanja")).intValue();

                    if (velicinaSeta < 2) {
                        Intent intent = new Intent(Ispit.this, VjezbeRezultati.class);
                        intent.putExtra("REZULTAT", "Trenutno ne postoji dovoljan broj pitanja za kreiranje ispita!");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        List<Integer> ListaBrojeva = new ArrayList<>();
                        Random random = new Random();
                        int temp;

                        for (int i = 0; i < 2; i++) {
                            temp = random.nextInt(velicinaSeta) + 1;
                            if (ListaBrojeva.contains(temp)) {
                                i--;
                            } else {
                                ListaBrojeva.add(temp);
                                DocumentReference documentReference1 = fStore.collection("Pitanja").document("Kat1").collection("PrometniZnakovi").document("ZnakoviOpasnosti").collection("Pitanja").document("Pitanje" + ListaBrojeva.get(i));
                                documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot snapshot = task.getResult();
                                            Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje") + " (3 boda)", snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), snapshot.getLong("TocanOdgovor"), snapshot.getString("Slika"));
                                            ListaPitanja.add(pitanje);
                                            ListaOdgovora.add(new OznaceniOdgovoriIspit("0", "0", "0", 3));
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

        documentReference = fStore.collection("Pitanja").document("Kat1").collection("BrojPitanja").document("PrometniZnakovi").collection("Pitanja").document("ZnakoviObavijesti");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    velicinaSeta = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getLong("BrojPitanja")).intValue();

                    if (velicinaSeta < 1) {
                        Intent intent = new Intent(Ispit.this, VjezbeRezultati.class);
                        intent.putExtra("REZULTAT", "Trenutno ne postoji dovoljan broj pitanja za kreiranje ispita!");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        List<Integer> ListaBrojeva = new ArrayList<>();
                        Random random = new Random();
                        int temp;

                        for (int i = 0; i < 1; i++) {
                            temp = random.nextInt(velicinaSeta) + 1;
                            if (ListaBrojeva.contains(temp)) {
                                i--;
                            } else {
                                ListaBrojeva.add(temp);
                                DocumentReference documentReference1 = fStore.collection("Pitanja").document("Kat1").collection("PrometniZnakovi").document("ZnakoviObavijesti").collection("Pitanja").document("Pitanje" + ListaBrojeva.get(i));
                                documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot snapshot = task.getResult();
                                            Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje") + " (3 boda)", snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), snapshot.getLong("TocanOdgovor"), snapshot.getString("Slika"));
                                            ListaPitanja.add(pitanje);
                                            ListaOdgovora.add(new OznaceniOdgovoriIspit("0", "0", "0", 3));
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

        documentReference = fStore.collection("Pitanja").document("Kat1").collection("BrojPitanja").document("PrometniZnakovi").collection("Pitanja").document("ZnakoviObavijestiItd");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    velicinaSeta = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getLong("BrojPitanja")).intValue();

                    if (velicinaSeta < 1) {
                        Intent intent = new Intent(Ispit.this, VjezbeRezultati.class);
                        intent.putExtra("REZULTAT", "Trenutno ne postoji dovoljan broj pitanja za kreiranje ispita!");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        List<Integer> ListaBrojeva = new ArrayList<>();
                        Random random = new Random();
                        int temp;

                        for (int i = 0; i < 1; i++) {
                            temp = random.nextInt(velicinaSeta) + 1;
                            if (ListaBrojeva.contains(temp)) {
                                i--;
                            } else {
                                ListaBrojeva.add(temp);
                                DocumentReference documentReference1 = fStore.collection("Pitanja").document("Kat1").collection("PrometniZnakovi").document("ZnakoviObavijestiItd").collection("Pitanja").document("Pitanje" + ListaBrojeva.get(i));
                                documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot snapshot = task.getResult();
                                            Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje") + " (3 boda)", snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), snapshot.getLong("TocanOdgovor"), snapshot.getString("Slika"));
                                            ListaPitanja.add(pitanje);
                                            ListaOdgovora.add(new OznaceniOdgovoriIspit("0", "0", "0", 3));
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

        documentReference = fStore.collection("Pitanja").document("Kat1").collection("BrojPitanja").document("PrometniZnakovi").collection("Pitanja").document("ZnakoviIzricitihNaredbi");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    velicinaSeta = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getLong("BrojPitanja")).intValue();

                    if (velicinaSeta < 1) {
                        Intent intent = new Intent(Ispit.this, VjezbeRezultati.class);
                        intent.putExtra("REZULTAT", "Trenutno ne postoji dovoljan broj pitanja za kreiranje ispita!");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        List<Integer> ListaBrojeva = new ArrayList<>();
                        Random random = new Random();
                        int temp;

                        for (int i = 0; i < 1; i++) {
                            temp = random.nextInt(velicinaSeta) + 1;
                            if (ListaBrojeva.contains(temp)) {
                                i--;
                            } else {
                                ListaBrojeva.add(temp);
                                DocumentReference documentReference1 = fStore.collection("Pitanja").document("Kat1").collection("PrometniZnakovi").document("ZnakoviIzricitihNaredbi").collection("Pitanja").document("Pitanje" + ListaBrojeva.get(i));
                                documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot snapshot = task.getResult();
                                            Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje") + " (3 boda)", snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), snapshot.getLong("TocanOdgovor"), snapshot.getString("Slika"));
                                            ListaPitanja.add(pitanje);
                                            ListaOdgovora.add(new OznaceniOdgovoriIspit("0", "0", "0", 3));
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

        documentReference = fStore.collection("Pitanja").document("Kat1").collection("BrojPitanja").document("PrometniZnakovi").collection("Pitanja").document("DopunskePloce");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    velicinaSeta = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getLong("BrojPitanja")).intValue();

                    if (velicinaSeta < 1) {
                        Intent intent = new Intent(Ispit.this, VjezbeRezultati.class);
                        intent.putExtra("REZULTAT", "Trenutno ne postoji dovoljan broj pitanja za kreiranje ispita!");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        List<Integer> ListaBrojeva = new ArrayList<>();
                        Random random = new Random();
                        int temp;

                        for (int i = 0; i < 1; i++) {
                            temp = random.nextInt(velicinaSeta) + 1;
                            if (ListaBrojeva.contains(temp)) {
                                i--;
                            } else {
                                ListaBrojeva.add(temp);
                                DocumentReference documentReference1 = fStore.collection("Pitanja").document("Kat1").collection("PrometniZnakovi").document("DopunskePloce").collection("Pitanja").document("Pitanje" + ListaBrojeva.get(i));
                                documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot snapshot = task.getResult();
                                            Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje") + " (3 boda)", snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), snapshot.getLong("TocanOdgovor"), snapshot.getString("Slika"));
                                            ListaPitanja.add(pitanje);
                                            ListaOdgovora.add(new OznaceniOdgovoriIspit("0", "0", "0", 3));
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });



        documentReference = fStore.collection("Pitanja").document("Kat1").collection("BrojPitanja").document("PropustanjeVozilaItd");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    velicinaSeta = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getLong("BrojPitanja")).intValue();

                    if (velicinaSeta < 4) {
                        Intent intent = new Intent(Ispit.this, VjezbeRezultati.class);
                        intent.putExtra("REZULTAT", "Trenutno ne postoji dovoljan broj pitanja za kreiranje ispita!");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        List<Integer> ListaBrojeva = new ArrayList<>();
                        Random random = new Random();
                        int temp;

                        for (int i = 0; i < 4; i++) {
                            temp = random.nextInt(velicinaSeta) + 1;
                            if (ListaBrojeva.contains(temp)) {
                                i--;
                            } else {
                                ListaBrojeva.add(temp);
                                DocumentReference documentReference1 = fStore.collection("Pitanja").document("Kat1").collection("PropustanjeVozilaItd").document("Pitanje" + ListaBrojeva.get(i));
                                documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot snapshot = task.getResult();
                                            Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje") + " (7 bodova)", snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), snapshot.getLong("TocanOdgovor"), snapshot.getString("Slika"));
                                            ListaPitanja.add(pitanje);
                                            ListaOdgovora.add(new OznaceniOdgovoriIspit("0", "0", "0", 7));
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

        documentReference = fStore.collection("Pitanja").document("Kat1").collection("BrojPitanja").document("OpasneSituacije");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    velicinaSeta = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getLong("BrojPitanja")).intValue();

                    if (velicinaSeta < 10) {
                        Intent intent = new Intent(Ispit.this, VjezbeRezultati.class);
                        intent.putExtra("REZULTAT", "Trenutno ne postoji dovoljan broj pitanja za kreiranje ispita!");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        List<Integer> ListaBrojeva = new ArrayList<>();
                        Random random = new Random();
                        int temp;

                        for (int i = 0; i < 10; i++) {
                            temp = random.nextInt(velicinaSeta) + 1;
                            if (ListaBrojeva.contains(temp)) {
                                i--;
                            } else {
                                ListaBrojeva.add(temp);
                                DocumentReference documentReference1 = fStore.collection("Pitanja").document("Kat1").collection("OpasneSituacije").document("Pitanje" + ListaBrojeva.get(i));
                                final int finalI = i;
                                documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot snapshot = task.getResult();
                                            if(finalI < 4){
                                                Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje") + " (4 boda)", snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), snapshot.getLong("TocanOdgovor"), snapshot.getString("Slika"));
                                                ListaPitanja.add(pitanje);
                                                ListaOdgovora.add(new OznaceniOdgovoriIspit("0", "0", "0", 4));
                                            }else{
                                                Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje") + " (3 boda)", snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), snapshot.getLong("TocanOdgovor"), snapshot.getString("Slika"));
                                                ListaPitanja.add(pitanje);
                                                ListaOdgovora.add(new OznaceniOdgovoriIspit("0", "0", "0", 3));
                                            }
                                            setPitanje();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

    }

    private void setPitanje() {
        redniBrojPitanja = 0;

        txtTekstPitanjaIspit.setText(ListaPitanja.get(0).getPitanje());
        txtBrojPitanjaIspit.setText("Pitanje: " + (redniBrojPitanja + 1) + "/38");
        btnOdgovor1Ispit.setText(ListaPitanja.get(0).getOdgovor1());
        btnOdgovor2Ispit.setText(ListaPitanja.get(0).getOdgovor2());


        if(ListaPitanja.get(redniBrojPitanja).getOdgovor3().equals("0")){
            btnOdgovor3Ispit.setVisibility(View.GONE);
            btnOdgovor3Ispit.setEnabled(false);
        }else{
            btnOdgovor3Ispit.setVisibility(View.VISIBLE);
            btnOdgovor3Ispit.setEnabled(true);
            btnOdgovor3Ispit.setText(ListaPitanja.get(0).getOdgovor3());
        }

        if (!ListaPitanja.get(0).getSlika().equals("")) {

            imgSlikaIspit.setVisibility(View.VISIBLE);

            Picasso.get().load(ListaPitanja.get(0).getSlika()).into(imgSlikaIspit, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    ucitavanje.makniUcitavanje();

                    final Dialog dialog = new Dialog(Ispit.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    dialog.setContentView(R.layout.obavijest_ispit);

                    Button btnDialog = dialog.findViewById(R.id.btnPokreniIspit);
                    btnDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            startTimer();
                        }
                    });
                    dialog.show();

                }

                @Override
                public void onError(Exception e) {

                }
            });
        }else{
            if(brojac == 0){
                imgSlikaIspit.setVisibility(View.GONE);
                ucitavanje.makniUcitavanje();
                final Dialog dialog = new Dialog(Ispit.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.obavijest_ispit);

                Button btnDialog = dialog.findViewById(R.id.btnPokreniIspit);
                btnDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startTimer();
                    }
                });
                dialog.show();
            }
            brojac++;
        }

        btnPrethodnoPitanje.setEnabled(false);
    }

    private void promjeniPitanje(boolean dugme) {
        if(dugme){
            if (redniBrojPitanja > 0){
                ucitavanje.pokreniUcitavanje();
                redniBrojPitanja--;

                navigationView.getMenu().getItem(redniBrojPitanja).setChecked(true);
                navigationView.getMenu().getItem(redniBrojPitanja).setTitle(Html.fromHtml("<font color='#ffcc00'>"+ (redniBrojPitanja + 1) +". pitanje</font>"));

                txtBrojPitanjaIspit.setText("Pitanje: " + (redniBrojPitanja + 1) + "/" + ListaPitanja.size());
                playAnim(txtTekstPitanjaIspit, 0, 0);
                playAnim(btnOdgovor1Ispit, 0, 1);
                playAnim(btnOdgovor2Ispit, 0, 2);
                if(ListaPitanja.get(redniBrojPitanja).getOdgovor3().equals("0")){
                    btnOdgovor3Ispit.setVisibility(View.GONE);
                    btnOdgovor3Ispit.setEnabled(false);
                }else{
                    btnOdgovor3Ispit.setEnabled(true);
                    btnOdgovor3Ispit.setVisibility(View.VISIBLE);
                    playAnim(btnOdgovor3Ispit, 0, 3);
                }

                if(!ListaPitanja.get(redniBrojPitanja).getSlika().equals("")){

                    imgSlikaIspit.setVisibility(View.VISIBLE);

                    Picasso.get().load(ListaPitanja.get(redniBrojPitanja).getSlika()).into(imgSlikaIspit, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            ucitavanje.makniUcitavanje();
                            btnPrethodnoPitanje.setEnabled(true);
                            btnSljedecePitanje.setEnabled(true);
                            btnZavrsiIspit.setEnabled(true);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }else{
                    imgSlikaIspit.setVisibility(View.GONE);
                    ucitavanje.makniUcitavanje();
                    btnPrethodnoPitanje.setEnabled(true);
                    btnSljedecePitanje.setEnabled(true);
                    btnZavrsiIspit.setEnabled(true);
                }

                if(redniBrojPitanja == 0){
                    btnPrethodnoPitanje.setEnabled(false);
                }
            }
        }else{
            if (redniBrojPitanja < ListaPitanja.size() - 1){
                redniBrojPitanja++;
                ucitavanje.pokreniUcitavanje();

                navigationView.getMenu().getItem(redniBrojPitanja).setChecked(true);
                navigationView.getMenu().getItem(redniBrojPitanja).setTitle(Html.fromHtml("<font color='#ffcc00'>"+ (redniBrojPitanja + 1) +". pitanje</font>"));

                txtBrojPitanjaIspit.setText("Pitanje: " + (redniBrojPitanja + 1) + "/" + ListaPitanja.size());

                playAnim(txtTekstPitanjaIspit, 0, 0);
                playAnim(btnOdgovor1Ispit, 0, 1);
                playAnim(btnOdgovor2Ispit, 0, 2);
                if(ListaPitanja.get(redniBrojPitanja).getOdgovor3().equals("0")){
                    btnOdgovor3Ispit.setVisibility(View.GONE);
                    btnOdgovor3Ispit.setEnabled(false);
                }else{
                    btnOdgovor3Ispit.setVisibility(View.VISIBLE);
                    btnOdgovor3Ispit.setEnabled(true);
                    playAnim(btnOdgovor3Ispit, 0, 3);
                }


                if(!ListaPitanja.get(redniBrojPitanja).getSlika().equals("")){

                    imgSlikaIspit.setVisibility(View.VISIBLE);

                    Picasso.get().load(ListaPitanja.get(redniBrojPitanja).getSlika()).into(imgSlikaIspit, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            ucitavanje.makniUcitavanje();
                            btnPrethodnoPitanje.setEnabled(true);
                            btnSljedecePitanje.setEnabled(true);
                            btnZavrsiIspit.setEnabled(true);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }else{
                    imgSlikaIspit.setVisibility(View.GONE);
                    ucitavanje.makniUcitavanje();
                    btnPrethodnoPitanje.setEnabled(true);
                    btnSljedecePitanje.setEnabled(true);
                    btnZavrsiIspit.setEnabled(true);
                }

                if(redniBrojPitanja == ListaPitanja.size() - 1){
                    btnSljedecePitanje.setEnabled(false);
                }
            }
        }
    }

    private void playAnim(final View view, final int value, final int viewBroj) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(150).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if(value == 0){
                    switch (viewBroj){
                        case 0:
                            ((TextView)view).setText(ListaPitanja.get(redniBrojPitanja).getPitanje());
                            break;

                        case 1:
                            ((Button)view).setText(ListaPitanja.get(redniBrojPitanja).getOdgovor1());
                            break;

                        case 2:
                            ((Button)view).setText(ListaPitanja.get(redniBrojPitanja).getOdgovor2());
                            break;

                        case 3:
                            ((Button)view).setText(ListaPitanja.get(redniBrojPitanja).getOdgovor3());
                            break;
                    }
                    playAnim(view, 1, viewBroj);
                }
            }
        }).withEndAction(new Runnable() {
            @Override
            public void run() {
                ucitavanjeOdgovora();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(2700000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtVrijemeIspit.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                zavrsetakIspita();
            }
        };
        countDownTimer.start();
    }

    private void oznacavanjeOdgovora(String oznaceniOdgovor) {
        if(oznaceniOdgovor.equals("1")){
            if(ListaOdgovora.get(redniBrojPitanja).getOznakaOdgovor1().equals("0")){
                ListaOdgovora.get(redniBrojPitanja).setOznakaOdgovor1("1");
                btnOdgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffcc00")));
                int img = R.drawable.oznaceno_icon;
                btnOdgovor1Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
            else{
                ListaOdgovora.get(redniBrojPitanja).setOznakaOdgovor1("0");
                btnOdgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                int img = R.drawable.neoznaceno_icon;
                btnOdgovor1Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
        }
        if(oznaceniOdgovor.equals("2")){
            if(ListaOdgovora.get(redniBrojPitanja).getOznakaOdgovor2().equals("0")){
                ListaOdgovora.get(redniBrojPitanja).setOznakaOdgovor2("1");
                btnOdgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffcc00")));
                int img = R.drawable.oznaceno_icon;
                btnOdgovor2Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
            else{
                ListaOdgovora.get(redniBrojPitanja).setOznakaOdgovor2("0");
                btnOdgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                int img = R.drawable.neoznaceno_icon;
                btnOdgovor2Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
        }
        if(oznaceniOdgovor.equals("3")){
            if(ListaOdgovora.get(redniBrojPitanja).getOznakaOdgovor3().equals("0")){
                ListaOdgovora.get(redniBrojPitanja).setOznakaOdgovor3("1");
                btnOdgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffcc00")));
                int img = R.drawable.oznaceno_icon;
                btnOdgovor3Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
            else{
                ListaOdgovora.get(redniBrojPitanja).setOznakaOdgovor3("0");
                btnOdgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                int img = R.drawable.neoznaceno_icon;
                btnOdgovor3Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
        }
    }

    private void ucitavanjeOdgovora() {
        if(ListaOdgovora.get(redniBrojPitanja).getOznakaOdgovor1().equals("1")){
            btnOdgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffcc00")));
            int img = R.drawable.oznaceno_icon;
            btnOdgovor1Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
        }else{
            btnOdgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
            int img = R.drawable.neoznaceno_icon;
            btnOdgovor1Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
        }
        if(ListaOdgovora.get(redniBrojPitanja).getOznakaOdgovor2().equals("1")){
            btnOdgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffcc00")));
            int img = R.drawable.oznaceno_icon;
            btnOdgovor2Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
        }else{
            btnOdgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
            int img = R.drawable.neoznaceno_icon;
            btnOdgovor2Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
        }
        if(ListaOdgovora.get(redniBrojPitanja).getOznakaOdgovor3().equals("1")){
            btnOdgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffcc00")));
            int img = R.drawable.oznaceno_icon;
            btnOdgovor3Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
        }else{
            btnOdgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
            int img = R.drawable.neoznaceno_icon;
            btnOdgovor3Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
        }
    }

    private void zavrsetakIspita() {

        ucitavanje.pokreniUcitavanje();
        countDownTimer.cancel();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();


        final DocumentReference documentReference = fStore.collection("users").document(userID).collection("BrojRjesenihIspita").document("Broj");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    if(!task.getResult().exists()){
                        Map<String, Integer> rezultatBroj = new HashMap<>();
                        rezultatBroj.put("Broj", 1);
                        documentReference.set(rezultatBroj);
                    }else{
                        brojRjesenihIspita = task.getResult().getLong("Broj").intValue() + 1;
                    }


                    Map<String, String> brojIspita = new HashMap<>();

                    DocumentReference documentReference1 = fStore.collection("users").document(userID).collection("RjeseniIspiti").document("Ispit" + brojRjesenihIspita);
                    brojIspita.put("Naziv", brojRjesenihIspita + ". ispit");
                    brojIspita.put("BrojIspita", "Ispit" + brojRjesenihIspita);
                    documentReference1.set(brojIspita);

                    Map<String, Object> rezultat = new HashMap<>();

                    for(int i = 0; i < 38; i++){
                        documentReference1 = fStore.collection("users").document(userID).collection("RjeseniIspiti").document("Ispit" + brojRjesenihIspita).collection("PitanjaIOdgovori").document("PitanjeIOdgovori" + (i + 1));

                        rezultat.put("Pitanje", ListaPitanja.get(i).getPitanje());
                        rezultat.put("Odgovor1", ListaPitanja.get(i).getOdgovor1());
                        rezultat.put("Odgovor2", ListaPitanja.get(i).getOdgovor2());
                        rezultat.put("Odgovor3", ListaPitanja.get(i).getOdgovor3());
                        rezultat.put("TocanOdgovor", ListaPitanja.get(i).getTocanOdgovor());
                        rezultat.put("Slika", ListaPitanja.get(i).getSlika());
                        rezultat.put("OznakaOdgovor1", ListaOdgovora.get(i).getOznakaOdgovor1());
                        rezultat.put("OznakaOdgovor2", ListaOdgovora.get(i).getOznakaOdgovor2());
                        rezultat.put("OznakaOdgovor3", ListaOdgovora.get(i).getOznakaOdgovor3());
                        rezultat.put("BrojBodova", ListaOdgovora.get(i).getBrojBodova());
                        rezultat.put("RedniBroj", i+1);

                        documentReference1.set(rezultat);
                        rezultat.clear();
                    }

                    Calendar c = Calendar.getInstance();

                    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
                    String formattedDate = df.format(c.getTime());

                    documentReference1 = fStore.collection("users").document(userID).collection("RjeseniIspiti").document("Ispit" + brojRjesenihIspita);
                    rezultat.put("BrojBodova", BrojBodova + "/120");
                    rezultat.put("BrojBodovaDovoljno", brojBodovaDovoljno);
                    rezultat.put("Postotak", formattedPercent);
                    rezultat.put("Prolaznost", prolaznost);
                    rezultat.put("Raskrizja", tocnostRaskrizja);
                    rezultat.put("Naziv", brojRjesenihIspita + ". ispit - " + formattedDate);
                    rezultat.put("BrojIspita", "Ispit" + brojRjesenihIspita);
                    rezultat.put("RedniBroj", brojRjesenihIspita);
                    documentReference1.set(rezultat);


                            Map<String, Integer> rezultatBroj = new HashMap<>();
                            rezultatBroj.put("Broj", brojRjesenihIspita);
                            documentReference.set(rezultatBroj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    ucitavanje.makniUcitavanje();
                                    Intent intent = new Intent(Ispit.this, ProlaznostIspit.class);
                                    intent.putExtra("BODOVI", BrojBodova + "/120");
                                    intent.putExtra("BROJBODOVADOVOLJNO", brojBodovaDovoljno);
                                    intent.putExtra("POSTOTAK", formattedPercent);
                                    intent.putExtra("PROLAZNOST", prolaznost);
                                    intent.putExtra("RASKRIZJA", tocnostRaskrizja);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }});
                }
            }
        });
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    provjeraOdgovora();
                    zavrsetakIspita();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    countDownTimer.cancel();
                    Intent intent = new Intent(Ispit.this, GlavniIzbornik.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    private void provjeraOdgovora() {
        for(int i = 0; i < ListaOdgovora.size(); i++){
            OznaceniOdgovor = 0;

            if(ListaOdgovora.get(i).getOznakaOdgovor1().equals("1")){
                OznaceniOdgovor = 1;
            }
            if(ListaOdgovora.get(i).getOznakaOdgovor2().equals("1")){
                if(OznaceniOdgovor == 1){
                    OznaceniOdgovor = 12;
                }
                else{
                    OznaceniOdgovor = 2;
                }
            }
            if(ListaOdgovora.get(i).getOznakaOdgovor3().equals("1")){
                if(OznaceniOdgovor == 1){
                    OznaceniOdgovor = 13;
                }
                if(OznaceniOdgovor == 12){
                    OznaceniOdgovor = 123;
                }
                if(OznaceniOdgovor == 2){
                    OznaceniOdgovor = 23;
                }
                if(OznaceniOdgovor == 0){
                    OznaceniOdgovor = 3;
                }
            }

            if(ListaPitanja.get(i).getTocanOdgovor() == OznaceniOdgovor) {
                BrojBodova = BrojBodova + ListaOdgovora.get(i).getBrojBodova();
                if(ListaOdgovora.get(i).getBrojBodova() == 7){
                    raskrizja++;
                }
            }
        }

        BrojBodova1 = BrojBodova;

        DecimalFormat df = new DecimalFormat("##.##%");
        postotak = (BrojBodova1 / 120);
        formattedPercent = df.format(postotak);

        if(BrojBodova > 107 && raskrizja == 4){
            brojBodovaDovoljno = "Broj bodova je dovoljan za prolaz!";
            prolaznost = "Ispit je položen!";
            tocnostRaskrizja = "Raskrižja su točno riješena!";
        }else{
            brojBodovaDovoljno = "Broj bodova nije dovoljan za prolaz!";
            prolaznost = "Ispit nije položen!";
            tocnostRaskrizja = "Raskrižja nisu točno riješena!";
            if(raskrizja == 4){
                tocnostRaskrizja = "Raskrižja su točno riješena, ali broj bodova je premali za prolazak!";
            }
        }
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private void headerUcitavanje() {
        DocumentReference documentReference = fStore.collection("users").document(fAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtHeaderImePrezime.setText(documentSnapshot != null ? documentSnapshot.getString("ImePrezime") : null);
                txtHeaderEmail.setText(documentSnapshot != null ? documentSnapshot.getString("Email") : null);
            }
        });
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Jeste li sigurni da želite napustiti ispit?").setPositiveButton("Da", dialogClickListener2)
                .setNegativeButton("Ne", dialogClickListener2).show();
    }
}