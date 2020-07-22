package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ListaIspita extends AppCompatActivity {

    private RecyclerView FireStoreListaIspita;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private FirestoreRecyclerAdapter adapter;
    Query query;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lista_ispita);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        FireStoreListaIspita = findViewById(R.id.FireStoreListaIspita);

        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        query = fStore.collection("users").document(userID).collection("RjeseniIspiti").orderBy("RedniBroj");

        FirestoreRecyclerOptions<Ispiti> options = new FirestoreRecyclerOptions.Builder<Ispiti>()
                .setQuery(query, Ispiti.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Ispiti, IspitiViewHolder>(options) {
            @NonNull
            @Override
            public IspitiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_svih_ispita, parent, false);
                return new IspitiViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull IspitiViewHolder holder, int position, @NonNull Ispiti model) {
                holder.Naziv.setText(model.getNaziv());
                holder.Naziv.setTransitionName(model.getBrojIspita());
                holder.NazivDatoteke = model.getBrojIspita();
            }
        };

        FireStoreListaIspita.setLayoutManager(new LinearLayoutManager(this));
        FireStoreListaIspita.setAdapter(adapter);
    }

    private class IspitiViewHolder extends RecyclerView.ViewHolder{

        private Button Naziv;
        private String NazivDatoteke;

        public IspitiViewHolder(@NonNull View itemView) {
            super(itemView);
            Naziv = itemView.findViewById(R.id.Naziv);

            Naziv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(ListaIspita.this, IspitRezultati.class);
                   intent.putExtra("ISPIT", NazivDatoteke);

                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(Naziv, NazivDatoteke);

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ListaIspita.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            });
        }
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