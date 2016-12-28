package com.hogervries.beaconscanner.beacondetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hogervries.beaconscanner.R;

import org.altbeacon.beacon.Beacon;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Beacon BeaconScannerService, file created on 07/03/16.
 *
 * @author Boyd Hogerheijde
 * @author Mitchell de Vries
 */
public class BeaconDetailActivity extends AppCompatActivity {

    private static final String EXTRA_BEACON = "com.hogervries.beaconscanner.activity.beaconactivity.extra_beacon";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.pager) ViewPager viewPager;

    public static Intent newIntent(@NonNull Context packageContext,
                                   @NonNull Beacon beacon) {
        Intent intent = new Intent(packageContext, BeaconDetailActivity.class);
        intent.putExtra(EXTRA_BEACON, beacon);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        ButterKnife.bind(this);

        setToolbar();

        setUpTabs();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpTabs() {
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
