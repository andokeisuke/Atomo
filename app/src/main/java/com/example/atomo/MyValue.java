package com.example.atomo;

import android.app.Application;

public class MyValue extends Application {

    private int Status = 0;
    private int Lifeday = 117;
    private float CO2 = 0;
    private boolean[] Item = new boolean[4];
    private boolean[] Interia = new boolean[4];


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getLifeday() {
        return Lifeday;
    }

    public void setLifeday(int lifeday) {
        Lifeday = lifeday;
    }

    public float getCO2() {
        return CO2;
    }

    public void setCO2(float co2) {
        CO2 = co2;
    }

    public boolean[] getItem() {
        return Item;
    }

    public void setItem(boolean[] item) {
        for(int i =0;i< item.length;i++){

            Item[i] = item[i];

        }
    }

    public boolean[] getInteria() {
        return Interia;
    }

    public void setInteria(boolean[] interia) {
        for(int i =0;i< interia.length;i++){

            Interia[i] = interia[i];

        }
    }
}
