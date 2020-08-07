package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class VjezbePitanja extends AppCompatActivity {

    LinearLayout linearLayout4;
    TextView txtBrojPitanja, txtTekstPitanja, txtBrojac, txtInfo;
    ImageView imgInfo, imgPitanja;
    Button btnOdgovor1, btnOdgovor2, btnOdgovor3, btnOdgovori;
    List<Pitanje> ListaPitanja;
    CountDownTimer countDownTimer;
    int redniBrojPitanja, rezultatExtra;
    int velinicnaSeta;
    int odgovor = 0;
    int provjeraTimera = 0;
    String OznakaOdgovor1 = "0", OznakaOdgovor2 = "0", OznakaOdgovor3 = "0";
    String userID;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    Ucitavanje ucitavanje = new Ucitavanje(VjezbePitanja.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vjezbe_pitanja);

        ucitavanje.pokreniUcitavanje();

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        txtBrojPitanja = findViewById(R.id.txtBrojPitanja);
        txtTekstPitanja = findViewById(R.id.txtTekstPitanja);
        txtInfo = findViewById(R.id.txtInfo);
        txtBrojac = findViewById(R.id.txtBrojac);

        imgInfo = findViewById(R.id.imgInfo);
        imgPitanja = findViewById(R.id.imgPitanja);

        btnOdgovor1 = findViewById(R.id.btnOdgovor1);
        btnOdgovor2 = findViewById(R.id.btnOdgovor2);
        btnOdgovor3 = findViewById(R.id.btnOdgovor3);
        btnOdgovori = findViewById(R.id.btnOdgovori);

        linearLayout4 = findViewById(R.id.linearLayout4);

        linearLayout4.setTransitionName(getIntent().getStringExtra("LOGO"));


        txtInfo.setText(getIntent().getStringExtra("KATEGORIJA"));
        if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Opća pitanja")){
            imgInfo.setImageResource(R.drawable.opca_pitanja_icon);
        }
        if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Prometni znakovi")){
            imgInfo.setImageResource(R.drawable.prometni_znakovi_icon);
        }
        if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Propuštanje vozila i prednost prolaska")){
            imgInfo.setImageResource(R.drawable.propustanje_vozila_itd_icon);
        }
        if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Opasne situacije")){
            imgInfo.setImageResource(R.drawable.opasne_situacije_icon);
        }
        if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "1 - 22")){
            imgInfo.setImageResource(R.drawable.first_aid_kit_icon);
        }
        if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "23 - 44")){
            imgInfo.setImageResource(R.drawable.bandage_icon);
        }
        if(Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "45 - 66")){
            imgInfo.setImageResource(R.drawable.revive_icon);
        }


        btnOdgovor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oznacavanjeOdgovora("1", v);
            }
        });
        btnOdgovor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oznacavanjeOdgovora("2", v);
            }
        });
        btnOdgovor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oznacavanjeOdgovora("3", v);
            }
        });

        btnOdgovori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                if(odgovor == 0){
                    btnOdgovori.setText("Sljedeće Pitanje");
                    provjeraOdogovora(OznakaOdgovor1, OznakaOdgovor2, OznakaOdgovor3);
                    odgovor = 1;
                }else{
                    btnOdgovori.setText("Odgovori");
                    oznacavanjeOdgovora("0", v);
                    promjeniPitanje();
                    odgovor = 0;
                }

            }
        });

        getListaPitanja();

        rezultatExtra = 0;
    }

    private void getListaPitanja(){
        ListaPitanja = new ArrayList<>();

        if(String.valueOf(getIntent().getStringExtra("IDDETALJNO")).equals("0")){

            DocumentReference documentReference = fStore.collection("Pitanja").document(String.valueOf(getIntent().getStringExtra("IDKATEGORIJE"))).collection("BrojPitanja").document(String.valueOf(getIntent().getStringExtra("IDSETA")));
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        velinicnaSeta = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getLong("BrojPitanja")).intValue();

                        if(velinicnaSeta < 20){
                            txtBrojPitanja.setText("Pitanje 1/" + velinicnaSeta);
                            if(velinicnaSeta == 0){

                                countDownTimer.cancel();

                                Intent intent = new Intent(VjezbePitanja.this, VjezbeRezultati.class);
                                intent.putExtra("REZULTAT", "Baza podataka je prazna!");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                            }else {
                                for (int i = 1; i <= velinicnaSeta; i++) {

                                    DocumentReference documentReference1 = fStore.collection("Pitanja").document(String.valueOf(getIntent().getStringExtra("IDKATEGORIJE"))).collection(String.valueOf(getIntent().getStringExtra("IDSETA"))).document("Pitanje" + i);
                                    documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot snapshot = task.getResult();
                                                assert snapshot != null;
                                                Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje"), snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), Objects.requireNonNull(snapshot.getLong("TocanOdgovor")), snapshot.getString("Slika"));
                                                ListaPitanja.add(pitanje);
                                                setPitanje();
                                            }
                                        }
                                    });
                                }
                            }

                        }else{
                            txtBrojPitanja.setText("Pitanje 1/20");
                            List<Integer> ListaBrojeva = new ArrayList<>();
                            Random random = new Random();
                            int temp;

                            for(int i = 0; i < 20; i++){
                                temp = random.nextInt(velinicnaSeta) +1;
                                if(ListaBrojeva.contains(temp)){
                                    i--;
                                }else{
                                    ListaBrojeva.add(temp);
                                    DocumentReference documentReference1 = fStore.collection("Pitanja").document(String.valueOf(getIntent().getStringExtra("IDKATEGORIJE"))).collection(String.valueOf(getIntent().getStringExtra("IDSETA"))).document("Pitanje" + ListaBrojeva.get(i));
                                    documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                DocumentSnapshot snapshot = task.getResult();
                                                assert snapshot != null;
                                                Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje"), snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), Objects.requireNonNull(snapshot.getLong("TocanOdgovor")), snapshot.getString("Slika"));
                                                ListaPitanja.add(pitanje);
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
        }else {
            DocumentReference documentReference = fStore.collection("Pitanja").document(String.valueOf(getIntent().getStringExtra("IDKATEGORIJE"))).collection("BrojPitanja").document(String.valueOf(getIntent().getStringExtra("IDSETA"))).collection("Pitanja").document(String.valueOf(getIntent().getStringExtra("IDDETALJNO")));
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        velinicnaSeta = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getLong("BrojPitanja")).intValue();

                        if(velinicnaSeta < 20){
                            txtBrojPitanja.setText("Pitanje 1/" + velinicnaSeta);
                            if(velinicnaSeta == 0){

                                countDownTimer.cancel();

                                Intent intent = new Intent(VjezbePitanja.this, VjezbeRezultati.class);
                                intent.putExtra("REZULTAT", "Baza podataka je prazna!");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                            }else {
                                for (int i = 1; i <= velinicnaSeta; i++) {

                                    DocumentReference documentReference1 = fStore.collection("Pitanja").document(String.valueOf(getIntent().getStringExtra("IDKATEGORIJE"))).collection(String.valueOf(getIntent().getStringExtra("IDSETA"))).document(String.valueOf(getIntent().getStringExtra("IDDETALJNO"))).collection("Pitanja").document("Pitanje" + i);
                                    documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot snapshot = task.getResult();
                                                assert snapshot != null;
                                                Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje"), snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), Objects.requireNonNull(snapshot.getLong("TocanOdgovor")), snapshot.getString("Slika"));
                                                ListaPitanja.add(pitanje);
                                                setPitanje();
                                            }
                                        }
                                    });
                                }
                            }

                        }else{
                            txtBrojPitanja.setText("Pitanje 1/20");
                            List<Integer> ListaBrojeva = new ArrayList<>();
                            Random random = new Random();
                            int temp;

                            for(int i = 0; i < 20; i++){
                                temp = random.nextInt(velinicnaSeta) +1;
                                if(ListaBrojeva.contains(temp)){
                                    i--;
                                }else{
                                    ListaBrojeva.add(temp);
                                    DocumentReference documentReference1 = fStore.collection("Pitanja").document(String.valueOf(getIntent().getStringExtra("IDKATEGORIJE"))).collection(String.valueOf(getIntent().getStringExtra("IDSETA"))).document(String.valueOf(getIntent().getStringExtra("IDDETALJNO"))).collection("Pitanja").document("Pitanje" + ListaBrojeva.get(i));
                                    documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                DocumentSnapshot snapshot = task.getResult();
                                                assert snapshot != null;
                                                Pitanje pitanje = new Pitanje(snapshot.getString("Pitanje"), snapshot.getString("Odgovor1"), snapshot.getString("Odgovor2"), snapshot.getString("Odgovor3"), Objects.requireNonNull(snapshot.getLong("TocanOdgovor")), snapshot.getString("Slika"));
                                                ListaPitanja.add(pitanje);
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
    }

    private void setPitanje() {
        txtBrojac.setText(String.valueOf(45));
        txtTekstPitanja.setText(ListaPitanja.get(0).getPitanje());

        btnOdgovor1.setText(ListaPitanja.get(0).getOdgovor1());
        btnOdgovor2.setText(ListaPitanja.get(0).getOdgovor2());
        if(ListaPitanja.get(redniBrojPitanja).getOdgovor3().equals("0")){
            btnOdgovor3.setVisibility(View.GONE);
        }else{
            btnOdgovor3.setVisibility(View.VISIBLE);
            btnOdgovor3.setText(ListaPitanja.get(0).getOdgovor3());
        }

        redniBrojPitanja = 0;

        if(!ListaPitanja.get(0).getSlika().equals("")){
            Picasso.get().load(ListaPitanja.get(0).getSlika()).into(imgPitanja, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    ucitavanje.makniUcitavanje();
                    startTimer();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }else{
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)imgPitanja.getLayoutParams();
            params.width = dpToPx(0, imgPitanja.getContext());
            params.height = dpToPx(0, imgPitanja.getContext());
            imgPitanja.setLayoutParams(params);
            imgPitanja.setImageResource(0);
            if(provjeraTimera == 0){
                ucitavanje.makniUcitavanje();
                startTimer();
            }
            provjeraTimera++;
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(46000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished < 45000){
                    txtBrojac.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                promjeniPitanje();
            }
        };
        countDownTimer.start();
    }

    private void provjeraOdogovora(String OznakaOdgovor1, String OznakaOdgovor2, String OznakaOdgovor3){

        btnOdgovor1.setEnabled(false);
        btnOdgovor2.setEnabled(false);
        btnOdgovor3.setEnabled(false);

        int OznaceniOdgovor = 0;

        if(OznakaOdgovor1.equals("1")){
            OznaceniOdgovor = 1;
        }

        if(OznakaOdgovor2.equals("1")){
            if(OznaceniOdgovor == 1){
                OznaceniOdgovor = 12;
            }
            else{
                OznaceniOdgovor = 2;
            }
        }

        if(OznakaOdgovor3.equals("1")){
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

       if(OznaceniOdgovor == ListaPitanja.get(redniBrojPitanja).getTocanOdgovor()) {

           switch (OznaceniOdgovor){
               case 1:
                   btnOdgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 2:
                   btnOdgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 3:
                   btnOdgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 12:
                   btnOdgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   btnOdgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 13:
                   btnOdgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   btnOdgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 23:
                   btnOdgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   btnOdgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 123:
                   btnOdgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   btnOdgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   btnOdgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
           }
           rezultatExtra++;
       }
       else {

           btnOdgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
           btnOdgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
           btnOdgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.RED));

           switch ((int) ListaPitanja.get(redniBrojPitanja).getTocanOdgovor()) {
               case 1:
                   btnOdgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 2:
                   btnOdgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 3:
                   btnOdgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 12:
                   btnOdgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   btnOdgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 13:
                   btnOdgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   btnOdgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 23:
                   btnOdgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   btnOdgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
               case 123:
                   btnOdgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   btnOdgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   btnOdgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                   break;
           }
       }
    }

    private void promjeniPitanje() {
        ucitavanje.pokreniUcitavanje();
        if (redniBrojPitanja < ListaPitanja.size() - 1){

            btnOdgovor1.setEnabled(true);
            btnOdgovor2.setEnabled(true);
            btnOdgovor3.setEnabled(true);

            redniBrojPitanja++;

            playAnim(txtTekstPitanja, 0, 0);
            playAnim(btnOdgovor1, 0, 1);
            playAnim(btnOdgovor2, 0, 2);
            if(ListaPitanja.get(redniBrojPitanja).getOdgovor3().equals("0")){
                btnOdgovor3.setVisibility(View.GONE);
            }else{
                btnOdgovor3.setVisibility(View.VISIBLE);
                playAnim(btnOdgovor3, 0, 3);
            }

            txtBrojPitanja.setText("Pitanje: " + (redniBrojPitanja+1) + "/" + ListaPitanja.size());

            txtBrojac.setText(String.valueOf(45));

            if(!ListaPitanja.get(redniBrojPitanja).getSlika().equals("")){
                    Picasso.get().load(ListaPitanja.get(redniBrojPitanja).getSlika()).into(imgPitanja, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            ucitavanje.makniUcitavanje();
                            startTimer();
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });

            }else{

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)imgPitanja.getLayoutParams();
                params.width = dpToPx(0, imgPitanja.getContext());
                params.height = dpToPx(0, imgPitanja.getContext());
                imgPitanja.setLayoutParams(params);
                imgPitanja.setImageResource(0);
                ucitavanje.makniUcitavanje();
                startTimer();
            }

            int img = R.drawable.neoznaceno_icon;
            btnOdgovor1.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            btnOdgovor2.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            btnOdgovor3.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
        }
        else{

            userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();


            DocumentReference documentReference = fStore.collection("users").document(userID).collection(String.valueOf(getIntent().getStringExtra("IDKATEGORIJE"))).document();
            Map<String, Object> rezultat = new HashMap<>();
            rezultat.put("BrojTocnihOdgovora", rezultatExtra);
            rezultat.put("BrojPitanja", ListaPitanja.size());
            documentReference.set(rezultat);

            Intent intent = new Intent(VjezbePitanja.this, VjezbeRezultati.class);
            intent.putExtra("REZULTAT", String.valueOf(rezultatExtra) + "/" + String.valueOf(ListaPitanja.size()));
            intent.putExtra("KATEGORIJA", getIntent().getStringExtra("KATEGORIJA"));
            intent.putExtra("LOGO", getIntent().getStringExtra("LOGO"));

            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(linearLayout4, getIntent().getStringExtra("LOGO"));

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VjezbePitanja.this, pairs);
            startActivity(intent, options.toBundle());
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

                    if(viewBroj != 0){
                        ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                    }
                    playAnim(view, 1, viewBroj);
                }
            }
        });
    }

    private void oznacavanjeOdgovora(String oznaceniOdgovor, View view){
        if(oznaceniOdgovor.equals("1")){
            if(OznakaOdgovor1.equals("0")){
                OznakaOdgovor1 = "1";
                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffcc00")));
                int img = R.drawable.oznaceno_icon;
                btnOdgovor1.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
            else{
                OznakaOdgovor1 = "0";
                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                int img = R.drawable.neoznaceno_icon;
                btnOdgovor1.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
        }
        if(oznaceniOdgovor.equals("2")){
            if(OznakaOdgovor2.equals("0")){
                OznakaOdgovor2 = "1";
                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffcc00")));
                int img = R.drawable.oznaceno_icon;
                btnOdgovor2.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
            else{
                OznakaOdgovor2 = "0";
                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                int img = R.drawable.neoznaceno_icon;
                btnOdgovor2.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
        }
        if(oznaceniOdgovor.equals("3")){
            if(OznakaOdgovor3.equals("0")){
                OznakaOdgovor3 = "1";
                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffcc00")));
                int img = R.drawable.oznaceno_icon;
                btnOdgovor3.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
            else{
                OznakaOdgovor3 = "0";
                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                int img = R.drawable.neoznaceno_icon;
                btnOdgovor3.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            }
        }
        if(oznaceniOdgovor.equals("0")){
            OznakaOdgovor1 = "0";
            OznakaOdgovor2 = "0";
            OznakaOdgovor3 = "0";
            btnOdgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
            btnOdgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
            btnOdgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
        }
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public void onBackPressed(){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        countDownTimer.cancel();
                        VjezbePitanja.super.onBackPressed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Jeste li sigurni da želite napustiti ovu vježbu?").setPositiveButton("Da", dialogClickListener)
                .setNegativeButton("Ne", dialogClickListener).show();
    }
}
