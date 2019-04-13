/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author nguyenducthao
 */
public class EncrypterDecrypter {

    Cipher ecipher;
    Cipher dcipher;

    EncrypterDecrypter(SecretKey key, String algorithm) {
        try {
            ecipher = Cipher.getInstance(algorithm);
            dcipher = Cipher.getInstance(algorithm);
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
        }
    }
    // encrypt() inputs a string and returns an encrypted version
    // of that String.

    public String encrypt(String str) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");
            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (BadPaddingException | IllegalBlockSizeException | IOException e) {
        }
        return null;
    }
    // decrypt() inputs a string and returns an encrypted version
    // of that String.

    public String decrypt(String str) {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);
            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (BadPaddingException | IllegalBlockSizeException | IOException e) {
        }
        return null;
    }
    // The following method is used for encrypting and decrypting
    // String using symmetric Secret Key using “DES” algorithm

    public static void useSecretKey() throws NoSuchAlgorithmException {
        try {
            String stringToBeEncrypted = "String to be encrypted";
            SecretKey desKey = KeyGenerator.getInstance("DES").generateKey();
            // Create encrypter/decrypter class
            EncrypterDecrypter encrypter = new EncrypterDecrypter(desKey, desKey.getAlgorithm());
            // Encrypt the string
            String encryptedString = encrypter.encrypt(stringToBeEncrypted);
            // Decrypt the string
            String decrypterString = encrypter.decrypt(encryptedString);
            // Display values
            System.out.println("\tEncryption algorithm Name:"
                    + desKey.getAlgorithm());
            System.out.println("\tOriginal String : " + stringToBeEncrypted);
            System.out.println("\tEncrypted String : " + encryptedString);
            System.out.println("\tDecrypted String : " + decrypterString);
        } catch (NoSuchAlgorithmException error) {

        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        useSecretKey();
    }
}
