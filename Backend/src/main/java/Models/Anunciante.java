/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author alesso
 */
public class Anunciante {
    
    private String username;
    private int idAnunciante;

    public Anunciante(String username, int idAnunciante) {
        this.username = username;
        this.idAnunciante = idAnunciante;
    }   
        
    public Anunciante(String username) {
        this.username = username;
    }

    public Anunciante() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdAnunciante() {
        return idAnunciante;
    }

    public void setIdAnunciante(int idAnunciante) {
        this.idAnunciante = idAnunciante;
    }
    
    
}
