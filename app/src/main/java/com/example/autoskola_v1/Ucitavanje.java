package com.example.autoskola_v1;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Ucitavanje {
    private Activity activity;
    private AlertDialog alertDialog;

    public Ucitavanje(Activity activity) {
        this.activity = activity;
    }

    void pokreniUcitavanje(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.ucitavanje, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    void makniUcitavanje(){
        alertDialog.dismiss();
    }
}
