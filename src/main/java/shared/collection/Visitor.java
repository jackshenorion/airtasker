package shared.collection;

public interface Visitor<T> {

    /// <summary>
    /// Start visiting the given node.
    /// </summary>
    /// <param name="treeNode"></param>
    /// <returns>true to visit the children of the given node, false otherwise</returns>
    public boolean enter(T treeNode);

    /// <summary>
    /// </summary>
    /// <param name="treeNode"></param>
    /// <returns>true to visit the next sibling of the given node, false otherwise</returns>
    public boolean leave(T treeNode);
	
	
}
