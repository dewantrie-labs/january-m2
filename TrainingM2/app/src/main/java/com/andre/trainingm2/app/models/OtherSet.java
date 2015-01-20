package com.andre.trainingm2.app.models;

/**
 * Created by Andree on 1/8/2015.
 */
public class OtherSet {
    private String imageSet;
    private Boolean refreshData;


    public void setImageSet(String imageSet) {
        this.imageSet = imageSet;
    }

    public String getImageSet() {
        return imageSet;
    }

    public void setRefreshData(Boolean refreshData) {
        this.refreshData = refreshData;
    }

    public Boolean getRefreshData() {
        return refreshData;
    }
}
