package com.android.varnit.ixigoassignment.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import com.android.varnit.ixigoassignment.model.FlightsData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ItemFlightViewModel extends BaseObservable {

    public final ObservableField<String> departureCity;
    public final ObservableField<String> arrivalCity;
    public final ObservableField<String> departureTime;
    public final ObservableField<String> arrivalTime;
    public final ObservableField<String> flightFare;
    public final ObservableField<String> providerName;
    public final ObservableField<String> flightDuration;
    private Context context;
    private FlightsData flightsData;

    public ItemFlightViewModel(FlightsData flightsData, HashMap<String, Object> providerMapData) {
        this.flightsData = flightsData;
        departureCity = new ObservableField<>(flightsData.getOriginCode());
        arrivalCity = new ObservableField<>(flightsData.getDestinationCode());
        departureTime = new ObservableField<>(millsToDateFormat(flightsData.getDepartureTime()));
        arrivalTime = new ObservableField<>(millsToDateFormat(flightsData.getArrivalTime()));
        flightFare = new ObservableField<>("₹ " + flightsData.getFares().get(0).getFare());
        if (providerMapData != null)
            providerName = new ObservableField<>(String.valueOf(providerMapData.get(flightsData.getFares().get(0).getProviderId())));
        else
            providerName = new ObservableField<>();
        flightDuration = new ObservableField<>(millsToDateFormat(flightsData.getArrivalTime() - flightsData.getDepartureTime()) + " (Duration)");
    }

    public String millsToDateFormat(long mills) {
        Date date = new Date(mills);
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateFormatted = formatter.format(date);
        return dateFormatted; //note that it will give you the time in GMT+0
    }

    public void setFlightsData(FlightsData flightsData) {
        this.flightsData = flightsData;
        notifyChange();
    }
}
