/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consoletask.classes;

import consoletask.enums.EstadoTareaEnum;
import consoletask.utils.Validaciones;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Oscar Orellana
 */
public class Usuario {

    private String nombreCompleto;
    private String userName;
    private int rolUsuario;
    private Nodo tareasAsignadas;
    private int tamanio;

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String userName, int rolUsuario, Nodo tareasAsignadas) {
        this.nombreCompleto = nombreCompleto;
        this.userName = userName;
        this.rolUsuario = rolUsuario;
        this.tareasAsignadas = tareasAsignadas;
        this.tamanio = 0;
    }

    public int getTamanio() {
        return tamanio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(int rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public Nodo getTareasAsignadas() {
        return tareasAsignadas;
    }

    public void setTareasAsignadas(Nodo tareasAsignadas) { //TEMPORAL MIENTRAS NO SE CREAN CLASES NODOS
        this.tareasAsignadas = tareasAsignadas;
    }

    /**
     * MÃ©todo para buscar una tarea por id de referencia
     *
     * @param referencia ID de la tarea a buscar
     * @return Un mapa clave-valor String-object para devolver tanto la bandera
     * de éxito, así como el nodo encontrado
     */
    public Map<String, Object> buscar(int referencia) {

        // Inicialización del mapa de retorno
        Map<String, Object> returnMap = new HashMap<>();

        // Crea una copia de la lista.
        Nodo aux = this.tareasAsignadas;
        // Bandera para indicar si el valor existe.
        boolean encontrado = false;
        // Establece llaves por defecto al mapa
        returnMap.put("encontrado", encontrado);
        returnMap.put("nodo", aux);
        // Recorre la lista hasta encontrar el elemento o hasta 
        // llegar al final de la lista.
        while (aux != null && encontrado != true) {
            // Consulta si el valor del nodo es igual al de referencia.
            if (referencia == aux.getTarea().getId()) {
                // Canbia el valor de la bandera.
                encontrado = true;
                // Modifica las llaves del mapa
                returnMap.put("encontrado", encontrado);
                returnMap.put("nodo", aux);
            } else {
                // Avansa al siguiente. nodo.
                aux = aux.getSiguiente();
            }
        }
        // Retorna el resultado de la bandera.
        return returnMap;
    }

    /**
     * MÃ©todo para buscar una tarea por id de referencia
     *
     * @param referencia ID de la tarea a buscar
     * @return true o false en caso que encuentre el valor o no lo encuentre;
     */
    public boolean buscarPorNombre(String referencia) {
        referencia = referencia.toLowerCase();
        // Crea una copia de la lista.
        Nodo aux = this.tareasAsignadas;
        // Bandera para indicar si el valor existe.
        boolean encontrado = false;
        // Recorre la lista hasta encontrar el elemento o hasta 
        // llegar al final de la lista.
        while (aux != null && encontrado != true) {
            // Consulta si el valor del nodo es igual al de referencia.
            if (referencia.equals(aux.getTarea().getNombreTarea().toLowerCase())) {
                // Canbia el valor de la bandera.
                encontrado = true;
            } else {
                // Avansa al siguiente. nodo.
                aux = aux.getSiguiente();
            }
        }
        // Retorna el resultado de la bandera.
        return encontrado;
    }

    /**
     * MÃ©todo para listar todas las tareas creadas
     */
    public void listar() {
        // Verifica si la lista contiene elementos.
        if (this.tareasAsignadas.getTarea() != null) {
            // Crea una copia de la lista.
            Nodo aux = this.tareasAsignadas;
            // Posicion de los elementos de la lista.
            int i = 0;
            // Recorre la lista hasta el final.
            while (aux != null) {
                // Imprime en pantalla el valor del nodo.
                System.out.print(i + 1 + ".[ " + aux.getTarea().toString() + " ]" + " ->  \n");
                // Avanza al siguiente nodo.
                aux = aux.getSiguiente();
                // Incrementa el contador de la posiÃ³n.
                i++;
            }
        } else {
            System.out.println("No hay tareas creadas");
        }
    }

    /**
     * MÃ©todo para agregar una Tarea al final de la lista
     *
     * @param tarea Tarea a agregar
     * @param usuarioInverso Usuario al que se le asignará la tarea
     */
    public void agregarAlFinal(Tarea tarea, Usuario usuarioInverso) {
        // Define un nuevo nodo.
        Nodo nuevo = new Nodo();
        // Agrega al valor al nodo.
        nuevo.setTarea(tarea);
        boolean exito = false;

        // Consulta si la lista esta vacia.
        if (this.tareasAsignadas.getTarea() == null) {
            // Inicializa la lista agregando como inicio al nuevo nodo.
            this.tareasAsignadas = nuevo;
            exito = true;
            // Caso contrario recorre la lista hasta llegar al ultimo nodo
            //y agrega el nuevo.
        } else {
            //Consultamos si ya existe la tarea a ingresar
            if ((boolean) this.buscar(tarea.getId()).get("encontrado")) {
                System.out.println("Â¡El id de tarea ya existe, no se puede agregar!");
                return;
            };

            if (this.buscarPorNombre(tarea.getNombreTarea())) {
                System.out.println("Â¡El nombre de la tarea ya existe, no se puede agregar!");
                return;
            }

            // Crea ua copia de la lista.
            Nodo aux = this.tareasAsignadas;
            // Recorre la lista hasta llegar al ultimo nodo.
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            // Agrega el nuevo nodo al final de la lista.
            aux.setSiguiente(nuevo);
            exito = true;
        }
        if (exito) {
            System.out.println("Â¡Tarea creada exitosamente!");
            this.listar();
            this.tamanio++;
            this.copiarLista(usuarioInverso);
        }
    }

    /**
     * MÃ©todo para copiar al otro Usuario la lista de tareas del otro
     *
     * @param usuarioInverso El usuario al cual se le va copiar la lista de tra
     */
    public void copiarLista(Usuario usuarioInverso) {
        usuarioInverso.setTareasAsignadas(this.tareasAsignadas);
    }

    /**
     * Elimina una tarea dependiendo del ID de referencia que se pase
     *
     * @param referencia ID de la tarea a pasar como referenciaS
     */
    public void eliminarPorID(int referencia) {
        // Consulta si el valor de referencia existe en la lista.
        if ((boolean) buscar(referencia).get("encontrado")) {
            // Consulta si el nodo a eliminar es el pirmero
            if (this.tareasAsignadas.getTarea().getId() == referencia) {
                // El primer nodo apunta al siguiente.
                this.tareasAsignadas = this.tareasAsignadas.getSiguiente();
            } else {
                // Crea ua copia de la lista.
                Nodo aux = this.tareasAsignadas;
                // Recorre la lista hasta llegar al nodo anterior 
                // al de referencia.
                while (aux.getSiguiente().getTarea().getId() != referencia) {
                    aux = aux.getSiguiente();
                }
                // Guarda el nodo siguiente del nodo a eliminar.
                Nodo siguiente = aux.getSiguiente().getSiguiente();
                // Enlaza el nodo anterior al de eliminar con el 
                // sguiente despues de el.
                aux.setSiguiente(siguiente);
            }
            System.out.println("Â¡La tarea se elimino con exito!");
            // Disminuye el contador de tamaÃ±o de la lista.
            tamanio--;
        } else {
            System.out.println("No existe la tarea con el id: " + referencia);
        }
    }

    /**
     * Modifica la fecha de fin de la tarea obtenida
     *
     * @param tarea Tarea obtenida
     * @param nuevaFecha Nueva fecha para modificar
     */
    public void modificarFechaFinTarea(Nodo tarea, LocalDate nuevaFecha) {

        tarea.getTarea().setFechaFin(nuevaFecha);
        System.out.println("¡Tarea modificada exitosamente!");

    }

    /**
     * Modifica el porcentaje de la tarea obtenida
     *
     * @param tarea Tarea obtenida
     * @param porcentaje Nuevo porcentaje para modificar
     */
    public void modificarPorcentajeTarea(Nodo tarea, int porcentaje) {

        // Valida si el porcentaje ingresado es menor al actual
        if (porcentaje < tarea.getTarea().getPorcentajeProgreso()) {
            System.out.println("No es posible disminuir el porcentaje de avance.");
            return;
        }

        if (porcentaje > 0) {
            tarea.getTarea().setEstadoTarea(EstadoTareaEnum.EN_PROGRESO.getId());
        }

        tarea.getTarea().setPorcentajeProgreso(porcentaje);
        // Valida si se ingresó porcentaje 100, para también establecer la tarea como finalizada
        if (porcentaje == 100) {
            tarea.getTarea().setEstadoTarea(EstadoTareaEnum.FINALIZADA.getId());
        }
        System.out.println("¡Tarea modificada exitosamente!");

    }

    /**
     * Modifica el estado de la tarea obtenida
     *
     * @param tarea Tarea obtenida
     * @param estado Nuevo estado para modificar
     */
    public void modificarEstadoTarea(Nodo tarea, int estado) {
        tarea.getTarea().setEstadoTarea(estado);
        // Valida si el estado que se ingresó es FINALIZADA, para también establecer el porcentaje al 100
        if (estado == EstadoTareaEnum.FINALIZADA.getId()) {
            tarea.getTarea().setPorcentajeProgreso(100);
        }
        System.out.println("¡Tarea modificada exitosamente!");

    }

    /**
     * Método para filtrar tareas por fecha de fin
     *
     * @param fecha1 Primera fecha del rango a filtrar
     * @param fecha2 Segunda fecha del rango a filtrar
     * @param cabezaTareas Nodo cabeza de la lista original de tareas
     * @return Una copia de la lista de tareas, vacía o con los resultados
     * obtenidos
     */
    public Nodo filtrarPorFecha(LocalDate fecha1, LocalDate fecha2, Nodo cabezaTareas) {

        Nodo copiaCabeza = new Nodo();
        Validaciones validaciones = new Validaciones();

        // Recorrido de lista de tareas
        while (cabezaTareas != null) {

            // Guardado local de fechaFin
            LocalDate fechaFin = cabezaTareas.getTarea().getFechaFin();

            // Comparación de fechaFin de tarea con fechas ingresadas para filtro
            if (validaciones.filtrarPorFechas(fechaFin, fecha1, fecha2)) {

                Tarea tareaEncontrada = cabezaTareas.getTarea();
                // Duplicación de lista
                // Si copia está vacía, guarda tarea encontrado al final
                if (copiaCabeza.getTarea() == null) {
                    copiaCabeza.setTarea(tareaEncontrada);
                } else {
                    // Sino, va recorriendo hasta encontrar el último nodo y guardar la tarea encontrada
                    Nodo aux = copiaCabeza;
                    while (aux.getSiguiente() != null) {
                        aux = aux.getSiguiente();
                    }
                    aux.setSiguiente(new Nodo(tareaEncontrada));
                }
                // Itera a la siguiente tarea
                cabezaTareas = cabezaTareas.getSiguiente();
            } else {
                // Itera a a la siguiente tarea
                cabezaTareas = cabezaTareas.getSiguiente();
            }

        }

        return copiaCabeza;

    }

    /**
     * Método para filtrar tareas por estado
     *
     * @param cabezaTareas Nodo cabeza de la lista original de tareas
     * @param estado ID de Estado por cual se desea filtrar
     * @return Una copia de la lista de tareas, vacía o con los resultados
     * obtenidos
     */
    public Nodo filtrarPorEstado(Nodo cabezaTareas, int estado) {

        Nodo copiaCabeza = new Nodo();

        while (cabezaTareas != null) {
            int estadoTarea = cabezaTareas.getTarea().getEstadoTarea();

            if (estado == estadoTarea) {
                Tarea tareaEncontrada = cabezaTareas.getTarea();

                if (copiaCabeza.getTarea() == null) {
                    copiaCabeza.setTarea(tareaEncontrada);
                } else {
                    // Sino, va recorriendo hasta encontrar el último nodo y guardar la tarea encontrada
                    Nodo aux = copiaCabeza;
                    while (aux.getSiguiente() != null) {
                        aux = aux.getSiguiente();
                    }
                    aux.setSiguiente(new Nodo(tareaEncontrada));
                }
                // Itera a la siguiente tarea
                cabezaTareas = cabezaTareas.getSiguiente();
            } else {
                // Itera a a la siguiente tarea
                cabezaTareas = cabezaTareas.getSiguiente();
            }

        }

        return copiaCabeza;

    }

    /**
     * Método que imprime la lista filtrada de tareas
     *
     * @param cabezaFiltro Nodo cabeza de la lista filtrada
     */
    public void listarConFiltros(Nodo cabezaFiltro) {

        Nodo aux = cabezaFiltro;

        if (aux.getTarea() == null) {
            System.out.println("No se encontraron tareas con los filtros proporcionados.");
            return;
        }

        System.out.println("\n=== TAREAS ENCONTRADAS === ");
        while (aux != null) {
            System.out.print(aux.getTarea().toString() + "\n");
            aux = aux.getSiguiente();
        }

    }
}
