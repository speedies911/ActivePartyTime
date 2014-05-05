package com.app.activepartytime.core.network.wifi;

import com.app.activepartytime.core.game.Team;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by Dave on 1.4.14.
 */
public class Connector extends Thread {

    private int numberOfTeams;
    private Server server;

    private ObjectInputStream ois;

    public Connector(int numberOfTeams, Server server) {
        this.numberOfTeams = numberOfTeams;
        this.server = server;
    }

    @Override
    public void run() {
        System.out.println("Searching players starts ...");
        try {
            for (short i = 1; i <= 1; i++) {
                System.out.println("_____________ BEFORE ACCEPT _______________");
                Socket socket = server.getServer().accept();

                ois = new ObjectInputStream(socket.getInputStream());

                while(true) {
                    String textpico = (String)ois.readObject();
                    System.out.println(textpico);
                    if (textpico.length() > 1000000) break;
                }
                //Team team = new Team(i);

                /*player.sendMessage(i);
                String name = (String)player.getOis().readObject();

                player.setNickname(name);

                server.getPlayers().add(player);

                System.out.println("Player #" + i + " " + name + " connected from " + socket.getInetAddress());

                player.sendMessage(Boolean.TRUE);*/
                System.out.println("Team #" + i + " connected from " + socket.getInetAddress());
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Error while connecting!");
            ex.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException while connecting!");
            e.printStackTrace();
        }

        this.server.setReady(true);

        System.out.println("All players connected.");
        System.out.println("Game starts.");
    }

}
