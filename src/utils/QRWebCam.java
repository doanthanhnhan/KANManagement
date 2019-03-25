/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javax.swing.JButton;

/**
 *
 * @author Doan Thanh Nhan
 */
public class QRWebCam extends JFrame implements Runnable, ThreadFactory {
    
    private static final long serialVersionUID = 6441489157408381878L;
    
    private final Executor executor = Executors.newSingleThreadExecutor(this);
    
    private Webcam webcam = null;
    private WebcamPanel panel = null;
    private JTextArea textarea = null;
    private JButton btn_Close = null;
    
    //Declare txtQR to reference from another form
    public ReadOnlyObjectWrapper<String> txtQR = new ReadOnlyObjectWrapper<>();
    public SimpleStringProperty txtQR_test;
    
    //Send back data function to the form call this method
    public ReadOnlyObjectProperty<String> txtQRProperty() {
        return txtQR.getReadOnlyProperty();
    }

    
    public QRWebCam() {
        super();                
        
        setLayout(new FlowLayout());
        setTitle("Read QR / Bar Code With Webcam");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        Dimension size = WebcamResolution.QVGA.getSize();
        
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);
        
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);
        
        textarea = new JTextArea();
        textarea.setEditable(false);
        textarea.setPreferredSize(size);
        
        btn_Close = new JButton();
        btn_Close.setText("Close");
        btn_Close.addActionListener((e) -> {
            closePanel();
        });
        
        add(panel);
        add(textarea);
        add(btn_Close);
        
        pack();
        setVisible(true);
        
        executor.execute(this);
    }
    
    @Override
    public void run() {
        
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            Result result = null;
            BufferedImage image = null;
            
            if (webcam.isOpen()) {
                
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
                
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                
                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    // fall thru, it means there is no QR code in image
                }
            }
            
            if (result != null) {
                textarea.setText(result.getText());
                
                //System.out.println(result.getText());
                //Setting value for txtQR
                txtQR.set(result.getText());
                //txtQR_test.set(result.getText());
            }
            
        } while (true);
    }
    
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "example-runner");
        t.setDaemon(true);
        return t;
    }   
    
    private void closePanel(){
        webcam.close();        
        this.dispose();        
    }
   
    //For testing
    public static void main(String[] args) {
        new QRWebCam();
    }
}
