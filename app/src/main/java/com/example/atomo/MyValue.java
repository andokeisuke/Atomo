package com.example.atomo;

import android.app.Application;
import android.view.ViewDebug;

import java.util.ArrayList;
import java.util.List;

public class MyValue extends Application {

    private int Status = 0;
    private int Lifeday = 117;
    private float CO2 = 0;
    private boolean[] Item = new boolean[4];
    private boolean[] Interia = new boolean[4];
    private int [] daily_status = {0,1,2,3,4};
    private int [] monthly_status = {0,1,2,3,4};
    private int [] weekly_status = {0,1,2,3,4};
    private int [] user_status = {2,2,2,2,2};
    private List<List<float[]>> madori_list = new ArrayList<>();
    private float[] in_status = {20,60};
    private float[] out_status = {0,20,60};
    private List<String[]> diagnose_list = new ArrayList<>();


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

    public int[] getdaily() {
        return daily_status;
    }

    public void setdaily(int[] Daily) {
        for(int i =0;i< Daily.length;i++){

            daily_status[i] = Daily[i];

        }
    }

    public int[] getweekly() {
        return weekly_status;
    }

    public void setweekly(int[] Weekly) {
        for(int i =0;i< Weekly.length;i++){

            weekly_status[i] = Weekly[i];

        }
    }

    public int[] getmonthly() {
        return monthly_status;
    }

    public void setmonthly(int[] Monthly) {
        for(int i =0;i< Monthly.length;i++){

            monthly_status[i] = Monthly[i];

        }
    }

    public int[] getUser_status() {
        return user_status;
    }

    public void setUser_status(int[] User_status) {
        for(int i =0;i< User_status.length;i++){

            user_status[i] = User_status[i];

        }
    }

    public List<List<float[]>> getMadori_list(){ return madori_list;}

    public void addMadori(List<float[]> Madori){
        madori_list.add(Madori);
    }

    public void setMadori(List<float[]> Madori,int index){
        for(int i = 0;i<madori_list.get(index).size();i++){

            madori_list.get(index).get(i)[2] = Madori.get(i)[2];

        }
    }

    public void setIn_status(float tempture,float humidity){
        in_status[0] = tempture;
        in_status[1] = humidity;
    }

    public float[] getIn_status(){

        return in_status;
    }

    public void setOut_status(int climate,float tempture,float humidity){
        out_status[0] = climate;
        out_status[1] = tempture;
        out_status[2] = humidity;
    }

    public float[] getOut_status(){

        return out_status;
    }

    public List<String[]>getDiagnose_list(){ return diagnose_list;}

    public void addDisagnose(String[] Diagnose){
        diagnose_list.add(Diagnose);
    }
}
