package com.vijfhart.casus.tree;
import java.util.*;

/**
 * A Node is an element in a {@link Tree}, with an optional parent node.
 *
 * All other (implied) information about a Node in a Tree are given by looping through a {@link TreeIterator}.
 *
 * @author Marko Draisma
 * @version 1.0
 * @param T the actual node type
 */

public interface Node<T extends Node<T>> extends LevelComparable<T>, Comparable<T>{

    /**
     * Gives the parent node, or null if there is no parent node.
     * Normally, there is one Node without a parent node in a Tree.
     * @return the parent node.
     */
    T getParent();


    /**
     * Sets the parent node.
     * @param t: the new parent node.
     * @throws IllegalArgumentException if the given node is a child node of this node.
     */
    void setParent(T t);
}
