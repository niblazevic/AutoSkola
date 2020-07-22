package com.example.autoskola_v1;

public class OznaceniOdgovoriIspit {
    private String OznakaOdgovor1;
    private String OznakaOdgovor2;
    private String OznakaOdgovor3;
    private int BrojBodova;

    public OznaceniOdgovoriIspit(String oznakaOdgovor1, String oznakaOdgovor2, String oznakaOdgovor3, int brojBodova) {
        OznakaOdgovor1 = oznakaOdgovor1;
        OznakaOdgovor2 = oznakaOdgovor2;
        OznakaOdgovor3 = oznakaOdgovor3;
        BrojBodova = brojBodova;
    }

    public int getBrojBodova() {
        return BrojBodova;
    }

    public void setBrojBodova(int brojBodova) {
        BrojBodova = brojBodova;
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
}
