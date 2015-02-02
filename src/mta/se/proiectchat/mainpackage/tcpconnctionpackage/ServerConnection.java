package mta.se.proiectchat.mainpackage.tcpconnctionpackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is used for ServerSocket when waiting incoming call
 * Created by Cristian on 2/2/2015.
 */
public class ServerConnection implements ISocketConnection {

    private ServerSocket serverSocket;
    private Socket socket;
    private int port;

    public ServerConnection(int listeningPort) {

        this.port = listeningPort;
    }

    @Override
    public void Open() {
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();

        } catch (IOException e) {
            System.out.println("Connection not established !");
            System.exit(-1);
        }
    }

    @Override
    public byte[] Read() {
        int len;
        byte[] dataReceived = null;

        try {
            DataInputStream streamForReceiving = new DataInputStream(socket.getInputStream());
            len = streamForReceiving.readInt();
            dataReceived = new byte[len];
            if (len > 0) {
                streamForReceiving.readFully(dataReceived);
            }


        } catch (IOException e) {
            System.out.println("Error occurred in reading data from socket !");
            System.exit(-1);
        } finally {
            return dataReceived;
        }
    }

    @Override
    public void Write(byte[] information) {
        try {
            DataOutputStream streamForSending = new DataOutputStream(socket.getOutputStream());
            if (information.length < 0)
                throw new IllegalArgumentException("Negative length not allowed");
            streamForSending.writeInt(information.length);
            if (information.length > 0) {
                streamForSending.write(information, 0, information.length);
                streamForSending.flush();
            }
        } catch (IOException e) {
            System.out.println("Error occurred in writing data to socket !");
            System.exit(-1);
        }
    }

    @Override
    public void Close() {
        try {
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error occurred in closing ServerSocket and Socket !");
            System.exit(-1);
        }

    }

    @Override
    public String getRemoteIP() {
        return socket.getInetAddress().getHostAddress();
    }
}
