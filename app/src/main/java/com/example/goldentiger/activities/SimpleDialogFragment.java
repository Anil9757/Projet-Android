package com.example.goldentiger.activities;

import android.accessibilityservice.AccessibilityService;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class SimpleDialogFragment extends DialogFragment {

    @NonNull
    @Override
    //Fragment sous forme de Dialog Fragment qui s'active uniquement s'il n'y pas de connexion à internet.
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Aucune Connexion à Internet");
        return builder.create();
    }
}
