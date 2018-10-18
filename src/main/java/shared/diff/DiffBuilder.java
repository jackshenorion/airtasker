package shared.diff;

import model.shared.HasStateUtil;
import util.shared.CollectionUtil;
import util.shared.ObjectUtil;
import util.shared.Precondition;
import util.shared.Strings;
import util.shared.binding.PropertyGetter;
import util.shared.collection.IntMap;
import util.shared.collection.IntMaps;
import util.shared.delegate.EqualityComparator;
import util.shared.delegate.IntUnaryFunc;
import util.shared.delegate.UnaryFunc;
import util.shared.format.ValueFormatter;
import util.shared.linq.Linq;
import util.shared.primitive.Val;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DiffBuilder<T> {

    private List<Diff> diff = new ArrayList<>();
    private DiffPath path = new DiffPath();

    public void compareNode(T oldNode,
                            T newNode,
                            DiffComparator<T> comparator,
                            ValueFormatter<T> formatter) {

        if (oldNode == null && newNode == null) {
            return;
        }

        path.push(new DiffNode(
            oldNode, oldNode != null ? formatter.format(oldNode) : "",
            newNode, newNode != null ? formatter.format(newNode) : ""));

        if (oldNode == null) {
            diff.add(Diff.nodeCreated(path));
        }
        else if (newNode == null) {
            diff.add(Diff.nodeRemoved(path));
        }
        else {
            comparator.compare(oldNode, newNode, this);
        }
        path.pop();
    }

    public void compareNodeListWithIntKey(List<T> oldNodes,
                                          List<T> newNodes,
                                          IntUnaryFunc<T> keySelector,
                                          DiffComparator<T> comparator,
                                          ValueFormatter<T> formatter) {

        IntMap<T> oldNodeIndex = IntMaps.from(oldNodes, keySelector::run);
        IntMap<T> newNodeIndex = IntMaps.from(newNodes, keySelector::run);

        for (int index : oldNodeIndex.keysAsInt()) {
            T oldNode = oldNodeIndex.get(index);
            T newNode = newNodeIndex.remove(index); // REMOVE
            if (newNode != null) {
                compareNode(oldNode, newNode, comparator, formatter);
            }
            else {
                logNodeRemoved(oldNode, formatter.format(oldNode));
            }
        }

        newNodeIndex.forEachValue(newNode -> {
            logNodeCreated(newNode, formatter.format(newNode));
        });
    }

    public void compareNodeListWithStringKey(List<T> oldNodes,
                                             List<T> newNodes,
                                             UnaryFunc<T, String> keySelector,
                                             DiffComparator<T> comparator,
                                             ValueFormatter<T> formatter) {

        Map<String, T> oldNodeIndex = Linq.wrap(oldNodes).toMap(keySelector);
        Map<String, T> newNodeIndex = Linq.wrap(newNodes).toMap(keySelector);

        for (String index : oldNodeIndex.keySet()) {
            T oldNode = oldNodeIndex.get(index);
            T newNode = newNodeIndex.remove(index); // REMOVE
            if (newNode != null) {
                compareNode(oldNode, newNode, comparator, formatter);
            }
            else {
                logNodeRemoved(oldNode, formatter.format(oldNode));
            }
        }

        Linq.from(newNodeIndex.values()).forEach(newNode -> {
            logNodeCreated(newNode, formatter.format(newNode));
        });
    }

    public void comparePropertyBool(String propertyName, PropertyGetter<T, Boolean> propertyGetter) {
        Boolean oldValue = propertyGetter.get(currentOldNode());
        Boolean newValue = propertyGetter.get(currentNewNode());
        if (oldValue != newValue) {
            diff.add(Diff.propertyUpdated(path, propertyName, Strings.toNotNull(oldValue), Strings.toNotNull(newValue)));
        }
    }

    public void comparePropertyDate(String propertyName, PropertyGetter<T, Date> propertyGetter) {
        Date oldValue = propertyGetter.get(currentOldNode());
        Date newValue = propertyGetter.get(currentNewNode());
        if (!HasStateUtil.isEffectiveDate(oldValue)) {
            oldValue = null;
        }
        if (!HasStateUtil.isEffectiveDate(newValue)) {
            newValue = null;
        }
        if (!ObjectUtil.equals(oldValue, newValue)) {
            diff.add(Diff.propertyUpdated(path, propertyName, Strings.toNotNull(oldValue), Strings.toNotNull(newValue)));
        }
    }

    public void comparePropertyEnum(String propertyName, PropertyGetter<T, Enum> propertyGetter) {
        Enum oldValue = propertyGetter.get(currentOldNode());
        Enum newValue = propertyGetter.get(currentNewNode());
        if (oldValue != newValue) {
            diff.add(Diff.propertyUpdated(path, propertyName, Strings.toNotNull(oldValue), Strings.toNotNull(newValue)));
        }
    }

    public void comparePropertyInt(String propertyName, IntUnaryFunc<T> propertyGetter) {
        comparePropertyInt(propertyName, propertyGetter, Strings::toNotNull);
    }

    public void comparePropertyInt(String propertyName, IntUnaryFunc<T> propertyGetter, ValueFormatter<Integer> formatter) {
        int oldValue = propertyGetter.run(currentOldNode());
        int newValue = propertyGetter.run(currentNewNode());
        if (oldValue != newValue) {
            diff.add(Diff.propertyUpdated(path, propertyName, formatter.format(oldValue), formatter.format(newValue)));
        }
    }

    public <P extends Comparable<P>> void comparePropertyList(String propertyName,
                                                              PropertyGetter<T, List<P>> propertyGetter) {

        comparePropertyList(propertyName, propertyGetter, Strings::toNotNull);
    }

    public <P extends Comparable<P>> void comparePropertyList(String propertyName,
                                                              PropertyGetter<T, List<P>> propertyGetter,
                                                              ValueFormatter<P> propertyFormatter) {

        List<P> oldValue = propertyGetter.get(currentOldNode());
        List<P> newValue = propertyGetter.get(currentNewNode());
        comparePropertyList(propertyName, oldValue, newValue, propertyFormatter);
    }

    public <P extends Comparable<P>> void comparePropertyList(String propertyName,
                                                              List<P> oldValue,
                                                              List<P> newValue,
                                                              ValueFormatter<P> propertyFormatter) {
        if (CollectionUtil.compares(oldValue, newValue) != 0) {
            diff.add(Diff.propertyUpdated(path, propertyName,
                CollectionUtil.join(", ", oldValue, propertyFormatter, true),
                CollectionUtil.join(", ", newValue, propertyFormatter, true)
            ));
        }
    }

    public void comparePropertyString(String propertyName, PropertyGetter<T, String> propertyGetter) {
        String oldValue = propertyGetter.get(currentOldNode());
        String newValue = propertyGetter.get(currentNewNode());
        if (!ObjectUtil.equals(oldValue, newValue)) {
            diff.add(Diff.propertyUpdated(path, propertyName, oldValue, newValue));
        }
    }

    public <P> void comparePropertyObject(String propertyName,
                                          PropertyGetter<T, P> propertyGetter,
                                          EqualityComparator<P> propertyComparator,
                                          ValueFormatter<P> propertyFormatter) {

        P oldValue = propertyGetter.get(currentOldNode());
        P newValue = propertyGetter.get(currentNewNode());
        if (oldValue == null && newValue == null) {
            return;
        }
        if (oldValue == null) {
            diff.add(Diff.propertyUpdated(path, propertyName, "", propertyFormatter.format(newValue)));
            return;
        }
        if (newValue == null) {
            diff.add(Diff.propertyUpdated(path, propertyName, propertyFormatter.format(oldValue), ""));
            return;
        }

        if (!propertyComparator.equals(oldValue, newValue)) {
            diff.add(Diff.propertyUpdated(path, propertyName, propertyFormatter.format(oldValue), propertyFormatter.format(newValue)));
            return;
        }
    }

    public void comparePropertyVal(String propertyName, PropertyGetter<T, Val> propertyGetter) {
        Val oldValue = propertyGetter.get(currentOldNode());
        Val newValue = propertyGetter.get(currentNewNode());
        if (oldValue == null && newValue == null) {
            return;
        }
        if (oldValue == null) {
            diff.add(Diff.propertyUpdated(path, propertyName, "", newValue.toText()));
            return;
        }
        if (newValue == null) {
            diff.add(Diff.propertyUpdated(path, propertyName, oldValue.toText(), ""));
            return;
        }
        if (!oldValue.isEqualTo(newValue)) {
            diff.add(Diff.propertyUpdated(path, propertyName, oldValue.toText(), newValue.toText()));
        }
    }

    public void logNodeCreated(Object newNode, String newNodeName) {
        path.push(new DiffNode(null, "", newNode, newNodeName));
        diff.add(Diff.nodeCreated(path));
        path.pop();
    }

    public void logNodeRemoved(Object oldNode, String oldNodeName) {
        path.push(new DiffNode(oldNode, oldNodeName, null, ""));
        this.diff.add(Diff.nodeRemoved(path));
        path.pop();
    }

    public void logNodeMoved(Object oldNode, String oldNodeName, Object newNode, String newNodeName) {
        path.push(new DiffNode(oldNode, oldNodeName, newNode, newNodeName));
        this.diff.add(Diff.nodeMoved(path));
        path.pop();
    }

    public void logNodeUpdated(Object oldNode, String oldNodeName, Object newNode, String newNodeName) {
        path.push(new DiffNode(oldNode, oldNodeName, newNode, newNodeName));
        this.diff.add(Diff.nodeUpdated(path));
        path.pop();
    }

    public void logPropertyUpdated(String propertyName, String oldValue, String newValue) {
        this.diff.add(Diff.propertyUpdated(path, propertyName, oldValue, newValue));
    }

    public List<Diff> toList() {
        Precondition.checkTrue(path.length() == 0);
        return diff;
    }

    public <Type> DiffBuilder<Type> cast() {
        return (DiffBuilder<Type>) this;
    }

    private DiffNode currentDiffNode() {
        return path.peek();
    }

    private T currentOldNode() {
        return (T) currentDiffNode().getOldNode();
    }

    private T currentNewNode() {
        return (T) currentDiffNode().getNewNode();
    }
}
