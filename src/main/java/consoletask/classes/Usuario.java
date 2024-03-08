/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consoletask.classes;

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
     * Método para buscar una tarea por id de referencia
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
     * Método para buscar una tarea por id de referencia
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
     * Método para listar todas las tareas creadas
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
                // Incrementa el contador de la posión.
                i++;
            }
            System.out.println("null");
        } else {
            System.out.println("No hay tareas creadas");
        }
    }

    /**
     * Método para agregar una Tarea al final de la lista
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
                System.out.println("¡El id de tarea ya existe, no se puede agregar!");
                return;
            };

            if (this.buscarPorNombre(tarea.getNombreTarea())) {
                System.out.println("¡El nombre de la tarea ya existe, no se puede agregar!");
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
            System.out.println("¡Tarea creada exitosamente!");
            this.listar();
            this.tamanio++;
            this.copiarLista(usuarioInverso);
        }
    }

    /**
     * Método para copiar al otro Usuario la lista de tareas del otro
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
            System.out.println("¡La tarea se elimino con exito!");
            // Disminuye el contador de tamaño de la lista.
            tamanio--;
        }else{
            System.out.println("No existe la tarea con el id: "+referencia);
        }
    }
}
