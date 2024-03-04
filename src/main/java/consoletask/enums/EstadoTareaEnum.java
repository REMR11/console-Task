/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package consoletask.enums;

/**
 *
 * @author Oscar Orellana
 */
public enum EstadoTareaEnum {
    
    PENDIENTE(Integer.valueOf(1)),
    EN_PROGRESO(Integer.valueOf(2)),
    FINALIZADA(Integer.valueOf(3));
    
    private final Integer id;

    private EstadoTareaEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
}
