package mta.se.proiectchat.mainpackage.cryptopackage;

import mta.se.proiectchat.mainpackage.tcpconnctionpackage.CallerConnection;
import mta.se.proiectchat.mainpackage.tcpconnctionpackage.ISocketConnection;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.cert.X509Certificate;

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
     * * This function starts a handshake and returns an aes key for a secure comunication
     * the socket conection must be opened !!!
     *
     * @param alias               - alias for stored priv key in JKS
     * @param password            - password for stored priv key in JKS
     * @param certificateFilename - filename of certificate
     * @param destination         - connection to caller
     * @return
     */
    public static byte[] establishAESkey(String alias, String password, String certificateFilename, ISocketConnection destination) {

        byte[] aeskey = null;
        byte[] certFromFile = CertificateManager.readCertificateFromFile(certificateFilename);


        destination.Write(certFromFile); // sending certificate

        byte[] encAESkey = destination.Read();
        PrivateKey privateKey = CertificateManager.LoadPrivKey(alias, password);
        try {
            Cipher RSAcipher = Cipher.getInstance("RSA");
            RSAcipher.init(Cipher.DECRYPT_MODE, privateKey);
            aeskey = RSAcipher.doFinal(encAESkey);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }


        return aeskey;
    }


    /**
     * This method waits for a handshake from caller
     *
     * @param destination - socket connection of caller
     * @return - a byte array of established aes key
     */
    public static byte[] waitForHandshake(ISocketConnection destination) {

        byte[] _certificate = null;
        _certificate = destination.Read();

        X509Certificate certificate = CertificateManager.loadFromByteArray(_certificate);
        PublicKey pubkey = certificate.getPublicKey();
        SecureRandom sRandom = new SecureRandom();
        byte[] aeskey = new byte[16];
        sRandom.nextBytes(aeskey);

        byte[] encAESkey = null;
        Cipher RSAcipher = null;
        try {
            RSAcipher = Cipher.getInstance("RSA");
            RSAcipher.init(Cipher.ENCRYPT_MODE, pubkey);
            encAESkey = RSAcipher.doFinal(aeskey);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        destination.Write(encAESkey);


        return aeskey;
    }


}
