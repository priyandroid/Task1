package com.abc.factorgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialogFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater =getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog, null);
        TextView  currentScore =view.findViewById(R.id.score);
        TextView  answer =view.findViewById(R.id.answer);
        TextView  highestScore =view.findViewById(R.id.high_score);

String s1=String.valueOf(getArguments().getInt("score"));
        currentScore.setText(s1);
s1=String.valueOf(getArguments().getInt("answer"));
        answer.setText(s1);
        s1=String.valueOf(getArguments().getInt("highest_score"));
        highestScore.setText(s1);

        builder.setView(view)
                .setTitle("Score")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();


    }
}
