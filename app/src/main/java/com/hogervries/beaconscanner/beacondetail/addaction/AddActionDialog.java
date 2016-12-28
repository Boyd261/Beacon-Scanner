package com.hogervries.beaconscanner.beacondetail.addaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.hogervries.beaconscanner.R;

/**
 * Trifork Netherlands.
 * GuestApp Social.
 * Mitchell de Vries & Mohammed Ali.
 */

public class AddActionDialog extends DialogFragment {

    public static AddActionDialog newInstance() {
        return new AddActionDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialog);
        builder.setView(R.layout.dialog_set_action);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO Implement action
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

}

