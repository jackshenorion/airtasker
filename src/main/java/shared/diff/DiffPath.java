package shared.diff;

import util.shared.CollectionUtil;
import util.shared.Precondition;

import java.util.ArrayList;
import java.util.List;

public final class DiffPath {

    private List<DiffNode> path = new ArrayList<>();

    public DiffPath() {
    }

    public DiffPath(DiffPath path) {
        this.path = new ArrayList<>(path.path);
    }

    public DiffPath push(DiffNode node) {
        path.add(node);
        return this;
    }

    public DiffNode peek() {
        Precondition.checkFalse(path.isEmpty());
        return path.get(path.size() - 1);
    }

    public DiffNode pop() {
        Precondition.checkFalse(path.isEmpty());
        return path.remove(path.size() - 1);
    }

    public String getOldPath() {
        return CollectionUtil.join("/", path, DiffNode::getOldNodeName, true);
    }

    public String getNewPath() {
        return CollectionUtil.join("/", path, DiffNode::getNewNodeName, true);
    }

    public int length() {
        return path.size();
    }
}
