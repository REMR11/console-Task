/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consoletask.classes;

import java.time.LocalDate;

/**
 *
 * @author Oscar Orellana
 */
public class Tarea {

    private String nombreTarea;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int estadoTarea;
    private int porcentajeProgreso;

    public Tarea() {
    }

    public Tarea(String nombreTarea, LocalDate fechaInicio, LocalDate fechaFin, int estadoTarea, int porcentajeProgreso) {
        this.nombreTarea = nombreTarea;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoTarea = estadoTarea;
        this.porcentajeProgreso = porcentajeProgreso;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getEstadoTarea() {
        return estadoTarea;
    }

    public void setEstadoTarea(int estadoTarea) {
        this.estadoTarea = estadoTarea;
    }

    public int getPorcentajeProgreso() {
        return porcentajeProgreso;
    }

    public void setPorcentajeProgreso(int porcentajeProgreso) {
        this.porcentajeProgreso = porcentajeProgreso;
    }

}
