/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author alesso
 */
public class Anuncio {

    private int idAnuncio;
    private String texto;
    private String urlVideo;
    private String imagen;
    private boolean activo;
    private Date fechaInicio;
    private Date fechaFinal;
    private double pago;
    private String nombreAnunciante;
    private String tipo;
    private int diasDuracion;
    private int tipoAnuncio;

    public Anuncio() {

    }

    public Anuncio(int idAnuncio, String texto, String urlVideo, String imagen, boolean activo, Date fechaInicio, Date fechaFinal, double pago, String tipo, String nombreAnunciante) {
        this.idAnuncio = idAnuncio;
        this.texto = texto;
        this.urlVideo = urlVideo;
        this.imagen = imagen;
        this.activo = activo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.pago = pago;
        this.nombreAnunciante = nombreAnunciante;
        this.tipo = tipo;
    }

    public int getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(int idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getPathImagen() {
        return imagen;
    }

    public void setPathImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDiasDuracion() {
        return diasDuracion;
    }

    public void setDiasDuracion(int diasDuracion) {
        this.diasDuracion = diasDuracion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getTipoAnuncio() {
        return tipoAnuncio;
    }

    public void setTipoAnuncio(int tipoAnuncio) {
        this.tipoAnuncio = tipoAnuncio;
    }

}
