package com.example.autoskola_v1;

public class PitanjaIOdgovori {
    private String Pitanje;
    private String Odgovor1;
    private String Odgovor2;
    private String Odgovor3;
    private long TocanOdgovor;
    private String Slika;
    private String OznakaOdgovor1;
    private String OznakaOdgovor2;
    private String OznakaOdgovor3;
    private int BrojBodova;
    private int RedniBroj;

    public PitanjaIOdgovori(){}

    public PitanjaIOdgovori(String pitanje, String odgovor1, String odgovor2, String odgovor3, long tocanOdgovor, String slika, String oznakaOdgovor1, String oznakaOdgovor2, String oznakaOdgovor3, int brojBodova, int redniBroj) {
        Pitanje = pitanje;
        Odgovor1 = odgovor1;
        Odgovor2 = odgovor2;
        Odgovor3 = odgovor3;
        TocanOdgovor = tocanOdgovor;
        Slika = slika;
        OznakaOdgovor1 = oznakaOdgovor1;
        OznakaOdgovor2 = oznakaOdgovor2;
        OznakaOdgovor3 = oznakaOdgovor3;
        BrojBodova = brojBodova;
        RedniBroj = redniBroj;
    }

    public int getRedniBroj() {
        return RedniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        RedniBroj = redniBroj;
    }

    public String getPitanje() {
        return Pitanje;
    }

    public void setPitanje(String pitanje) {
        Pitanje = pitanje;
    }

    public String getOdgovor1() {
        return Odgovor1;
    }

    public void setOdgovor1(String odgovor1) {
        Odgovor1 = odgovor1;
    }

    public String getOdgovor2() {
        return Odgovor2;
    }

    public void setOdgovor2(String odgovor2) {
        Odgovor2 = odgovor2;
    }

    public String getOdgovor3() {
        return Odgovor3;
    }

    public void setOdgovor3(String odgovor3) {
        Odgovor3 = odgovor3;
    }

    public long getTocanOdgovor() {
        return TocanOdgovor;
    }

    public void setTocanOdgovor(long tocanOdgovor) {
        TocanOdgovor = tocanOdgovor;
    }

    public String getSlika() {
        return Slika;
    }

    public void setSlika(String slika) {
        Slika = slika;
    }

    public String getOznakaOdgovor1() {
        return OznakaOdgovor1;
    }

    public void setOznakaOdgovor1(String oznakaOdgovor1) {
        OznakaOdgovor1 = oznakaOdgovor1;
    }

    public String getOznakaOdgovor2() {
        return OznakaOdgovor2;
    }

    public void setOznakaOdgovor2(String oznakaOdgovor2) {
        OznakaOdgovor2 = oznakaOdgovor2;
    }

    public String getOznakaOdgovor3() {
        return OznakaOdgovor3;
    }

    public void setOznakaOdgovor3(String oznakaOdgovor3) {
        OznakaOdgovor3 = oznakaOdgovor3;
    }

    public int getBrojBodova() {
        return BrojBodova;
    }

    public void setBrojBodova(int brojBodova) {
        BrojBodova = brojBodova;
    }
}
