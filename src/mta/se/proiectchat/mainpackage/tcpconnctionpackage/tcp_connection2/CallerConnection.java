package tcp_connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by ADY on 07.01.2015.
 */
public class CallerConnection implements ISocketConnection{
    private Socket socketUsed;
    private String ip;
    private int port;
    @Override
    public void Open() {
        try {
            socketUsed=new Socket(ip,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] Read() {

        try {
            DataInputStream streamForReceiving=new DataInputStream(socketUsed.getInputStream());
            ReceivingMessage messageReceived=new ReceivingMessage(streamForReceiving);
            Thread t1=new Thread(messageReceived);
            t1.start();
            byte[] message;
            while(true){
                if(messageReceived.dataReceived!=null)
                {
                    message=new byte[messageReceived.dataReceived.length];
                    message=messageReceived.dataReceived;
                    messageReceived.dataReceived=null;
                    return message;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;
    }

    @Override
    public void Write(byte[] information) {

        try {
            DataOutputStream streamForSending=new DataOutputStream(socketUsed.getOutputStream());
            if (information.length < 0)
                throw new IllegalArgumentException("Negative length not allowed");
            streamForSending.writeInt(information.length);
            if(information.length>0){
                streamForSending.write(information,0,information.length);
                streamForSending.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void Close() {
        try {
            socketUsed.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    CallerConnection(String ip,int port ){
        this.ip=ip;
        this.port=port;
    }
}
