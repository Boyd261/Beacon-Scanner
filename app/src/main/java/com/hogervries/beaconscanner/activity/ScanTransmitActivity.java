package com.hogervries.beaconscanner.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.hogervries.beaconscanner.R;
import com.hogervries.beaconscanner.adapter.BeaconAdapter.OnBeaconSelectedListener;
import com.hogervries.beaconscanner.fragment.ScanTransmitFragment;

import org.altbeacon.beacon.Beacon;

/**
 * Beacon Scanner, file created on 07/03/16.
 *
 * @author Boyd Hogerheijde
 * @author Mitchell de Vries
 */
public class ScanTransmitActivity extends SingleFragmentActivity implements OnBeaconSelectedListener {

    @Override
    protected Fragment createFragment() {
        return ScanTransmitFragment.newInstance();
    }

    @Override
    public void onBeaconSelected(Beacon beacon) {
        Intent beaconIntent = BeaconDetailsActivity.newIntent(this, beacon);
        startActivity(beaconIntent);
        // Overriding screen transition animation.
        overridePendingTransition(R.anim.anim_transition_from_right, R.anim.anim_transition_fade_out);
    }
}
