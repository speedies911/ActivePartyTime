package com.app.activepartytime.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TabHost;

import com.app.activepartytime.R;
import com.app.activepartytime.activities.tabs.HostWiFiActivity;
import com.app.activepartytime.activities.tabs.JoinWiFiActivity;

public class WiFiTeams extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_teams);

        TabHost tabHost = getTabHost();

        // Tab for HOST
        TabHost.TabSpec hostSpec = tabHost.newTabSpec("Host");
        hostSpec.setIndicator("HOST");
        Intent hostIntent = new Intent(this, HostWiFiActivity.class);
        hostSpec.setContent(hostIntent);

        // Tab for JOIN
        TabHost.TabSpec joinSpec = tabHost.newTabSpec("Join");
        joinSpec.setIndicator("JOIN");
        Intent joinIntent = new Intent(this, JoinWiFiActivity.class);
        joinSpec.setContent(joinIntent);

        // Adding all tabs to TabHost
        tabHost.addTab(hostSpec);
        tabHost.addTab(joinSpec);

        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wi_fi_teams, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_wifi_teams, container, false);
            return rootView;
        }
    }*/

}
