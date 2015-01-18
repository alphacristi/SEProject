package mta.se.proiectchat.mainpackage.cryptopackage;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateFactory;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * This is the class used for generation and management of certificate
 * Created by Cristian on 12/27/2014.
 */
public class CertificateManager {

    /**
     * This is the constructor
     */
    public CertificateManager() {

    }

    /**
     * This is a static method for generating an certificate and place it in a Java Key Store
     *
     * @param alias    - an alias for java key store storage
     * @param password - an passoword used for getting information from Java Key Store
     */
    static public void generate(String alias, String password) {


        File keyst = new File("SecureChatKeyStore.jks");
        if (keyst.exists()) {
            System.out.println("An java key store with an certificate already exists under the name : SecureChatKeyStore.jks ");
            return;
        }

        System.out.println(" Please give answers to the following questions in order to generate an certificate :");

        String filename = new String("SecureChatKeyStore.jks");
        StringBuilder genCert = new StringBuilder();
        try {
            genCert.append("start /WAIT keytool -genkeypair -alias ");
            genCert.append(alias);
            genCert.append(" -keyalg RSA -keysize 2048 -validity 360 -keypass ");
            genCert.append(password);
            genCert.append(" -storepass ");
            genCert.append(password);
            genCert.append(" -keystore ");
            genCert.append(filename);

            ProcessBuilder genCertProcess = new ProcessBuilder("cmd.exe", "/c", genCert.toString());
            Process genProcess = genCertProcess.start();
            genProcess.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * This method is used for extracting Public Certificate from a Java Key Tool
     *
     * @param alias    - alias for user who stored information in java key tool
     * @param password - password used for store
     */
    static public void ExtractPublicCert(String alias, String password) {

        File certificate = new File("ChatCertificate.cer");
        if (certificate.exists()) {
            System.out.println("An certificate already exists under the name : ChatCertificate.cer ");
            return;
        }


        File keyst = new File("SecureChatKeyStore.jks");
        if (!keyst.exists()) {
            System.out.println("An java key store does not exist under the name: SecureChatKeyStore.jks ");
            return;
        }

        String filename = new String("SecureChatKeyStore.jks");
        StringBuilder extrCert = new StringBuilder();
        extrCert.append("keytool -exportcert -keystore ");
        extrCert.append(filename);
        extrCert.append(" -alias ");
        extrCert.append(alias);
        extrCert.append(" -storepass ");
        extrCert.append(password);
        extrCert.append(" -file ChatCertificate.cer");

        try {
            ProcessBuilder ExtractCertProcess = new ProcessBuilder("cmd.exe", "/c", extrCert.toString());
            ExtractCertProcess.start();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * This method extracts the private key from an entry with alias: "alias" available in a java key store.
     *
     * @param alias    - alas used in storing the private key in java key tol
     * @param password - password used for storing
     * @return
     */
    static public PrivateKey LoadPrivKey(String alias, String password) {

        PrivateKey privKey = null;

        try {

            KeyStore store = KeyStore.getInstance("JKS");
            store.load(new FileInputStream("SecureChatKeyStore.jks"), password.toCharArray());

            KeyStore.PrivateKeyEntry Entry = (KeyStore.PrivateKeyEntry) store.getEntry(alias, new KeyStore.PasswordProtection(password.toCharArray()));

            privKey = Entry.getPrivateKey();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return privKey;

    }

    /**
     * This is the class which transforms an byte[] certificate to a X509 structure certificate
     *
     * @param _certificate a byte array of certificate
     * @return - a X509 certificate
     */
    public static X509Certificate loadFromByteArray(byte[] _certificate) {

        X509Certificate certificate = null;

        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            InputStream inputStream = new ByteArrayInputStream(_certificate);
            certificate = (X509Certificate) factory.generateCertificate(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            return certificate;
        }


    }

    /**
     * This method is used for reading a X509 format certificate and returns
     * a byte[] of the DER econding
     *
     * @param certFilename - filename of the certificate
     * @return - a byte[] of certificate
     */
    public static byte[] readCertificateFromFile(String certFilename) {

        byte[] _certificate = null;

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            FileInputStream inputStream = new FileInputStream(certFilename);
            X509Certificate certificate = (X509Certificate) cf.generateCertificate(inputStream);
            _certificate = certificate.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return _certificate;
        }

    }


}
