package com.vijfhart.casus.tree;
import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

/**
 * A Tree-structured collection containing Node elements.
 * The main characteristic of a Tree is that is an IterableTree:
 * it can iterate through its elements using a TreeIterator.
 * Elements can be added to a Tree using the add method.
 * Furthermore, it can list the elements descending a given node.
 * This also makes it possible to count the elements beneath a node.
 * A total can be obtained using a {@link NodeInt} implementation.
 *
 * @author Marko Draisma
 * @version 1.0
 * @param <T>: the node type in use
 */
public interface Tree<T extends Node<T>> extends IterableTree<T> {

    /**
     * Adds a node element to the tree.
     * @param t: the node to add to the tree
     */

    void add(T t);

    /**
     * Returns the sum of a given Node-characteristic, provided by the application
     * using a NodeInt implementation.
     * A Tree doesn't know what characteristics of Type T can be used to calculate a sum.
     * This can be provided by the application however: then the Node type to be used is known.
     * The interface {@link NodeInt} is used for this purpose.
     * @param  t: the node beneath which the sum is calculated.
     * @param nodeInt: the NodeInt implementation, as needed to specify what characteristic of type T
     * is to be used to calculate the sum.
     * @return the sum of int values calculated for the nodes descending and including the given node.
     */

    int descendantSum(T t, NodeInt<T> nodeInt);

    /**
     * Returns the sum of a given Node-characteristic, provided by the application
     * using a NodeInt implementation.
     * A Tree doesn't know what characteristics of Type T can be used to calculate a sum.
     * This can be provided by the application however: then the Node type to be used is known.
     * The interface {@link NodeInt} is used for this purpose.
     * @param  t: the node beneath which the sum is calculated.
     * @param function: the ToLongFunction implementation, as needed to specify what characteristic of type T
     * is to be used to calculate the sum.
     * @return the sum of int values calculated for the nodes descending and including the given node.
     */

    long descendantSum(T t, ToLongFunction<T> function);

    /** Returns the sum of a given Node-characteristic, provided by the application
     * using a NodeDouble implemenation.
     * @see int descendantSum(T t, NodeInt nodeInt);
     * @param  t: the node beneath which the sum is calculated.
     * @param nodeDouble: the NodeDouble implementation, as needed to specify what characteristic of type T
     * is to be used to calculate the sum.
     * @return the sum of double values calculated for the nodes descending and including the given node.
     */
    double descendantSum(T t, NodeDouble<T> nodeDouble);

    /** Returns the sum of a given Node-characteristic, provided by the application
     * using a NodeDouble implemenation.
     * @see int descendantSum(T t, ToLongFunction function);
     * @param  t: the node beneath which the sum is calculated.
     * @param function: the ToDoubleFunction implementation, as needed to specify what characteristic of type T
     * is to be used to calculate the sum.
     * @return the sum of double values calculated for the nodes descending and including the given node.
     */
    double descendantSum(T t, ToDoubleFunction<T> function);

    /**
     * The count of elements beneath the given node.
     * @param t: the node beneath which the count is calculated.
     * @return the count of nodes descending and excluding the given node.
     */
    int descendantCount(T t);

    /**
     * A list of elements beneath the given node.
     * @param node: the node beneath which the list is generated.
     * @return a list of nodes descending and including the given node.
     */

    List<T> descendantsOf(T node);

}
