package mta.se.proiectchat.mainpackage.tcpconnctionpackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by ADY on 11.01.2015.
 */
public class ServerManagement implements Runnable {
    Socket s, d;
    DataInputStream din;
    DataOutputStream dout;

    ServerManagement(Socket source, Socket destination) {
        this.s = source;
        this.d = destination;
    }

    @Override
    public void run() {
        int len;


        try {
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(d.getOutputStream());
            byte[] dataReceived;


            while (true) {
                len = din.readInt();
                dataReceived = new byte[len];

                if (len > 0) {

                    din.readFully(dataReceived);
                    dout.writeInt(len);
                    dout.write(dataReceived, 0, len);
                    //    System.out.println(new String(dataReceived));
                    //    System.out.println(len);
                    dout.flush();
                    dataReceived = null;
                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
