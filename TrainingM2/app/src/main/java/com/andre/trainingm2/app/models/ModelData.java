package com.andre.trainingm2.app.models;

import android.graphics.Bitmap;

/**
 * Created by Andree on 1/5/2015.
 */
public class ModelData {
    private String Pict;
    private String Name;
    private String Number;



    public void setName(String name) {
        Name = name;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public void setPict(String pict) {
        Pict = pict;
    }

    public String getName() {
        return Name;
    }

    public String  getPict() {
        return Pict;
    }

    public String getNumber() {
        return Number;
    }
}
