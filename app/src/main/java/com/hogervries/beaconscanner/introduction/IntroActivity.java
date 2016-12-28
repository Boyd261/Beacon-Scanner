package com.hogervries.beaconscanner.introduction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.hogervries.beaconscanner.scantransmit.ScanTransmitActivity;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(IntroStartFragment.newInstance());
        addSlide(IntroScanFragment.newInstance());
        addSlide(IntroTransmitFragment.newInstance());
        addSlide(IntroLogFragment.newInstance());
        addSlide(IntroSettingsFragment.newInstance());
        addSlide(IntroActionFragment.newInstance());
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        starMainActivity();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        starMainActivity();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    private void starMainActivity() {
        startActivity(new Intent(this, ScanTransmitActivity.class));
    }
}
