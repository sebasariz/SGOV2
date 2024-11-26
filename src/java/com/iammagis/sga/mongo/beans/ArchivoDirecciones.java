/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author sebastianarizmendy
 */
public class ArchivoDirecciones extends ActionForm{
    
    public static String[] estados ={"Procesando","Terminado"};
    int idArchivoDirecciones;
    long fecha;
    long registros;
    int idEmpresa;
    int idUsuario;
    String estado;
    String nombre_usuario;
    String archivo;
    transient FormFile file;
    //int 1= archivo
    //int 2= json
    //int 3= ftp
    int tipo;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    
    
    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }
    
    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
    public int getIdArchivoDirecciones() {
        return idArchivoDirecciones;
    }

    public void setIdArchivoDirecciones(int idArchivoDirecciones) {
        this.idArchivoDirecciones = idArchivoDirecciones;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public long getRegistros() {
        return registros;
    }

    public void setRegistros(long registros) {
        this.registros = registros;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
    
    
            
    
}
