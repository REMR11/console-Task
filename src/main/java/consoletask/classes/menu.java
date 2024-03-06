package consoletask.classes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import consoletask.enums.EstadoTareaEnum;
import consoletask.enums.RolUsuarioEnum;
import consoletask.utils.LocalDateAdapter;
import consoletask.utils.Validaciones;


public class menu {
    private Usuario usuarioActual;
    private Scanner scanner;
    private Validaciones validaciones;

    public menu(Usuario usuarioActual){
        this.usuarioActual=usuarioActual;
        this.scanner= new Scanner(System.in);
        this.validaciones= new Validaciones();

    }
    public void mostrarMenu(){
        if(usuarioActual.getRolUsuario()==RolUsuarioEnum.ADMIN.getId()){
            mostrarMenuAdmin();
        }else{
            mostrarMenuUsuario();
        }
    }
    private void mostrarMenuAdmin(){
    
        int opcion; 
        do{
            System.out.println("===MENU DE ADMINISTRADOR ===");
            System.out.println("1.Ver tareas");
            System.out.println("2.Crear tarea");
            System.out.println("3.Salir.");
            System.out.print("Ingrese su opcion: ");
            opcion=validaciones.solicitarOpcionMenu("Opcion invalida",1,3);
            switch(opcion){
                case 1:
                mostrarMenuGestionTareasAdmin();
                break;

                case 2: 
                crearTarea();
                break;

                case 3: 
                System.out.println("Saliendo...");
                break;
                
    }
}while (opcion !=3);
    }
    private void mostrarMenuGestionTareasAdmin(){
        int opcion;
        do{
            System.out.println("=== GESTION DE TAREAS ===");
            System.out.println("1. Modificar tarea");
            System.out.println("2. Eliminar tarea");
            System.out.println("3. Filtrar tareas por fecha.");
            System.out.println("4. Filtrar tareas por estado.");
            System.out.println("5. Filtrar tareas por usuario asignado");
            System.out.println("6. Volver al menu principal");
            opcion=validaciones.solicitarOpcionMenu("Opcion invalida",1,3);
            
            switch (opcion){
                case 1:
                modificarTarea();
                break;

                case 2: 
                eliminarTarea();
                break;

                case 3:
                filtrarTareasPorFecha();
                break;

                case 4:
                filtrarTareasPorEstado();
                break;

                case 5:
                filtrarTareasPorUsuarioAsignado();
                break;
            }
        }while (opcion != 6);
    }

    private void crearTarea(){
        String nombreTarea=validaciones.solicitarString("El nombre de la tarea no puede estar vacio.");
        LocalDate fechaInicio= validaciones.solicitarFecha("Formato de fecha incorrecto para la fecha de inicio.");
        LocalDate fechaFin= validaciones.solicitarFechaMayor("La fecha de fin debe ser posterior a la fecha de inicio.",fechaInicio);
        int EstadoTareaEnum= validaciones.solicitarOpcionMenu("Estado invalido. Debe ser 1(pendiente), 2(En progreso),3(Finalizado)",1,3);
        int porcentajeProgreso= validaciones.solicitarEntero("El porcentaje debe ser un numero entero entre 0 y 100");
        Tarea nuevaTarea=new Tarea(nombreTarea,fechaFin,fechaFin,EstadoTareaEnum,porcentajeProgreso);
        usuarioActual.getTareasAsignadas().add(nuevaTarea);
        System.out.println("Tarea creada exitosamente: "+nuevaTarea);
    }

    private void modificarTarea(){
        System.out.println("=== MODIFICAR TAREA ===");
        if(usuarioActual.getTareasAsignadas().isEmpty()){
            System.out.println("No hay tareas asignadas para modificar.");

        }else{
            System.out.println("Seleccione la tarea que desea modificar:");
            for(int i=0;i<usuarioActual.getTareasAsignadas().size(); i++){
                System.out.println((i+1)+"."+usuarioActual.getTareasAsignadas().get(i).getNombreTarea());
            }
            int opcionTarea=validaciones.solicitarOpcionMenu("Opcion invalida",1,usuarioActual.getTareasAsignadas().size())-1;
            Tarea tareaSeleccionada=usuarioActual.getTareasAsignadas().get(opcionTarea);
            System.out.println("Ingrese el nuevo nombre de la tarea: ");
            String nuevoNombre=scanner.nextLine().trim();
            tareaSeleccionada.setNombreTarea(nuevoNombre);
            System.out.println("Tarea modificada exitosamente: "+tareaSeleccionada);


        }
    }
    private void eliminarTarea(){
        System.out.println("=== Eliminar Tarea ===");
        if(usuarioActual.getTareasAsignadas().isEmpty()){
            System.out.println("No hay tareas asignadas para eliminar.");
        }else{
            System.out.println("Seleccione la tarea que desea eliminar:");
            for(int i=0; i< usuarioActual.getTareasAsignadas().size();i++){
                System.out.println((i+1)+"."+usuarioActual.getTareasAsignadas().get(i).getNombreTarea());
            }
            int opcionTarea= validaciones.solicitarOpcionMenu("Opcion invalida",1,usuarioActual.getTareasAsignadas().size())-1;
            Tarea tareaEliminada=usuarioActual.getTareasAsignadas().remove(opcionTarea);
            System.out.println("Tarea eliminada exitosamente: "+tareaEliminada);
        }
    }
    private void filtrarTareasPorFecha(){
        System.out.println("=== FILTRAR TAREAS POR FECHA ===");
            LocalDate fecha=validaciones.formatearFecha("Ingrese la fecha para filtrar las tareas (dd/MM/yyyy): ");
            System.out.println("Tareas para la feha"+validaciones.formatearFecha(fecha)+":");
            boolean encontrada=false;
            for(Tarea tarea : usuarioActual.getTareasAsignadas()){
                if(tarea.getFechaInicio().equals(fecha)|| tarea.getFechaFin().equals(fecha)){
                    System.out.println(tarea);
                    encontrada=true;
                }
            }
            if (!encontrada){
                System.out.println("No se encontraron tareas para la fecha seleccionada.");
            }
        
    }
    private void filtrarTareasPorEstado(){
        System.out.println("=== FILTRAR TAREAS POR ESTADO===");
        int estado=validaciones.solicitarOpcionMenu("Ingrese el estado de la tarea(1. pendiente, 2.En progreso , 3.Finalizada)",1,3);
        System.out.println("Tareas con estado"+EstadoTareaEnum.values()[estado-1]+":");
        boolean encontrada=false;
        for(Tarea tarea : usuarioActual.getTareasAsignadas()){
            if(tarea.getEstadoTarea()==estado){
                System.out.println(tarea);
                encontrada=true;
            }
        }
        if (!encontrada){
            System.out.println("No se encontraron tareas con el estado seleccionado. ");

        }
    }
    private void filtrarTareasPorUsuarioAsignado() {
        System.out.println("=== FILTRAR TAREAS POR USUARIO ASIGNADO ===");
        if (usuarioActual.getTareasAsignadas().isEmpty()) {
            System.out.println("No hay tareas asignadas.");
            return;
        }
    
        System.out.println("Lista de usuarios:");
        for (int i = 0; i < usuarioActual.getTareasAsignadas().size(); i++) {
            System.out.println((i + 1) + ". " + usuarioActual.getTareasAsignadas().get(i).getUserName());
        }
    
        int opcion = validaciones.solicitarOpcionMenu("Seleccione un usuario (ingrese 0 si desea cancelar): ", 0, usuarioActual.getTareasAsignadas().size());
        if (opcion == 0) {
            System.out.println("Operación cancelada.");
            return;
        }
    
        String usuarioSeleccionado = usuarioActual.getTareasAsignadas().get(opcion - 1).getUserName();
        System.out.println("Tareas asignadas a " + usuarioSeleccionado + ":");
        boolean encontrada = false;
        for (Tarea tarea : usuarioActual.getTareasAsignadas()) {
            if (tarea.getUserName().equals(usuarioSeleccionado)) {
                System.out.println(tarea);
                encontrada = true;
            }
        }
    
        if (!encontrada) {
            System.out.println("No se encontraron tareas asignadas a " + usuarioSeleccionado + ".");
        }
    }
    
private void mostrarMenuUsuario(){
   

    int opcion;
    do{
        System.out.println("=== MENU DE USUARIO ===");
        System.out.println("1. Ver tareas");
        System.out.println("2. Cerrar sesion");
        System.out.print("Ingrese su opcion: ");
        opcion= validaciones.solicitarOpcionMenu("Opcion invalida",1,2);
        switch (opcion) {
            case 1:
            mostrarMenuGestionTareasUsuario();
            break;
            case 2:
            System.out.println("Cerrando sesion...");
            break;
             
        }
    }while(opcion!=2);
}
private void mostrarMenuGestionTareasUsuario(){
    int opcion;
    do{
        System.out.println("=== GESTION DE TAREAS ===");
        System.out.println("1. Seleccionar tarea");
        System.out.println("2. Modificar tarea");
        System.out.println("3. Filtrar tareas");
        System.out.println("4. Volver al menu principal");
        System.out.print("Ingrese su opcion: ");
        opcion=validaciones.solicitarOpcionMenu("Opcion invalida",1,4);
        switch(opcion){
            case 1:
            seleccionarTarea();
            break;
            case 2:
            modificarTareasUsuario();
            break;
            case 3:
            filtrarTareasUsuario();
            break;
        }

    
        

    }while (opcion !=4);
}
private void seleccionarTarea(){
    System.out.println("=== SELECCIONAR TAREA ===");
    if(usuarioActual.getTareasAsignadas().isEmpty()){
            System.out.println("No hay tareas asignadas.");
            return;
    }
    System.out.println("Lista de tareas: ");
    for(int i=0; i<usuarioActual.getTareasAsignadas().size();i++){
        System.out.println((i+1)+"."+usuarioActual.getTareasAsignadas().get(i).getNombreTarea());

    }
    int opcion = validaciones.solicitarOpcionMenu("Selecione una tarea( ingrese 0 si desea cancelar):",0,usuarioActual.getTareasAsignadas().size());
    if(opcion==0){
    System.out.println("Operacion cancelada.");
    return;
    }
    Tarea tareaSeleccionada= usuarioActual.getTareasAsignadas().get(opcion-1);
    System.out.println("Tarea seleccionada: "+tareaSeleccionada);

}
private void modificarTareasUsuario(){
    System.out.println("=== MODIFICAR TAREA ===");
    if (usuarioActual.getTareasAsignadas().isEmpty()) {
        System.out.println("No hay tareas asignadas para modificar.");
        return;
    }

    
    System.out.println("Lista de tareas asignadas:");
    for (int i = 0; i < usuarioActual.getTareasAsignadas().size(); i++) {
        System.out.println((i + 1) + ". " + usuarioActual.getTareasAsignadas().get(i).getNombreTarea());
    }

    
    int opcionTarea = validaciones.solicitarOpcionMenu("Seleccione la tarea que desea modificar (0 para cancelar): ", 0, usuarioActual.getTareasAsignadas().size());
    if (opcionTarea == 0) {
        System.out.println("Operación cancelada.");
        return;
    }

    Tarea tareaSeleccionada = usuarioActual.getTareasAsignadas().get(opcionTarea - 1);


    System.out.print("Ingrese el nuevo nombre de la tarea: ");
    String nuevoNombre = scanner.nextLine().trim();
    tareaSeleccionada.setNombreTarea(nuevoNombre);


    System.out.println("Tarea modificada exitosamente: " + tareaSeleccionada);
}

private void filtrarTareasUsuario() {
    System.out.println("=== FILTRAR TAREAS ===");
    System.out.println("1. Filtrar por fecha");
    System.out.println("2. Filtrar por estado");
    System.out.println("3. Volver");
    int opcion = validaciones.solicitarOpcionMenu("Opción inválida", 1, 3);
    switch (opcion) {
        case 1:
            filtrarTareasPorFecha();
            break;
        case 2:
            filtrarTareasPorEstado();
            break;
        case 3:
            System.out.println("Volviendo al menú principal...");
            break;
    }
}
}
