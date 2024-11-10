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
public class SuscriptorRevista {

    private int idSuscripcion;
    private Date fecha;
    private int idRevista;
    private String tituloRevista;
    private String user;

    public SuscriptorRevista(int idSuscripcion, Date fecha, int idRevista, String tituloRevista, String user) {
        this.idSuscripcion = idSuscripcion;
        this.fecha = fecha;
        this.idRevista = idRevista;
        this.tituloRevista = tituloRevista;
        this.user = user;
    }

    // Getters y setters
    public int getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(int idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(int idRevista) {
        this.idRevista = idRevista;
    }

    public String getTituloRevista() {
        return tituloRevista;
    }

    public void setTituloRevista(String tituloRevista) {
        this.tituloRevista = tituloRevista;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
