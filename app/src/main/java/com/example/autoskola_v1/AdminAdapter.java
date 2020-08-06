package com.example.autoskola_v1;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.viewHolder> {

    private List<Pitanje> mLPitanja;
    private OnPitanjeClickListener mOnPitanjeListener;

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView Pitanje, Odgovor1, Odgovor2, Odgovor3;
        public ImageView Slika;
        public long TocanOdgovor;
        public String name;
        OnPitanjeClickListener onPitanjeClickListener;

        public viewHolder(@NonNull View itemView, OnPitanjeClickListener onPitanjeClickListener) {
            super(itemView);

            Pitanje = itemView.findViewById(R.id.Pitanje);
            Odgovor1 = itemView.findViewById(R.id.Odgovor1);
            Odgovor2 = itemView.findViewById(R.id.Odgovor2);
            Odgovor3 = itemView.findViewById(R.id.Odgovor3);
            Slika = itemView.findViewById(R.id.Slika);
            this.onPitanjeClickListener = onPitanjeClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPitanjeClickListener.onPitanjeClickListener(getAdapterPosition());
        }
    }

    public interface OnPitanjeClickListener{
        void onPitanjeClickListener(int position);
    }

    public AdminAdapter(List<Pitanje> LPitanja, OnPitanjeClickListener onPitanjeClickListener){
        this.mLPitanja = LPitanja;
        this.mOnPitanjeListener = onPitanjeClickListener;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pitanje, parent, false);
        viewHolder vHolder = new viewHolder(v, mOnPitanjeListener);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Pitanje trenutnoPitanje = mLPitanja.get(position);

        holder.Pitanje.setText(trenutnoPitanje.getName() + ". " + trenutnoPitanje.getPitanje());
        holder.Odgovor1.setText(trenutnoPitanje.getOdgovor1());
        holder.Odgovor2.setText(trenutnoPitanje.getOdgovor2());
        holder.Odgovor3.setText(trenutnoPitanje.getOdgovor3());
        holder.TocanOdgovor = trenutnoPitanje.getTocanOdgovor();

        switch ((int) trenutnoPitanje.getTocanOdgovor()){
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

        if(!trenutnoPitanje.getSlika().equals("")){
            holder.Slika.setVisibility(View.VISIBLE);
            Picasso.get().load(trenutnoPitanje.getSlika()).into(holder.Slika);
        }else{
            holder.Slika.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mLPitanja.size();
    }
}
