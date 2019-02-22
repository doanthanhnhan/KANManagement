/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Admin
 */
public class MD5Encrypt {
      public String encryptPass(String pass) {

        String passEncrypt;

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            
        }
        md5.update(pass.getBytes());
        BigInteger dis = new BigInteger(1, md5.digest());
        passEncrypt = dis.toString();
        return passEncrypt;
    }
    public String hashPass(String pass){
        String hash;
        pass = pass + "KAN";
        hash = encryptPass(pass);
        return hash;
    }
}
