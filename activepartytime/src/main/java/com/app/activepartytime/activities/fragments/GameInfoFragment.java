package com.app.activepartytime.activities.fragments;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.activepartytime.R;
import com.app.activepartytime.StartPageActivity;
import com.app.activepartytime.activities.GameMoveActivity;
import com.app.activepartytime.core.data.tasks.TaskDB;
import com.app.activepartytime.core.data.tasks.TaskDatabaseHandler;
import com.app.activepartytime.core.game.Game;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class GameInfoFragment extends Fragment {


    private LinearLayout taskInformation;

    private TaskDatabaseHandler database;
    private TaskDB currentTask;
    private TaskDB[] finalTasks;
    private boolean finalState;
    private boolean isForAll;

    private int side;

    private Button startStopButton;
    private Button card;
    private RelativeLayout taskLayout;
    private RelativeLayout taskLayoutGenerate;
    private RelativeLayout taskLayoutHidenTask;

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
    private TextView teamName;


    public GameInfoFragment(Game game, GameMoveActivity gameMoveActivity) {
        // Required empty public constructor
        this.game = game;
        this.activity = gameMoveActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        side = 0;
        database = new TaskDatabaseHandler(getActivity());
        currentTask = null;
        finalTasks = new TaskDB[6];
        view1 = inflater.inflate(R.layout.fragment_game_info, container, false);
        tick = (ImageButton) view1.findViewById(R.id.tickButton);
        cross = (ImageButton) view1.findViewById(R.id.crossButton);

        finalState = false;
        isForAll = false;


        taskLayoutGenerate = (RelativeLayout) view1.findViewById(R.id.taskLayoutGenerate);
        taskLayoutHidenTask = new RelativeLayout(getActivity());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
        taskLayoutHidenTask.setBackgroundColor(Color.parseColor("#006400"));
        taskLayoutHidenTask.setLayoutParams(layoutParams);
        taskLayoutHidenTask.setId("taskLayoutHidenTask".hashCode());
        TextView showTask = new TextView(getActivity());
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        showTask.setText("SHOW TASK");
        showTask.setTextSize(20);
        showTask.setTextColor(Color.YELLOW);
        showTask.setLayoutParams(layoutParams);
        taskLayoutHidenTask.addView(showTask);
        teamName = (TextView) view1.findViewById(R.id.activeTeam);


        startStopButton = (Button) view1.findViewById(R.id.startStopButton);
        taskLayout = (RelativeLayout) view1.findViewById(R.id.taskLayout);
        setCurrentTeam();


        taskLayoutGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateFunction();
            }
        });

        taskLayoutHidenTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard(true);
            }
        });


        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopFunc();
            }
        });


        taskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard(false);
            }
        });
        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                success();
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                failed();
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
        taskLayoutGenerate.setVisibility(RelativeLayout.GONE);
        taskLayoutGenerate.setEnabled(false);
        taskLayout.setVisibility(RelativeLayout.VISIBLE);
        view1.findViewById(R.id.stopWatch).setVisibility(RelativeLayout.VISIBLE);
        view1.findViewById(R.id.startStopButton).setVisibility(RelativeLayout.VISIBLE);

        if (game.getCurrentTeam().getPlaygroundPosition() == game.getPlayground().getPlaygroundLength()-1){//final state
            finalTasks = database.getFinalTasks();
        }else{
            currentTask = database.getTask(game.getPlayground().getTaskType(game.getCurrentTeam().getPlaygroundPosition()), -1, false);
        }

        drawTask();
        //card.setText(currentTask.getName() + " (" + currentTask.getPoints() + ")");
        //card.setEnabled(true);
        timeInit(MAX_TIME_IN_MS);

    }

    public void flipCard(boolean show){
        if(show){
            //taskLayoutHidenTask.setVisibility(View.GONE);

            taskLayout.removeView(taskLayoutHidenTask);

           /* card.setBackgroundColor(Color.MAGENTA);
            card.setText("Zobrazit zadani");
            card.setTextColor(Color.CYAN);
            side = 1;*/
        } else {
           // taskLayoutHidenTask.setVisibility(View.VISIBLE);
            taskLayout.addView(taskLayoutHidenTask);
           /* card.setBackgroundColor(Color.BLUE);
            card.setText(currentTask.getName() + " (" + currentTask.getPoints() + ")");
            card.setTextColor(Color.YELLOW);
            side = 0;*/
        }

    }


    private void success() {
        if (isForAll){
            winDialog();
        }else{
            game.moveTeam(game.getCurrentTeam(),currentTask.getPoints());


        if (finalState){
            showWinner(game.getCurrentTeam().getName());

        }else{
            game.nextTeam();
        }

            activity.updatePlaygroundFragment();
            createNewView();
        }

    }

    private void failed(){
        game.nextTeam();

        activity.updatePlaygroundFragment();
        createNewView();
    }


    private void createNewView(){
        timerDisplay.setVisibility(View.GONE);
        taskLayoutGenerate.setVisibility(View.VISIBLE);
        taskLayoutGenerate.setEnabled(true);
        //card.setVisibility(View.GONE);
        startStopButton.setVisibility(View.GONE);
        tick.setVisibility(View.GONE);
        cross.setVisibility(View.GONE);
        timer.cancel();
        timeInit(MAX_TIME_IN_MS);
        taskLayout.removeAllViews();

        setCurrentTeam();


    }

    private void setCurrentTeam(){
        teamName.setText(game.getCurrentTeam().getName());

    }


    private void drawTask(){
        RelativeLayout.LayoutParams layoutParams;
        isForAll = false;
        if(game.getCurrentTeam().getPlaygroundPosition() == game.getPlayground().getPlaygroundLength()-1){//final state

            finalState = true;

            TextView taskFinalHelpText = new TextView(getActivity());
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_TOP, "taskLayout".hashCode());
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

            taskFinalHelpText.setTextSize(18);
            taskFinalHelpText.setId("taskFinalHelpText".hashCode());
            taskFinalHelpText.setText("Other teams will choose your task.");
            taskFinalHelpText.setTypeface(null, Typeface.BOLD);
            taskFinalHelpText.setLayoutParams(layoutParams);

            taskLayout.addView(taskFinalHelpText);

            for (int i=0; i < finalTasks.length; i++){

                ImageView taskImage = new ImageView(getActivity());
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i > 0){
                    layoutParams.addRule(RelativeLayout.BELOW, "taskImage".hashCode() + (i-1)*10);
                }else{//==0
                    layoutParams.addRule(RelativeLayout.BELOW, "taskFinalHelpText".hashCode());
                }
                layoutParams.height = 35;
                layoutParams.width = 35;
                layoutParams.topMargin = 10;
                layoutParams.leftMargin = 10;
                taskImage.setLayoutParams(layoutParams);
                //taskImage.setId(currentTask.getTaskType().getImageLocation());
                switch (i%3){
                    case 0: taskImage.setImageResource(R.drawable.drawing);break;
                    case 1: taskImage.setImageResource(R.drawable.speaking);break;
                    case 2: taskImage.setImageResource(R.drawable.pantomime);break;
                }
                taskImage.setId("taskImage".hashCode() + i*10);
                taskLayout.addView(taskImage);

                TextView taskText = new TextView(getActivity());
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.RIGHT_OF, "taskImage".hashCode() + i*10);
                layoutParams.addRule(RelativeLayout.ALIGN_TOP, "taskImage".hashCode() + i*10);
                layoutParams.leftMargin = 10;
                layoutParams.topMargin = 5;
                taskText.setTextSize(16);
                taskText.setId("taskText".hashCode()+ i*20);
                taskText.setText(finalTasks[i].getName());
                taskText.setLayoutParams(layoutParams);

                taskLayout.addView(taskText);
            }

        }else{
            finalState = false;

            if (currentTask.getPoints() == 6){//FOR ALL players
                isForAll = true;
                TextView taskForAllText = new TextView(getActivity());
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_TOP, "taskLayout".hashCode());
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

                taskForAllText.setTextSize(18);
                taskForAllText.setId("taskForAllText".hashCode());
                taskForAllText.setText("!!! TASK IS FOR ALL PLAYERS !!!");
                taskForAllText.setTypeface(null, Typeface.BOLD);
                taskForAllText.setLayoutParams(layoutParams);

                taskLayout.addView(taskForAllText);

            }
            ImageView taskImage = new ImageView(getActivity());
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.height = 150;
            layoutParams.width = 150;
            layoutParams.leftMargin = 20;
            taskImage.setLayoutParams(layoutParams);
            //taskImage.setId(currentTask.getTaskType().getImageLocation());
            switch (currentTask.getTaskType()){
                case DRAWING: taskImage.setImageResource(R.drawable.drawing);break;
                case SPEAKING: taskImage.setImageResource(R.drawable.speaking);break;
                case PANTOMIME: taskImage.setImageResource(R.drawable.pantomime);break;
            }
            taskImage.setId("taskImage".hashCode());
            taskLayout.addView(taskImage);

            TextView taskTypeText = new TextView(getActivity());
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, "taskImage".hashCode());
            layoutParams.addRule(RelativeLayout.ALIGN_TOP, "taskImage".hashCode());
            layoutParams.leftMargin = 10;
            layoutParams.topMargin = 20;
            taskTypeText.setTextSize(18);
            taskTypeText.setId("taskTypeText".hashCode());
            taskTypeText.setText(currentTask.getTaskType().toString() + " ("+ currentTask.getPoints() + ")"+':');
            taskTypeText.setTypeface(null, Typeface.BOLD);
            taskTypeText.setLayoutParams(layoutParams);

            taskLayout.addView(taskTypeText);


            TextView taskText = new TextView(getActivity());
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, "taskImage".hashCode());
            layoutParams.addRule(RelativeLayout.BELOW, "taskTypeText".hashCode());
            layoutParams.leftMargin = 10;
            layoutParams.topMargin = 10;
            taskText.setTextSize(16);
            taskText.setId("taskText".hashCode());
            taskText.setText(currentTask.getName().toString());
            taskText.setLayoutParams(layoutParams);

            taskLayout.addView(taskText);

            /*
            maybe only at first time ??
             */
            TextView taskHelpText = new TextView(getActivity());
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, "taskLayout".hashCode());
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            taskHelpText.setId("taskHelpText".hashCode());
            taskHelpText.setText("Tap to area to hide task card");
            taskHelpText.setTextColor(Color.GRAY);
            taskHelpText.setLayoutParams(layoutParams);

            taskLayout.addView(taskHelpText);
        }




    }


    private void showWinner (String team) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("WINNER");
        builder.setMessage("Team " + team + " won the game! Congratulations");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Handle Ok
                Intent intent = new Intent(getActivity(), StartPageActivity.class); intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(intent);

            }
        });
        builder.create();
        builder.show();

    }

    private void winDialog() {
        final String[] items = new String[game.getTeams().length];
        for (int i=0; i < items.length;i++){
            items[i] = game.getTeams()[i].getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Who won this task?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(StartPageActivity.this,items[i],Toast.LENGTH_SHORT).show();
                game.moveTeam(game.getTeams()[i],currentTask.getPoints());
                activity.updatePlaygroundFragment();
                game.nextTeam();
                createNewView();
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }
}
