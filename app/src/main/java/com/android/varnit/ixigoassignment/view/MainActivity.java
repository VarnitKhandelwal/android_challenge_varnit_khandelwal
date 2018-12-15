package com.android.varnit.ixigoassignment.view;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import com.android.varnit.ixigoassignment.R;
import com.android.varnit.ixigoassignment.adapter.ItemListAdapter;
import com.android.varnit.ixigoassignment.databinding.ActivityMainBinding;
import com.android.varnit.ixigoassignment.model.FlightsData;
import com.android.varnit.ixigoassignment.viewmodel.MainViewModel;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class MainActivity extends AppCompatActivity implements Observer {

    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;
    int currentSortOrderBy = R.id.pLH;
    private ItemListAdapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setupFlightsList(activityMainBinding.flightsRecyclerView);
        setupObserver(mainViewModel);
    }

    private void initDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        activityMainBinding.setMainViewModel(mainViewModel);
        activityMainBinding.sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(MainActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_sort_options);

                RadioGroup options = (RadioGroup) d.findViewById(R.id.options);
                options.check(currentSortOrderBy);
                options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        switch (checkedId) {
                            case R.id.pHL:
                                currentSortOrderBy = R.id.pHL;
                                Collections.sort(mainViewModel.getFlightsDataList(), new Comparator<FlightsData>() {
                                    @Override
                                    public int compare(FlightsData o1, FlightsData o2) {
                                        Integer p1 = Integer.parseInt(o1.getFares().get(0).getFare());
                                        Integer p2 = Integer.parseInt(o2.getFares().get(0).getFare());
                                        return -1 * p1.compareTo(p2);
                                    }
                                });
                                itemListAdapter.notifyDataSetChanged();
                                break;
                            case R.id.pLH:
                                currentSortOrderBy = R.id.pLH;
                                Collections.sort(mainViewModel.getFlightsDataList(), new Comparator<FlightsData>() {
                                    @Override
                                    public int compare(FlightsData o1, FlightsData o2) {
                                        Integer p1 = Integer.parseInt(o1.getFares().get(0).getFare());
                                        Integer p2 = Integer.parseInt(o2.getFares().get(0).getFare());
                                        return p1.compareTo(p2);
                                    }
                                });
                                itemListAdapter.notifyDataSetChanged();
                                break;

                            case R.id.earliest:
                                currentSortOrderBy = R.id.earliest;
                                Collections.sort(mainViewModel.getFlightsDataList(), new Comparator<FlightsData>() {
                                    @Override
                                    public int compare(FlightsData o1, FlightsData o2) {
                                        Long c1 = o1.getDepartureTime();
                                        Long c2 = o2.getDepartureTime();
                                        return c1.compareTo(c2);
                                    }
                                });
                                itemListAdapter.notifyDataSetChanged();
                                break;
                            case R.id.latest:
                                currentSortOrderBy = R.id.latest;
                                Collections.sort(mainViewModel.getFlightsDataList(), new Comparator<FlightsData>() {
                                    @Override
                                    public int compare(FlightsData o1, FlightsData o2) {
                                        Long c1 = o1.getArrivalTime();
                                        Long c2 = o2.getArrivalTime();
                                        return c1.compareTo(c2);
                                    }
                                });
                                itemListAdapter.notifyDataSetChanged();
                                break;
                            default:
                                currentSortOrderBy = -1;
                                break;
                        }
                        d.dismiss();
                    }
                });
                d.show();
            }
        });
    }

    private void setupFlightsList(RecyclerView flightsRecyclerView) {
        itemListAdapter = new ItemListAdapter();
        flightsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        flightsRecyclerView.setAdapter(itemListAdapter);
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.reset();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MainViewModel) {
            MainViewModel mainViewModel = (MainViewModel) observable;
            if (itemListAdapter != null) {
                Gson gson = new Gson();
                // converts object to json string
                String jsonString = gson.toJson(mainViewModel.getAppendixData().getProviders());
                try {
                    HashMap<String, Object> providerMap = jsonToMap(new JSONObject(jsonString));
                    itemListAdapter.setFlightsDataList(mainViewModel.getFlightsDataList(), providerMap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static HashMap<String, Object> jsonToMap(JSONObject json) throws JSONException {
        HashMap<String, Object> retMap = new HashMap<String, Object>();

        if(json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static HashMap<String, Object> toMap(JSONObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}
