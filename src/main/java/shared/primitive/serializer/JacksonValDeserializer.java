package shared.primitive.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import util.shared.primitive.Val;
import util.shared.primitive.Vals;

import java.io.IOException;
import java.util.*;

public class JacksonValDeserializer extends StdDeserializer<Val> {

    public JacksonValDeserializer() {
        super(Val.class);
    }

    @Override
    public Val deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode v = p.getCodec().readTree(p);
        return decodeJSON(v);
    }

    public Val decodeJSON(JsonNode v) throws IOException {
        if (v.isNull()) {
            return Vals.ofNull();
        }
        if (v.isBoolean()) {
            return Vals.of(v.asBoolean());
        }
        if (v.isInt()) {
            return Vals.of(v.asInt());
        }
        if (v.isDouble()) {
            return Vals.of(v.asDouble());
        }
        if (v.isTextual()) {
            return Vals.of(v.asText());
        }
        if (v.isArray()) {
            List<Val> list = new ArrayList<>();
            for (int i = 0, total = v.size(); i < total; i++) {
                list.add(decodeJSON(v.get(i)));
            }
            return Vals.ofList(list);
        }

        /**
         * Not really expect to convert a Json object. Mainly just be compatible with JsonUtil#toVal.
         */
        Map<String, Val> map = new HashMap<>();
        Iterator<String> fieldNames = v.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            map.put(fieldName, decodeJSON(v.get(fieldName)));
        }
        return Vals.of(map);
    }

    @Override
    public Val getNullValue() {
        return Vals.ofNull();
    }
}
