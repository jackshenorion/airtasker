package shared.date.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import util.shared.date.ShortDate;

import java.io.IOException;

public class JacksonShortDateSerializer extends JsonSerializer<ShortDate> {

    @Override
    public void serialize(ShortDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getTimeInMinutes());
    }
}
