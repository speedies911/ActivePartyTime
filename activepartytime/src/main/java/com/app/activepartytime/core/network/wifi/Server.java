package com.app.activepartytime.core.network.wifi;

import android.widget.Button;

import com.app.activepartytime.core.game.Team;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave on 1.4.14.
 */
public class Server {

    private final int PORT;
    private ServerSocket server;
    private Socket[] sockets;

    private final int NUMBER_OF_TEAMS;

    private OutputStream os;
    private InputStreamProvider isp;

    public Server(int numberOfTeams, int port) throws IOException {
        this.PORT = port;
        this.server = new ServerSocket(port);
        this.NUMBER_OF_TEAMS = numberOfTeams;
        this.sockets = new Socket[numberOfTeams];
        System.out.println("Server is ready ...");
    }

    public int getPORT() {
        return PORT;
    }

    public OutputStream getOs() {
        return os;
    }

    public void run() {
        connectTeams();

        try {
            initThreads();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void initThreads() throws IOException {
        os = new OutputStream();
        isp = new InputStreamProvider();
    }

    private void connectTeams() {
        System.out.println("Waiting for teams ...");
        for (int i = 0; i < NUMBER_OF_TEAMS; i++) {
            try {
                Socket s = server.accept();
                sockets[i] = s;
                System.out.println("Team #" + i + " connected from " + s.getInetAddress());

            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
        }
        System.out.println("All teams connected");
    }


    /**
     *
     */
    public class OutputStream {

        private ObjectOutputStream[] dos;

        public OutputStream () {
            dos = new ObjectOutputStream[NUMBER_OF_TEAMS];
            init();
        }

        private void init() {
            for (int i = 0; i < NUMBER_OF_TEAMS; i++) {
                try {
                    dos[i] = new ObjectOutputStream(sockets[i].getOutputStream());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void sendMessageTo(Object what, int team) {
            try {
                dos[team].writeObject(what);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        public void sendMessageToAll(Object what) {
            for (int i = 0; i < sockets.length; i++) {
                try {
                    dos[i].writeObject(what);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }


    /**
     *
     */
    private class InputStreamProvider {

        private InputStreamThread[] threads;

        private InputStreamProvider() throws IOException {
            this.threads = new InputStreamThread[NUMBER_OF_TEAMS];
            init();
            startThreads();
        }

        private void init() throws IOException {
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new InputStreamThread(sockets[i]);
            }
        }

        private void startThreads() {
            for (int i = 0; i < threads.length; i++) {
                threads[i].start();
            }
        }

    }

    /**
     *
     */
    private class InputStreamThread extends Thread {

        private ObjectInputStream ois;
        private Socket socket;

        private InputStreamThread(Socket socket) throws IOException {
            this.socket = socket;
        }

        @Override
        public void run() {
            Object msg = null;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            while(true) {
                try {
                    msg = ois.readObject();
                    if (msg instanceof String) {
                        String text = (String)msg;
                        System.out.println("Incoming String: " + text);
                    }
                    System.out.println("Msg: " + msg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }

}
