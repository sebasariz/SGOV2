/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;

import java.util.List;
/**
 *
 * @author sebastianarizmendy
 */
public class Modulo {
    
    int idModulo;
    String nombre;
    transient List<Submodulo>  submodulos;

    public List<Submodulo> getSubmodulos() {
        return submodulos;
    }

    public void setSubmodulos(List<Submodulo> submodulos) {
        this.submodulos = submodulos;
    }
    
    

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
