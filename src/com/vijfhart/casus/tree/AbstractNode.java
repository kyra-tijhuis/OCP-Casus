package com.vijfhart.casus.tree;

/**
 * An abstract implementation of Node.
 * This class has an implementation of setParent and isLeaf, and it implements LevelComparable.
 *
 * @author Marko Draisma
 * @version 1.0
 * @param <T> a subtype of AbstractNode: the actual node type to work with.
 */
public abstract class AbstractNode<T extends AbstractNode<T>> implements Node<T> {

    private T parent;
    private int descendantsCount=0;

    /**
     * Sets the parent of this node<p>
     * @param t: a node of the same type as this node.
     * @throws IllegalArgumentException if the provided parent node is a child node of this node.
     */
    public void setParent(T t){
        if(isChild(t))
            throw new IllegalArgumentException(String.format("%s is a child of %s, so can't be a parent",t, this));
        this.parent=t;
    }

    protected AbstractNode(){ }


    /**
     * Returns the parent of this node.
     * @return the parent of this node.
     */

    public T getParent(){
        return parent;
    }

    /**
     * Determines if the provided node is a child node of this node.
     * Used by setParent to determine if the provided node can be used as a parent node.
     * @param t: the node to be checked
     * @return true if the given node is a child node of this node, otherwise false.
     */
    private boolean isChild(T t){
        T node = t;
        while(node!=null && node.getParent()!=null){
            if(node.getParent()==this) return true;
            node=node.getParent();
        }
        return false;
    }

    /**
     * Compares the levels of the argument node and this node.
     *
     * @param other: another node
     * @return a positive int if this node has higher level than the other node,
     *         a negative int if this node has a lower leven than the other node,
     *         otherwise: zero.
     * @see LevelComparable
     */
    public int compareLevelTo(T other){
        if(other==null) return 1;
        if(other.getParent()==null) return 1;
        if(this.getParent()==null) return -1;
        if(this.getParent()==other.getParent()) return 0;
        return this.getParent().compareLevelTo(other.getParent());
    }

    /**
     * Used to determine the position of this Node in the tree.
     * The following rules are used to determine the position of a node:
     *   a node without a parent comes first
     *   the following node is a child node
     *   if that node has child nodes, these nodes will follow,
     *   otherwise, the other child nodes of the parent node will follow.
     *
     * @param other: the other node
     * @return a negative int if this comes before other,
     *         a positive int if this comes after other,
     *         otherwise zero.
     */
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

        return this.getParent().compareTo(other.getParent());
    }
}

