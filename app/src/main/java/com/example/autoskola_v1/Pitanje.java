package com.example.autoskola_v1;

public class Pitanje {
    private String Pitanje;
    private String Odgovor1;
    private String Odgovor2;
    private String Odgovor3;
    private long TocanOdgovor;
    private String Slika;

    public Pitanje(){}

    public Pitanje(String Pitanje, String Odgovor1, String Odgovor2, String Odgovor3, long TocanOdgovor, String Slika) {
        this.Pitanje = Pitanje;
        this.Odgovor1 = Odgovor1;
        this.Odgovor2 = Odgovor2;
        this.Odgovor3 = Odgovor3;
        this.TocanOdgovor = TocanOdgovor;
        this.Slika = Slika;
    }

    public void setPitanje(String pitanje) {
        Pitanje = pitanje;
    }

    public void setOdgovor1(String odgovor1) {
        Odgovor1 = odgovor1;
    }

    public void setOdgovor2(String odgovor2) {
        Odgovor2 = odgovor2;
    }

    public void setOdgovor3(String odgovor3) {
        Odgovor3 = odgovor3;
    }

    public void setTocanOdgovor(long tocanOdgovor) {
        TocanOdgovor = tocanOdgovor;
    }

    public void setSlika(String slika) {
        Slika = slika;
    }

    public String getPitanje() {
        return Pitanje;
    }

    public String getOdgovor1() {
        return Odgovor1;
    }

    public String getOdgovor2() {
        return Odgovor2;
    }

    public String getOdgovor3() {
        return Odgovor3;
    }

    public long getTocanOdgovor() {
        return TocanOdgovor;
    }

    public String getSlika() {
        return Slika;
    }
}
