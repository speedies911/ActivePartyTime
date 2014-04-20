package com.app.activepartytime.activities.fragments;



import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.activepartytime.R;
import com.app.activepartytime.activities.GameMoveActivity;
import com.app.activepartytime.core.data.tasks.TaskDB;
import com.app.activepartytime.core.data.tasks.TaskDatabaseHandler;
import com.app.activepartytime.core.game.Game;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class GameInfoFragment extends Fragment {

    private Button generateButton;
    private LinearLayout taskInformation;

    private TaskDatabaseHandler database;
    private TaskDB currentTask;

    private int side;

    private Button startStopButton;
    private Button card;

    private ImageButton tick;
    private ImageButton cross;

    private static final long MAX_TIME_IN_MS = 20 * 1000;//2 * 60 * 1000;
    private static final long COUNTDOWN_INTERVAL_IN_MS = 1000;

    private CountDownTimer timer;
    private TextView timerDisplay;
    private boolean timerIsRunning;
    private long timePause;

    private View view1;
    private Game game;
    private GameMoveActivity activity;

    public GameInfoFragment(Game game) {
        // Required empty public constructor
        this.game = game;
        this.activity = (GameMoveActivity)getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        side = 0;
        database = new TaskDatabaseHandler(getActivity());
        currentTask = null;

        view1 = inflater.inflate(R.layout.fragment_game_info, container, false);
        tick = (ImageButton) view1.findViewById(R.id.tickButton);
        cross = (ImageButton) view1.findViewById(R.id.crossButton);


        generateButton = (Button) view1.findViewById(R.id.generateButton);
        startStopButton = (Button) view1.findViewById(R.id.startStopButton);
        card = (Button) view1.findViewById(R.id.taskCard);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateFunction();
            }
        });


        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopFunc();
            }
        });


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });
        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                success();
            }
        });

        return view1;
    }

    private void startStopFunc () {
        tick.setVisibility(RelativeLayout.VISIBLE);
        cross.setVisibility(RelativeLayout.VISIBLE);
        if (timerIsRunning) {
            timer.cancel();
            timerIsRunning = false;
        } else {
            timer = new CountDownTimer(timePause, COUNTDOWN_INTERVAL_IN_MS) {
                @Override
                public void onTick(long millisUntilFinished) {
                    StringBuffer time = new StringBuffer();
                    int milTime = (int) millisUntilFinished / 1000;
                    time.append('0');
                    int minutes = (int)milTime/60;
                    time.append(minutes);
                    time.append(':');
                    int seconds = milTime - 60*minutes;
                    if (seconds < 10){
                        time.append('0');
                    }
                    time.append(milTime - 60*minutes);
                    if(seconds == 0){
                        time.append('0');
                    }
                    timerDisplay.setText(time.toString());
                    timePause = millisUntilFinished;

                }

                @Override
                public void onFinish() {
                    timerDisplay.setText("END !!!");
                }
            }.start();
            timerIsRunning = true;
        }
    }

    private void timeInit(long maxTime){

        startStopButton = (Button)view1.findViewById(R.id.startStopButton);
        timerDisplay = (TextView)view1.findViewById(R.id.stopWatch);
        timePause = maxTime;
        /*
        show time before countdown starts
         */
        StringBuffer time = new StringBuffer();
        int milTime = (int) maxTime / 1000;
        time.append('0');
        int minutes = (int)milTime/60;
        time.append(minutes);
        time.append(':');
        int seconds = milTime - 60*minutes;
        time.append(milTime - 60*minutes);
        if (seconds == 0){
            time.append('0');
        }
        timerDisplay.setText(time.toString());

        timerIsRunning = false;

    }

    public void generateFunction(){
        generateButton.setVisibility(RelativeLayout.GONE);
        generateButton.setEnabled(false);
        card.setVisibility(RelativeLayout.VISIBLE);
        view1.findViewById(R.id.stopWatch).setVisibility(RelativeLayout.VISIBLE);
        view1.findViewById(R.id.startStopButton).setVisibility(RelativeLayout.VISIBLE);

        currentTask = database.getRandomTask();

        card.setText(currentTask.getName() + " (" + currentTask.getPoints() + ")");
        card.setEnabled(true);
        timeInit(MAX_TIME_IN_MS);

    }

    public void flipCard(){
        if(side == 0){
            card.setBackgroundColor(Color.MAGENTA);
            card.setText("Zobrazit zadani");
            card.setTextColor(Color.CYAN);
            side = 1;
        } else {
            card.setBackgroundColor(Color.BLUE);
            card.setText(currentTask.getName() + " (" + currentTask.getPoints() + ")");
            card.setTextColor(Color.YELLOW);
            side = 0;
        }

    }

    public void success() {
        game.moveTeam(game.getCurrentTeam(),currentTask.getPoints());
        game.nextTeam();
        activity.mPager.setCurrentItem(1);
        activity.update();

    }


}
