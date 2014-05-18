package com.app.activepartytime.core.network.wifi;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Created by Dave on 5.5.14.
 */
public class JoinTask extends AsyncTask<Void,Void,Void> {

    private Socket client;
    private String IP;
    private int PORT;
    private String teamName;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Queue<Object> messageToBeSent;
    private boolean isConnected;
    private JoinReceiver jr;

    public JoinTask(String IP, int PORT, String teamName ,Button b) {
        this.client = null;
        this.IP = IP;
        this.PORT = PORT;
        this.teamName = teamName;
        isConnected = false;
        messageToBeSent = new LinkedList<Object>();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posliPico();
            }
        });

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
                        isConnected = true;
                        System.out.println("______ CONNECTED _____");

                      //  System.out.println("text poslan  1111111112222222");
                        oos = new ObjectOutputStream(client.getOutputStream());
                       // System.out.println("text poslan  11111111133333333");

                       // System.out.println("text poslan  111111111");
                       // sendMessage("DEBILE, TADY TUNTA");
                        //System.out.println("text poslan");
                        jr = new JoinReceiver(client);
                        jr.run();
                        sendMessage(teamName);

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

    private void sendMessage(Object o)  {
       messageToBeSent.add(o);

        while(!messageToBeSent.isEmpty()){ // send all Messages
            System.out.println("client posila: " + messageToBeSent.peek());
            try {
                oos.writeObject(messageToBeSent.poll());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    int kuk;
    public void posliPico(){
        sendMessage("client posila kuk cislo pico " + kuk);
        kuk++;

    }


}
