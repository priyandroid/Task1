package com.abc.factorgame;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private EditText editText;
    private Button button;
    private ImageView image;
    private TextView score_board;
    private TextView text_score;
    private Button tv1;
    private Button tv2;
    private Button tv3;
    private LinearLayout linearLayout;
    private TextView mTextField;
    CountDownTimer cTimer = null;
    private boolean mTimerRunning;


    private void shakeIt() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, 10));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }

    int score;
    int color;
    int highScore;
    private String saved_high_score_key = "high_score";
    private int default_value_key = 0;
    int flag;
    private int answer;
    int v1, v2, v3, number;
    private long mEndTime;
    private  long mTimeLeftInMillis=10000;

    Options obj = new Options();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_text);
        button = findViewById(R.id.enter_button);
        image = findViewById(R.id.image);
        text_score = findViewById(R.id.text_score);
        score_board = findViewById(R.id.score);
        linearLayout = findViewById(R.id.linear2);
        mTextField = findViewById(R.id.timer);
        tv1 = findViewById(R.id.text_opt1);
        tv2 = findViewById(R.id.text_opt2);
        tv3 = findViewById(R.id.text_opt3);


        if (savedInstanceState != null) {
            flag = savedInstanceState.getInt("flag");
            mTimerRunning=savedInstanceState.getBoolean("checktimer");
            if (mTimerRunning) {
                mEndTime = savedInstanceState.getLong("endTime");
                mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
                startTimer();
            }

            if (flag == 1) {
                v1 = savedInstanceState.getInt("value1");
                v2 = savedInstanceState.getInt("value2");
                v3 = savedInstanceState.getInt("value3");
                answer = savedInstanceState.getInt("answer");
                score = savedInstanceState.getInt("score");
                color = savedInstanceState.getInt("color");
                score_board.setText(String.valueOf(score));
                setActivityBackgroundColor(color);
                setValues();
            }
        }
editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onPressedEnter();

        }
        return false;
    }
});



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPressedEnter();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "tv called");
                checkAns(v1);

            }
        });


        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAns(v2);

            }
        });


        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAns(v3);

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("highest_score", gethighScore());
                ScoreDialog scoreDialog = new ScoreDialog();
                scoreDialog.setArguments(bundle);
                scoreDialog.show(getSupportFragmentManager(), "scoreDialog");


            }
        });
    }

    public void setOptions() {
        String string = editText.getText().toString();
        Log.d(TAG, string);

        number = Integer.parseInt(string);
        if (number == 0) {
            Toast.makeText(this, "Has infinite factors", Toast.LENGTH_SHORT).show();


            Log.d(TAG, "number" + (number));
        } else {
            ArrayList<Integer> list = new ArrayList<>();

            v1 = obj.getfactor(number);
            answer = v1;
            list.add(v1);
            v2 = obj.getOption(number);
            list.add(v2);
            v3 = obj.getOption(number);
            list.add(v3);
            Log.d(TAG, "value" + v1 + v2 + v3);


            Collections.shuffle(list);
            v1 = list.get(0);
            v2 = list.get(1);
            v3 = list.get(2);
            setValues();
        }
        if (number == 1) {
            Toast.makeText(this, "1 is its only factor", Toast.LENGTH_SHORT).show();
        }
        if (number == 2) {
            Toast.makeText(this, "2 is even prime", Toast.LENGTH_SHORT).show();
        }

    }



    public void setValues() {

        tv1.setText(String.valueOf(v1));
        tv2.setText(String.valueOf(v2));
        tv3.setText(String.valueOf(v3));

        linearLayout.setVisibility(View.VISIBLE);
    }

    public void setActivityBackgroundColor(int color) {
        Log.d(TAG, "color");
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

    void checkAns(int x) {

        cancelTimer();
        if (x == answer) {
            color = ContextCompat.getColor(this, R.color.green);
            setActivityBackgroundColor(color);
            score++;
            score_board.setText(String.valueOf(score));
        } else {
            shakeIt();
            color = ContextCompat.getColor(this, R.color.red);
            setActivityBackgroundColor(color);
            openDialog();
            score = 0;

        }


    }

    public void openDialog() {
        Bundle bundle = new Bundle();
        bundle.putInt("answer", answer);
        bundle.putInt("score", score);
        bundle.putInt("highest_score", gethighScore());
        ExampleDialogFragment exampleDialog = new ExampleDialogFragment();
        exampleDialog.setArguments(bundle);

exampleDialog.show(getSupportFragmentManager(), "dialog");

    }


    public int gethighScore() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        highScore = sharedPref.getInt(saved_high_score_key, default_value_key);
        if (highScore < score) {
            highScore = score;
            updateHigh_score();
        }
        return highScore;
    }

    public void updateHigh_score() {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(saved_high_score_key, highScore);
        editor.apply();

    }

    void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        cTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {

            public void onTick(long millisUntilFinished) {
               mTimeLeftInMillis = millisUntilFinished / 1000;
                mTextField.setText(String.format("%02d", mTimeLeftInMillis));
            }

            public void onFinish() {
                mTextField.setText("10");
                mTimerRunning=false;
                openDialog();
            }

        };
        cTimer.start();
        mTimerRunning =true;

    }


    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
        mTimerRunning=false;
        mTextField.setText("10");
    }

    public void onPressedEnter() {
        flag = 1;
        mTimeLeftInMillis=10000;
        color = ContextCompat.getColor(getApplicationContext(), R.color.white);
        setActivityBackgroundColor(color);
        setOptions();
        startTimer();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("value1", v1);
        outState.putInt("value2", v2);
        outState.putInt("value3", v3);
        outState.putInt("answer", answer);
        outState.putInt("score", score);
        outState.putInt("color", color);
        outState.putInt("flag", flag);
        outState.putLong("millisleft", mTimeLeftInMillis);
        outState.putBoolean("checktimer", mTimerRunning);
        outState.putLong("endTime", mEndTime);



    }

}




