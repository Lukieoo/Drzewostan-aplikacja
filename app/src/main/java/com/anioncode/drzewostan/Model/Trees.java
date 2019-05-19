package com.anioncode.drzewostan.Model;

public class Trees {

    int id;
    String srednica;
    int klasa_1;
    int klasa_2;
    int klasa_3;
    int klasa_a;
    int klasa_b;
    int klasa_c;
    int wysokosc;


    public Trees(int id, String srednica, int klasa_1, int klasa_2, int klasa_3, int klasa_a, int klasa_b, int klasa_c, int wysokosc) {
        this.id = id;
        this.srednica = srednica;
        this.klasa_1 = klasa_1;
        this.klasa_2 = klasa_2;
        this.klasa_3 = klasa_3;
        this.klasa_a = klasa_a;
        this.klasa_b = klasa_b;
        this.klasa_c = klasa_c;
        this.wysokosc = wysokosc;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrednica() {
        return srednica;
    }

    public void setSrednica(String srednica) {
        this.srednica = srednica;
    }

    public int getKlasa_1() {
        return klasa_1;
    }

    public void setKlasa_1(int klasa_1) {
        this.klasa_1 = klasa_1;
    }

    public int getKlasa_2() {
        return klasa_2;
    }

    public void setKlasa_2(int klasa_2) {
        this.klasa_2 = klasa_2;
    }

    public int getKlasa_3() {
        return klasa_3;
    }

    public void setKlasa_3(int klasa_3) {
        this.klasa_3 = klasa_3;
    }

    public int getKlasa_a() {
        return klasa_a;
    }

    public void setKlasa_a(int klasa_a) {
        this.klasa_a = klasa_a;
    }

    public int getKlasa_b() {
        return klasa_b;
    }

    public void setKlasa_b(int klasa_b) {
        this.klasa_b = klasa_b;
    }

    public int getKlasa_c() {
        return klasa_c;
    }

    public void setKlasa_c(int klasa_c) {
        this.klasa_c = klasa_c;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public void setWysokosc(int wysokosc) {
        this.wysokosc = wysokosc;
    }
}
