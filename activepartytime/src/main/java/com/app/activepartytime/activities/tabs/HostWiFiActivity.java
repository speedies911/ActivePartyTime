package com.app.activepartytime.activities.tabs;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import com.app.activepartytime.R;
import com.app.activepartytime.core.network.wifi.Server;

public class HostWiFiActivity extends Activity {

    private TextView IPaddressText;
    private Button startHostButton;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_wifi);

        IPaddressText = (TextView)findViewById(R.id.textIPaddress);
        startHostButton = (Button)findViewById(R.id.buttonStartHost);

        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        if(wifiMgr.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            String ipAddress = Formatter.formatIpAddress(ip);

            IPaddressText.setText(ipAddress);
        } else {
            IPaddressText.setText("Nemas zaplou WiFi PICO");
        }


        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }

    public void startHost(View view) {
        server = new Server(2,"ACTIVITY JAK CIP", (Button) findViewById(R.id.buttonPICOposli));
        server.connectPlayers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.host_wi_fi, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_host_wifi, container, false);
            return rootView;
        }
    }*/

}
