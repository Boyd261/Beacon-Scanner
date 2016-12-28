package com.hogervries.beaconscanner.data.model;

import android.support.annotation.DrawableRes;

/**
 * Beacon BeaconScannerService, file created on 07/03/16.
 *
 * @author Boyd Hogerheijde
 * @author Mitchell de Vries
 */
public class LogItem {

    private int logo;
    private String action;
    private String timeStamp;

    public LogItem(@DrawableRes int logo, String action, String timeStamp) {
        this.logo = logo;
        this.action = action;
        this.timeStamp = timeStamp;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
