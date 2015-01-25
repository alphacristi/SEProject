package mta.se.proiectchat.mainpackage;

import mta.se.proiectchat.mainpackage.audiopackage.Records;

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
           byte[] messageToSend=readMessage(client);
       }
       catch (Exception e) {
           e.printStackTrace();
       }


    }


    public static byte[] readMessage(Records client){
        try {
            client.openPickupLine();
            byte[] dataCaptured = client.captureAudioData();
            return dataCaptured;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
