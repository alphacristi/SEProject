package mta.se.proiectchat.mainpackage.cryptopackage;

import mta.se.proiectchat.mainpackage.tcpconnctionpackage.ISocketConnection;

/**
 * This is the class used for establishing an aes key
 * in order to comunicate secure
 * Created by Cristian on 1/18/2015.
 */
public class Handshake {
    /**
     * Constructor
     */
    public Handshake() {
    }


    /**
     * This function returns an aes key for a secure comunication
     *
     * @param certificateFilename - filename of caller certificate
     * @param destination         - a socket connection to destination
     * @return - a byte[] with a aes key
     */
    public byte[] establishAESkey(String certificateFilename, ISocketConnection destination) {

        byte[] aeskey = new byte[16];



        destination.Open();


        destination.Close();
        return aeskey;
    }
}
