/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;

import org.apache.struts.action.ActionForm;
/**
 *
 * @author sebastianarizmendy
 */
public class RutaRecoleccion extends ActionForm {

    int idRutaServicio;
    long fechaCreacion;
    long fechaComienzo;
    String ruta;
    String paquetes;
    String nombre_conductor;
    int idGeocerca;
    int idConductor;
    String nombre_geocerca;
    int idEmpresa_creadora;
    String nombre_empresa;
    //1. Preparada
    //2. Ejecución
    //3. Terminada
    String estado;

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    } 
    public String getNombre_conductor() {
        return nombre_conductor;
    }

    public void setNombre_conductor(String nombre_conductor) {
        this.nombre_conductor = nombre_conductor;
    }

    public int getIdGeocerca() {
        return idGeocerca;
    }

    public void setIdGeocerca(int idGeocerca) {
        this.idGeocerca = idGeocerca;
    }

    public String getNombre_geocerca() {
        return nombre_geocerca;
    }

    public void setNombre_geocerca(String nombre_geocerca) {
        this.nombre_geocerca = nombre_geocerca;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

     
     

    public int getIdEmpresa_creadora() {
        return idEmpresa_creadora;
    }

    public void setIdEmpresa_creadora(int idEmpresa_creadora) {
        this.idEmpresa_creadora = idEmpresa_creadora;
    }

    
    public int getIdRutaServicio() {
        return idRutaServicio;
    }

    public void setIdRutaServicio(int idRutaServicio) {
        this.idRutaServicio = idRutaServicio;
    }

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getFechaComienzo() {
        return fechaComienzo;
    }

    public void setFechaComienzo(long fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(String paquetes) {
        this.paquetes = paquetes;
    }

     
}
