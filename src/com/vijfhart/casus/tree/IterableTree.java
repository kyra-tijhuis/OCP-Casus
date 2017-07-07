package com.vijfhart.casus.tree;
import java.util.Iterator;

/**
 * Sub-interface of Iterable, to indicate that a class implementing this interface can loop through a Tree.
 * We could well do without this interface, but maybe a future java version can use this interface
 * for an Tree-version of the enhanced for each loop.
 *
 * @author Marko Draisma
 * @version 1.0
 * @param T - the node type of elements in the tree
 */
public interface IterableTree<T extends Node<T>> extends Iterable<T> {

    /**
     * Returns a TreeIterator to loop through the node elements in a Tree.
     * @return a TreeIterator
     */
    TreeIterator<T> iterator();
}
