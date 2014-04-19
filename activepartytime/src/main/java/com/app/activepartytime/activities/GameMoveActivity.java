package com.app.activepartytime.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;

import com.app.activepartytime.R;
import com.app.activepartytime.activities.fragments.GameInfoFragment;
import com.app.activepartytime.activities.fragments.GamePlaygroundFragment;
import com.app.activepartytime.core.data.tasks.TaskDB;
import com.app.activepartytime.core.data.tasks.TaskDatabaseHandler;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_move);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        database = new TaskDatabaseHandler(this);

        side = 0;
        currentTask = null;
    }

    @Override
    public void onBackPressed() {
        /*if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }*/
        super.onBackPressed();
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
                    fragment = new GamePlaygroundFragment();
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
        card = (Button)findViewById(R.id.card);

        generate.setVisibility(RelativeLayout.INVISIBLE);
        generate.setEnabled(false);
        card.setVisibility(RelativeLayout.VISIBLE);

        currentTask = database.getRandomTask();

        card.setText(currentTask.getName() + " (" + currentTask.getPoints() + ")");
        card.setEnabled(true);
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

}
