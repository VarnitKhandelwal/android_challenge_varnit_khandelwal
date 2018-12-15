package com.android.varnit.ixigoassignment.viewmodel;

import android.content.Context;
import android.content.IntentFilter;
import android.databinding.ObservableInt;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.view.View;
import com.android.varnit.ixigoassignment.application.FlightsApplication;
import com.android.varnit.ixigoassignment.data.FlightsResponse;
import com.android.varnit.ixigoassignment.data.FlightsFactory;
import com.android.varnit.ixigoassignment.data.FlightsService;
import com.android.varnit.ixigoassignment.model.AppendixData;
import com.android.varnit.ixigoassignment.model.FlightsData;
import com.android.varnit.ixigoassignment.receiver.NetworkChangeReceiver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MainViewModel extends Observable implements NetworkChangeReceiver.NetworkStateReceiverListener {

    public ObservableInt progressBarVisibility;
    public ObservableInt recyclerViewVisibility;
    public ObservableInt sortVisibility;
    public ObservableInt noInternetVisibility;
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<FlightsData> flightsDataList;
    private AppendixData appendixData;
    private Context context;
    private NetworkChangeReceiver networkChangeReceiver;

    public MainViewModel(@NonNull Context context) {
        this.context = context;
        this.flightsDataList = new ArrayList<>();
        progressBarVisibility = new ObservableInt(View.GONE);
        recyclerViewVisibility = new ObservableInt(View.GONE);
        noInternetVisibility = new ObservableInt(View.GONE);
        sortVisibility = new ObservableInt(View.GONE);

        // Add Network Listener
        networkChangeReceiver = new NetworkChangeReceiver();
        networkChangeReceiver.addListener(this);
        context.registerReceiver(networkChangeReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        initializeViews();
        fetchFlightsList();
    }

    public void initializeViews() {
        recyclerViewVisibility.set(View.GONE);
        sortVisibility.set(View.GONE);
    }

    public void fetchFlightsList() {
        if (isNetworkAvailable()) {
            progressBarVisibility.set(View.VISIBLE);
            noInternetVisibility.set(View.GONE);

            FlightsApplication flightsApplication = FlightsApplication.create(context);
            FlightsService flightsService = flightsApplication.getFlightsService();

            Disposable disposable = flightsService.fetchFlightsData(FlightsFactory.RANDOM_FLIGHT_URL)
                    .subscribeOn(flightsApplication.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<FlightsResponse>() {
                        @Override
                        public void accept(FlightsResponse flightsResponse) throws Exception {
                            appendixData = flightsResponse.getAppendix();
                            flightsDataList.addAll(flightsResponse.getFlights());
                            changeFlightsDataSet();
                            progressBarVisibility.set(View.GONE);
                            noInternetVisibility.set(View.GONE);
                            recyclerViewVisibility.set(View.VISIBLE);
                            sortVisibility.set(View.VISIBLE);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            progressBarVisibility.set(View.GONE);
                            noInternetVisibility.set(View.VISIBLE);
                            if (flightsDataList == null || flightsDataList.size() == 0)
                                recyclerViewVisibility.set(View.GONE);
                            sortVisibility.set(View.GONE);
                        }
                    });

            compositeDisposable.add(disposable);
        } else {
            progressBarVisibility.set(View.GONE);
            recyclerViewVisibility.set(View.GONE);
            sortVisibility.set(View.GONE);
            if (flightsDataList == null || flightsDataList.size() == 0)
                noInternetVisibility.set(View.VISIBLE);
        }
    }

    public void changeFlightsDataSet() {
        setChanged();
        notifyObservers();
    }

    public List<FlightsData> getFlightsDataList() {
        return flightsDataList;
    }

    public AppendixData getAppendixData() {
        return appendixData;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;

        // Remove Network Listener
        networkChangeReceiver.removeListener(this);
        context.unregisterReceiver(networkChangeReceiver);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void networkAvailable() {
        if (flightsDataList == null || flightsDataList.size() == 0)
            fetchFlightsList();
    }

    @Override
    public void networkUnavailable() {
        progressBarVisibility.set(View.GONE);
        if (flightsDataList == null || flightsDataList.size() == 0) {
            recyclerViewVisibility.set(View.GONE);
            sortVisibility.set(View.GONE);
            noInternetVisibility.set(View.VISIBLE);
        }
    }
}
