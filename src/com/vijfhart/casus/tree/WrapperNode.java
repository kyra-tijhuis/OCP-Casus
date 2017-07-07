package com.vijfhart.casus.tree;
/**
 * A Wrapper class to create a Tree-node out of any Comparable object.
 * A WrapperNode is a subclass of AbstractNode.
 * It keeps a reference to the contained object.
 *
 * @author Marko Draisma
 * @version 1.0
 * @param T: the newly created node type
 * @param E: the type of the wrapped object
 */

public abstract class WrapperNode<T extends WrapperNode<T,E>, E extends Comparable<E>> extends AbstractNode<T> {

    private E object;

    /**
     * Constructor to create the root node of a tree.
     * @param object: the wrapped object of this node.
     */
    protected WrapperNode(E object){
        this.object=object;
    }

    /**
     * constructor to create a node as a child of the given parent node.
     * @param object: the wrapped object of this node.
     * @param parent: the parent node.
     */

    protected WrapperNode(E object, T parent){
        this.object=object;
        setParent(parent);
    }

    /**
     * Retrieves the wrapped object.
     * @return the wrapped object.
     */

    public E getObject(){
        return object;
    }

    /**
     * Overridden version of compareTo to implement Comparable.
     * This method is implemented in the same way as in AbstractNode,
     * with the following added behavior:
     * sibblings are ordered by using the compareTo method of the wrapped object.
     */
    @Override
    public int compareTo(T other){
        if(getParent()==null) return -1;
        if(other.getParent()==null) return 1;
        if (this.compareLevelTo(other)>0){
            if(getParent()==other) return 1;
            return getParent().compareTo(other);
        }
        if (this.compareLevelTo(other)<0){
            if(this==other.getParent()) return -1;
            return this.compareTo(other.getParent());
        }
        if(this.getParent()==other.getParent()){
            return object.compareTo(other.getObject());
        }
        return this.getParent().compareTo(other.getParent());
    }

    @Override
    public String toString(){
        return object.toString();
    }

}

