package com.hogervries.beaconscanner.introduction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hogervries.beaconscanner.R;

/**
 * Trifork Netherlands.
 * GuestApp Social.
 * Mitchell de Vries & Mohammed Ali.
 */

public class IntroTransmitFragment extends Fragment {

    public static IntroTransmitFragment newInstance() {
        return new IntroTransmitFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro_transmit, container, false);
    }
}
