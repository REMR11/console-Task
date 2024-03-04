package consoletask.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Jesús Esquivel
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    @Override
    public LocalDate read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }

        String fechaStr = reader.nextString();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return LocalDate.parse(fechaStr, formato);
    }

    @Override
    public void write(JsonWriter writer, LocalDate value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }

        Validaciones validaciones = new Validaciones();

        String fechaFormateada = validaciones.formatearFecha(value);
        writer.value(fechaFormateada);
    }
}
