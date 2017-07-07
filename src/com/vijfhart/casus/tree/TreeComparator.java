package com.vijfhart.casus.tree;
import java.util.*;
/**
 * A comparator to specify the order of nodes in a tree.
 * The comparator uses a local comparator to specify the order of siblings.
 *
 * @author Marko Draisma
 * @version 1.0
 * @param <T> the node type
 */

public class TreeComparator<T extends Node<T>> implements Comparator<T> {

    private Comparator<T> orderSiblingsBy;

    /**
     * Specifies what comparator to use when sorting siblings in a tree.
     * @param comparator: the comparator to use for sorting siblings.
     */
    public void orderSiblingsBy(Comparator<T> comparator){
        orderSiblingsBy=comparator;
    }


    /**
     * Specifies the ordering of nodes in a Tree.
     * This is a recursive method: the ordering of nodes is known when:
     * - one of the nodes has no parent (this should be the first node)
     * - one node is the parent of the other (the parent should be placed first)
     * - two nodes share the same parent (ordering is specified by orderSiblingsBy)
     * If two nodes seem unrelated, the ordering is specified by ordering the parents.
     */
    public int compare(T first, T other){
        if(first.getParent()==null) return -1;
        if(other.getParent()==null) return 1;
        if (first.compareLevelTo(other)>0){
            if(first.getParent().equals(other)) return 1;
            return compare(first.getParent(),other);
        }
        if (first.compareLevelTo(other)<0){
            if(first.equals(other.getParent())) return -1;
            return compare(first,other.getParent());
        }
        if (first.getParent().equals(other.getParent())&&orderSiblingsBy!=null){
            return orderSiblingsBy.compare(first,other);
        }
        return compare(first.getParent(),other.getParent());
    }

}
