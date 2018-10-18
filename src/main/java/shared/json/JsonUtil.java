package shared.json;

import util.shared.primitive.Val;
import util.shared.primitive.Vals;

public class JsonUtil {

    public static JsonValue toJson(Val v) {
        if (v == null) {
            return JsonNull.Instance;
        }
        if (v.isInt()) {
            return new JsonNumber(v.asInt());
        }
        else if (v.isDouble()) {
            return new JsonNumber(v.asDouble());
        }
        else if (v.isBool()) {
            return JsonBoolean.get(v.asBool());
        }
        else if (v.isText()) {
            return new JsonString(v.asText());
        }
        else if (v.isList()) {
            JsonArray array = JsonArray.create();
            for (Val item : v.asList()) {
                array.add(toJson(item));
            }
            return array;
        }
        return JsonNull.Instance;
    }

    public static Val toVal(JsonValue v) {
        if (v.isNull()) {
            return Vals.ofNull();
        }
        if (v.isBoolean()) {
            return Vals.of(v.asBoolean().getValue());
        }
        if (v.isNumber()) {
            return Vals.of(v.asNumber().getValue());
        }
        if (v.isString()) {
            return Vals.of(v.asString().getValue());
        }
        if (v.isArray()) {
            return Vals.ofList(v.asArray().getList(JsonUtil::toVal));
        }
        return Vals.of(v.asObject().toMap(JsonUtil::toVal));
    }
}
