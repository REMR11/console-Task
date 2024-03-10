package consoletask.utils;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jesús Esquivel
 */
public class Validaciones {

    //Se crea un objeto de tipo Scanner para poder leer la entrada de datos del usuario
    private static Scanner lector = new Scanner(System.in);

    /**
     * Método para validar y hacer la solicitud de la opción de cualquier
     * menú dentro de la aplicación, devuelve un ENTERO que representa el
     * número de opción que se ha escogido
     *
     * @param advertencia Representa el mensaje que se debe mostrar al usuario
     * en caso el usuario ingrese una opción erronea
     * @param rMenor Representa el número menor que se permite ingresar al
     * usuario
     * @param rMayor Representa el número mayor que se permite ingresar al
     * usuario
     * @return Número entero que representa la selección del menú
     */
    public int solicitarOpcionMenu(String advertencia, int rMenor, int rMayor) {
        //Creamos un bucle para consultar en caso se equivoque, mostrando un mensaje si se equivoco
        while (true) {
            //Validamos que sea un entero
            if (lector.hasNextInt()) {
                int n = lector.nextInt();//Leemos el número
                //Validamos que este entre el rango
                if (rMenor <= n && n <= rMayor) {
                    return n;
                }
            }

            //Ya que no cumple la validación mostramos el mensaje
            System.out.println(advertencia + ", recuerde que debe ser un número entre " + rMenor + " y " + rMayor);
            lector.nextLine();
        }
    }

    /**
     * Método para validar y hacer la solicitud de un número entero
     *
     * @param advertencia Representa el mensaje que se debe mostrar al usuario
     * en caso el usuario ingrese una opción erronea
     * @return Número entero
     */
    public int solicitarEntero(String advertencia) {
        //Creamos un bucle para consultar en caso se equivoque, mostrando un mensaje si se equivoco
        while (true) {
            //Validamos que sea un entero
            if (lector.hasNextInt()) {
                int n = lector.nextInt();//Leemos el número
                return n;
            }

            //Ya que no cumple la validación mostramos el mensaje
            System.out.println(advertencia + ", recuerde que debe ser un número entero");
            lector.nextLine();
        }
    }

    /**
     * Método para validar y solicitar el ingrese de un STRING evitando
     * espacios vacios
     *
     * @param advertencia Representa el mensaje que se debe mostrar al usuario
     * en caso el usuario ingrese un dato incorrecto
     * @return String que se esta seguro que no es solo un espacio en blanco
     */
    public String solicitarString(String advertencia) {
        String input;//Varible a almacenar el dato que ingrese el usuario
        //Creamos un bucle para poder solicitar la información en caso se equivoque
        do {
            input = lector.nextLine().trim(); //Obtenemos el valor del usuario

            //Validamos que no este vacio para mostrar la advertencia
            if (input.isEmpty()) {
                System.out.println(advertencia);
            }
        } while (input.isEmpty());

        return input;
    }

    /**
     * Método para obtener la fecha actual formateada en formato: {dd/MM/yyyy}
     *
     * @param fecha Fecha que se va convertir de tipo {LocalDate}
     * @return La fecha pasada en formato dd/MM/yyyy tipo {String}
     */
    public String formatearFecha(LocalDate fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");//Crea un formato de fecha
        return fecha.format(formato);//Retornamos la fecha en el formato dd/MM/yyyy
    }

    /**
     * M�todo que formate la primera fecha del filtro de tareas
     * @return La fecha formateada a LocalDate
     */
    public LocalDate solicitarFecha() {

        LocalDate fecha = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");//Crea un formato de fecha
        boolean formatoValido = true;
        do {
            try {
                lector.nextLine();
                String fechaString = lector.nextLine();
                fecha = LocalDate.parse(fechaString, formato);
                formatoValido = true;
            } catch (Exception ex) {
                System.out.println("Debe ingresar la fecha en formato v�lido (dd/MM/yyyy)");
                formatoValido = false;
            }

        } while (!formatoValido);

        return fecha;

    }

    /**
     * Método para validar y solicitar un fecha mayor que la fecha de
     * referencia (parametro).
     *
     * @param advertencia Representa el mensaje que se debe mostrar al usuario
     * en caso el usuario ingrese una fecha en formato no admisible
     * @param fechaReferencia La fecha de referencia que será menor que la que
     * se solicite
     * @return La fecha ingresada por el usuario
     */
    public LocalDate solicitarFechaMayor(String advertencia, LocalDate fechaReferencia) {
        LocalDate fecha;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");//Crea un formato de fecha
        do {
            fecha = fechaReferencia;
            try {
                String fechaStr = lector.nextLine();
                fecha = LocalDate.parse(fechaStr, formato);
                if (fecha.compareTo(fechaReferencia) <= 0) {
                    System.out.println(advertencia + " " + "La fecha (dd/MM/yyyy) ingresada debe ser mayor que " + fechaReferencia.format(formato));
                }
            } catch (Exception e) {
                System.out.println(advertencia + " " + "La fecha (dd/MM/yyyy) ingresada debe ser mayor que " + fechaReferencia.format(formato));
            }
        } while (fecha.compareTo(fechaReferencia) <= 0);

        return fecha;
    }

    /**
     * Método para solicitar un nombre de usuario al usuario y validar que
     * contenga solo letras.
     *
     * @return El nombre de usuario ingresado.
     */
    public String solicitarNombreUsuario(String advertencia) {
        Pattern patron = Pattern.compile("[a-zA-Z]+");
        String nombreUsuario;
        Matcher matcher;
        do {
            System.out.print("Ingrese su nombre: ");
            nombreUsuario = lector.nextLine().trim(); // Eliminar espacios en blanco al principio y al final

            // Realizar la validación usando la expresión regular
            matcher = patron.matcher(nombreUsuario);

            // Verificar si el nombre de usuario contiene solo letras
            if (!matcher.matches()) {
                System.out.println(advertencia);
            }
        } while (!matcher.matches()); // Repetir hasta que el nombre de usuario sea válido
        return nombreUsuario;
    }

    /**
     * Método para obtener una confirmación si se desea hacer una acción o no
     * Ejemplo: ¿{pregunta} ..... S(si) o N(no)? y dependiendo de la selección
     * devuelve true o false
     *
     * @param pregunta Mensaje que se mostrará para consultarla acción
     * @return true o false dependiendo de la selección {boolean}
     */
    public boolean confirmarAccion(String pregunta) {
        System.out.println("¿" + pregunta + " S(Si) o N(no)?");
        String desicion = lector.nextLine().toLowerCase();
        while (!desicion.equals("s") && !desicion.equals("si") && !desicion.equals("n") && !desicion.equals("no")) {
            System.out.println("¡Opción icorrecta!---Recuerda S(si) o N(no)");
            desicion = lector.nextLine().toLowerCase();
        }

        return desicion.equals("s") || desicion.equals("si");
    }
}
