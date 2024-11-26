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
public class Pedido extends ActionForm{
    
    int idPedido;
    int incremental;
    long fechaCreacion;
    long fechaEntrega;
    long fechaDespacho;
    long fechaElimnacion;
    int idUsuarioCreador;
    Usuario usuarioCreador;
    int idUsuarioDespachador; 
    Usuario usuarioDespachador;
    int idUsuarioEliminador;
    Usuario usuarioEliminador;
    int idEmpresaCliente;
    Empresa empresaCliente;
    int idEmpresaCreadora;
    Empresa emmpresaCreadora;
    int estado;
    //1 pendiente
    //2 empacado parcial
    //3 completado
    //4 despachado
    //5 entregado
    //6 despacho parcial
    //7 cotizacion
    //-1 eliminado
    String nota; 
    String numeroGuia;
    String transportadora;
    String notaGuia;
    
    String idTercero;
    
    double lat;
    double lng; 
    String arrayProductosSolicitadosString;  

    public String getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(String idTercero) {
        this.idTercero = idTercero;
    }

    
    
    public String getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(String transportadora) {
        this.transportadora = transportadora;
    }

    public String getNotaGuia() {
        return notaGuia;
    }

    public void setNotaGuia(String notaGuia) {
        this.notaGuia = notaGuia;
    }

    
    
    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Usuario getUsuarioDespachador() {
        return usuarioDespachador;
    }

    public void setUsuarioDespachador(Usuario usuarioDespachador) {
        this.usuarioDespachador = usuarioDespachador;
    }

    public Usuario getUsuarioEliminador() {
        return usuarioEliminador;
    }

    public void setUsuarioEliminador(Usuario usuarioEliminador) {
        this.usuarioEliminador = usuarioEliminador;
    }

    public Empresa getEmmpresaCreadora() {
        return emmpresaCreadora;
    }

    public void setEmmpresaCreadora(Empresa emmpresaCreadora) {
        this.emmpresaCreadora = emmpresaCreadora;
    }

    
    
    public int getIdUsuarioEliminador() {
        return idUsuarioEliminador;
    }

    public void setIdUsuarioEliminador(int idUsuarioEliminador) {
        this.idUsuarioEliminador = idUsuarioEliminador;
    }

    public long getFechaElimnacion() {
        return fechaElimnacion;
    }

    public void setFechaElimnacion(long fechaElimnacion) {
        this.fechaElimnacion = fechaElimnacion;
    }

    
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    
    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    
    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    
    public int getIdEmpresaCreadora() {
        return idEmpresaCreadora;
    }

    public void setIdEmpresaCreadora(int idEmpresaCreadora) {
        this.idEmpresaCreadora = idEmpresaCreadora;
    }

     
    
    public String getArrayProductosSolicitadosString() {
        return arrayProductosSolicitadosString;
    }

    public void setArrayProductosSolicitadosString(String arrayProductosSolicitadosString) {
        this.arrayProductosSolicitadosString = arrayProductosSolicitadosString;
    }

    
    
    public Empresa getEmpresaCliente() {
        return empresaCliente;
    }

    public void setEmpresaCliente(Empresa empresaCliente) {
        this.empresaCliente = empresaCliente;
    }

    
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIncremental() {
        return incremental;
    }

    public void setIncremental(int incremental) {
        this.incremental = incremental;
    }

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(long fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public long getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(long fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public int getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(int idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public int getIdUsuarioDespachador() {
        return idUsuarioDespachador;
    }

    public void setIdUsuarioDespachador(int idUsuarioDespachador) {
        this.idUsuarioDespachador = idUsuarioDespachador;
    }

    public int getIdEmpresaCliente() {
        return idEmpresaCliente;
    }

    public void setIdEmpresaCliente(int idEmpresaCliente) {
        this.idEmpresaCliente = idEmpresaCliente;
    }
  
    
}
