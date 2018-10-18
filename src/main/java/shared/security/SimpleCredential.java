package shared.security;

import util.shared.json.JsonObject;
import util.shared.json.JsonSerializable;
import util.shared.json.JsonValue;

public final class SimpleCredential implements JsonSerializable {

    private String username;
    private String password;

    public SimpleCredential() {
    }

    public SimpleCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public SimpleCredential setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SimpleCredential setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public void readJson(JsonValue json) {
        JsonObject jso = json.asObject();
        if (jso == null) {
            return;
        }
        username = jso.getString("username");
        password = jso.getString("password");
    }

    @Override
    public JsonValue writeJson() {
        return JsonObject.create()
            .put("username", username)
            .put("password", password)
        ;
    }
}
