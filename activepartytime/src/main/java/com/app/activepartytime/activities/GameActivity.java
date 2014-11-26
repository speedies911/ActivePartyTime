package com.app.activepartytime.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextSwitcher;
import android.widget.TextView;



import com.app.activepartytime.R;
import com.app.activepartytime.core.data.tasks.TaskDatabaseHandler;
import com.app.activepartytime.core.game.Game;
import com.app.activepartytime.core.game.Playground;
import com.app.activepartytime.core.game.Team;

import java.util.Arrays;

public class GameActivity extends Activity {

    private Game game;
    private Team[] teams;
    private Playground playground;

    private static final int LENGTH = 20;

    private TaskDatabaseHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        database = new TaskDatabaseHandler(this);

        Object[] tmp = (Object[])getIntent().getSerializableExtra("teamList");
        teams = new Team[tmp.length];


        for (int i = 0; i < tmp.length; i++) {
            teams[i] = (Team)tmp[i];
        }

        //this.game = new Game(LENGTH, teams);
        //game.play();

        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }

    public void display (View view) {
        Switch switchDisplay = (Switch)findViewById(R.id.switchDisplay);
        TextView taskText = (TextView)findViewById(R.id.taskText);

        // TODO - images
        if (switchDisplay.isChecked()) {
            taskText.setBackgroundColor(Color.RED);
        } else {
            taskText.setBackgroundColor(Color.YELLOW);
        }
    }

    public void generate (View view) {
        Button generate = (Button)findViewById(R.id.generate);
        FrameLayout card = (FrameLayout) findViewById(R.id.Card1);
        generate.setEnabled(false);
        generate.setVisibility(RelativeLayout.GONE);
        card.setEnabled(true);
        card.setVisibility(RelativeLayout.VISIBLE);
        /*TextView taskText = (TextView)findViewById(R.id.taskText);
        taskText.setVisibility(RelativeLayout.VISIBLE);
        Switch switchDisplay = (Switch)findViewById(R.id.switchDisplay);
        switchDisplay.setVisibility(RelativeLayout.VISIBLE);*/
        //LinearLayout taskInformation = (LinearLayout)findViewById(R.id.taskInformation);
        //taskInformation.setVisibility(RelativeLayout.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
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
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    /*public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_game, container, false);
            return rootView;
        }
    }*/

}
