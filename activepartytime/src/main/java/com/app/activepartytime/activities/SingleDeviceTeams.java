package com.app.activepartytime.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.app.activepartytime.R;

public class SingleDeviceTeams extends Activity {

    final static int MIN_TEAMS = 2;
    final static int MAX_TEAMS = 4;

    private Button minus;
    private Button plus;
    private TextView numberOfTeams;
    private int teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_device_teams);

        teams = MIN_TEAMS;

        minus = (Button) findViewById(R.id.buttonMinus);
        plus = (Button) findViewById(R.id.buttonPlus);
        numberOfTeams = (TextView) findViewById(R.id.numberOfTeams);
        numberOfTeams.setText(String.valueOf(MIN_TEAMS));

        findViewById(R.id.LayoutTeam3).setVisibility(LinearLayout.GONE);
        findViewById(R.id.LayoutTeam4).setVisibility(LinearLayout.GONE);
        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }


    public void minus (View view) {
        if (teams != MIN_TEAMS) {
            setVisibleTeamsOff(teams);
            teams--;
            numberOfTeams.setText(String.valueOf(teams));
        }
    }

    public void plus (View view) {
        if (teams != MAX_TEAMS) {
            teams++;
            setVisibleTeamsOn(teams);
            numberOfTeams.setText(String.valueOf(teams));
        }
    }

    private void setVisibleTeamsOff(int id) {
        if (id == 3) {
            findViewById(R.id.LayoutTeam3).setVisibility(LinearLayout.GONE);
        } else {
            findViewById(R.id.LayoutTeam4).setVisibility(LinearLayout.GONE);
        }
    }

    private void setVisibleTeamsOn(int id) {
        if (id == 3) {
            findViewById(R.id.LayoutTeam3).setVisibility(LinearLayout.VISIBLE);
        } else {
            findViewById(R.id.LayoutTeam4).setVisibility(LinearLayout.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_device_teams, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_single_device_teams, container, false);
            return rootView;
        }
    }*/

}
