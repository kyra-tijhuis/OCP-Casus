package com.vijfhart.casus.tree;

/**
 * interface used to specify what part of a node
 * is to be used to calculate a summary measure of nodes in a Tree.
 *
 * Note: in java 8 this interface can be replaced by the generic interface java.util.function.Funtion.
 * @author Marko Draisma
 * @version 1.0
 *
 */

public interface NodeInt<T extends Node>{

    /**
     * Method that is used to obtain an int value out of a node.
     * @param t: a node
     * @return a int value
     */
    int get(T t);

}

