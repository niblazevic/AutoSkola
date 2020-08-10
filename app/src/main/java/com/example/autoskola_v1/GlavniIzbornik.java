package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.annotation.Nullable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GlavniIzbornik extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    ImageView imgAuto;
    TextView txtHeaderImePrezime, txtHeaderEmail, txtNaslovGlavniIzbornik, txtPodnaslovGlavniIzbornik;
    Button btnTeorijaPropisi, btnTeorijaPrvaPomoc, btnVjezbePropisi, btnVjezbePrvaPomoc, btnIspit, btnRezultati;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View headerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_glavni_izbornik);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbarmenu);

        btnTeorijaPropisi = findViewById(R.id.btnTeorijaPropisi);
        btnTeorijaPrvaPomoc = findViewById(R.id.btnTeorijaPrvaPomoc);
        btnVjezbePropisi = findViewById(R.id.btnVjezbePropisi);
        btnVjezbePrvaPomoc = findViewById(R.id.btnVjezbePrvaPomoc);
        btnIspit = findViewById(R.id.btnIspit);
        btnRezultati = findViewById(R.id.btnRezultati);
        imgAuto = findViewById(R.id.imgAuto);
        txtNaslovGlavniIzbornik = findViewById(R.id.txtNaslovGlavniIzbornik);
        txtPodnaslovGlavniIzbornik = findViewById(R.id.txtPodnaslovGlavniIzbornik);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Menu menu = navigationView.getMenu();
        MenuItem admin = menu.findItem(R.id.navAdmin);

        if(!Objects.equals(Objects.requireNonNull(fAuth.getCurrentUser()).getEmail(), "nblaevi7@gmail.com")){
            admin.setVisible(false);
        }else{
            admin.setVisible(true);
        }


        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        txtHeaderImePrezime = headerView.findViewById(R.id.txtHeaderImePrezime);
        txtHeaderEmail = headerView.findViewById(R.id.txtHeaderEmail);

        headerUcitavanje();

        btnTeorijaPropisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlavniIzbornik.this,TeorijaPropisi.class);
                startActivity(intent);
            }
        });
        btnTeorijaPrvaPomoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GlavniIzbornik.this,TeorijaPrvaPomoc.class));
            }
        });
        btnVjezbePropisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GlavniIzbornik.this,VjezbePropisi.class));
            }
        });
        btnVjezbePrvaPomoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GlavniIzbornik.this,VjezbePrvaPomoc.class));
            }
        });
        btnIspit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GlavniIzbornik.this,Ispit.class));
            }
        });
        btnRezultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GlavniIzbornik.this,Rezultati.class));
            }
        });

        }

    private void headerUcitavanje() {
        DocumentReference documentReference = fStore.collection("users").document(Objects.requireNonNull(fAuth.getCurrentUser()).getUid());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtHeaderImePrezime.setText(documentSnapshot != null ? documentSnapshot.getString("ImePrezime") : null);
                txtHeaderEmail.setText(documentSnapshot != null ? documentSnapshot.getString("Email") : null);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.navPromjenaPodataka:
                startActivity(new Intent(GlavniIzbornik.this,PromjenaPodataka.class));
                break;

            case R.id.navUpute:
                startActivity(new Intent(GlavniIzbornik.this, Upute.class));
                break;

            case R.id.navAdmin:
                startActivity(new Intent(GlavniIzbornik.this, AdminBiranjeKategorije.class));
                break;

            case R.id.navOdjava:
                fAuth.signOut();
                Intent intent = new Intent(GlavniIzbornik.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(imgAuto, "logo_image");
                pairs[1] = new Pair<View, String>(txtNaslovGlavniIzbornik, "logo_text");
                pairs[2] = new Pair<View, String>(txtPodnaslovGlavniIzbornik, "logo_desc");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GlavniIzbornik.this, pairs);
                startActivity(intent, options.toBundle());
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}