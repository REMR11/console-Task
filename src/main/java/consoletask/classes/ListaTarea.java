package consoletask.classes;

/**
 *
 * @author GRECIA
 */
public class ListaTarea {

    private Nodo inicio;
    private int tamanio;

    public ListaTarea() {
        inicio = null;
        tamanio = 1;
    }

    public boolean esVacia() {
        return this.inicio == null;
    }

    public int getTamanio() {
        return tamanio;
    }

    /**
     * Método para buscar una tarea por id de referencia
     *
     * @param referencia ID de la tarea a buscar
     * @return true o false en caso que encuentre el valor o no lo encuentre;
     */
    public boolean buscar(int referencia) {
        // Crea una copia de la lista.
        Nodo aux = inicio;
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
        Nodo aux = inicio;
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

    public void listar() {
        // Verifica si la lista contiene elementoa.
        if (!esVacia()) {
            // Crea una copia de la lista.
            Nodo aux = inicio;
            // Posicion de los elementos de la lista.
            int i = 0;
            // Recorre la lista hasta el final.
            while (aux != null) {
                // Imprime en pantalla el valor del nodo.
                System.out.print(i + ".[ " + aux.getTarea().toString() + " ]" + " ->  ");
                // Avanza al siguiente nodo.
                aux = aux.getSiguiente();
                // Incrementa el contador de la posión.
                i++;
            }
        } else {
            System.out.println("No hay tareas creadas");
        }
    }

    /**
     * Método para agregar una Tarea al final de la lista
     *
     * @param tarea Tarea a agregar
     */
    public void agregarAlFinal(Tarea tarea) {
        // Define un nuevo nodo.
        Nodo nuevo = new Nodo();
        // Agrega al valor al nodo.
        nuevo.setTarea(tarea);
        //Bandera para saber si hubo exito
        boolean exito = false;
        // Consulta si la lista esta vacia.
        if (this.esVacia()) {
            // Inicializa la lista agregando como inicio al nuevo nodo.
            this.inicio = nuevo;
            exito = true;
            // Caso contrario recorre la lista hasta llegar al ultimo nodo
            //y agrega el nuevo.
        } else {
            //Consultamos si ya existe la tarea a ingresar
            if (this.buscar(tarea.getId())) {
                System.out.println("El id de tarea ya existe, no se puede agregar");
                return;
            };

            if (this.buscarPorNombre(tarea.getNombreTarea())) {
                System.out.println("El nombre de la tarea ya existe, no se puede agregar");
            }

            // Crea ua copia de la lista.
            Nodo aux = this.inicio;
            // Recorre la lista hasta llegar al ultimo nodo.
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            // Agrega el nuevo nodo al final de la lista.
            aux.setSiguiente(nuevo);
            exito = true;
        }
        // Incrementa el contador de tamaño de la lista
        if (exito) {
            System.out.println("¡Tarea creada exitosamente!");
            this.listar();
            this.tamanio++;
        }
    }
}