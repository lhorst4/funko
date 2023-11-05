package com.example.listviewexample;

import androidx.annotation.NonNull;

public class Funko {
    String name;
    int number;
    String type;
    String fandom;
    boolean on_off;
    String ultimate;
    double price;

    public Funko(String name, int number, String type, String fandom, boolean on_off, String ultimate, double price){
        this.name = name;
        this.number = number;
        this.type = type;
        this.fandom = fandom;
        this.on_off = on_off;
        this.ultimate = ultimate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFandom() {
        return fandom;
    }

    public void setFandom(String fandom) {
        this.fandom = fandom;
    }

    public boolean isOn_off() {
        return on_off;
    }

    public void setOn_off(boolean on_off) {
        this.on_off = on_off;
    }

    public String getUltimate() {
        return ultimate;
    }

    public void setUltimate(String ultimate) {
        this.ultimate = ultimate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
