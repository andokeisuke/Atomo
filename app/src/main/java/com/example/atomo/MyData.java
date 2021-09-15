package com.example.atomo;


public class MyData {
    private String textData = "";
    private Boolean checkdata = false;
    private Boolean title = false;

    public void setTextdata(String str){
        textData = str;
    }
    public String getTextData(){
        return textData;
    }
    public void setChecked(boolean bool){
        checkdata = bool;
    }
    public boolean isChecked(){
        return checkdata;
    }

    public void setTitle(boolean bool){
        title = bool;
    }
    public boolean getTitle(){
        return title;
    }


}