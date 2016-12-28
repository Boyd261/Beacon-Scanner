# Beacon-Scanner (Plus)

Scans, transmits and shows details of every iBeacon/Eddystone beacon in your area.

The app displays the following information of a beacon:
* UUID, Minor and Major values
* Distance to beacon
* TX and RSSI values
* Bluetooth information like MAC address and more

In the settings menu you can customise scanning and transmitting settings. Some of the settings include:
* Scan period time
* Between scan periods time
* How long beacons should remain in the cache
* UUID, major, minor, frequency etc.

You can download the app via [Google Play](https://play.google.com/store/apps/details?id=com.hogervries.beaconscanner) or you can fork this repo and install it via Android Studio.

This application is an open source project. We're students in our 3rd year so any feedback is much appreciated!

We're working hard on making this app as awesome as it could be. Some of the features we're working on right now are:
* Beacon Scanner Plus(Replacing the old app)
* Fixing annoying bugs
* Saving and sending logs
* Better settings
* Background scanning
* Adding an action for a particular beacon(opening an app/sending a message etc).

# Credits

A huge thanks to the guys from Radius Networks for providing a great beacon library and lots of information! Be sure to check them out!

##Libraries

* [AltBeacon](https://altbeacon.github.io/android-beacon-library/index.html): Beacon scan/transmit library.
* [Butterknife](http://jakewharton.github.io/butterknife/): View injection library.
