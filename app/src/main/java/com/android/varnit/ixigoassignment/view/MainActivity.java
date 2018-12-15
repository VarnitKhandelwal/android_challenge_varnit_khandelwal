package com.android.varnit.ixigoassignment.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.android.varnit.ixigoassignment.R;
import com.android.varnit.ixigoassignment.adapter.ItemListAdapter;
import com.android.varnit.ixigoassignment.databinding.ActivityMainBinding;
import com.android.varnit.ixigoassignment.viewmodel.MainViewModel;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class MainActivity extends AppCompatActivity implements Observer {

    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;

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
    }

    private void setupFlightsList(RecyclerView flightsRecyclerView) {
        ItemListAdapter adapter = new ItemListAdapter();
        flightsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        flightsRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        flightsRecyclerView.setAdapter(adapter);
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
            ItemListAdapter itemListAdapter = (ItemListAdapter) activityMainBinding.flightsRecyclerView.getAdapter();
            MainViewModel mainViewModel = (MainViewModel) observable;
            if (itemListAdapter != null) {
                Gson gson = new Gson();
                // converts object to json string
                String jsonString = gson.toJson(mainViewModel.getAppendixData().getProviders());
                try {
                    HashMap<String, Object> providerMap = jsonToMap(new JSONObject(jsonString));
//                    HashMap<String, String> providerMap = gson.fromJson(jsonString, new TypeToken<Map<String, HashMap>>(){}.getType());
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
