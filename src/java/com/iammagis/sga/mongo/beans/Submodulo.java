/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;
/**
 *
 * @author sebastianarizmendy
 */
public class Submodulo {
    
    int idSubmodulo;
    String nombre;
    String icon;
    String accion;
    int idModulo;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    

    public int getIdSubmodulo() {
        return idSubmodulo;
    }

    public void setIdSubmodulo(int idSubmodulo) {
        this.idSubmodulo = idSubmodulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }
    
    
    
    
}
