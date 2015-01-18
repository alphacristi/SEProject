package tcp_connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by ADY on 11.01.2015.
 */
public class ServerManagement implements Runnable {
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
    ServerManagement(Socket s){
        this.s=s;
    }
    @Override
    public void run() {
        int len;


            try {
                din=new DataInputStream(s.getInputStream());
                dout=new DataOutputStream(s.getOutputStream());
                byte[] dataReceived;
                while(true) {
                    len = din.readInt();
                     dataReceived = new byte[len];
                    if (len > 0) {
                        din.readFully(dataReceived);
                        dout.writeInt(len);
                        dout.write(dataReceived,0,len);
                        dout.flush();
                    }
                }

            } catch (IOException e) {

                e.printStackTrace();

            }

    }
}
