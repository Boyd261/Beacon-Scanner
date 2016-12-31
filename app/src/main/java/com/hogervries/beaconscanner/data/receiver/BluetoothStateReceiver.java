package com.hogervries.beaconscanner.data.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

/**
 * Created by boydhogerheijde on 31/12/2016.
 */

public class BluetoothStateReceiver extends BroadcastReceiver {

    private final IntentFilter FILTER = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

    private BluetoothStateChangedListener bluetoothStateChangedListener;

    public interface BluetoothStateChangedListener {

        void onBluetoothTurnedOff();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)
                && bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF
                && bluetoothStateChangedListener != null) {
            bluetoothStateChangedListener.onBluetoothTurnedOff();
        }
    }

    public void register(@NonNull Context context,
                         @NonNull BluetoothStateChangedListener bluetoothStateChangedListener) {
        this.bluetoothStateChangedListener = bluetoothStateChangedListener;
        context.registerReceiver(this, FILTER);
    }

    public void unregister(@NonNull Context context) {
        this.bluetoothStateChangedListener = null;
        context.unregisterReceiver(this);
    }

}
