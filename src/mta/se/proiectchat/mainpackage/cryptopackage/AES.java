package mta.se.proiectchat.mainpackage.cryptopackage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Cristian on 12/25/2014.
 */

/**
 * This the class which implements AES Encryption/Decryption
 */
public class AES {

    SecretKeySpec key;
    Cipher cipher;

    int mode = -1;


    /**
     * This is the constructor
     *
     * @param _key - the parameter where the 128 bit key is found - byte[]
     */
    public AES(byte[] _key) {

        key = new SecretKeySpec(_key, "AES");

        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the encrypt method for AES
     *
     * @param in - a byte[] input data is found
     */
    public byte[] encrypt(byte[] in) {

        byte[] out = null;
        try {
            if (mode != 1) {
                cipher.init(Cipher.ENCRYPT_MODE, this.key);
                mode = 1;
            }
            out = cipher.doFinal(in);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } finally {
            return out;
        }


    }

    /**
     * This is the method for decrypting with AES
     *
     * @param in - a byte[] where cipherdata is found
     */
    public byte[] decrypt(byte[] in) {

        byte[] out = null;
        try {
            if (mode != 2) {
                cipher.init(Cipher.DECRYPT_MODE, this.key);
                mode = 2;
            }
            out = cipher.doFinal(in);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } finally {
            return out;
        }
    }

}





