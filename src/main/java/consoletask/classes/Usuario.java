/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consoletask.classes;

import java.util.List;

/**
 *
 * @author Oscar Orellana
 */
public class Usuario {
    
    private String nombreCompleto;
    private String userName;
    private int rolUsuario;
    private List<Tarea> tareasAsignadas; //TEMPORAL MIENTRAS NO SE CREAN CLASES NODOS

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String userName, int rolUsuario, List<Tarea> tareasAsignadas) {
        this.nombreCompleto = nombreCompleto;
        this.userName = userName;
        this.rolUsuario = rolUsuario;
        this.tareasAsignadas = tareasAsignadas;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(int rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public List<Tarea> getTareasAsignadas() {
        return tareasAsignadas;
    }

    public void setTareasAsignadas(List<Tarea> tareasAsignadas) { //TEMPORAL MIENTRAS NO SE CREAN CLASES NODOS
        this.tareasAsignadas = tareasAsignadas;
    }
    
}
