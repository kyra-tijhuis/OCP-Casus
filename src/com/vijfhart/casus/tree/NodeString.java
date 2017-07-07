package com.vijfhart.casus.tree;

/**
 * interface to generate a String value out of a node
 *
 * Note: in java 8 this interface can be replaced by the generic interface java.util.function.Funtion.
 * @author Marko Draisma
 * @version 1.0
 *
 */


public interface NodeString<T extends Node>{

    /**
     * Method that is used to obtain a String value out of a node.
     * @param t: the node
     * @return the String value
     */
    String get(T t);

}

