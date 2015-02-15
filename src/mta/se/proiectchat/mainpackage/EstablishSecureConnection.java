package mta.se.proiectchat.mainpackage;

import mta.se.proiectchat.mainpackage.audiopackage.Playback;
import mta.se.proiectchat.mainpackage.audiopackage.Records;
import mta.se.proiectchat.mainpackage.cryptopackage.AES;
import mta.se.proiectchat.mainpackage.cryptopackage.Handshake;
import mta.se.proiectchat.mainpackage.tcpconnctionpackage.CallerConnection;
import mta.se.proiectchat.mainpackage.tcpconnctionpackage.ServerConnection;


import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Observable;

/**
 * Created by ADY on 15.02.2015.
 */
public class EstablishSecureConnection implements Runnable {

    private volatile int state;

    private String username;
    private String password;

    public static int portForListen = 8012;

    public static int connected = 1;

    public static int disconnected = 0;


    Records clientRecord = null;
    Playback clientPlayback = null;


    private byte[] keyUsed = null;

    CallerConnection client = null;
    ServerConnection server = null;

    private Object lockTransmission = new Object();
    private Object lockKey = new Object();


    public EstablishSecureConnection(String username, String password) {
        state = disconnected;

        this.username = username;
        this.password = password;

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("SecureChatKeyStore.jks"), password.toCharArray());

            FileInputStream certFileStream = new FileInputStream("ChatCertificate.cer");
            certFileStream = null;

            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(username, new KeyStore.PasswordProtection(password.toCharArray()));
            if (privateKeyEntry == null)
                throw new Exception();

        } catch (Exception e) {

            System.out.print("You need to setup the application for this username " + username + " or check the password again!");
            System.exit(-1);
        }


        System.out.println("You are signed in.");

        (new Thread(this)).start();
    }


    public void call(String destinationIP) {
        byte[] dataToSend = null;
        byte[] cipherText = null;
        state = connected;
        try {
            client = new CallerConnection(destinationIP, portForListen);
            client.Open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Handshake handshakeUsed = new Handshake();
        keyUsed = handshakeUsed.establishAESkey(username, password, "ChatCertificate.cer", client);

        synchronized (lockKey) {
            lockKey.notify();
        }

        clientRecord = new Records();
        clientRecord.openPickupLine();

        AES cipherUsed = new AES(keyUsed);


        System.out.println("Conversation started.");
        System.out.print("\nData: 0 bytes");

        int size = 0;

        while (true) {

            dataToSend = clientRecord.captureAudioData();
            cipherText = cipherUsed.encrypt(dataToSend);
            size += cipherText.length;
            System.out.print("\rData: " + size + " bytes");

            try {
                client.Write(cipherText);
            } catch (Exception ex) {
                System.out.println("\nA connection error was detected!!! Please try again");
                System.exit(-1);
            }

        }


    }


    public void handleIncomingCall() {

        try {
            System.out.println("Waiting for any call...");
            synchronized (lockTransmission) {
                lockTransmission.wait();
            }

            client = new CallerConnection(((ServerConnection) server).getRemoteIP(), portForListen);
            client.Open();

            clientRecord = new Records();
            clientRecord.openPickupLine();

            AES cipherUsed = new AES(keyUsed);

            synchronized (lockKey) {
                lockKey.notify();
            }

            byte[] dataToSend = null;
            byte[] cipherText = null;

            System.out.println("Start talking...");
            System.out.print("\rTransfered data: 0 bytes");
            int size = 0;

            while (true) {

                dataToSend = clientRecord.captureAudioData();
                cipherText = cipherUsed.encrypt(dataToSend);
                size += cipherText.length;
                System.out.print("\rTransfered data: " + size + " bytes");
                client.Write(cipherText);
            }

        } catch (Exception e) {
            System.out.println("\nA connection error was detected!!! Please try again !! ");
            e.printStackTrace();
            System.exit(-1);
        }

    }


    public void endCall() {
        client.Close();
        server.Close();
    }


    @Override
    public void run() {

        server = new ServerConnection(portForListen);
        server.Open();

        clientPlayback = new Playback();
        clientPlayback.openLineForPlayback();

        if (state == disconnected) {
            Handshake handshake = new Handshake();
            keyUsed = handshake.waitForHandshake(server);
            synchronized (lockTransmission) {
                lockTransmission.notify();
            }
        }

        AES cipherUsed = null;

        synchronized (lockKey) {
            try {
                lockKey.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cipherUsed = new AES(keyUsed);
        }

        byte[] encryptedData = cipherUsed.encrypt(new byte[Playback.dataSize]);
        byte[] plainData = null;

        while (true) {

            try {
                encryptedData = server.Read();
            } catch (Exception e) {
                System.out.println("\nA connection error was detected!!! Please try again ! ");
                System.exit(-1);
            }

            try {
                plainData = cipherUsed.decrypt(encryptedData);

                clientPlayback.play(plainData);
            } catch (Exception e) {
                continue;
            }
        }

    }
}