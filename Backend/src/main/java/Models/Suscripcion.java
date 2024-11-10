/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author alesso
 */
public class Suscripcion {
    
    private int idSuscripcion;
    private String fecha;
    private int idRevista;
    private String nombreUsuario;
    private Usuario lector;
    
    public Suscripcion(int idRevista, String fecha, String user){
        this.idRevista = idRevista;
        this.fecha = fecha;
        this.nombreUsuario = user;
    }
    public Suscripcion() {
    }

    public int getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(int idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(int idRevista) {
        this.idRevista = idRevista;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario getLector() {
        return lector;
    }

    public void setLector(Usuario lector) {
        this.lector = lector;
    }

}
