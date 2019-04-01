/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            //JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath() + reportPath);
            JasperReport jasperReport = JasperCompileManager.compileReport("E:\\Nhan\\02.Aptech\\02.SEM2\\06.EProject\\KANManagement\\src\\reports\\Bill.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connectDB.connectSQLServer());
            
            JRViewer jrViewer = new JRViewer(jasperPrint);
            jrViewer.setOpaque(true);
            jrViewer.setVisible(true);
            
            this.add(jrViewer);
            this.setSize(1366, 760);
            this.setVisible(true);
        } catch (JRException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PrintReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
