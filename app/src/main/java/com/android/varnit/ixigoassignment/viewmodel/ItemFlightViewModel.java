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

    public ObservableField<String> departureCity;
    public ObservableField<String> arrivalCity;
    public ObservableField<String> departureTime;
    public ObservableField<String> arrivalTime;
    public ObservableField<String> flightFare;
    public ObservableField<String> providerName;
    public ObservableField<String> flightDuration;
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

    public void setFlightsData(FlightsData flightsData, HashMap<String, Object> providerMapData) {
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
        notifyChange();
    }
}
