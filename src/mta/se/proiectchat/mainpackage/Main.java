package mta.se.proiectchat.mainpackage;

import mta.se.proiectchat.mainpackage.audiopackage.Playback;
import mta.se.proiectchat.mainpackage.audiopackage.Records;
import mta.se.proiectchat.mainpackage.tcpconnctionpackage.CallerConnection;

import java.awt.*;
import java.io.Console;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.chrono.MinguoChronology;
import java.util.logging.ConsoleHandler;

/**
 * This class which conaints main function - the entry point in program
 * Created by Cristian on 1/18/2015.
 */
public class Main {
    /**
     * This is the entry point in program
     */
    public static void main(String args[]) {


        String username = "";
        String password = "";

        if (args.length == 0) {
            System.out.println("Please enter the username :");
            username = Main.getUsername();
            System.out.println("Please enter the password :");
            password = Main.getPassword();

            EstablishSecureConnection secureComunication = new EstablishSecureConnection(username, password);
            secureComunication.handleIncomingCall();


        } else {

            if (args[0].equals("-setup")) {
                System.out.println("Please enter the username :");
                username = Main.getUsername();
                System.out.println("Please enter the password :");
                password = Main.getPassword();
                System.out.println("Plase re-enter your password: ");
                String rePassword = Main.getPassword();
                if (!password.equals(rePassword)) {
                    System.out.println("Password are not equal !");
                    System.exit(-1);
                }
                Setup.setup(username, password);   // this  method creates a javakeystoretool and a certificate
                System.out.println("Setup ready !");
            } else {

                if (args[0].startsWith("-ip=")) {
                    System.out.println("Please enter the username :");
                    username = Main.getUsername();
                    System.out.println("Please enter the password :");
                    password = Main.getPassword();
                    String ip = args[0].substring(4);

                    EstablishSecureConnection secureComunication = new EstablishSecureConnection(username, password);
                    secureComunication.call(ip);

                } else
                    Main.Help();
            }

        }


    }

    /**
     * This method gets the username from user
     *
     * @return a string which contains the username
     */
    public static String getUsername() {
        String user = "";


        try {
            StringBuilder string = new StringBuilder();
            int ascii = System.in.read();

            while ((ascii != '\n') && (ascii != 13)) {
                string.append((char) ascii);
                ascii = System.in.read();
            }
            user = string.toString();
        } catch (IOException ex) {
            System.out.println("Error occured in reading the username !");
            System.exit(-1);

        } finally {
            return user;
        }
    }

    /**
     * This method gets the password form user without typing to STDOUT the chars
     *
     * @return a String with the password
     */
    public static String getPassword() {
        Console consola;
        consola = System.console();
        if (consola == null) {
            System.out.println("Error occured  in reading the password ! ");
            System.exit(-1);
        }
        String password = new String(consola.readPassword());
        return password;
    }

    /**
     * This method prints the help text.
     */
    public static void Help() {
        System.out.println();
        System.out.println("For setup          : java MainClassFile -setup ");
        System.out.println("For calling an ip  : java MainClassFile -ip=XX.XX.XX.XX ");
        System.out.println("For waiting an call: java MainClassFile ");
    }
}
