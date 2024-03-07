package consoletask.classes;

import consoletask.enums.RolUsuarioEnum;
import java.util.Scanner;
import consoletask.utils.Validaciones;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Validaciones validacion = new Validaciones();
        int opcion;
        
        Nodo lista = new Nodo();
        
        //Usuarios
        Usuario admin = new Usuario("Admin", "admin123", RolUsuarioEnum.ADMIN.getId(), lista);
        Usuario usuario = new Usuario("Usuario", "user123", RolUsuarioEnum.USER.getId(), lista);
        
        //Menú admin
        menu menuAdmin = new menu(admin, usuario);//Recibe el otro parametro ya que a este se le va copiar lo mismo que el otro usuario

        //Menú usuario
        menu menuUsuario = new menu(usuario, admin);

        boolean salida = false;
        while (!salida) {
            // Solicitar al usuario que elija el tipo de usuario
            System.out.println("=== BIENVENIDO ===");
            System.out.println("Por favor, seleccione el tipo de usuario:");
            System.out.println("1. Administrador");
            System.out.println("2. Usuario");
            System.out.println("3. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = validacion.solicitarOpcionMenu("Opción invalida", 1, 6);

            // Crear una instancia de Menu según la opción seleccionada
            switch (opcion) {
                case 1:
                    menuAdmin.mostrarMenu();
                    break;
                case 2:
                    menuUsuario.mostrarMenu();
                    break;
                case 3:
                    salida = true;
                    break;
                default:
                    System.out.println("Opción inválida. Saliendo del programa...");
                    break;
            }
        }

        scanner.close();
    }

}
