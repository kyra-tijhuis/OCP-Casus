package com.vijfhart.casus.tree;
import java.util.*;

/**
 * A interface to iterate over Nodes in a Tree.
 * This interface provides information about the level in the hierarchy,
 * about the path of the node in the tree, if the node is a leaf node.
 * The ordering of siblings can be specified. The iteration can be started at a given node.
 * <p>
 * This interface is loosely inspired by Oracle's hierarchical query.
 */
public interface TreeIterator<T extends Node<T>> extends Iterator<T> {

    /**
     * The level of the current node in the tree.
     * @return 0 if the current node is the root node of the tree
     * (absolute, or relative as specified by startWith)
     * otherwise a positive integer, indicating the distance between the current node
     * and the root of the tree.
     */
    int level();

    /**
     * Specifies at what node the iteration will be started.
     * During the iteration, this will be the root of the tree.
     * @param t: the node where the iteration will be started.
     */
    void startWith(T t);

    /**
     * Returns the path from the ancestors to the current node.
     * This method uses a NodeString implementation to specify what part of a node
     * is shown in the path.
     * @param nodeString: a NodeString to specify what String value
     *                    of the node will be shown as part of the path.
     * @param separator: the String to separate the parts of a path
     * @return the generated path
     */
    String path(NodeString<T> nodeString , String separator);

    /**
     * Specifies how siblings are to be sorted.
     * @param comparator: a Comparator to specify the ordering of siblings.
     */
    void orderSiblingsBy(Comparator<T> comparator);

    /**
     * Returns true if the node is a leaf node.
     * @return true if the node is a leaf node, or false otherwise.
     */
    boolean isLeaf();

}
