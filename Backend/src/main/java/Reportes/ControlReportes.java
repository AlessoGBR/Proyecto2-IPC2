/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import Backend.DB.ConexionPool;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author alesso
 */
public class ControlReportes {
    
    private ConexionPool dataSource = ConexionPool.getInstance();   

    public void imprimirReportesConParametros(OutputStream targetStream, Map<String, Object> parametros, String pathReporte) throws JRException {
        InputStream compiledReport = getClass().getClassLoader().getResourceAsStream(pathReporte);
        JasperPrint printer = JasperFillManager.fillReport(compiledReport, parametros);
        JasperExportManager.exportReportToPdfStream(printer, targetStream);
    }

    public void imprimirReporteBeans(OutputStream targetStream, String path, JRDataSource parametros) throws JRException {
        InputStream compiledReport = getClass().getClassLoader().getResourceAsStream(path);
        JasperPrint printer = JasperFillManager.fillReport(compiledReport, null, parametros);
        JasperExportManager.exportReportToPdfStream(printer, targetStream);
    }
}
 