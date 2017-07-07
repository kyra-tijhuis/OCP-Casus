package com.vijfhart.casus.tree;

/**
 * Objects of classes implementing this interface can be compared by level.
 *
 * @author Marko Draisma
 * @version 1.0
 */
public interface LevelComparable<T>{

    /**
     * Indicates the level of this element compared to the other element.
     * Typically, a lower level means a higher position in the hierarchy.
     *
     * @param t: the other object, to compare levels with
     * @return a positive int if this has a higher level than t,
     *         a negative int if this has a lower level than t,
     *         otherwise: zero
     */
    int compareLevelTo(T t);

}

