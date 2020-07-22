package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class IspitRezultati extends AppCompatActivity {

    Animation bottomAnim, topAnim;

    LinearLayout infoLayout, linearLayout13;
    Button btnSmanji;
    ImageView imgSlikaIspit;
    TextView txtRezultatIspit, txtBrojBodovaIspitRezultati, txtPostotakIspitRezultati, txtBrojBodovaDovoljnoIspitRezultati, txtTocnostRaskrizjaIspitRezultati, txtProlaznostIspitRezultati;

    private RecyclerView FireStoreListaPitanjaIOdgovora;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private FirestoreRecyclerAdapter adapter;
    Query query;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ispit_rezultati);

        imgSlikaIspit = findViewById(R.id.imgSlikaIspit);
        txtRezultatIspit = findViewById(R.id.txtRezultatIspit);
        txtBrojBodovaIspitRezultati = findViewById(R.id.txtBrojBodovaIspitRezultati);
        txtPostotakIspitRezultati = findViewById(R.id.txtPostotakIspitRezultati);
        txtBrojBodovaDovoljnoIspitRezultati = findViewById(R.id.txtBrojBodovaDovoljnoIspitRezultati);
        txtTocnostRaskrizjaIspitRezultati = findViewById(R.id.txtTocnostRaskrizjaIspitRezultati);
        txtProlaznostIspitRezultati = findViewById(R.id.txtProlaznostIspitRezultati);
        FireStoreListaPitanjaIOdgovora = findViewById(R.id.FireStoreListaPitanjaIOdgovora);
        infoLayout = findViewById(R.id.infoLayout);
        linearLayout13 = findViewById(R.id.linearLayout13);
        btnSmanji = findViewById(R.id.btnSmanji);

        linearLayout13.setTransitionName(getIntent().getStringExtra("ISPIT"));

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        btnSmanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(infoLayout.getVisibility() == View.VISIBLE){
                    btnSmanji.setText("Povećaj detalje o ispitu");
                    infoLayout.setVisibility(View.GONE);
                }else{
                    btnSmanji.setText("Smanji detalje o ispitu");
                    infoLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        ucitavanjeDetalja();

        query = fStore.collection("users").document(userID).collection("RjeseniIspiti").document(Objects.requireNonNull(getIntent().getStringExtra("ISPIT"))).collection("PitanjaIOdgovori").orderBy("RedniBroj");

        FirestoreRecyclerOptions<PitanjaIOdgovori> options = new FirestoreRecyclerOptions.Builder<PitanjaIOdgovori>()
                .setQuery(query, PitanjaIOdgovori.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<PitanjaIOdgovori, PitanjaIodgovoriViewHolder>(options) {
            @NonNull
            @Override
            public PitanjaIodgovoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_ispit_pitanja_i_odgovori, parent, false);
                return new PitanjaIodgovoriViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PitanjaIodgovoriViewHolder holder, int position, @NonNull PitanjaIOdgovori model) {
                holder.PitanjeIspit.setText((position + 1) + ". " + model.getPitanje());
                holder.Odgovor1Ispit.setText(model.getOdgovor1());
                holder.Odgovor2Ispit.setText(model.getOdgovor2());
                holder.Odgovor3Ispit.setText(model.getOdgovor3());

                if(model.getOdgovor3().equals("0")){
                    holder.Odgovor3Ispit.setVisibility(View.GONE);
                }else{
                    holder.Odgovor3Ispit.setVisibility(View.VISIBLE);
                }

                int OznaceniOdgovor = 0;

                if(model.getOznakaOdgovor1().equals("1")){
                    int img = R.drawable.oznaceno_icon;
                    holder.Odgovor1Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
                    OznaceniOdgovor = 1;
                }else{
                    int img = R.drawable.neoznaceno_icon;
                    holder.Odgovor1Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
                }

                if(model.getOznakaOdgovor2().equals("1")){
                    int img = R.drawable.oznaceno_icon;
                    holder.Odgovor2Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
                    if(OznaceniOdgovor == 1){
                        OznaceniOdgovor = 12;
                    }
                    else{
                        OznaceniOdgovor = 2;
                    }
                }else{
                    int img = R.drawable.neoznaceno_icon;
                    holder.Odgovor2Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
                }

                if(model.getOznakaOdgovor3().equals("1")){
                    int img = R.drawable.oznaceno_icon;
                    holder.Odgovor3Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
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
                }else{
                    int img = R.drawable.neoznaceno_icon;
                    holder.Odgovor3Ispit.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
                }

                if(OznaceniOdgovor == model.getTocanOdgovor()) {

                    holder.ConstraintLista.setBackgroundResource(R.drawable.bordercardview);

                    switch (OznaceniOdgovor){
                        case 1:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                            break;
                        case 2:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                            break;
                        case 3:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            break;
                        case 12:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                            break;
                        case 13:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            break;
                        case 23:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            break;
                        case 123:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            break;
                    }
                }
                else {

                    holder.ConstraintLista.setBackgroundResource(R.drawable.bordercardview2);


                    switch ((int) model.getTocanOdgovor()) {
                        case 1:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            break;
                        case 2:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            break;
                        case 3:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            break;
                        case 12:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            break;
                        case 13:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            break;
                        case 23:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            break;
                        case 123:
                            holder.Odgovor1Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor2Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            holder.Odgovor3Ispit.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            break;
                    }
                }


                if(model.getSlika()!=""){
                    Picasso.get().load(model.getSlika()).into(holder.SlikaIspit);
                    holder.SlikaIspit.setVisibility(View.VISIBLE);
                }else{
                    holder.SlikaIspit.setVisibility(View.GONE);
                }
            }
        };

        FireStoreListaPitanjaIOdgovora.setLayoutManager(new LinearLayoutManager(this));
        FireStoreListaPitanjaIOdgovora.setAdapter(adapter);

    }

    private class PitanjaIodgovoriViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout ConstraintLista;
        private TextView PitanjeIspit, Odgovor1Ispit, Odgovor2Ispit, Odgovor3Ispit;
        private ImageView SlikaIspit;

        public PitanjaIodgovoriViewHolder(@NonNull View itemView) {
            super(itemView);

            PitanjeIspit = itemView.findViewById(R.id.PitanjeIspit);
            Odgovor1Ispit = itemView.findViewById(R.id.Odgovor1Ispit);
            Odgovor2Ispit = itemView.findViewById(R.id.Odgovor2Ispit);
            Odgovor3Ispit = itemView.findViewById(R.id.Odgovor3Ispit);
            SlikaIspit = itemView.findViewById(R.id.SlikaIspit);
            ConstraintLista = itemView.findViewById(R.id.ConstraintLista);
        }
    }

    private void ucitavanjeDetalja() {
        DocumentReference documentReference = fStore.collection("users").document(userID).collection("RjeseniIspiti").document(Objects.requireNonNull(getIntent().getStringExtra("ISPIT")));
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    assert snapshot != null;
                    txtBrojBodovaIspitRezultati.setText(snapshot.getString("BrojBodova"));
                    txtPostotakIspitRezultati.setText(snapshot.getString("Postotak"));
                    txtBrojBodovaDovoljnoIspitRezultati.setText(snapshot.getString("BrojBodovaDovoljno"));
                    txtTocnostRaskrizjaIspitRezultati.setText(snapshot.getString("Raskrizja"));
                    txtProlaznostIspitRezultati.setText(snapshot.getString("Prolaznost"));
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}