package com.hogervries.beaconscanner.scantransmit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hogervries.beaconscanner.R;
import com.hogervries.beaconscanner.data.BeaconDistanceComparator;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Beacon Scanner.
 *
 * @author Boyd Hogerheijde.
 * @author Mitchell de Vries.
 */
public class BeaconListAdapter extends RecyclerView.Adapter<BeaconListAdapter.BeaconHolder> {

    private List<Beacon> beacons;
    private OnBeaconClickListener beaconClickListener;

    public interface OnBeaconClickListener {

        void onBeaconClicked(Beacon beacon);
    }

    public BeaconListAdapter(@NonNull OnBeaconClickListener beaconClickListener) {
        this.beacons = new ArrayList<>();
        this.beaconClickListener = beaconClickListener;
    }

    @Override
    public BeaconHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View beaconItemView = inflater.inflate(R.layout.list_item_beacon, parent, false);
        return new BeaconHolder(beaconItemView);
    }

    @Override
    public void onBindViewHolder(BeaconHolder holder, int position) {
        final Beacon beacon = beacons.get(position);

        String beaconType;
        String beaconInfo;
        if (isEddystoneUID(beacon)) {
            beaconType = "Eddystone";
            beaconInfo = beacon.getId1() + "" + beacon.getId2();
        } else if (isEddystoneURL(beacon)) {
            beaconType = "Eddystone";
            beaconInfo = UrlBeaconUrlCompressor.uncompress(beacon.getId1().toByteArray());
        } else {
            beaconType = "iBeacon";
            beaconInfo = "Maj: " + beacon.getId2().toString() + " Min: " + beacon.getId3().toString();
        }

        holder.beaconTypeTextView.setText(beaconType);
        holder.infoTextView.setText(beaconInfo);
        holder.distanceTextView.setText(String.valueOf(beacon.getDistance()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beaconClickListener.onBeaconClicked(beacon);
            }
        });
    }

    private boolean isEddystoneUID(Beacon beacon) {
        return beacon.getServiceUuid() == 0xfeaa && beacon.getBeaconTypeCode() == 0x00;
    }

    private boolean isEddystoneURL(Beacon beacon) {
        return beacon.getServiceUuid() == 0xfeaa && beacon.getBeaconTypeCode() == 0x10;
    }

    @Override
    public int getItemCount() {
        return beacons.size();
    }

    public void replaceListData(@NonNull List<Beacon> beacons) {
        setList(beacons);
        notifyDataSetChanged();
    }

    private void setList(List<Beacon> beacons) {
        this.beacons = beacons;
        Collections.sort(this.beacons, new BeaconDistanceComparator());
    }

    class BeaconHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.beacon_type_text) TextView beaconTypeTextView;
        @BindView(R.id.beacon_info_text) TextView infoTextView;
        @BindView(R.id.beacon_distance_text) TextView distanceTextView;

        public BeaconHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
