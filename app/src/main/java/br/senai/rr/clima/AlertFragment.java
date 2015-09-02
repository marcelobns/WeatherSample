package br.senai.rr.clima;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by marcelobarbosa on 7/20/15.
 */
public class AlertFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Falha na Rede!")
                .setMessage("Sua requisição falhou!")
                .setPositiveButton("OK", null);

        return builder.create();
    }
}
