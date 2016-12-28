package com.hogervries.beaconscanner.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.hogervries.beaconscanner.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        setHasOptionsMenu(true);

        bindPreferenceSummaryToValue(findPreference("preference_tracking_age"));
        bindPreferenceSummaryToValue(findPreference("preference_scan_frequency"));
        bindPreferenceSummaryToValue(findPreference("preference_between_scan_period"));
        bindPreferenceSummaryToValue(findPreference("preference_uuid"));
        bindPreferenceSummaryToValue(findPreference("preference_major"));
        bindPreferenceSummaryToValue(findPreference("preference_minor"));
        bindPreferenceSummaryToValue(findPreference("preference_tx_power"));
        bindPreferenceSummaryToValue(findPreference("preference_log_max_lines"));
    }

    private Preference.OnPreferenceChangeListener bindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                preference.setSummary(
                        index >= 0 ? listPreference.getEntries()[index] : null);

            } else {
                if (preference.getKey().equals("preference_major") || preference.getKey().equals("preference_minor")) {
                    if (Integer.parseInt(stringValue) > 65535) {
                        Toast.makeText(getActivity(), "Invalid Value", Toast.LENGTH_SHORT).show();
                    } else {
                        preference.setSummary(stringValue);
                    }
                } else {
                    preference.setSummary(stringValue);

                }
            }
            return true;
        }
    };

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(bindPreferenceSummaryToValueListener);

        bindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
