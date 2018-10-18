package shared.date.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import util.shared.date.ShortDate;

import java.io.IOException;

public class JacksonShortDateDeserializer extends JsonDeserializer<ShortDate> {

    @Override
    public ShortDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        int i = p.getValueAsInt(Integer.MIN_VALUE);
        return i != Integer.MIN_VALUE ? new ShortDate(i) : null;
    }
}
