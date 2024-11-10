/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author alesso
 */
public class Reaccion {

    private int idReaccion;
    private String fecha;
    private int idRevista;
    private boolean reaccion;
    private String nombreUsuario;

    public Reaccion(int idReaccion, String fecha, String nombreUsuario, int idRevista) {
        this.idReaccion = idReaccion;
        this.fecha = fecha;
        this.idRevista = idRevista;
        this.nombreUsuario = nombreUsuario;
    }

    public Reaccion(int idReaccion, boolean reaccion, String fecha, String nombreUsuario, int idRevista) {
        this.idReaccion = idReaccion;
        this.fecha = fecha;
        this.idRevista = idRevista;
        this.reaccion = reaccion;
        this.nombreUsuario = nombreUsuario;
    }

    public Reaccion() {
    }

    public int getIdReaccion() {
        return idReaccion;
    }

    public void setIdReaccion(int idReaccion) {
        this.idReaccion = idReaccion;
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

    public boolean isReaccion() {
        return reaccion;
    }

    public void setReaccion(boolean reaccion) {
        this.reaccion = reaccion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

}
