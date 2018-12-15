package com.android.varnit.ixigoassignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AirportData implements Serializable {

    @SerializedName("DEL")
    private String newDelhi;

    @SerializedName("BOM")
    private String mumbai;

    public String getNewDelhi() {
        return newDelhi;
    }

    public String getMumbai() {
        return mumbai;
    }
}
