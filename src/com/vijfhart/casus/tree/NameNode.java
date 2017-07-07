package com.vijfhart.casus.tree;

public class NameNode extends AbstractNode<NameNode>{

    private String name;

    public NameNode(String name, NameNode parent){
        setParent(parent);
        this.name=name;
    }

    public NameNode(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public int compareTo(NameNode other){
        if(this.getParent()==null) return -1;
        if(other.getParent()==null) return 1;
        if (this.compareLevelTo(other)>0){
            if(this.getParent()==other) return 1;
            return this.getParent().compareTo(other);
        }
        if (this.compareLevelTo(other)<0){
            if(this==other.getParent()) return -1;
            return this.compareTo(other.getParent());
        }
        if (this.getParent()==other.getParent())
            return getName().compareTo(other.getName());
        return this.getParent().compareTo(other.getParent());
    }

    public String toString(){
        return name;
    }

}
