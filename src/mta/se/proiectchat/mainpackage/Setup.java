package mta.se.proiectchat.mainpackage;

import mta.se.proiectchat.mainpackage.cryptopackage.CertificateManager;

/**
 * This is the class used for setup.
 * The setup consists in passing 3 arguments :
 * -> the word "setup"
 * -> an alias used for generating an certificate
 * -> an password used for generating
 * An example would be : progamname setup cristian password
 * Created by Cristian on 1/6/2015.
 */
public class Setup {

    public static void setup(String username, String password) {

        CertificateManager.generate(username, password);
        CertificateManager.ExtractPublicCert(username, password);
    }
}
