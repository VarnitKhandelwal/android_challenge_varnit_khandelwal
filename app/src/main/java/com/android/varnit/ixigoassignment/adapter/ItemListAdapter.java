package com.android.varnit.ixigoassignment.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.varnit.ixigoassignment.R;
import com.android.varnit.ixigoassignment.databinding.ItemLayoutBinding;
import com.android.varnit.ixigoassignment.model.FlightsData;
import com.android.varnit.ixigoassignment.viewmodel.ItemFlightViewModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private List<FlightsData> flightsDataList;
    private ItemLayoutBinding itemLayoutBinding;
    private static HashMap<String, Object> providerMapData;

    public ItemListAdapter() {
        this.flightsDataList = Collections.emptyList();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        itemLayoutBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout,
                        parent, false);

        return new ViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(flightsDataList.get(position));

        if (position == 0 && flightsDataList != null && flightsDataList.size() % 2 != 0) {
            final ViewGroup.LayoutParams layoutParams = holder.itemLayoutBinding.getRoot().getLayoutParams();
            GridLayoutManager layoutManager = new GridLayoutManager(itemLayoutBinding.getRoot().getContext(), 2);
            // Set the height by params
            layoutParams.height = holder.itemLayoutBinding.getRoot().getLayoutParams().height * 2;
            // set height of RecyclerView
            holder.itemLayoutBinding.getRoot().setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return flightsDataList.size();
    }

    public void setFlightsDataList(List<FlightsData> flightsDataList, HashMap<String, Object> providerMapData) {
        this.flightsDataList = flightsDataList;
        this.providerMapData = providerMapData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemLayoutBinding itemLayoutBinding;

        public ViewHolder(ItemLayoutBinding itemLayoutBinding) {
            super(itemLayoutBinding.getRoot());
            this.itemLayoutBinding = itemLayoutBinding;
        }

        void bindItem(FlightsData flightsData) {
            if (itemLayoutBinding.getImageViewModel() == null) {
                itemLayoutBinding.setImageViewModel(
                        new ItemFlightViewModel(flightsData, providerMapData));
            } else {
                itemLayoutBinding.getImageViewModel().setFlightsData(flightsData, providerMapData);
            }
        }
    }
}

