package consoletask.classes;

import consoletask.enums.RolUsuarioEnum;
import java.util.Scanner;
import consoletask.utils.Validaciones;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        //crear un objeto de la calse Validaciones para validar la entrada del usuario
        Validaciones validacion = new Validaciones(); 
        int opcion;
        //crear un nuevo nodo para almacenar las listas
        Nodo lista = new Nodo();
        
        //Crear instancias de usuario un administrador y un usuario
        Usuario admin = new Usuario("Admin", "admin123", RolUsuarioEnum.ADMIN.getId(), lista);
        Usuario usuario = new Usuario("Usuario", "user123", RolUsuarioEnum.USER.getId(), lista);
        
        //crear instancias de menu para el administrador y el usuario
        menu menuAdmin = new menu(admin, usuario);//Recibe el otro parametro ya que a este se le va copiar lo mismo que el otro usuario
        menu menuUsuario = new menu(usuario, admin);
//varible para controlar la salida del bucle 
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
                    menuAdmin.mostrarMenu(); //mostrar el menu para el administrador
                    break;
                case 2:
                    menuUsuario.mostrarMenu(); //mostrar menu para el usuario
                    break;
                case 3:
                    salida = true; //establecer la variable de salida en verdadero para salir del bucle
                    break;
                default:
                    System.out.println("Opción inválida. Saliendo del programa..."); // mensaje de error para opciones invalidas
                    break;
            }
        }

        scanner.close(); //cerrar objeto scanner para liberar recursos
    }

}
