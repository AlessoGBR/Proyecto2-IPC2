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
public class Revista {
    
    private int idRevista;
    private int likes;
    private String titulo;
    private int no_version;
    private String fecha;
    private String revistaPath;
    private String descripcion;
    private boolean aprobado;
    private boolean suscripciones;
    private boolean esPago;
    private boolean tieneComentarios;
    private boolean tieneReacciones;
    private boolean tieneAnuncios;
    private boolean denegada;
    private double precio;
    private String usuario;
    private List<Etiqueta> etiquetas;
    private List<String> etiquetasString;

   public Revista(String titulo, String descripcion, int version, String fecha,
                   boolean suscripciones, boolean tieneComentarios,
                   boolean tieneReacciones, String usuario, List<Etiqueta> etiquetas, boolean aprobado, boolean dengada, double precio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.no_version = version;  
        this.fecha = fecha;
        this.suscripciones = suscripciones;
        this.tieneComentarios = tieneComentarios;
        this.tieneReacciones = tieneReacciones;
        this.usuario = usuario;
        this.etiquetas = etiquetas;
        this.aprobado = aprobado;
        this.denegada = dengada;
        this.precio = precio;
    }
   
   public Revista(String titulo, String descripcion, int version, String fecha,
                   boolean suscripciones, boolean tieneComentarios,
                   boolean tieneReacciones, boolean tieneAnuncios, String usuario, List<Etiqueta> etiquetas, boolean aprobado, boolean dengada, double precio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.no_version = version;  
        this.fecha = fecha;
        this.suscripciones = suscripciones;
        this.tieneComentarios = tieneComentarios;
        this.tieneReacciones = tieneReacciones;
        this.tieneAnuncios = tieneAnuncios;
        this.usuario = usuario;
        this.etiquetas = etiquetas;
        this.aprobado = aprobado;
        this.denegada = dengada;
        this.precio = precio;
    }

    public Revista() {

    }

    public int getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(int idRevista) {
        this.idRevista = idRevista;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNo_version() {
        return no_version;
    }

    public void setNo_version(int no_version) {
        this.no_version = no_version;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getrevistaPath() {
        return revistaPath;
    }

    public void setrevistaPath(String revistaPath) {
        this.revistaPath = revistaPath;
    } 

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isAprobada() {
        return aprobado;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobado = aprobada;
    }

    public String getusuario() {
        return usuario;
    }

    public void setusuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isSuscripciones() {
        return suscripciones;
    }

    public void setSuscripciones(boolean suscripciones) {
        this.suscripciones = suscripciones;
    }

    public boolean isEsPago() {
        return esPago;
    }

    public void setEsPago(boolean esPago) {
        this.esPago = esPago;
    }

    public boolean isTieneComentarios() {
        return tieneComentarios;
    }

    public void setTieneComentarios(boolean tieneComentarios) {
        this.tieneComentarios = tieneComentarios;
    }

    public boolean isTieneReacciones() {
        return tieneReacciones;
    }

    public void setTieneReacciones(boolean tieneReacciones) {
        this.tieneReacciones = tieneReacciones;
    }

    public boolean isTieneAnuncios() {
        return tieneAnuncios;
    }

    public void setTieneAnuncios(boolean tieneAnuncios) {
        this.tieneAnuncios = tieneAnuncios;
    }

    public boolean isDenegada() {
        return denegada;
    }

    public void setDenegada(boolean denegada) {
        this.denegada = denegada;
    }    

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }
    
    public List<String> getEtiquetasString() {
        return etiquetasString;
    }

    public void setEtiquetasString(List<String> etiquetas) {
        this.etiquetasString = etiquetas;
    }
}
