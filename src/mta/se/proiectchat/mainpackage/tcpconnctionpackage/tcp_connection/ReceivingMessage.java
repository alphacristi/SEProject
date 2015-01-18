package tcp_connection;

import java.io.DataInputStream;
import java.io.IOException;

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

            try {

              //  while(true) {
                    len = receivingConnection.readInt();
                    dataReceived = new byte[len];
               //    System.out.println(len);
                    if (len > 0) {
                        receivingConnection.readFully(dataReceived);
                   //     System.out.println(dataReceived);
                        len=0;
                    }
             //   }
            } catch (IOException e) {

                e.printStackTrace();

            }


    }
}
