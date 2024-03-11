package consoletask.classes;

import java.time.LocalDate;
import java.util.Scanner;

import consoletask.enums.EstadoTareaEnum;
import consoletask.enums.RolUsuarioEnum;
import consoletask.utils.Validaciones;

public class menu {

    private Usuario usuarioActual;
    private Usuario usuarioInverso;
    private Scanner scanner;
    private Validaciones validaciones;

    public menu(Usuario usuarioActual, Usuario usuarioInverso) {
        this.usuarioActual = usuarioActual;
        this.scanner = new Scanner(System.in);
        this.validaciones = new Validaciones();
        this.usuarioInverso = usuarioInverso;
    }

    public void mostrarMenu() {
        if (usuarioActual.getRolUsuario() == RolUsuarioEnum.ADMIN.getId()) {
            mostrarMenuAdmin();
        } else {
            mostrarMenuUsuario();
        }
    }

    /* MÉTODOS PARA ADMIN */
    private void mostrarMenuAdmin() {

        int opcion;
        do {
            System.out.println("\n===MENU DE ADMINISTRADOR ===");
            System.out.println("1. Gestionar tareas");
            System.out.println("2. Crear tarea");
            System.out.println("3. Salir.");
            System.out.print("Ingrese su opcion: ");
            opcion = validaciones.solicitarOpcionMenu("Opcion invalida", 1, 3);
            switch (opcion) {
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
        } while (opcion != 3);
    }

    private void mostrarMenuGestionTareasAdmin() {
        int opcion;
        System.out.println("\n=== GESTION DE TAREAS ===");
        System.out.println("1. Modificar tarea");
        System.out.println("2. Eliminar tarea");
        System.out.println("3. Filtrar tareas.");
        System.out.println("4. Volver al menu principal");
        System.out.println("Ingrese su opción: ");
        opcion = validaciones.solicitarOpcionMenu("Opcion invalida", 1, 4);

        switch (opcion) {
            case 1:
                modificarTarea();
                break;

            case 2:
                eliminarTarea();
                break;
            case 3:
                filtrarTareas();
                break;
            case 4:
                mostrarMenu();
                break;
        }
    }

    private void crearTarea() {
        System.out.println("\n=== CREAR TAREA===");
        System.out.println("Ingrese el nombre de la tarea: ");
        String nombreTarea;
        do {
            nombreTarea = validaciones.solicitarString("El nombre de la tarea no puede estar vacio.");

            //Validamos que el nombre no exista
            if (usuarioActual.getTareasAsignadas().getTarea() != null && usuarioActual.buscarPorNombre(nombreTarea)) {
                System.out.println("Â¡El nombre de la tarea ya existe, no se puede duplicar!");
            }
        } while (usuarioActual.getTareasAsignadas().getTarea() != null && usuarioActual.buscarPorNombre(nombreTarea));

        LocalDate fechaInicio = LocalDate.now();
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

    private void eliminarTarea() {

        System.out.println("\n=== ELIMINAR TAREA ===");
        if (usuarioActual.getTareasAsignadas().getTarea() == null) {
            System.out.println("No hay tareas para eliminar");
        } else {
            usuarioActual.listar();
            System.out.println("Seleccione la tarea que desea eliminar (Ingrese el ID), u otro nÃºmero si desea cancelar:");

            int opcionTarea = validaciones.solicitarEntero("Ingrese un ID válido");

            if (!(boolean) usuarioActual.buscar(opcionTarea).get("encontrado")) {
                System.out.println("No se ha encontrado tarea con ID " + opcionTarea);
                return;
            }

            if (validaciones.confirmarAccion("Â¿Desea eliminar la tarea?") && validaciones.confirmarAccion("Â¿Esta seguro de eliminar la tarea?")) {
                usuarioActual.eliminarPorID(opcionTarea);
            } else {
                System.out.println("Cancelando...");
            }
        }
    }

    private void menuAdminModificarTarea(Nodo tarea) {

        System.out.println("1. Fecha de finalización\n2. Estado\n3. Porcentaje de progreso");
        int opcion = validaciones.solicitarOpcionMenu("Ingrese una opción válida", 1, 3);
        switch (opcion) {
            case 1:
                System.out.println("\nIngrese la fecha de finalización la cual desea modificar la tarea:");
                LocalDate fecha = validaciones.solicitarFechaMayor("Hubo un error al obtener la fecha.", LocalDate.now());
                usuarioActual.modificarFechaFinTarea(tarea, fecha);
                break;
            case 2:
                System.out.println("\nIngrese el estado al cual desea modificar la tarea:");
                System.out.println("1. Pendiente\n2. En progreso\n3. Finalizada");
                int estado = validaciones.solicitarOpcionMenu("Estado inválido", 1, 3);
                usuarioActual.modificarEstadoTarea(tarea, estado);
                break;
            case 3:
                System.out.println("\nIngrese el porcentaje de progreso al cual desea modificar la tarea:");
                int porcentaje = validaciones.solicitarOpcionMenu("Porcentaje de progreso inválido", 0, 100);
                usuarioActual.modificarPorcentajeTarea(tarea, porcentaje);
                break;
        }
        System.out.println("¡Tarea modificada exitosamente!");

    }

    /* MÉTODOS PARA USUARIO */
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
                    mostrarMenuGestionTareasUsuario();
                    break;
                case 2:
                    System.out.println("Cerrando sesion...");
                    break;
            }
        } while (opcion != 2);
    }

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
                    modificarTarea();
                    break;
                case 2:
                    filtrarTareas();
                    break;
                case 3:
                    mostrarMenu();
            }

        } while (opcion != 3);
    }

    private void menuUsuarioModificarTarea(Nodo tarea) {

        System.out.println("1. Estado\n2.Porcentaje de progreso");
        System.out.println("Ingrese el dato que desea modificar de la tarea: ");

        int opcion = validaciones.solicitarOpcionMenu("Ingrese una opción válida", 1, 2);
        switch (opcion) {
            case 1:
                System.out.println("\nIngrese el estado al cual desea modificar la tarea:");
                System.out.println("1. Pendiente\n2. En progreso\n3. Finalizada");
                int estado = validaciones.solicitarOpcionMenu("Estado inválido", 1, 3);
                usuarioActual.modificarEstadoTarea(tarea, estado);
                break;
            case 2:
                System.out.println("\nIngrese el porcentaje de progreso al cual desea modificar la tarea:");
                int porcentaje = validaciones.solicitarOpcionMenu("Porcentaje de progreso inválido", 0, 100);
                usuarioActual.modificarPorcentajeTarea(tarea, porcentaje);
                break;
        }
        System.out.println("¡Tarea modificada exitosamente!");

    }

    /* MÉTODOS COMPARTIDOS */
    private void modificarTarea() {

        System.out.println("\n=== MODIFICAR TAREA ===");
        if (usuarioActual.getTareasAsignadas().getTarea() == null) {
            System.out.println("No hay tareas para modificar.");
            return;
        }

        usuarioActual.listar();
        System.out.println("Seleccione la tarea que desea modificar (Ingrese el ID), u otro número si desea cancelar: ");

        int idTarea = validaciones.solicitarEntero("Ingrese un ID válido.");

        boolean tareaEncontradaBool = (boolean) usuarioActual.buscar(idTarea).get("encontrado");

        if (!tareaEncontradaBool) {
            System.out.println("No se ha encontrado tarea con ID " + idTarea);
            return;
        }

        Nodo tareaEncontradaNodo = (Nodo) usuarioActual.buscar(idTarea).get("nodo");

        if (tareaEncontradaNodo.getTarea().getEstadoTarea() == EstadoTareaEnum.FINALIZADA.getId()) {
            System.out.println("No es posible modificar el porcentaje a una tarea finalizada.");
            return;
        }

        System.out.println("\nIngrese la opción correspondiente al dato que desea modificar en la tarea.");
        if (usuarioActual.getRolUsuario() == RolUsuarioEnum.ADMIN.getId()) {
            menuAdminModificarTarea(tareaEncontradaNodo);
        } else {
            menuUsuarioModificarTarea(tareaEncontradaNodo);
        }
    }

    private void filtrarTareas() {

        Nodo cabezaTareas = usuarioActual.getTareasAsignadas();

        if (cabezaTareas.getTarea() == null) {
            System.out.println("\nNo hay tareas para filtrar");
            return;
        }

        System.out.println("\n=== FILTRAR TAREAS ===");
        System.out.println("1. Filtrar por fecha");
        System.out.println("2. Filtrar por estado");
        System.out.println("3. Volver");
        int opcion = validaciones.solicitarOpcionMenu("OpciÃ³n invÃ¡lida", 1, 3);
        switch (opcion) {
            case 1:
                filtrarTareasPorFecha(cabezaTareas);
                break;
            case 2:
                filtrarTareasPorEstado(cabezaTareas);
                break;
            case 3:
                System.out.println("Volviendo al menÃº principal...");
                break;
        }
    }

    private void filtrarTareasPorFecha(Nodo cabezaTareas) {

        System.out.println("\n=== FILTRAR TAREAS POR FECHA ===");

        // Solicita las dos fechas para filtrar
        System.out.println("Ingrese el rango de fechas con el cual desea filtrar (dd/MM/yyyy): ");
        LocalDate fecha1 = validaciones.solicitarFecha();
        LocalDate fecha2 = validaciones.solicitarFechaMayor("La fecha de fin debe ser posterior a la fecha de inicio.", fecha1);

        Nodo resultadoFiltros = usuarioActual.filtrarPorFecha(fecha1, fecha2, cabezaTareas);
        usuarioActual.listarConFiltros(resultadoFiltros);

    }

    private void filtrarTareasPorEstado(Nodo cabezaTareas) {

        System.out.println("\n=== FILTRAR TAREAS POR ESTADO ===");

        System.out.println("Seleccione uno de los estados de tarea por el cual desea filtrar, o cualquier otro número para volver atrás.");
        System.out.println("1. Pendiente\n2. En progreso\n3. Finalizada");
        System.out.println("Ingrese su opción: ");

        int opcion = validaciones.solicitarEntero("Error de selección");

        if (opcion > 3) {
            filtrarTareas();
        } else {
            Nodo resultadoFiltros = usuarioActual.filtrarPorEstado(cabezaTareas, opcion);
            usuarioActual.listarConFiltros(resultadoFiltros);
        }

    }
}
