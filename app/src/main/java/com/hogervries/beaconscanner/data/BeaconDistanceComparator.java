package com.hogervries.beaconscanner.data;

import org.altbeacon.beacon.Beacon;

import java.util.Comparator;

/**
 * Beacon Scanner.
 *
 * @author Boyd Hogerheijde.
 * @author Mitchell de Vries.
 */
public class BeaconDistanceComparator implements Comparator<Beacon> {

    @Override
    public int compare(Beacon beacon, Beacon beaconToCompare) {
        return Double.compare(beacon.getDistance(), beaconToCompare.getDistance());
    }

}
