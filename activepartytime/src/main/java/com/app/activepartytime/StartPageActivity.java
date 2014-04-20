package com.app.activepartytime;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.activepartytime.activities.SettingsActivity;
import com.app.activepartytime.activities.SingleDeviceTeams;
import com.app.activepartytime.activities.WiFiTeams;
import com.app.activepartytime.core.data.tasks.TaskDatabaseHandler;

public class StartPageActivity extends Activity {

    public static final String MY_PREFERENCES = "prefs" ;
    public static final String DB_INIT = "dbKey";

    private SharedPreferences sharedPreferences;
    private TaskDatabaseHandler database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        if (!sharedPreferences.contains(DB_INIT)) {
            // TODO - popup status window
            System.out.println("First instance - filling database");

            initDatabase();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(DB_INIT, true);
            editor.commit();
        }
    }

    private void initDatabase() {
        database = new TaskDatabaseHandler(this);
        database.fillDatabase();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_page, menu);
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

    public void quit (View view) {
        System.exit(0);
    }

    public void play (View view) {
        playDialog();
    }


    public void settings(View view){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
    }

    public void oneDevice() {
        Intent intent = new Intent(this, SingleDeviceTeams.class);
        startActivity(intent);
    }

    public void wifi() {
        Intent intent = new Intent(this, WiFiTeams.class);
        startActivity(intent);
    }

    private void playDialog() {
        final String[] items = {"On one device","Bluetooth","WiFi"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            
            builder.setTitle(R.string.dialogStartPagePlay);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Toast.makeText(StartPageActivity.this,items[i],Toast.LENGTH_SHORT).show();
                    switch (i) {
                        case 0: oneDevice();
                            break;
                        case 2: wifi();
                            break;
                    }
                }
            });
            builder.setNegativeButton("Cancel",null);
            builder.show();
        }

}
