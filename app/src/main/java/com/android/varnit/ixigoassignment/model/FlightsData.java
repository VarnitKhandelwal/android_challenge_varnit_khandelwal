package com.android.varnit.ixigoassignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FlightsData implements Serializable {

    @SerializedName("originCode")
    private String originCode;

    @SerializedName("destinationCode")
    private String destinationCode;

    @SerializedName("departureTime")
    private Long departureTime;

    @SerializedName("arrivalTime")
    private Long arrivalTime;

    @SerializedName("airlineCode")
    private String airlineCode;

    @SerializedName("class")
    private String flightClass;

    @SerializedName("fares")
    private List<FaresData> fares;

    private AppendixData appendixData;

    public String getOriginCode() {
        return originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public Long getDepartureTime() {
        return departureTime;
    }

    public Long getArrivalTime() {
        return arrivalTime;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public List<FaresData> getFares() {
        return fares;
    }

    public AppendixData getAppendixData() {
        return appendixData;
    }

    public void setAppendixData(AppendixData appendixData) {
        this.appendixData = appendixData;
    }
}
