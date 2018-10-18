package shared.regex;

public abstract class IsMatcher {

    public abstract boolean matches();
    public abstract String getGroup(int index);

}
