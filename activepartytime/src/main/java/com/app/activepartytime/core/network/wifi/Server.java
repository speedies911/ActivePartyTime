package com.app.activepartytime.core.network.wifi;

import com.app.activepartytime.core.game.Team;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave on 1.4.14.
 */
public class Server {

    private ServerSocket server;
    private int numberOfTeams;
    private List<Team> teams;

    public static final int PORT = 5750;

    private String nickname;
    private boolean ready;

    public Server(int numberOfPlayers, String nickname) {
        try {
            this.server = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Port is not available.");
            System.exit(-1);
        }
        this.numberOfTeams = numberOfPlayers;
        this.teams = new ArrayList<Team>();

        this.nickname = nickname;
        this.ready = false;

        System.out.println("Server is running...");
    }

    public void disconnect() {
        try {
            this.server.close();
        } catch (IOException ex) {
            System.out.println("Disconnecting error");
        }
    }

    public boolean isRunning() {
        return !this.server.isClosed();
    }

    public void connectPlayers() {
        new Connector(numberOfTeams, this).start();
    }

    public List<Socket> getSockets() {
        List<Socket> out = new ArrayList<Socket>();
        for (Team team : teams) {
            out.add(team.getSocket());
        }
        return out;
    }

    public ServerSocket getServer() {
        return server;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

}
