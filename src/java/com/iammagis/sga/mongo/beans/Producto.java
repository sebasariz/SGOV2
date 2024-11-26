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
public class Producto extends ActionForm {

    int idProducto;
    String nombre;
    String referencia;
    int cantidad;
    double valor;
    double valorCosto;
    int idEmpresa;
    String imagen;
    transient FormFile file;
    transient int reinicio;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public double getValorCosto() {
        return valorCosto;
    }

    public void setValorCosto(double valorCosto) {
        this.valorCosto = valorCosto;
    }

    public int getReinicio() {
        return reinicio;
    }

    public void setReinicio(int reinicio) {
        this.reinicio = reinicio;
    }

    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
