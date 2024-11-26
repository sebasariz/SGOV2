/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;


/**
 *
 * @author sebastianarizmendy
 */
public class Geocerca extends ActionForm{
    
    int idGeocerca;
    String nombre;
    int idEmpresa_creadora;
    int idUsuario_creador;
    String puntos_geocerca;
    ArrayList<Usuario> usuarios_pertenecientes;
    String repartidores;

    public String getRepartidores() {
        return repartidores;
    }

    public void setRepartidores(String repartidores) {
        this.repartidores = repartidores;
    }
    
    

    
    public int getIdGeocerca() {
        return idGeocerca;
    }

    public void setIdGeocerca(int idGeocerca) {
        this.idGeocerca = idGeocerca;
    }

    public int getIdEmpresa_creadora() {
        return idEmpresa_creadora;
    }

    public void setIdEmpresa_creadora(int idEmpresa_creadora) {
        this.idEmpresa_creadora = idEmpresa_creadora;
    }

     

    public int getIdUsuario_creador() {
        return idUsuario_creador;
    }

    public void setIdUsuario_creador(int idUsuario_creador) {
        this.idUsuario_creador = idUsuario_creador;
    }

    public String getPuntos_geocerca() {
        return puntos_geocerca;
    }

    public void setPuntos_geocerca(String puntos_geocerca) {
        this.puntos_geocerca = puntos_geocerca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Usuario> getUsuarios_pertenecientes() {
        return usuarios_pertenecientes;
    }

    public void setUsuarios_pertenecientes(ArrayList<Usuario> usuarios_pertenecientes) {
        this.usuarios_pertenecientes = usuarios_pertenecientes;
    }
 
    
    
    
}
