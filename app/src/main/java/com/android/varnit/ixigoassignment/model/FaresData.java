package com.android.varnit.ixigoassignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FaresData implements Serializable {

    @SerializedName("providerId")
    private String providerId;

    @SerializedName("fare")
    private String fare;

    public String getProviderId() {
        return providerId;
    }

    public String getFare() {
        return fare;
    }
}
