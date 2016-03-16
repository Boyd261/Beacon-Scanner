package com.hogervries.beaconscanner.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.hogervries.beaconscanner.R;

/**
 * Trifork
 * GuestAppSocial
 */
public class IntroActivity extends AppIntro2 {

    @Override
    public void init(Bundle savedInstanceState) {

        addSlide(AppIntroFragment.newInstance("Beacon Scanner", "We will guide you through the new features of version 2.0",
                R.drawable.ic_intro_logo, Color.parseColor("#212121")));

        addSlide(AppIntroFragment.newInstance("Scan", "Detect nearby beacons and show their details",
                R.drawable.ic_button_scan, Color.parseColor("#F44336")));

        addSlide(AppIntroFragment.newInstance("Transmit", "You can now use your phone as a beacon",
                R.drawable.ic_button_transmit, Color.parseColor("#4CAF50")));

        addSlide(AppIntroFragment.newInstance("Settings", "Fully customisable settings for scanning and transmitting",
                R.drawable.ic_intro_setting, Color.parseColor("#2196F3")));

        setProgressButtonEnabled(true);
    }

    @Override
    public void onDonePressed() {
            startActivity(new Intent(this, ScanTransmitActivity.class));
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }
}
