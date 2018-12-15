package com.android.varnit.ixigoassignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppendixData implements Serializable {

    @SerializedName("airlines")
    private AirlinesData airlines;

    @SerializedName("airports")
    private AirportData airports;

    @SerializedName("providers")
    private ProvidersData providers;

    public AirlinesData getAirlines() {
        return airlines;
    }

    public AirportData getAirports() {
        return airports;
    }

    public ProvidersData getProviders() {
        return providers;
    }
}
