package com.hogervries.beaconscanner.data;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

/**
 * Beacon Scanner.
 *
 * @author Boyd Hogerheijde.
 * @author Mitchell de Vries.
 */
public class BeaconConsumerImpl implements BeaconConsumer {

    private static final String REGION_ID = "beacon_scanner_region";

    private Context context;
    private BeaconManager beaconManager;
    private OnScanBeaconsListener onScanBeaconsCallback;

    /**
     * This callback interface handles on scan events.
     * Interface must be implemented by classes using this service.
     */
    public interface OnScanBeaconsListener {
        /**
         * Called when beacons are scanned.
         *
         * @param beacons List of beacons which are scanned.
         */
        void onScanBeacons(Collection<Beacon> beacons);
    }

    public BeaconConsumerImpl(@NonNull Context context,
                              @NonNull BeaconManager beaconManager,
                              @NonNull OnScanBeaconsListener scanBeaconsListener) {
        this.context = context;
        this.beaconManager = beaconManager;

        try {
            this.onScanBeaconsCallback = scanBeaconsListener;
        } catch (ClassCastException callbackNotImplementedException) {
            throw new ClassCastException(context.getPackageName()
                    + " must implement OnScanBeaconsListener");
        }

        setUpBeaconManager();
    }

    private void setUpBeaconManager() {
        addBeaconLayouts();
        setScanPeriod();
        enableCache();
    }

    private void addBeaconLayouts() {
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_TLM_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
    }

    private void setScanPeriod() {
        beaconManager.setForegroundScanPeriod(1100);
        beaconManager.setForegroundBetweenScanPeriod(0);

        beaconManager.setBackgroundMode(false);
        beaconManager.setBackgroundScanPeriod(1100);
        beaconManager.setBackgroundBetweenScanPeriod(0);
    }

    private void enableCache() {
        BeaconManager.setUseTrackingCache(true);
        beaconManager.setMaxTrackingAge(5000);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                onScanBeaconsCallback.onScanBeacons(collection);
            }
        });

        try {
            // Starting ranging beacons within defined region(null values indicate that it scans for every beacon).
            Region region = new Region(REGION_ID, null, null, null);
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void bind() {
        beaconManager.bind(this);
    }

    public void unbind() {
        beaconManager.unbind(this);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return context.bindService(intent, serviceConnection, i);
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
    }

    @Override
    public Context getApplicationContext() {
        return context.getApplicationContext();
    }
}
