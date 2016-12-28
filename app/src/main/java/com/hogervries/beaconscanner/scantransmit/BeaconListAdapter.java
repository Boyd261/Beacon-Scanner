package com.hogervries.beaconscanner.scantransmit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hogervries.beaconscanner.R;
import com.hogervries.beaconscanner.data.BeaconComparator;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Beacon BeaconScannerService.
 *
 * @author Boyd Hogerheijde.
 * @author Mitchell de Vries.
 */
public class BeaconListAdapter extends RecyclerView.Adapter<BeaconListAdapter.BeaconHolder> {

    private List<Beacon> beacons;
    private OnBeaconClickListener beaconClickListener;

    /**
     * Callback which has to be implemented by the hosting activity.
     * <p/>
     * Callback interface allows for a component to be a completely self-contained,
     * modular component that defines its own layout and behaviour.
     */
    public interface OnBeaconClickListener {

        /**
         * Handles on beacon selected event.
         *
         * @param beacon Selected beacon.
         */
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

        holder.beaconTypeTextView.setText("Beacon");
        holder.uuidTextView.setText("UUID");
        holder.distanceTextView.setText("Distance");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beaconClickListener.onBeaconClicked(beacon);
            }
        });
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
        Collections.sort(this.beacons, new BeaconComparator());
    }

    class BeaconHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.beacon_type_text) TextView beaconTypeTextView;
        @BindView(R.id.beacon_uuid_text) TextView uuidTextView;
        @BindView(R.id.beacon_distance_text) TextView distanceTextView;

        public BeaconHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
