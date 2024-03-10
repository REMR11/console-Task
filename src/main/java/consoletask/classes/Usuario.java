/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consoletask.classes;

import java.time.LocalDate;
import java.util.List;

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
     * @return true o false en caso que encuentre el valor o no lo encuentre;
     */
    public boolean buscar(int referencia) {
        // Crea una copia de la lista.
        Nodo aux = this.tareasAsignadas;
        // Bandera para indicar si el valor existe.
        boolean encontrado = false;
        // Recorre la lista hasta encontrar el elemento o hasta 
        // llegar al final de la lista.
        while (aux != null && encontrado != true) {
            // Consulta si el valor del nodo es igual al de referencia.
            if (referencia == aux.getTarea().getId()) {
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
            System.out.println("null");
        } else {
            System.out.println("No hay tareas creadas");
        }
    }

    /**
     * MÃ©todo para agregar una Tarea al final de la lista
     *
     * @param tarea Tarea a agregar
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
            if (this.buscar(tarea.getId())) {
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
        }
    }

    /**
     * MÃ©todo para copiar al otro Usuario la lista de tareas del otro
     *
     * @param usuario El usuario al cual se le va copiar la lista de tra
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
        if (buscar(referencia)) {
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
     * Método para filtrar tareas por fecha de fin
     * @param fecha1 Primera fecha del rango a filtrar
     * @param fecha2 Segunda fecha del rango a filtrar
     * @param cabezaTareas Nodo cabeza de la lista original de tareas
     * @return Una copia de la lista de tareas, vacía o con los resultados obtenidos
     */
    public Nodo filtrarPorFecha(LocalDate fecha1, LocalDate fecha2, Nodo cabezaTareas) {

        Nodo copiaCabeza = new Nodo();

        // Recorrido de lista de tareas
        while (cabezaTareas != null) {

            // Guardado local de fechaFin
            LocalDate fechaFin = cabezaTareas.getTarea().getFechaFin();

            // Comparación de fechaFin de tarea con fechas ingresadas para filtro
            if (fechaFin.isAfter(fecha1) && fechaFin.isBefore(fecha2)) {

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
     * @param cabezaTareas Nodo cabeza de la lista original de tareas
     * @param estado ID de Estado por cual se desea filtrar
     * @return Una copia de la lista de tareas, vacía o con los resultados obtenidos
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
     * @param cabezaFiltro Nodo cabeza de la lista filtrada
     */
    public void listarConFiltros(Nodo cabezaFiltro) {

        Nodo aux = cabezaFiltro;

        if (aux.getTarea() == null) {
            System.out.println("No se encontraron tareas con los filtros proporcionados.");
            return;
        }

        System.out.println("=== TAREAS ENCONTRADAS === ");
        while (aux != null) {
            System.out.print(aux.getTarea().toString() + "\n");
            aux = aux.getSiguiente();
        }

    }
}
