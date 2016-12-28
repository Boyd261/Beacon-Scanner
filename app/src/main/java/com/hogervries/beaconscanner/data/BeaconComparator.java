package com.hogervries.beaconscanner.data;

import org.altbeacon.beacon.Beacon;

import java.util.Comparator;

/**
 * Created by boydhogerheijde on 28/12/2016.
 */

public class BeaconComparator implements Comparator<Beacon> {

    @Override
    public int compare(Beacon beacon, Beacon beaconToCompare) {
        return Double.compare(beacon.getDistance(), beaconToCompare.getDistance());
    }

}
