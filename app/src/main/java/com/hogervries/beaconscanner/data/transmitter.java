package com.hogervries.beaconscanner.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;

/**
 * Created by boydhogerheijde on 31/12/2016.
 */

public class Transmitter {

    private static final String TAG = "Transmitter";

    private BeaconTransmitter beaconTransmitter;

    public Transmitter(@NonNull Context context) {
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
        beaconTransmitter = new BeaconTransmitter(context.getApplicationContext(), beaconParser);
    }

    public void start() {
        Beacon beacon = new Beacon.Builder()
                .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6")
                .setId2("1")
                .setId3("2")
                .setManufacturer(0x0118)
                .setTxPower(-59)
                .setDataFields(Arrays.asList(new Long[]{0l}))
                .build();

        Log.d(TAG, "start: Starting transmitting as " + beacon);
        beaconTransmitter.startAdvertising(beacon);
    }

    public void stop() {
        Log.d(TAG, "stop: Stopping transmitting.");
        beaconTransmitter.stopAdvertising();
    }
}
