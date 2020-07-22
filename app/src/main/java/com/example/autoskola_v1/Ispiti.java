package com.example.autoskola_v1;

public class Ispiti {
    private String Naziv;
    private String BrojIspita;

    Ispiti(){}

    public Ispiti(String naziv, String brojIspita) {
        Naziv = naziv;
        BrojIspita = brojIspita;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public String getBrojIspita() {
        return BrojIspita;
    }

    public void setBrojIspita(String brojIspita) {
        BrojIspita = brojIspita;
    }
}
