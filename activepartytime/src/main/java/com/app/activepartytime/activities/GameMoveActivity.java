package com.app.activepartytime.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.activepartytime.R;
import com.app.activepartytime.StartPageActivity;
import com.app.activepartytime.activities.fragments.GameInfoFragment;
import com.app.activepartytime.activities.fragments.GamePlaygroundFragment;
import com.app.activepartytime.core.data.tasks.TaskDB;
import com.app.activepartytime.core.data.tasks.TaskDatabaseHandler;
import com.app.activepartytime.core.game.Game;
import com.app.activepartytime.core.game.Playground;
import com.app.activepartytime.core.game.Team;


/**
 * Created by Dave on 8.4.14.
 */
public class GameMoveActivity extends FragmentActivity {

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private TaskDatabaseHandler database;

    private int side;
    private Button card;

    private TaskDB currentTask;
    private Team currentTeam;
    private Team[] teams;

    private Game game;

    public static final int LENGTH = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_move);

        //getActionBar().hide();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        // Specify that tabs should be displayed in the action bar.
        ActionBar actionBar = getActionBar();

        actionBar.setDisplayOptions(0);



        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new SimoTabListener();

        mPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

        // Add 2 tabs, specifying the tab's text and TabListener

        actionBar.addTab(actionBar.newTab().setText("Card").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Time").setTabListener(tabListener));


        database = new TaskDatabaseHandler(this);

        side = 0;
        currentTask = null;

        Object[] tmp = (Object[])getIntent().getSerializableExtra("teamList");
        teams = new Team[tmp.length];


        for (int i = 0; i < tmp.length; i++) {
            teams[i] = (Team)tmp[i];
        }

        game = new Game(LENGTH, teams);
    }

    @Override
    public void onBackPressed() {
        /*if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }*/

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position) {
                case 0:
                    fragment = new GameInfoFragment();
                    break;
                case 1:
                    fragment = new GamePlaygroundFragment(game.getPlayground(),teams);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void generateFunction(View view){
        Button generate = (Button)findViewById(R.id.generateButton);
        card = (Button)findViewById(R.id.taskCard);

        generate.setVisibility(RelativeLayout.GONE);
        generate.setEnabled(false);
        card.setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.stopWatch).setVisibility(RelativeLayout.VISIBLE);
        findViewById(R.id.startStopButton).setVisibility(RelativeLayout.VISIBLE);

        currentTask = database.getRandomTask();

        card.setText(currentTask.getName() + " (" + currentTask.getPoints() + ")");
        card.setEnabled(true);
        timeInit(MAX_TIME_IN_MS);

    }

    public void flipCard(View view){
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

    /*
    * Simo 2014 04 20 I have lost Petr's code that repairs time problem
     */

    private void timeInit(long maxTime){
        startStopButton = (Button)findViewById(R.id.startStopButton);
        timerDisplay = (TextView)findViewById(R.id.stopWatch);
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
    /*
    Petr Code 2014 04 17
     */
    private CountDownTimer timer;
    private TextView timerDisplay;
    private boolean timerIsRunning;
    private long timePause;

    private Button startStopButton;

    private static final long MAX_TIME_IN_MS = 20 * 1000;//2 * 60 * 1000;
    private static final long COUNTDOWN_INTERVAL_IN_MS = 1000;
    public void startStop(View view) {
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


    /*
        Scroll settings list
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_move, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_quit) {
            Intent intent = new Intent(this, StartPageActivity.class); intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //tabListener 2014 04 17
    public class SimoTabListener implements ActionBar.TabListener {

        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            mPager.setCurrentItem(tab.getPosition());
        }

        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            // hide the given tab
        }

        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            // probably ignore this event
        }




    }




}