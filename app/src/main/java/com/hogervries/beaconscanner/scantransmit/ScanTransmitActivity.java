package com.hogervries.beaconscanner.scantransmit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.hogervries.beaconscanner.R;
import com.hogervries.beaconscanner.scantransmit.ScanTransmitFragment;

/**
 * Beacon Scanner.
 *
 * @author Boyd Hogerheijde
 * @author Mitchell de Vries
 */
public class ScanTransmitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_transmit);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = ScanTransmitFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}
