package com.app.activepartytime.core.network.wifi;

import android.view.View;
import android.widget.Button;

import com.app.activepartytime.core.game.Team;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Dave on 1.4.14.
 */
public class Connector extends Thread {

    private int numberOfTeams;
    private Server server;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Queue<Object> messageToBeSent;
    private boolean isConnected;
    private HostReceiver hr;

    public Connector(int numberOfTeams, Server server, Button b) {
        this.numberOfTeams = numberOfTeams;
        this.server = server;
        messageToBeSent = new LinkedList<Object>();
        isConnected = false;
        kuk = 1;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posliPico();
            }
        });
    }

    @Override
    public void run() {
        System.out.println("Searching players starts ...");
        try {
            for (short i = 1; i <= 1; i++) {
                System.out.println("_____________ BEFORE ACCEPT _______________");
                Socket socket = server.getServer().accept();
                isConnected = true;
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                sendMessage("pripojil se hrac");
                sendMessage("zprava 2");
               /* while(true) {
                    String textpico = (String)ois.readObject();
                    System.out.println("server rika: " + textpico);
                    if (textpico.length() > 1000000) break;
                }*/
                //Team team = new Team(i);

                /*player.sendMessage(i);
                String name = (String)player.getOis().readObject();

                player.setNickname(name);

                server.getPlayers().add(player);

                System.out.println("Player #" + i + " " + name + " connected from " + socket.getInetAddress());

                player.sendMessage(Boolean.TRUE);*/
                hr = new HostReceiver(socket);
                hr.run();
                System.out.println("Team #" + i + " connected from " + socket.getInetAddress());
            }
        } catch (IOException e) {
            System.err.println("IOException while connecting!");
            e.printStackTrace();
        }

        this.server.setReady(true);

        System.out.println("All players connected.");
        System.out.println("Game starts.");


    }


    private void sendMessage(Object o)  {
        messageToBeSent.add(o);
    }


    int kuk;
    private void posliPico(){
        sendMessage("server posila kuk cislo pico " + kuk);
        kuk++;

    }
}
