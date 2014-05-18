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
import android.widget.ImageButton;
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


import com.app.activepartytime.core.data.tasks.TaskDB;
import com.app.activepartytime.core.data.tasks.TaskDatabaseHandler;
import com.app.activepartytime.core.game.SimoViewPager;

import java.util.List;


/**
 * Created by Dave on 8.4.14.
 */
public class GameMoveActivity extends FragmentActivity {

    private static final int NUM_PAGES = 2;
    public ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private Team[] teams;

    private Game game;

    private GameInfoFragment gameInfoFragment;
    private GamePlaygroundFragment gamePlaygroundFragment;

    public static final int LENGTH = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_move);

        Object[] tmp = (Object[])getIntent().getSerializableExtra("teamList");
        teams = new Team[tmp.length];


        for (int i = 0; i < tmp.length; i++) {
            teams[i] = (Team)tmp[i];
        }

        game = new Game(LENGTH, teams);
        //getActionBar().hide();

        gameInfoFragment = new GameInfoFragment(game, this);

        gamePlaygroundFragment = new GamePlaygroundFragment(game,this);

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

        actionBar.addTab(actionBar.newTab().setText("Playground").setTabListener(tabListener));

        game.startGame();
    }



    public void updatePlaygroundFragment() {

        gamePlaygroundFragment.updateState();


        mPager.setCurrentItem(1);
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

            if (position == 0) {
                return gameInfoFragment;
            } else {
                return gamePlaygroundFragment;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
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
