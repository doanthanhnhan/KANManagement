/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Image;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Doan Thanh Nhan
 */
public class PrintReport extends JFrame {

    public PrintReport() {
    }

    public void showReport(String reportPath) {
        File file = new File("");

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath() + reportPath);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connectDB.connectSQLServer());

            JRViewer jrViewer = new JRViewer(jasperPrint);
            jrViewer.setOpaque(true);
            jrViewer.setVisible(true);

            this.add(jrViewer);
            this.setSize(1366, 760);
            this.setTitle("Jasper Report Viewer");
            ImageIcon img = new ImageIcon(file.getAbsolutePath() + "/src/images/KAN Logo.png");
            this.setIconImage(img.getImage());
            this.setLocationRelativeTo(null);
            this.setAlwaysOnTop(true);
            this.setVisible(true);
        } catch (JRException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showReport_Customer_Bill(String reportPath, String checkOutID) {
        File file = new File("");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath() + reportPath);
            
            HashMap<String, Object> map = new HashMap<>();
            map.put("checkOutID", checkOutID);
            map.put("realPath", file.getAbsolutePath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connectDB.connectSQLServer());

            JRViewer jrViewer = new JRViewer(jasperPrint);
            jrViewer.setOpaque(true);
            jrViewer.setVisible(true);

            this.add(jrViewer);
            this.setSize(1366, 760);
            this.setTitle("Jasper Report Viewer");
            ImageIcon img = new ImageIcon(file.getAbsolutePath() + "/src/images/KAN Logo.png");
            this.setIconImage(img.getImage());
            this.setLocationRelativeTo(null);
            this.setAlwaysOnTop(true);
            this.setVisible(true);
        } catch (JRException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
