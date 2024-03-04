/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package consoletask.enums;

/**
 *
 * @author Oscar Orellana
 */
public enum RolUsuarioEnum {

    ADMIN(Integer.valueOf(1)),
    USER(Integer.valueOf(2));
    
    private final Integer id;

    private RolUsuarioEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
