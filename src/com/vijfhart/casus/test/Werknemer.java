package com.vijfhart.casus.test;
import com.vijfhart.casus.tree.*;
class Werknemer implements Comparable<Werknemer>{
    private String naam;
    private int salaris;

    Werknemer(String naam, int salaris){
        this.naam=naam;
        this.salaris=salaris;
    }

    public int getSalaris(){
        return salaris;
    }

    @Override
    public int compareTo(Werknemer other){
        return getNaam().compareTo(other.getNaam());
    }

    public String getNaam(){
        return naam;
    }

    public String toString(){
        return naam;
    }

}
