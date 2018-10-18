package shared.json;


/**
 * A class implementing this interface can be converted to and convert back from JSON.
 * 
 * @author
 */
public interface JsonSerializable {

	void readJson(JsonValue json);

	JsonValue writeJson();

}
