package com.app.activepartytime.core.network.wifi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by Simo on 5.5.14.
 */
public class HostReceiver extends Thread {

    private Socket s;
    private ObjectInputStream ois;
    private boolean isConnected;

    public HostReceiver(Socket s) {
        this.s = s;
        try {
            ois = new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        receiveMesage();
    }
    private void receiveMesage()  {
        try {
            while (isConnected){


                    System.out.println("server prijima: " + (String)ois.readObject());



            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
