package com.android.varnit.ixigoassignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProvidersData implements Serializable {

    @SerializedName("1")
    private String makeMyTrip;

    @SerializedName("2")
    private String cleartrip;

    @SerializedName("3")
    private String yatra;

    @SerializedName("4")
    private String musafir;

    public String getMakeMyTrip() {
        return makeMyTrip;
    }

    public String getCleartrip() {
        return cleartrip;
    }

    public String getYatra() {
        return yatra;
    }

    public String getMusafir() {
        return musafir;
    }
}
