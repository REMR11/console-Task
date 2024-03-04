package consoletask.classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import consoletask.enums.EstadoTareaEnum;
import consoletask.enums.RolUsuarioEnum;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    
    public static void main(String[] args) {
        
        // Configurando una instancia de Gson global para poder imprimir los objetos en formato json, de forma más legible y rápida 
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        // Crear una tarea y dos usuarios (admin y user)
        Tarea tarea1 = new Tarea("tarea1", new Date(2024, 3, 3), new Date(2024, 3, 10), EstadoTareaEnum.PENDIENTE.getId(), 0);
        System.out.println("Tarea creada: " + gson.toJson(tarea1));
        
        Usuario admin = new Usuario("El Admin", "adminpro1234", RolUsuarioEnum.ADMIN.getId(), new ArrayList<>());
        Usuario user = new Usuario("Usuario Explotado", "usuarionoob123", RolUsuarioEnum.USER.getId(), new ArrayList<>());

        // Agregando la tarea a la lista de ambos usuarios
        admin.getTareasAsignadas().add(tarea1);
        user.getTareasAsignadas().add(tarea1);
        
        System.out.println("Admin:" + gson.toJson(admin));
        System.out.println("Usuario: " + gson.toJson(user));
        
    }
    
}
