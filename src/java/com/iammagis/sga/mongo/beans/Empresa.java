/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


/**
 *
 * @author sebastianarizmendy
 */
public class Empresa extends ActionForm {

    int idEmpresa;
    //incremental de los pedidos
    int incremental;
    //incremental de las guias
    int incremetnal_guia;
    String codigo;
    String nombre;
    String nit;
    String direccion;
    String ciudad;
    String contacto;
    String telefono;
    String logo;
    long fechaUltimoPago;
    double valorPendientePago;
    long fechaUltimoPedido;
    long fehaRegistro;
    //1 cliente iam magis
    //2 cliente de empresa
    int tipoEmpresa;
    int idEmpresaCreadora;
    //0 inactiva
    //1 activa
    int activa;
    String uuid;
    String correodespacho;
    String vendedor;
    //1 o 0 aprobada
    //2 pendiente de aprobacion
    int estado;
    String colorPrimario;
    String colorSecundario;
    transient FormFile file;
    String puntero;
    transient FormFile filePuntero;
    ArrayList<Submodulo> submodulos;
    double lat;
    double lng; 
    

    
    
     
    
    public int getActiva() {
        return activa;
    }

    public void setActiva(int activa) {
        this.activa = activa;
    }

    
    public int getIncremetnal_guia() {
        return incremetnal_guia;
    }

    public void setIncremetnal_guia(int incremetnal_guia) {
        this.incremetnal_guia = incremetnal_guia;
    }

    
    public long getFehaRegistro() {
        return fehaRegistro;
    }

    public void setFehaRegistro(long fehaRegistro) {
        this.fehaRegistro = fehaRegistro;
    }

    //string parametros ruteo
    String jsonRuteoParametros;

    public String getJsonRuteoParametros() {
        return jsonRuteoParametros;
    }

    public void setJsonRuteoParametros(String jsonRuteoParametros) {
        this.jsonRuteoParametros = jsonRuteoParametros;
    }
 

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getCorreodespacho() {
        return correodespacho;
    }

    public void setCorreodespacho(String correodespacho) {
        this.correodespacho = correodespacho;
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

    public ArrayList<Submodulo> getSubmodulos() {
        return submodulos;
    }

    public void setSubmodulos(ArrayList<Submodulo> submodulos) {
        this.submodulos = submodulos;
    }

    public int getIncremental() {
        return incremental;
    }

    public void setIncremental(int incremental) {
        this.incremental = incremental;
    }

    public String getPuntero() {
        return puntero;
    }

    public void setPuntero(String puntero) {
        this.puntero = puntero;
    }

    public FormFile getFilePuntero() {
        return filePuntero;
    }

    public void setFilePuntero(FormFile filePuntero) {
        this.filePuntero = filePuntero;
    }

    public String getColorPrimario() {
        return colorPrimario;
    }

    public void setColorPrimario(String colorPrimario) {
        this.colorPrimario = colorPrimario;
    }

    public String getColorSecundario() {
        return colorSecundario;
    }

    public void setColorSecundario(String colorSecundario) {
        this.colorSecundario = colorSecundario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(long fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public double getValorPendientePago() {
        return valorPendientePago;
    }

    public void setValorPendientePago(double valorPendientePago) {
        this.valorPendientePago = valorPendientePago;
    }

    public long getFechaUltimoPedido() {
        return fechaUltimoPedido;
    }

    public void setFechaUltimoPedido(long fechaUltimoPedido) {
        this.fechaUltimoPedido = fechaUltimoPedido;
    }

    public int getIdEmpresaCreadora() {
        return idEmpresaCreadora;
    }

    public void setIdEmpresaCreadora(int idEmpresaCreadora) {
        this.idEmpresaCreadora = idEmpresaCreadora;
    }

    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(int tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

}
