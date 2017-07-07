package com.vijfhart.casus.test;
import com.vijfhart.casus.tree.*;
class WerknemerNode extends WrapperNode<WerknemerNode, Werknemer>{


    WerknemerNode(Werknemer werknemer, WerknemerNode parent){
        super(werknemer,parent);
    }

    WerknemerNode(Werknemer werknemer){
        super(werknemer);
    }



    public boolean equals(WerknemerNode other){
        return getObject().equals(other.getObject());
    }

}
