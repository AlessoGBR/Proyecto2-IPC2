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
public class RevistaReport {

    private Revista revista;
    private List<Comentario> comentarios;
    private List<Reaccion> reacciones;
    private List<Suscripcion> suscripciones;
    private double monto;

    public RevistaReport(Revista revista, List<Comentario> comentarios, List<Reaccion> reacciones, List<Suscripcion> suscripciones) {
        this.revista = revista;
        this.comentarios = comentarios;
        this.reacciones = reacciones;
        this.suscripciones = suscripciones;
    }

    public Revista getRevista() {
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Reaccion> getReacciones() {
        return reacciones;
    }

    public void setReacciones(List<Reaccion> reacciones) {
        this.reacciones = reacciones;
    }

    public List<Suscripcion> getSuscripciones() {
        return suscripciones;
    }

    public void setSuscripciones(List<Suscripcion> suscripciones) {
        this.suscripciones = suscripciones;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
