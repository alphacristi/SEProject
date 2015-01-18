package tcp_connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by ADY on 09.01.2015.
 */
public class ReceivingMessage implements Runnable {

     private DataInputStream receivingConnection;
     byte [] dataReceived;

    ReceivingMessage(DataInputStream receivingConnection)
    {
        this.receivingConnection=receivingConnection;
        this.dataReceived=null;
    }

    @Override
    public void run() {
      int len;
        while(true) {
            try {
                len = receivingConnection.readInt();
                dataReceived = new byte[len];
                if (len > 0) {
                    receivingConnection.readFully(dataReceived);
                }

            } catch (IOException e) {

                e.printStackTrace();

            }
        }

    }
}
