package com.andre.trainingm2.app.models;

import android.graphics.Bitmap;

/**
 * Created by Andree on 1/5/2015.
 */
public class ModelData {
    private Bitmap Pict;
    private String Name;
    private String Number;

    public ModelData(Bitmap bitmap, String name, String number) {
        Pict =bitmap;
        Name=name;
        Number=number;
    }

    public String getName() {
        return Name;
    }

    public Bitmap getPict() {
        return Pict;
    }

    public String getNumber() {
        return Number;
    }
}
