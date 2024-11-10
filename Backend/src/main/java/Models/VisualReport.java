/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.List;

/**
 *
 * @author alesso
 */
public class VisualReport {
    
    private String anunciante;
    private Anuncio anuncio;
    private List<Visualizacion> visualizaciones;

    public VisualReport(String anunciante, Anuncio anuncio, List<Visualizacion> visualizaciones) {
        this.anunciante = anunciante;
        this.anuncio = anuncio;
        this.visualizaciones = visualizaciones;
    }

    public String getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(String anunciante) {
        this.anunciante = anunciante;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public List<Visualizacion> getVisualizaciones() {
        return visualizaciones;
    }

    public void setVisualizaciones(List<Visualizacion> visualizaciones) {
        this.visualizaciones = visualizaciones;
    }
    
    public int getAnuncioTipo(){
        return this.anuncio.getTipoAnuncio();
    }
}
