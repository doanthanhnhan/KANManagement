/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;
import javafx.scene.control.DatePicker;

/**
 *
 * @author Admin
 */
public class formatCalender {
    public static void format(String pattern,DatePicker idNgayGiao){
        //idNgayGiao.setPromptText(pattern.toLowerCase());
        idNgayGiao.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }
}

