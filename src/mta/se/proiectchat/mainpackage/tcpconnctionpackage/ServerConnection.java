package mta.se.proiectchat.mainpackage.tcpconnctionpackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ADY on 09.01.2015.
 */
public class ServerConnection {
    private int port;
    ServerSocket serverSocketUsed;
    Socket clientSocket1, clientSocket2;

    ServerConnection(int port) {
        this.port = port;
        this.serverSocketUsed = null;
        this.clientSocket1 = null;
        this.clientSocket2 = null;
    }

    public void Open() {
        try {
            serverSocketUsed = new ServerSocket(this.port);
            clientSocket1 = serverSocketUsed.accept();
            clientSocket2 = serverSocketUsed.accept();
            ServerManagement send_receiveMessage = new ServerManagement(clientSocket1, clientSocket2);
            Thread t = new Thread(send_receiveMessage);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void Close() {

        try {
            serverSocketUsed.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
