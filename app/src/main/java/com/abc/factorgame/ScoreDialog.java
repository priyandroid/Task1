package com.abc.factorgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ScoreDialog extends AppCompatDialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater =getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_score, null);
        TextView textView = view.findViewById(R.id.text_view);
        String string = String.valueOf(getArguments().getInt("highest_score"));
        textView.setText(string);
        builder.setView(view)
                .setTitle("Highest Score");
        return builder.create();

    }
}
