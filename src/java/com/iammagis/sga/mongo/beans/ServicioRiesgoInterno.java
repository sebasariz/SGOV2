/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.json.JSONArray;
/**
 *
 * @author sebastianarizmendy
 */
public class ServicioRiesgoInterno extends ActionForm {

    int idServicioRiesgoInterno;
    String nombre;
    String descripcionCorta;
    String descripcion;
    String img;
    int idEmpresa;
    transient FormFile file;
    JSONArray documentosPorServicio;
    double valor;
    double comision;
    double comisionAcumulada;
    double valorLiquidar;
    
    long fechaUltimoServicio;
    int numeroServiciosPendientes;

    
    
    public double getComisionAcumulada() {
        return comisionAcumulada;
    }

    public void setComisionAcumulada(double comisionAcumulada) {
        this.comisionAcumulada = comisionAcumulada;
    }

    public double getValorLiquidar() {
        return valorLiquidar;
    }

    public void setValorLiquidar(double valorLiquidar) {
        this.valorLiquidar = valorLiquidar;
    }

    
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    
    
    public long getFechaUltimoServicio() {
        return fechaUltimoServicio;
    }

    public void setFechaUltimoServicio(long fechaUltimoServicio) {
        this.fechaUltimoServicio = fechaUltimoServicio;
    }

    public int getNumeroServiciosPendientes() {
        return numeroServiciosPendientes;
    }

    public void setNumeroServiciosPendientes(int numeroServiciosPendientes) {
        this.numeroServiciosPendientes = numeroServiciosPendientes;
    }
    

    
    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

     

    
    
    
    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public int getIdServicioRiesgoInterno() {
        return idServicioRiesgoInterno;
    }

    public void setIdServicioRiesgoInterno(int idServicioRiesgoInterno) {
        this.idServicioRiesgoInterno = idServicioRiesgoInterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public JSONArray getDocumentosPorServicio() {
        return documentosPorServicio;
    }

    public void setDocumentosPorServicio(JSONArray documentosPorServicio) {
        this.documentosPorServicio = documentosPorServicio;
    }

}
