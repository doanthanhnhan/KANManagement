/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Doan Thanh Nhan
 */
public class GetInetAddress {

    public static String getMacAddress() {
        String macAddress = null;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();            
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < mac.length; j++) {
                sb.append(String.format("%02X%s", mac[j], (j < mac.length - 1) ? "-" : ""));
            }            
            macAddress = sb.toString();
        } catch (UnknownHostException | SocketException ex) {
            Logger.getLogger(GetInetAddress.class.getName()).log(Level.SEVERE, null, ex);
        }
        return macAddress;
    }
    
    public static void main(String[] args) {
        System.out.println(GetInetAddress.getMacAddress());        
    }
}
