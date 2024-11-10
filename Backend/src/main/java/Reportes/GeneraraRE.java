/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import Models.AnuncianteReport;
import Models.RevistaReport;
import Models.VisualReport;
import ObtenerObjetos.ObAdmin;
import ObtenerObjetos.ObEditor;
import java.io.OutputStream;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author alesso
 */
public class GeneraraRE {

    private final ObEditor obE = new ObEditor();
    private final ObAdmin obA = new ObAdmin();
    private final ControlReportes reporte = new ControlReportes();
    //Reportes de Editor
    private final String R1 = "Reportes/EditorR/R1.jasper";
    private final String R2 = "Reportes/EditorR/R2.jasper";
    private final String R3 = "Reportes/EditorR/R3.jasper";
    private final String R4 = "Reportes/EditorR/R4.jasper";
    //Reportes de Admin
    private final String R1A = "Reportes/AdminR/R1A.jasper";
    private final String R2A = "Reportes/AdminR/R2A.jasper";
    private final String R4A = "Reportes/AdminR/R4A.jasper";
    private final String R5A = "Reportes/AdminR/R5A.jasper";
    private final String R6A = "Reportes/AdminR/R6A.jasper";
    private final String R7A = "Reportes/AdminR/R7A.jasper";

    public void generarReporte(String usuario, String fecha_inicio, String fecha_final, int idRevista, int opcion, OutputStream out) {
        try {
            switch (opcion) {
                case 1:
                    List<RevistaReport> revistas = obE.obtenerRevistasReporte(usuario, fecha_inicio, fecha_final, idRevista, opcion);
                    JRDataSource source = new JRBeanCollectionDataSource(revistas);
                    reporte.imprimirReporteBeans(out, R1, source);
                    break;
                case 2:
                    List<RevistaReport> r2 = obE.obtenerRevistasReporte(usuario, fecha_inicio, fecha_final, idRevista, opcion);
                    JRDataSource source2 = new JRBeanCollectionDataSource(r2);
                    reporte.imprimirReporteBeans(out, R2, source2);
                    break;
                case 3:
                    List<RevistaReport> r3 = obE.obtenerRevistasMasGustadas(idRevista, usuario, fecha_inicio, fecha_final);
                    JRDataSource source3 = new JRBeanCollectionDataSource(r3);
                    reporte.imprimirReporteBeans(out, R3, source3);
                    break;
                case 4:
                    List<RevistaReport> r4 = obE.obtenerRevistasReporte(usuario, fecha_inicio, fecha_final, idRevista, opcion);
                    JRDataSource source4 = new JRBeanCollectionDataSource(r4);
                    reporte.imprimirReporteBeans(out, R4, source4);
                    break;
                default:
                    break;
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void generarReporteAdmin(String fecha_inicio, String fecha_final, int idRevista, int opcion, OutputStream out, String anunciante) {
        try {
            switch (opcion) {
                case 1:
                    List<RevistaReport> revistas = obA.obtenerRevistasReporte(fecha_inicio, fecha_final, idRevista, opcion);
                    JRDataSource source = new JRBeanCollectionDataSource(revistas);
                    reporte.imprimirReporteBeans(out, R1A, source);
                    break;
                case 2:
                    List<AnuncianteReport> a = obA.obtenerAnunciantesReporte(fecha_inicio, fecha_final, anunciante);
                    JRDataSource source2 = new JRBeanCollectionDataSource(a);
                    reporte.imprimirReporteBeans(out, R6A, source2);
                    break;
                case 3:
                    List<AnuncianteReport> b = obA.obtenerAnunciantesReporte(fecha_inicio, fecha_final, anunciante);
                    JRDataSource source3 = new JRBeanCollectionDataSource(b);
                    reporte.imprimirReporteBeans(out, R2A, source3);
                    break;
                case 4:
                    List<RevistaReport> revistas4 = obA.obtenerRevistasPopulares(1, fecha_inicio, fecha_final);
                    JRDataSource source4 = new JRBeanCollectionDataSource(revistas4);
                    reporte.imprimirReporteBeans(out, R4A, source4);
                    break;
                case 5:
                    List<RevistaReport> revistas5 = obA.obtenerRevistasPopulares(2, fecha_inicio, fecha_final);
                    JRDataSource source5 = new JRBeanCollectionDataSource(revistas5);
                    reporte.imprimirReporteBeans(out, R5A, source5);
                    break;
                case 6:
                    List<RevistaReport> revistas6 = obA.obtenerRevistasPopulares(2, fecha_inicio, fecha_final);
                    JRDataSource source6 = new JRBeanCollectionDataSource(revistas6);
                    reporte.imprimirReporteBeans(out, R7A, source6);
                    break;
                default:
                    break;
            }
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }
}
