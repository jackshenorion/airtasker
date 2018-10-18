package shared.json.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.*;
import com.google.gwt.core.shared.GwtIncompatible;
import util.shared.collection.Lazy;
import util.shared.date.ShortDate;
import util.shared.date.serializer.JacksonShortDateDeserializer;
import util.shared.date.serializer.JacksonShortDateSerializer;
import util.shared.json.*;
import util.shared.primitive.Val;
import util.shared.primitive.serializer.JacksonValDeserializer;
import util.shared.primitive.serializer.JacksonValSerializer;

/**
 * Jackson based parser can be used on server and in Android but is incompatible with GWT.
 * You must not use this class directly.
 */
@GwtIncompatible
public class JacksonJsonParser extends JsonParser {

    static Lazy<ObjectMapper> objectMapper = Lazy.of(() -> {
        SimpleModule module = new SimpleModule();
        module.addSerializer(ShortDate.class, new JacksonShortDateSerializer());
        module.addDeserializer(ShortDate.class, new JacksonShortDateDeserializer());
        module.addSerializer(Val.class, new JacksonValSerializer());
        module.addDeserializer(Val.class, new JacksonValDeserializer());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        return mapper;
    });

	@Override
	public JsonValue parseJson(String text) {
        try {
            JsonNode v = objectMapper.get().readTree(text);
            if (v == null) {
                return JsonNull.Instance;
            }
            return wrap(v);
        }
        catch (Exception error) {
            throw new IllegalArgumentException("Invalid JSON: " + text, error);
        }
	}
	
	@Override
	public JsonArray createArray() {
		return new JsonArrayImpl(JsonNodeFactory.instance.arrayNode());
	}
	
	@Override
	public JsonObject createObject() {
		return new JsonObjectImpl(JsonNodeFactory.instance.objectNode());
	}

	public static JsonValue wrap(JsonNode v) {
		if (v.isObject()) {
			return new JsonObjectImpl((ObjectNode)v);
		}
		if (v.isArray()) {
			return new JsonArrayImpl((ArrayNode)v);
		}
		if (v.isNull()) {
			return JsonNull.Instance;
		}
        if (v.isBoolean()) {
            return JsonBoolean.get(v.asBoolean());
        }
        if (v.isNumber()) {
            return new JsonNumber(v.asDouble());
        }
        if (v.isTextual()) {
            return new JsonString(v.asText(""));
        }
		return JsonNull.Instance;
	}
	
	public static JsonNode unwrap(JsonValue v) {
        if (v == null || v.isNull()) {
            return NullNode.getInstance();
        }
		if (v.isObject()) {
			return ((JsonObjectImpl) v).impl;
		}
		if (v.isArray()) {
			return ((JsonArrayImpl) v).impl;
		}
		if (v.isString()) {
			return new TextNode(v.asString().getValue());
		}
		if (v.isNumber()) {
			return new DoubleNode(v.asNumber().getValue());
		}
		if (v.isBoolean()) {
			return BooleanNode.valueOf(v.asBoolean().getValue());
		}
		return NullNode.getInstance();
	}

    private static class JsonArrayImpl extends JsonArray {

        ArrayNode impl;

        public JsonArrayImpl(ArrayNode impl) {
            this.impl = impl;
        }

        @Override
        public void add(JsonValue value) {
            impl.add(JacksonJsonParser.unwrap(value));
        }

        @Override
        public JsonValue get(int index) {
            JsonNode item = impl.get(index);
            return item != null ? JacksonJsonParser.wrap(item) : JsonNull.Instance;
        }

        @Override
        public int size() {
            return impl.size();
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public String toJsonString() {
            return impl.toString();
        }

    }

    private static class JsonObjectImpl extends JsonObject {

        ObjectNode impl;

        public JsonObjectImpl(ObjectNode impl) {
            this.impl = impl;
        }

        @Override
        public JsonValue get(String key) {
            JsonNode item = impl.get(key);
            return item != null ? JacksonJsonParser.wrap(item) : JsonNull.Instance;
        }

        @Override
        public Iterable<String> getKeys() {
            return impl::fieldNames;
        }

        @Override
        public JsonObject put(String key, JsonValue value) {
            if (value != null && value != JsonNull.Instance) {
                impl.set(key, JacksonJsonParser.unwrap(value));
            }
            return this;
        }

        @Override
        public int size() {
            return impl.size();
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public String toJsonString() {
            return impl.toString();
        }

    }


}
