package com.app.activepartytime.activities.fragments;



import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.app.activepartytime.R;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class GamePlaygroundFragment extends Fragment {

    private CountDownTimer timer;
    private TextView timerDisplay;
    private boolean timerIsRunning;

    private Button startStopButton;

    private static final long MAX_TIME_IN_MS = 2 * 60 * 1000;
    private static final long COUNTDOWN_INTERVAL_IN_MS = 1000;

    public GamePlaygroundFragment() {
        // Required empty public constructor
    }

    public void startStop() {
        if (timerIsRunning) {
            timer.cancel();
            timerIsRunning = false;
        } else {
            timer = new CountDownTimer(MAX_TIME_IN_MS, COUNTDOWN_INTERVAL_IN_MS) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerDisplay.setText(millisUntilFinished / 1000 + "s");
                }

                @Override
                public void onFinish() {
                    timerDisplay.setText("TIME OUT !!!");
                }
            }.start();
            timerIsRunning = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_game_playground, container, false);

        timerDisplay = (TextView)v.findViewById(R.id.timerDisplay);
        timerDisplay.setText(MAX_TIME_IN_MS / 1000 + "s");

        timerIsRunning = false;
        startStopButton = (Button) v.findViewById(R.id.startstop);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });

        return v;
    }


}
