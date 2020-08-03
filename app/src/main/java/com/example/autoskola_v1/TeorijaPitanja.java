package com.example.autoskola_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class TeorijaPitanja extends AppCompatActivity {

    private RecyclerView FireStoreListaPitanja;
    private FirebaseFirestore fStore;

    private FirestoreRecyclerAdapter adapter;

    ImageView imgInfoTeorija;
    TextView txtInfoTeorija;
    LinearLayout linearLayout11;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_teorija_pitanja);

        imgInfoTeorija = findViewById(R.id.imgInfoTeorija);
        txtInfoTeorija = findViewById(R.id.txtInfoTeorija);
        linearLayout11 = findViewById(R.id.linearLayout11);

        linearLayout11.setTransitionName(getIntent().getStringExtra("LOGO"));

        txtInfoTeorija.setText(getIntent().getStringExtra("KATEGORIJA"));

        if (Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Opća pitanja")) {
            imgInfoTeorija.setImageResource(R.drawable.opca_pitanja_icon);
        }
        if (Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Prometni znakovi")) {
            imgInfoTeorija.setImageResource(R.drawable.prometni_znakovi_icon);
        }
        if (Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Propuštanje vozila i prednost prolaska")) {
            imgInfoTeorija.setImageResource(R.drawable.propustanje_vozila_itd_icon);
        }
        if (Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "Opasne situacije")) {
            imgInfoTeorija.setImageResource(R.drawable.opasne_situacije_icon);
        }
        if (Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "1 - 22")) {
            imgInfoTeorija.setImageResource(R.drawable.first_aid_kit_icon);
        }
        if (Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "23 - 44")) {
            imgInfoTeorija.setImageResource(R.drawable.bandage_icon);
        }
        if (Objects.equals(getIntent().getStringExtra("KATEGORIJA"), "45 - 66")) {
            imgInfoTeorija.setImageResource(R.drawable.revive_icon);
        }

        FireStoreListaPitanja = findViewById(R.id.FireStoreListaPitanja);
        fStore = FirebaseFirestore.getInstance();

        if(String.valueOf(getIntent().getStringExtra("IDDETALJNO")).equals("0")){
            query = fStore.collection("Pitanja").document(String.valueOf(getIntent().getStringExtra("IDKATEGORIJE"))).collection(String.valueOf(getIntent().getStringExtra("IDSETA")));
        }else{
            query = fStore.collection("Pitanja").document(String.valueOf(getIntent().getStringExtra("IDKATEGORIJE"))).collection(String.valueOf(getIntent().getStringExtra("IDSETA"))).document(String.valueOf(getIntent().getStringExtra("IDDETALJNO"))).collection("Pitanja");
        }


        FirestoreRecyclerOptions<Pitanje> options = new FirestoreRecyclerOptions.Builder<Pitanje>()
                .setQuery(query, Pitanje.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Pitanje, PitanjeViewHolder>(options) {
            @NonNull
            @Override
            public PitanjeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pitanje, parent, false);
                return new PitanjeViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PitanjeViewHolder holder, int position, @NonNull Pitanje model) {
                holder.Pitanje.setText((position + 1) + ". " + model.getPitanje());
                holder.Odgovor1.setText(model.getOdgovor1());
                holder.Odgovor2.setText(model.getOdgovor2());
                holder.Odgovor3.setText(model.getOdgovor3());

                if(model.getOdgovor3().equals("0")){
                    holder.Odgovor3.setVisibility(View.GONE);
                }else{
                    holder.Odgovor3.setVisibility(View.VISIBLE);
                }

                switch ((int) model.getTocanOdgovor()){
                    case 1:
                        holder.Odgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        holder.Odgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                        holder.Odgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                        break;
                    case 2:
                        holder.Odgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                        holder.Odgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        holder.Odgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                        break;
                    case 3:
                        holder.Odgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                        holder.Odgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                        holder.Odgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                    case 12:
                        holder.Odgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        holder.Odgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        holder.Odgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                        break;
                    case 13:
                        holder.Odgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        holder.Odgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                        holder.Odgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                    case 23:
                        holder.Odgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#132131")));
                        holder.Odgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        holder.Odgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                    case 123:
                        holder.Odgovor1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        holder.Odgovor2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        holder.Odgovor3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        break;
                }

                if(!model.getSlika().equals("")){
                    holder.Slika.setVisibility(View.VISIBLE);
                    Picasso.get().load(model.getSlika()).into(holder.Slika);
                }else{
                    holder.Slika.setVisibility(View.GONE);
                }
            }
        };

        FireStoreListaPitanja.setLayoutManager(new LinearLayoutManager(this));
        FireStoreListaPitanja.setAdapter(adapter);
    }

   private class PitanjeViewHolder extends RecyclerView.ViewHolder{

        private TextView Pitanje, Odgovor1, Odgovor2, Odgovor3;
        private ImageView Slika;

        public PitanjeViewHolder(@NonNull View itemView) {
            super(itemView);

            Pitanje = itemView.findViewById(R.id.Pitanje);
            Odgovor1 = itemView.findViewById(R.id.Odgovor1);
            Odgovor2 = itemView.findViewById(R.id.Odgovor2);
            Odgovor3 = itemView.findViewById(R.id.Odgovor3);
            Slika = itemView.findViewById(R.id.Slika);
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