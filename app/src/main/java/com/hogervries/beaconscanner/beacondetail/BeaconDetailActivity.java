package com.hogervries.beaconscanner.beacondetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hogervries.beaconscanner.R;
import com.hogervries.beaconscanner.beacondetail.log.BeaconLogFragment;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Beacon Scanner.
 *
 * @author Boyd Hogerheijde
 * @author Mitchell de Vries
 */
public class BeaconDetailActivity extends AppCompatActivity {

    private static final String EXTRA_BEACON = "com.hogervries.beaconscanner.activity.beaconactivity.extra_beacon";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.pager) ViewPager viewPager;
    @BindString(R.string.tab_title_detail) String detailTitle;
    @BindString(R.string.tab_title_log) String logTitle;

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

        setSupportActionBar(toolbar);
        showHomeAsUpButton();
        setUpTabLayout();
    }

    private void showHomeAsUpButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpTabLayout() {
        // Pairing fragments with their titles to properly display the tab title for each fragment.
        List<Pair<Fragment, String>> tabs = new ArrayList<>();
        tabs.add(new Pair<Fragment, String>(BeaconDetailFragment.newInstance(), detailTitle));
        tabs.add(new Pair<Fragment, String>(BeaconLogFragment.newInstance(), logTitle));

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabs);
        viewPager.setAdapter(tabPagerAdapter);

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

    private class TabPagerAdapter extends FragmentStatePagerAdapter {

        private List<Pair<Fragment, String>> tabs;

        public TabPagerAdapter(@NonNull FragmentManager fragmentManager,
                               @NonNull List<Pair<Fragment, String>> tabs) {
            super(fragmentManager);
            this.tabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            return tabs.get(position).first;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position).second;
        }

        @Override
        public int getCount() {
            return tabs.size();
        }
    }
}
