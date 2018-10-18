package shared.primitive;

/**
 * Created by   on 17/03/2015.
 */
public final class TextVal extends Val {

    private String val;

    public TextVal(String val) {
        this.val = val;
    }

    @Override
    public int comparesTo(Val other) {
        return other.isText() ? val.compareTo(other.asText()) : -1;
    }

    @Override
    public boolean isEqualTo(Val other) {
        return  other.isText() ? val.equals(other.asText()) :
            other.isList() ? other.asListVal().isEqualTo(this) :
                false;
    }

    @Override
    public boolean isText() {
        return true;
    }

    @Override
    public String asText() {
        return val;
    }

    @Override
    public boolean isEmpty() {
        return val.isEmpty();
    }

    @Override
    public Val plus(Val other) {
        return Vals.of(other.isText() ? val + other.asText() : "");
    }

    @Override
    public String toText() {
        return val;
    }

    @Override
    public Object toObject() {
        return val;
    }

    @Override
    public int hashCode() {
        return val != null ? val.hashCode() : 0;
    }
}
