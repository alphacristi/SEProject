package mta.se.proiectchat.mainpackage;

import mta.se.proiectchat.mainpackage.audiopackage.Playback;
import mta.se.proiectchat.mainpackage.audiopackage.Records;
import mta.se.proiectchat.mainpackage.tcpconnctionpackage.CallerConnection;

/**
 * This class which conaints main function - the entry point in program
 * Created by Cristian on 1/18/2015.
 */
public class Main {
    /**
     * This is the entry point in program
     */
    public static void main(String arvs[]) {

        try {
            Records client = new Records();
            byte[] messageToSend = readMessage(client);

            // sendData(messageToSend);

            //   byte[] messageReceived=receiveData();

            Playback client2 = new Playback();
            playMessage(client2, messageToSend);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static byte[] readMessage(Records client) {
        try {
            client.openPickupLine();
            byte[] dataCaptured = client.captureAudioData();
            return dataCaptured;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void playMessage(Playback client, byte[] dataToPlay) {
        try {
            client.openLineForPlayback();
            client.play(dataToPlay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void sendData(byte[] dataToSend) {
        try {
            CallerConnection ex = new CallerConnection("127.0.0.1", 8012);
            ex.Open();
            ex.Write(dataToSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] receiveData() {

        try {
            CallerConnection ex = new CallerConnection("127.0.0.1", 8012);
            ex.Open();
            byte[] dataReceived = ex.Read();
            return dataReceived;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
