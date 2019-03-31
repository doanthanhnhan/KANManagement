/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Doan Thanh Nhan
 */
public class QRCreate {

    public static Image creatQRCode(String qrCodeData) {

        String charset = "UTF-8"; // or "ISO-8859-1"

        Map< EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
        } catch (WriterException | UnsupportedEncodingException ex) {
            Logger.getLogger(QRCreate.class.getName()).log(Level.SEVERE, null, ex);
        }

        // GENERATE QR CODE
        BufferedImage img = MatrixToImageWriter.toBufferedImage(matrix);
        Image image = SwingFXUtils.toFXImage(img, null);

        System.out.println("QR Code image created successfully!");
        return image;

    }

    public static void main(String[] args) {
        try {
            String qrCodeData = "Doan Thanh Nhan is the best!";
            String filePath = "/src/test/doanthanhnhan.png";
            String charset = "UTF-8"; // or "ISO-8859-1"
            File file = new File("");
            Map< EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                    .lastIndexOf('.') + 1), new File(file.getAbsolutePath() + filePath));
            System.out.println("QR Code image created successfully!");
        } catch (WriterException | IOException e) {
            System.err.println(e);
        }
    }
}
