/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Admin
 */
public class FormatName {
    public static String format(String str) {
        if(str.equals("")){
            return str;
        }
        else{
            str = (str.replaceAll("[\\s]\\s{1,}", " ")).trim();
            String temp[] = str.split(" ");
            str = "";
            for (String string : temp) {
                string = string.toLowerCase();
                String str1 = string;
                str1 = string.substring(0, 1).toUpperCase() + string.substring(1);
                if (str.equalsIgnoreCase("")) {
                    str = str1;
                } else {
                    str = str+ " " + str1;
                }
            }
            return str;
        }
    }
}
