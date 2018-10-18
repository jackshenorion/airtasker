package shared.json;


public abstract class JsonValue {

    public JsonArray asArray()      { return null; }
    public JsonBoolean asBoolean()  { return null; }
    public JsonNull asNull()        { return null; }
    public JsonNumber asNumber()    { return null; }
    public JsonObject asObject()    { return null; }
    public JsonString asString()    { return null; }

    public boolean isArray()        { return false; }
    public boolean isBoolean()      { return false; }
    public boolean isNull()         { return false; }
    public boolean isNumber()       { return false; }
    public boolean isObject()       { return false; }
    public boolean isString()       { return false; }

    public abstract boolean isEmpty();

    public abstract String toJsonString();
}
