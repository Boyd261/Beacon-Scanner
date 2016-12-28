package com.hogervries.beaconscanner.beacondetail.log;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hogervries.beaconscanner.data.model.LogItem;
import com.hogervries.beaconscanner.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Beacon BeaconScannerService, file created on 07/03/16.
 *
 * @author Boyd Hogerheijde
 * @author Mitchell de Vries
 */
public class BeaconLogFragment extends Fragment {

    @BindView(R.id.log_recycler) RecyclerView logRecycler;

    public static BeaconLogFragment newInstance() {
        return new BeaconLogFragment();
    }

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beacon_log, container, false);
        unbinder = ButterKnife.bind(this, view);

        logRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        logRecycler.setAdapter(new LogItemListAdapter(getLogItems(), null, getActivity()));
        return view;
    }

    private List<LogItem> getLogItems() {
        List<LogItem> logItems = new ArrayList<>();
        for (int i = 29; i > 20; i--) {
            logItems.add(new LogItem(R.drawable.ic_log_stop, "Stopped Scanning", i + "-06-2016 14:56:12"));
            logItems.add(new LogItem(R.drawable.ic_log_distance, "Distance: " + i + "m", i + "-06-2016 14:56:14"));
            logItems.add(new LogItem(R.drawable.ic_log_rssi, "Rssi value: -59", i + "-06-2016 14:56:14"));
            logItems.add(new LogItem(R.drawable.ic_beacon, "iBeacon Found", i + "-06-2016 14:56:14"));
            logItems.add(new LogItem(R.drawable.ic_log_start, "Started Scanning", i + "-06-2016 14:56:12"));
        }
        return logItems;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
