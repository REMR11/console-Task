package consoletask.classes;

import java.time.LocalDate;
import java.util.Scanner;

import consoletask.enums.EstadoTareaEnum;
import consoletask.enums.RolUsuarioEnum;
import consoletask.utils.Validaciones;

public class menu {
//Definicion de variables para el menu y sus metodos 

    private Usuario usuarioActual; // Usuario actual que accede a menu
    private Usuario usuarioInverso; //Usuario inverso para ciertas operaciones
    private Scanner scanner; 
    private Validaciones validaciones; // Clase de utilidades para validaciones

//Constructor de clase menu 
    public menu(Usuario usuarioActual, Usuario usuarioInverso) {
        this.usuarioActual = usuarioActual;
        this.scanner = new Scanner(System.in);
        this.validaciones = new Validaciones();
        this.usuarioInverso = usuarioInverso;
    }
//Metodo para mostrar el menu principal segun el rol del usuario

    public void mostrarMenu() {
        if (usuarioActual.getRolUsuario() == RolUsuarioEnum.ADMIN.getId()) {
            mostrarMenuAdmin();
        } else {
            mostrarMenuUsuario();
        }
    }

    /* M�TODOS PARA ADMIN */
    //Metodo para mostrar menu de administrador
    private void mostrarMenuAdmin() {

        int opcion;
        //ciclo que se ejecuta hasta que el usuario elija salir del menu
        do {
            System.out.println("\n===MENU DE ADMINISTRADOR ===");
            System.out.println("1. Gestionar tareas");
            System.out.println("2. Crear tarea");
            System.out.println("3. Salir.");
            System.out.print("Ingrese su opcion: ");
            //solicitar al que se ingrese una opcion y asegurarse de que sea valida
            opcion = validaciones.solicitarOpcionMenu("Opcion invalida", 1, 3);
            switch (opcion) {
                case 1:
                    mostrarMenuGestionTareasAdmin();  //mostrar el menu de gestion de tareas
                    break;

                case 2:
                    crearTarea(); // metodo para crear una tarea
                    break;

                case 3:
                    System.out.println("Saliendo..."); //mensaje de salida
                    break;

            }
        } while (opcion != 3); //continuar el ciclo hasta que se elija salir del menu
    }
//Metodo para mostrar el menu de gestion de tareas para el administrador

    private void mostrarMenuGestionTareasAdmin() {
        int opcion;
        System.out.println("\n=== GESTION DE TAREAS ===");
        System.out.println("1. Modificar tarea");
        System.out.println("2. Eliminar tarea");
        System.out.println("3. Filtrar tareas.");
        System.out.println("4. Volver al menu principal");
        System.out.println("Ingrese su opci�n: ");
        opcion = validaciones.solicitarOpcionMenu("Opcion invalida", 1, 4);

        switch (opcion) {
            case 1:
                modificarTarea(); //llamada al metodo modificar tarea
                break;

            case 2:
                eliminarTarea(); //llamada al metodo eliminar una tarea
                break;
            case 3:
                filtrarTareas(); //llamada al metodo de filtrar tareas
                break;
            case 4:
                mostrarMenu(); //volver al menu principal 
                break;
        }
    }
//Metodo para crear una tarea
    private void crearTarea() {
        System.out.println("\n=== CREAR TAREA===");
        System.out.println("Ingrese el nombre de la tarea: "); //Solicitar nombre de la tarea
        String nombreTarea;
        do {
            nombreTarea = validaciones.solicitarString("El nombre de la tarea no puede estar vacio.");

            //Validamos que el nombre no exista
            if (usuarioActual.getTareasAsignadas().getTarea() != null && usuarioActual.buscarPorNombre(nombreTarea)) {
                System.out.println("¡El nombre de la tarea ya existe, no se puede duplicar!");
            }
        } while (usuarioActual.getTareasAsignadas().getTarea() != null && usuarioActual.buscarPorNombre(nombreTarea));

        LocalDate fechaInicio = LocalDate.now();
        //Solicitar fecha de vencimiento de la tarea
        System.out.println("Ingrese la fecha de vencimiento de la tarea en formato (dd/MM/yyyy -> 01/01/2001): ");
        LocalDate fechaFin = validaciones.solicitarFechaMayor("La fecha de fin debe ser posterior a la fecha de inicio.", fechaInicio);
        int estado = EstadoTareaEnum.EN_PROGRESO.getId();
        int porcentajeProgreso = 0;

        int id = 1;

        //Generamos el id de la tarea en caso no sea la primera tarea
        if (usuarioActual.getTareasAsignadas().getTarea() != null) {
            id = usuarioActual.getTamanio() + 1;
            System.out.println(id);
        }
        Tarea nuevaTarea = new Tarea(id, nombreTarea, fechaInicio, fechaFin, EstadoTareaEnum.PENDIENTE.getId(), 0);
        usuarioActual.agregarAlFinal(nuevaTarea, usuarioInverso);
    }
//Metodo para Eliminar una tarea
    private void eliminarTarea() {

        System.out.println("\n=== ELIMINAR TAREA ===");
        if (usuarioActual.getTareasAsignadas().getTarea() == null) {
            System.out.println("No hay tareas para eliminar");
        } else {
            //Listar las tareas disponibles para eliminar
            usuarioActual.listar();
            System.out.println("Seleccione la tarea que desea eliminar (Ingrese el ID), u otro número si desea cancelar:");

            int opcionTarea = validaciones.solicitarEntero("Ingrese un ID v�lido");
//Verificar si la tarea existe
            if (!(boolean) usuarioActual.buscar(opcionTarea).get("encontrado")) {
                System.out.println("No se ha encontrado tarea con ID " + opcionTarea);
                return;
            }
//confirma la eliminacion de la tarea
            if (validaciones.confirmarAccion("¿Desea eliminar la tarea?") && validaciones.confirmarAccion("¿Esta seguro de eliminar la tarea?")) {
                usuarioActual.eliminarPorID(opcionTarea);
            } else {
                System.out.println("Cancelando...");
            }
        }
    }
//Metodo para modificar una tarea Administrador
    private void menuAdminModificarTarea(Nodo tarea) {
//Mostrar las opciones disponibles para modificar la tarea
        System.out.println("1. Fecha de finalizaci�n\n2. Estado\n3. Porcentaje de progreso");
        int opcion = validaciones.solicitarOpcionMenu("Ingrese una opci�n v�lida", 1, 3);
        //Segun la opcion elegida por el usuario ejecutar una accion
        switch (opcion) {
            case 1:
            //Modificar la fecha de finalizacion de la tarea
                System.out.println("\nIngrese la fecha de finalizaci�n la cual desea modificar la tarea:");
                LocalDate fecha = validaciones.solicitarFechaMayor("Hubo un error al obtener la fecha.", LocalDate.now());
                usuarioActual.modificarFechaFinTarea(tarea, fecha);
                break;
            case 2:
            //Modificar el estado de la tarea
                System.out.println("\nIngrese el estado al cual desea modificar la tarea:");
                System.out.println("1. Pendiente\n2. En progreso\n3. Finalizada");
                int estado = validaciones.solicitarOpcionMenu("Estado inv�lido", 1, 3);
                usuarioActual.modificarEstadoTarea(tarea, estado);
                break;
            case 3:
            //Modificar el porcentaje de progreso de la tarea
                System.out.println("\nIngrese el porcentaje de progreso al cual desea modificar la tarea:");
                int porcentaje = validaciones.solicitarOpcionMenu("Porcentaje de progreso inv�lido", 0, 100);
                usuarioActual.modificarPorcentajeTarea(tarea, porcentaje);
                break;
        }
        System.out.println("�Tarea modificada exitosamente!");

    }

    /* M�TODOS PARA USUARIO */
    private void mostrarMenuUsuario() {
        int opcion;
        do {
            System.out.println("\n=== MENU DE USUARIO ===");
            System.out.println("1. Gestionar tareas");
            System.out.println("2. Cerrar sesion");
            System.out.print("Ingrese su opcion: ");
            opcion = validaciones.solicitarOpcionMenu("Opcion invalida", 1, 2);
            switch (opcion) {
                case 1:
                    mostrarMenuGestionTareasUsuario(); //Llamada al metedo para mostar el menu de gestion de tareas
                    break;
                case 2:
                    System.out.println("Cerrando sesion..."); // Indica el cierre de sesion
                    break;
            }
        } while (opcion != 2);  //continua mostrando el menu de usuario hasta que se elija cerrar sesion 
    }
//Metodo mostrar menu de tareas de Usuario
    private void mostrarMenuGestionTareasUsuario() {
        int opcion;
        do {
            System.out.println("\n=== GESTION DE TAREAS ===");
//            System.out.println("1. Seleccionar tarea");
            System.out.println("1. Modificar tarea");
            System.out.println("2. Filtrar tareas");
            System.out.println("3. Volver al menu principal");
            System.out.print("Ingrese su opcion: ");
            opcion = validaciones.solicitarOpcionMenu("Opcion invalida", 1, 3);
            switch (opcion) {
                case 1:
                    modificarTarea(); //Llama al metodo modificar tarea
                    break;
                case 2:
                    filtrarTareas(); //Llama al metodo para filtrar tareas
                    break;
                case 3:
                    mostrarMenu(); //llama al metodo para mostrar menu principal
            }

        } while (opcion != 3);
    }
//Metodo modificar una tarea Usuario
    private void menuUsuarioModificarTarea(Nodo tarea) {
//Muestra las opciones disponibles
        System.out.println("1. Estado\n2.Porcentaje de progreso");
        System.out.println("Ingrese el dato que desea modificar de la tarea: ");

        int opcion = validaciones.solicitarOpcionMenu("Ingrese una opci�n v�lida", 1, 2);
        switch (opcion) {
            case 1:
            //Modificar el estado de la tarea
                System.out.println("\nIngrese el estado al cual desea modificar la tarea:");
                System.out.println("1. Pendiente\n2. En progreso\n3. Finalizada");
                int estado = validaciones.solicitarOpcionMenu("Estado inv�lido", 1, 3);
                usuarioActual.modificarEstadoTarea(tarea, estado);
                break;
            case 2:
            //Modificar el porcentaje de progreso de la tarea
                System.out.println("\nIngrese el porcentaje de progreso al cual desea modificar la tarea:");
                int porcentaje = validaciones.solicitarOpcionMenu("Porcentaje de progreso inv�lido", 0, 100);
                usuarioActual.modificarPorcentajeTarea(tarea, porcentaje);
                break;
        }
        System.out.println("�Tarea modificada exitosamente!");

    }

    /* M�TODOS COMPARTIDOS */
    //Metodo para modificar una tarea
    private void modificarTarea() {

        System.out.println("\n=== MODIFICAR TAREA ===");
        //verificar si hay tareas disponibles para modificar
        if (usuarioActual.getTareasAsignadas().getTarea() == null) {
            System.out.println("No hay tareas para modificar.");
            return; //salir del metodo si no hay tareas
        }
//Mostrar la lista disponible para que el usuario elija la que desea modificar
        usuarioActual.listar();
        System.out.println("Seleccione la tarea que desea modificar (Ingrese el ID), u otro n�mero si desea cancelar: ");
//solicitar al usuario que ingrese el ID  de la tarea que desea modificar
        int idTarea = validaciones.solicitarEntero("Ingrese un ID v�lido.");
//buscar la tarea con el ID proporcionado
        boolean tareaEncontradaBool = (boolean) usuarioActual.buscar(idTarea).get("encontrado");
//verificar si se encontro la tarea
        if (!tareaEncontradaBool) {
            System.out.println("No se ha encontrado tarea con ID " + idTarea);
            return;
        }
//obtener el nodo de la tarea encontrada
        Nodo tareaEncontradaNodo = (Nodo) usuarioActual.buscar(idTarea).get("nodo");
//verificar si la tarea esta finalizada y no se puede modificar
        if (tareaEncontradaNodo.getTarea().getEstadoTarea() == EstadoTareaEnum.FINALIZADA.getId()) {
            System.out.println("No es posible modificar el porcentaje a una tarea finalizada.");
            return;
        }
//solicitar al usuario que elijha que aspecto de la tarea desea modificar
        System.out.println("\nIngrese la opci�n correspondiente al dato que desea modificar en la tarea.");
        if (usuarioActual.getRolUsuario() == RolUsuarioEnum.ADMIN.getId()) {
            menuAdminModificarTarea(tareaEncontradaNodo); //llamada al metodo de modificacion de tarea para ADMIN
        } else {
            menuUsuarioModificarTarea(tareaEncontradaNodo); //llamda al metodo de modificacion de tarea para Usuario
        }
    }
//Metodo para filtrar tareas
    private void filtrarTareas() {
//obtener la lista de tareas asignadas al usuario
        Nodo cabezaTareas = usuarioActual.getTareasAsignadas();
//verificar si hay tareas disponibles para filtrar
        if (cabezaTareas.getTarea() == null) {
            System.out.println("\nNo hay tareas para filtrar");
            return; // salir del metodo si no hay tareas disponibles para filtrar
        }

        System.out.println("\n=== FILTRAR TAREAS ===");
        System.out.println("1. Filtrar por fecha");
        System.out.println("2. Filtrar por estado");
        System.out.println("3. Volver");
        //solicitar al usuario que elija una opcion de filtrado
        int opcion = validaciones.solicitarOpcionMenu("Opción inválida", 1, 3);
        switch (opcion) {
            case 1:
                filtrarTareasPorFecha(cabezaTareas); //llamado al metodo de filtrar por fecha
                break;
            case 2:
                filtrarTareasPorEstado(cabezaTareas); //llamado al metodo de filtrar por estado
                break;
            case 3:
                System.out.println("Volviendo al menú principal..."); //volviendo al menu principal
                break;
        }
    }
//metodo para filtrar tareas por fecha
    private void filtrarTareasPorFecha(Nodo cabezaTareas) {

        System.out.println("\n=== FILTRAR TAREAS POR FECHA ===");

        // Solicita las dos fechas para filtrar
        System.out.println("Ingrese el rango de fechas con el cual desea filtrar (dd/MM/yyyy): ");
        //solicitar la primera fecha
        LocalDate fecha1 = validaciones.solicitarFecha(); 
        //solicitar la segunda fecha 
        LocalDate fecha2 = validaciones.solicitarFechaMayor("La fecha de fin debe ser posterior a la fecha de inicio.", fecha1);
//filtrar las tareas dentro del rango de fechas especificado
        Nodo resultadoFiltros = usuarioActual.filtrarPorFecha(fecha1, fecha2, cabezaTareas);
      //mostar las tareas filtradas al usuario
        usuarioActual.listarConFiltros(resultadoFiltros);

    }
//metodo para filtrar tareas por estado
    private void filtrarTareasPorEstado(Nodo cabezaTareas) {

        System.out.println("\n=== FILTRAR TAREAS POR ESTADO ===");

        System.out.println("Seleccione uno de los estados de tarea por el cual desea filtrar, o cualquier otro n�mero para volver atr�s.");
        System.out.println("1. Pendiente\n2. En progreso\n3. Finalizada");
        System.out.println("Ingrese su opci�n: ");
//soliicitar al usuario que elija una opcio de estado de tarea
        int opcion = validaciones.solicitarEntero("Error de selecci�n");
//verificar si la opcion  ingresada es mayor que 3 (valor invalido)
        if (opcion > 3) {
            filtrarTareas();//volver al menu de filtrado de tareas si se ingresa una opcion invalida
        } else {
            //filtrar las tareas segun el estado seleccionado por el usuario
        Nodo resultadoFiltros = usuarioActual.filtrarPorEstado(cabezaTareas, opcion);
      //mostrar las tareas filtradas por usuario     
        usuarioActual.listarConFiltros(resultadoFiltros);
        }

    }
}
