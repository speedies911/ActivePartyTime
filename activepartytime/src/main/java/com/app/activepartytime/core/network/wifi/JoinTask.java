package com.app.activepartytime.core.network.wifi;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

/**
 * Created by Dave on 5.5.14.
 */
public class JoinTask extends AsyncTask<Void,Void,Void> {

    private Socket client;
    private String IP;
    private int PORT;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public JoinTask(String IP, int PORT) {
        this.client = null;
        this.IP = IP;
        this.PORT = PORT;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        connectToServer(IP);
        return null;
    }

    private boolean connectToServer(String ip) {
        boolean found = false, foundIP = false;

        System.out.println("JOIN IP PICO: " + ip);

        while (!found) {
            try {
                while (!foundIP) {
                    //StringTokenizer token = new StringTokenizer(ip, ":");
                    try {
                        client = new Socket(ip, 5750);
                        foundIP = true;
                        System.out.println("______ CONNECTED _____");

                        System.out.println("text poslan  1111111112222222");
                        this.oos = new ObjectOutputStream(client.getOutputStream());
                        System.out.println("text poslan  11111111133333333");
                     //   this.ois = new ObjectInputStream(client.getInputStream());
                        System.out.println("text poslan  111111111");
                        oos.writeObject("DEBILE, TADY TUNTA");
                        System.out.println("text poslan");
                        oos.close();

                    } catch (NoSuchElementException e) {
                        //wrongIP();
                        System.out.println("WRONG IP");
                    }
                    found = true;
                }
            } catch (UnknownHostException ex) {
                //noServerPane();
                System.out.println("Unknown HOST");
            } catch (IOException ex) {
                //noServerPane();
                System.out.println("IOE");
            }
        }

        return found;
    }

    private void sendMessage() {

    }


}
