package shared.diff;

import util.shared.Strings;

public final class DiffNode {

    private Object oldNode;
    private String oldNodeName;
    private Object newNode;
    private String newNodeName;

    public DiffNode(Object oldNode, String oldNodeName, Object newNode, String newNodeName) {
        this.oldNode = oldNode;
        this.oldNodeName = Strings.toNotNull(oldNodeName);
        this.newNode = newNode;
        this.newNodeName = Strings.toNotNull(newNodeName);
    }

    public Object getOldNode() {
        return oldNode;
    }

    public String getOldNodeName() {
        return oldNodeName;
    }

    public Object getNewNode() {
        return newNode;
    }

    public String getNewNodeName() {
        return newNodeName;
    }
}
