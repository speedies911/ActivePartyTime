package com.app.activepartytime.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.app.activepartytime.R;
import com.app.activepartytime.core.game.Team;

import org.apache.http.util.VersionInfo;

import java.util.ArrayList;
import java.util.List;

public class SingleDeviceTeams extends Activity {

    final static int MIN_TEAMS = 2;
    final static int MAX_TEAMS = 4;

    private Button minus;
    private Button plus;
    private TextView numberOfTeams;
    private EditText[] teamNames;

    private LinearLayout ll3;
    private LinearLayout ll4;

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

        ll3 = (LinearLayout)findViewById(R.id.LayoutTeam3);
        ll3.setVisibility(LinearLayout.GONE);

        ll4 = (LinearLayout)findViewById(R.id.LayoutTeam4);
        ll4.setVisibility(LinearLayout.GONE);

        teamNames = new EditText[4];
        teamNames[0] = (EditText)findViewById(R.id.NameTeam1);
        teamNames[1] = (EditText)findViewById(R.id.NameTeam2);
        teamNames[2] = (EditText)findViewById(R.id.NameTeam3);
        teamNames[3] = (EditText)findViewById(R.id.NameTeam4);
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
            ll3.setVisibility(LinearLayout.GONE);
        } else {
            ll4.setVisibility(LinearLayout.GONE);
        }
    }

    private void setVisibleTeamsOn(int id) {
        if (id == 3) {
            ll3.setVisibility(LinearLayout.VISIBLE);
        } else {
            ll4.setVisibility(LinearLayout.VISIBLE);
        }
    }

    /**
     * TODO alert dialog on missing team name
     * @param view
     */
    public void confirm (View view) {
        boolean all = true;
        boolean different = true;
        String missing = "";
        String same = "";
        for (int i = 0; i < teams; i++) {
            if (teamNames[i].getText().length() == 0) {
                all = false;
                missing += "Team " + (i+1) + "\n";
            } else {
                for (int j = i+1; j < teams; j++) {
                    if (teamNames[i].getText().toString()
                            .equals(teamNames[j].getText().toString())) {
                        different = false;
                        same += "Team " + (i+1) + " and Team " + (j+1)+"\n";
                    }
                }
            }
        }
        System.out.println(all + " " + different);
        if (all && different) {
            Team[] teamList = new Team[teams];

            for (short i = 0; i < teams; i++) {
                teamList[i] = new Team(i,teamNames[i].getText().toString(),Color.RED);
            }
            Intent intent = new Intent(this, GameMoveActivity.class);

            // TODO - send (save) team list to Game Activity
            intent.putExtra("teamList",teamList);
            startActivity(intent);
        } else {
            showTeamNameMissingDialog(missing, same, all, different);
        }
    }

    private void showTeamNameMissingDialog (String missing, String same, boolean all, boolean different) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialogSingleDeviceActivityError);

        if (!all && !different) {
            builder.setMessage("Missing names at:\n" + missing + "\nEquals teams:\n" + same);
        } else if (!all) {
            builder.setMessage("Missing names at:\n" + missing);
        } else {
            builder.setMessage("Equals teams:\n" + same);
        }

        builder.setPositiveButton("Back", null);
        builder.show();
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

}
