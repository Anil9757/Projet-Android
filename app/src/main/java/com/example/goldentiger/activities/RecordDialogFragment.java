package com.example.goldentiger.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class RecordDialogFragment extends DialogFragment {

    @NonNull
    @Override
    //Dialogue Fragment affichant le message Ce livre a été rajouté dans les favoris.
    //Il est activé uniquement quand on clique sur le bouton où il y l'étoile (voir code BookDetails.java).
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Ce livre a été rajouté dans vos favoris");
        return builder.create();
    }
}
