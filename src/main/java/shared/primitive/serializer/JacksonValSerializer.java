package shared.primitive.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import util.shared.primitive.Val;

import java.io.IOException;

public class JacksonValSerializer extends StdSerializer<Val> {

    public JacksonValSerializer() {
        super(Val.class);
    }

    @Override
    public void serialize(Val v, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (v.isInt()) {
            gen.writeNumber(v.asInt());
        }
        else if (v.isDouble()) {
            gen.writeNumber(v.asDouble());
        }
        else if (v.isBool()) {
            gen.writeBoolean(v.asBool());
        }
        else if (v.isText()) {
            gen.writeString(v.asText());
        }
        else if (v.isList()) {
            gen.writeStartArray();
            for (Val item : v.asList()) {
                serialize(item, gen, serializers);
            }
            gen.writeEndArray();
        }
    }

    @Override
    public void serializeWithType(Val value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForObject(value, gen);
        serialize(value, gen, serializers);
        typeSer.writeTypeSuffixForObject(value, gen);
    }
}
