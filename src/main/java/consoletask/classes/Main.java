package consoletask.classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import consoletask.enums.EstadoTareaEnum;
import consoletask.enums.RolUsuarioEnum;
import consoletask.utils.LocalDateAdapter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    
    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
        int opcion;
        
        // Solicitar al usuario que elija el tipo de usuario
        System.out.println("=== BIENVENIDO ===");
        System.out.println("Por favor, seleccione el tipo de usuario:");
        System.out.println("1. Administrador");
        System.out.println("2. Usuario");
        System.out.print("Ingrese su opción: ");
        opcion = scanner.nextInt();
        
        // Crear una instancia de Menu según la opción seleccionada
        switch (opcion) {
            case 1:
                Usuario admin = new Usuario("Admin", "admin123", RolUsuarioEnum.ADMIN.getId(), new ArrayList<>());
                menu menuAdmin = new menu(admin);
                menuAdmin.mostrarMenu();
                break;
            case 2:
                Usuario usuario = new Usuario("Usuario", "user123", RolUsuarioEnum.USER.getId(), new ArrayList<>());
                menu menuUsuario = new menu(usuario);
                menuUsuario.mostrarMenu();
                break;
            default:
                System.out.println("Opción inválida. Saliendo del programa...");
                break;
        }
        
        scanner.close();
        // Configurando una instancia de Gson global para poder imprimir los objetos en formato json, de forma m�s legible y r�pida 
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).disableHtmlEscaping().create();

        // Crear una tarea y dos usuarios (admin y user) TEMPORAL MIENTRAS NO SE DEFINE LOS 
        Tarea tarea1 = new Tarea("tarea1", LocalDate.of(2024,3,3), LocalDate.of(202, 3, 10), EstadoTareaEnum.PENDIENTE.getId(), 0);
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
