package com.android.varnit.ixigoassignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AirlinesData implements Serializable {

    @SerializedName("SG")
    private String spicejet;

    @SerializedName("AI")
    private String airIndia;

    @SerializedName("G8")
    private String goAir;

    @SerializedName("9W")
    private String jetAirways;

    @SerializedName("6E")
    private String indigo;

    public String getSpicejet() {
        return spicejet;
    }

    public String getAirIndia() {
        return airIndia;
    }

    public String getGoAir() {
        return goAir;
    }

    public String getJetAirways() {
        return jetAirways;
    }

    public String getIndigo() {
        return indigo;
    }
}
