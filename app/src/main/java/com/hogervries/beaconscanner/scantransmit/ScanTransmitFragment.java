package com.hogervries.beaconscanner.scantransmit;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.hogervries.beaconscanner.R;
import com.hogervries.beaconscanner.beacondetail.BeaconDetailActivity;
import com.hogervries.beaconscanner.data.Scanner;
import com.hogervries.beaconscanner.data.Transmitter;
import com.hogervries.beaconscanner.settings.SettingsActivity;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BleNotAvailableException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Beacon Scanner.
 * <p>
 * This fragment scans for beacons and transmits as a beacon.
 * If there are beacons in the area a list will be displayed.
 * <p>
 * This fragment mostly contains UI code to indicate that the app is scanning or transmitting.
 * The button in the middle of the screen will expand and a ring will pulse,
 * indicating that it is scanning.
 *
 * @author Boyd Hogerheijde
 * @author Mitchell de Vries
 */
public class ScanTransmitFragment extends Fragment {

    private static final int SCANNING = 0;
    private static final int TRANSMIT = 1;
    private static final int REQUEST_ENABLE_BLUETOOTH = 2;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.start_button_circle) ImageView startButtonCircle;
    @BindView(R.id.start_button) ImageButton startButton;
    @BindView(R.id.pulse_ring) ImageView pulseRing;
    @BindView(R.id.switch_layout) RelativeLayout modeSwitchLayout;
    @BindView(R.id.scan_mode_button) Button scanModeButton;
    @BindView(R.id.transmit_mode_button) Button transmitModeButton;
    @BindView(R.id.mode_switch) Switch modeSwitch;
    @BindView(R.id.list_panel_layout) CoordinatorLayout listPanelLayout;
    @BindView(R.id.beacon_recycler) RecyclerView beaconRecycler;
    @BindColor(R.color.grey) int grey;
    @BindColor(R.color.white) int white;
    @BindString(R.string.beacon_scanner) String scannerTitle;
    @BindString(R.string.beacon_transmitter) String transmitTitle;
    @BindDrawable(R.drawable.ic_button_scan) Drawable scanIcon;
    @BindDrawable(R.drawable.ic_button_transmit) Drawable transmitIcon;

    BeaconListAdapter.OnBeaconClickListener beaconClickListener = new BeaconListAdapter.OnBeaconClickListener() {
        @Override
        public void onBeaconClicked(Beacon beacon) {
            startActivity(BeaconDetailActivity.newIntent(getActivity(), beacon));
        }
    };

    Scanner.OnScanBeaconsListener beaconScanListener = new Scanner.OnScanBeaconsListener() {
        @Override
        public void onScanBeacons(final Collection<Beacon> beacons) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isAdded() && isScanning) {
                            updateBeaconList((List<Beacon>) beacons);
                        }
                    }
                });
        }
    };

    private int mode;
    private boolean isScanning;
    private boolean isTransmitting;
    private Scanner scanner;
    private Transmitter transmitter;
    private BeaconManager beaconManager;
    private BeaconListAdapter beaconListAdapter;
    private MenuItem stopMenuButton;
    private Unbinder viewUnbinder;

    public static ScanTransmitFragment newInstance() {
        return new ScanTransmitFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        beaconManager = BeaconManager.getInstanceForApplication(getActivity());

        scanner = new Scanner(getActivity(), beaconManager, beaconScanListener);
        transmitter = new Transmitter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View beaconListView = inflater.inflate(R.layout.fragment_scan_transmit, container, false);
        viewUnbinder = ButterKnife.bind(this, beaconListView);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        beaconListAdapter = new BeaconListAdapter(beaconClickListener);
        beaconRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        beaconRecycler.setAdapter(beaconListAdapter);
        beaconRecycler.setHasFixedSize(true);

        disableModeSwitchTouchDrag();

        return beaconListView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_beacon_list, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        stopMenuButton = menu.findItem(R.id.stop_scanning);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stop_scanning:
                stopScanning();
                stopMenuButton.setVisible(false);
                return true;
            case R.id.settings:
                startActivity(SettingsActivity.newIntent(getActivity()));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                // TODO: 31/12/2016 show snackbar or smth.
            } else {
                // TODO: 28/12/2016 show message to user stating that without BLT the app doesn't do anything.
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isScanning) {
            scanner.stop();
        } else if (isTransmitting) {
            transmitter.stop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewUnbinder.unbind();
    }

    private void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    private void disableModeSwitchTouchDrag() {
        modeSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getActionMasked() == MotionEvent.ACTION_MOVE;
            }
        });
    }

    private void updateBeaconList(List<Beacon> beacons) {
        if (listPanelLayout.getVisibility() == View.GONE && !beacons.isEmpty()) {
            listPanelLayout.setVisibility(View.VISIBLE);
            listPanelLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_bottom));
        } else if (listPanelLayout.getVisibility() == View.VISIBLE && beacons.isEmpty()){
            listPanelLayout.setVisibility(View.GONE);
            listPanelLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_bottom));
        }

        beaconListAdapter.replaceListData(beacons);
    }

    @OnClick({R.id.start_button, R.id.start_button_circle})
    void onStartButtonClicked() {
        if (!(isScanning || isTransmitting)) {
            if (!isBluetoothLEAvailable()) {
                showBluetoothLENotAvailableMessage();
                return;
            }

            if (isBlueToothEnabled()) {
                if (mode == SCANNING) {
                    toggleScanning();
                } else {
                    toggleTransmitting();
                }
            } else {
                requestEnableBluetooth();
            }
        }
    }

    @OnClick(R.id.mode_switch)
    void onModeSwitched() {
        if (mode == SCANNING) {
            switchToTransmitMode();
        } else {
            switchToScanMode();
        }
    }

    @OnClick(R.id.scan_mode_button)
    void switchToScanMode() {
        mode = SCANNING;
        modeSwitch.setChecked(false);
        scanModeButton.setTextColor(white);
        transmitModeButton.setTextColor(grey);
        startButton.setImageDrawable(scanIcon);

        setToolbarTitle(scannerTitle);
    }

    @OnClick(R.id.transmit_mode_button)
    void switchToTransmitMode() {
        mode = TRANSMIT;
        modeSwitch.setChecked(true);
        scanModeButton.setTextColor(grey);
        transmitModeButton.setTextColor(white);
        startButton.setImageDrawable(transmitIcon);

        setToolbarTitle(transmitTitle);
    }

    private void toggleScanning() {
        if (!isScanning) {
            startScanning();
        } else {
            stopScanning();
        }
    }

    private void startScanning() {
        isScanning = true;
        stopMenuButton.setVisible(true);
        startPulseAnimation();

        scanner.start();
    }

    private void stopScanning() {
        isScanning = false;
        stopMenuButton.setVisible(false);
        stopPulseAnimation();

        scanner.stop();

        // Passing an empty list will slide the list panel down again.
        updateBeaconList(new ArrayList<Beacon>());
    }

    private void toggleTransmitting() {
        if (!isTransmitting) {
            startTransmitting();
        } else {
            stopTransmitting();
        }
    }

    // TODO: 28/12/2016 implement transmitting
    private void startTransmitting() {
        isTransmitting = true;
        startPulseAnimation();

        transmitter.start();
    }

    private void stopTransmitting() {
        isTransmitting = false;
        stopPulseAnimation();

        transmitter.stop();
    }

    private void startPulseAnimation() {
        AnimationSet pulseAnimation = new AnimationSet(false);
        pulseAnimation.addAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.pulse));

        startButtonCircle.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.expand));
        startButton.setImageResource(R.drawable.ic_button_stop);
        pulseRing.setVisibility(View.VISIBLE);
        pulseRing.startAnimation(pulseAnimation);
        modeSwitchLayout.setVisibility(View.GONE);
    }

    private void stopPulseAnimation() {
        startButtonCircle.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.contract));
        startButton.setImageDrawable(mode == SCANNING ? scanIcon : transmitIcon);
        pulseRing.setVisibility(View.GONE);
        pulseRing.clearAnimation();
        modeSwitchLayout.setVisibility(View.VISIBLE);
    }

    private boolean isBluetoothLEAvailable() {
        boolean isBluetoothLEAvailable = false;

        try {
            isBluetoothLEAvailable = beaconManager.checkAvailability();
        } catch (BleNotAvailableException e) {
            // Intentionally left blank.
        }

        return isBluetoothLEAvailable;
    }

    private void showBluetoothLENotAvailableMessage() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Bluetooth LE not supported")
                .setMessage("Bluetooth LE is supported by your device. Without Bluetooth LE this application does not work.")
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();
    }

    private boolean isBlueToothEnabled() {
        boolean isBluetoothEnabled = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            isBluetoothEnabled = true;
        }

        return isBluetoothEnabled;
    }

    private void requestEnableBluetooth() {
        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH);
    }
}