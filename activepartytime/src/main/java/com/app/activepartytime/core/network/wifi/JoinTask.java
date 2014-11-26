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
public class JoinTask {

    private Socket socket;
    private String IP;
    private int PORT;

    private OutputStream os;
    private InputListener il;

    public JoinTask (String IP, int PORT) {
        this.IP = IP;
        this.PORT = PORT;
    }

    public OutputStream getOs() {
        return os;
    }



    public boolean run() {

        try {

            socket = new Socket(IP, PORT);
            System.out.println("Connected to server!");

            os = new OutputStream();
            il = new InputListener();
            il.start();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public class OutputStream {

        private ObjectOutputStream dos;

        public OutputStream() {
            try {
                dos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void sendMessage(Object what) {
            try {
                dos.writeObject(what);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public class InputListener extends Thread {

        private ObjectInputStream ois;

        public InputListener() {
            try {
                this.ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(true) {
                try {
                    String msg = (String)ois.readObject();
                    System.out.println("Message: " + msg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }


}
