/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author alesso
 */
public class Respuesta {

    private String mensaje;
    private Integer userType;
    private String username;

    public Respuesta(String mensaje) {
        this.mensaje = mensaje;
    }

    public Respuesta(String mensaje, Integer userType, String username) {
        this.mensaje = mensaje;
        this.userType = userType;
        this.username = username;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
