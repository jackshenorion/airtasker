package shared.json;

/**
 * A flag for indicating the naming conversion of JSON properties. It is default to lowerCamelCase.
 */
public enum JsonPropertyNamingMode {

    LowerCamelCase,
    UpperCamelCase;

    public static JsonPropertyNamingMode Mode = LowerCamelCase;

}
