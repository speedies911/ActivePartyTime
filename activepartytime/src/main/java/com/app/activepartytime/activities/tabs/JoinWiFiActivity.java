package com.app.activepartytime.activities.tabs;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
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
import android.widget.EditText;
import android.widget.TextView;

import com.app.activepartytime.R;
import com.app.activepartytime.core.network.wifi.JoinTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.NoSuchElementException;

public class JoinWiFiActivity extends Activity {

    EditText IPaddress;
    WifiManager mainWifi;
    private JoinTask joinTask;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_wifi);

        IPaddress = (EditText) findViewById(R.id.textIPaddress);
        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if(mainWifi.isWifiEnabled()) {
            WifiInfo wifiInfo = mainWifi.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            System.out.println("IP:" + ip);
            String ipAddress = Formatter.formatIpAddress(ip);

            IPaddress.setText(ipAddress);
        } else {
            IPaddress.setText("Nemas zaplou WiFi PICO");
        }

        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }

    public void join(View view) {
        joinTask = new JoinTask(IPaddress.getText().toString(), 5750);
        joinTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.join_wi_fi, menu);
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

    /*class WifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            sb = new StringBuilder();
            wifiList = mainWifi.getScanResults();
            for(int i = 0; i < wifiList.size(); i++){
                sb.append(new Integer(i+1).toString() + ".");
                sb.append((wifiList.get(i)).toString());
                sb.append("\\n\\n");
            }
            //mainText.setText(sb);
            System.out.println(sb.toString());
        }
    }*/


    /**
     * A placeholder fragment containing a simple view.
     */
    /*public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_join_wifi, container, false);
            return rootView;
        }
    }*/

}
