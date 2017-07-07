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

interface NodeDouble<T extends Node>{
    /**
     * Method that is used to obtain a double value out of a node.
     * @param t: a node
     * @return a double value
     */
    double get(T t);
}

