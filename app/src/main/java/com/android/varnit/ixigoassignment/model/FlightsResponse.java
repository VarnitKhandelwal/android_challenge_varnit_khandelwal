package com.android.varnit.ixigoassignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FlightsResponse implements Serializable {

    @SerializedName("appendix")
    private AppendixData appendix;

    @SerializedName("flights")
    private List<FlightsData> flights;

    public AppendixData getAppendix() {
        return appendix;
    }

    public List<FlightsData> getFlights() {
        return flights;
    }
}
