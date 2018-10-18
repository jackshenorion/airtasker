package shared.diff;

public class Diff {

    private ChangeType type = ChangeType.Updated;
    private DiffPath path;
    private String propertyName = "";
    private String oldValue = "";
    private String newValue = "";

    public ChangeType getType() {
        return type;
    }
    public DiffPath getPath() { return path; }
    public String getPropertyName() {
        return propertyName;
    }
    public String getOldValue() {
        return oldValue;
    }
    public String getNewValue() {
        return newValue;
    }

    @Override
    public String toString() {
        if (type == ChangeType.Created) {
            return "Added: {newNode='" + path.getNewPath() + "'}";
        }
        if (type == ChangeType.Removed) {
            return "Deleted: {oldNode='" + path.getOldPath() + "'}";
        }
        return type.name() + ": {" +
            "oldNode='" + path.getOldPath() +
            "', newNode='" + path.getNewPath() + '\'' +
            "', propertyName='" + propertyName + '\'' +
            "', oldValue='" + oldValue + '\'' +
            "', newValue='" + newValue +
            "'}";
    }

    public static Diff nodeCreated(DiffPath path) {
        Diff diff = new Diff();
        diff.path = new DiffPath(path);
        diff.propertyName = "";
        diff.oldValue = "";
        diff.newValue = "";
        diff.type = ChangeType.Created;
        return diff;
    }

    public static Diff nodeRemoved(DiffPath path) {
        Diff diff = new Diff();
        diff.path = new DiffPath(path);
        diff.type = ChangeType.Removed;
        return diff;
    }

    public static Diff nodeMoved(DiffPath path) {
        Diff diff = new Diff();
        diff.path = new DiffPath(path);
        diff.type = ChangeType.Moved;
        return diff;
    }

    public static Diff nodeUpdated(DiffPath path) {
        Diff diff = new Diff();
        diff.path = new DiffPath(path);
        diff.type = ChangeType.Updated;
        return diff;
    }

    public static Diff propertyUpdated(DiffPath path, String propertyName, String oldValue, String newValue) {
        Diff diff = new Diff();
        diff.path = new DiffPath(path);
        diff.propertyName = propertyName;
        diff.oldValue = oldValue;
        diff.newValue = newValue;
        diff.type = ChangeType.Updated;
        return diff;
    }

}
