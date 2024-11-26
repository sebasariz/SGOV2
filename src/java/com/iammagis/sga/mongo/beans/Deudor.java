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
public class Deudor extends ActionForm {

    int idDeudor;
    String identificacion;
    String nombreDeudor;
    String representanteLegal;
    String direccion;
    String telefono;
    String celular;
    String contacto;
    String email;
    String ciudad;
    String anotaciones;
    JSONArray obligacionesPendientes;
    double saldoActual;
    double saldoTotal;
    long fechaRegistro;
    long fechaUltimoContacto;
    long fechaProximoPago;
    //Estado
    // 1. Nuevo
    // 2. Acuerdo de pago
    // 3. Sin contacto
    // 4. Cobro juridico
    int estado;
    JSONArray historialContactos;
    JSONArray multimedia;
    int idEmpresa;
    int idEmpresaCliente;
    String nombreEmpresaCliente;
    //transients
    transient FormFile file;
    transient String obligacionesPendientesString;

    public long getFechaProximoPago() {
        return fechaProximoPago;
    }

    public void setFechaProximoPago(long fechaProximoPago) {
        this.fechaProximoPago = fechaProximoPago;
    }

    
    
    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    

    public String getNombreEmpresaCliente() {
        return nombreEmpresaCliente;
    }

    public void setNombreEmpresaCliente(String nombreEmpresaCliente) {
        this.nombreEmpresaCliente = nombreEmpresaCliente;
    }

    public int getIdEmpresaCliente() {
        return idEmpresaCliente;
    }

    public void setIdEmpresaCliente(int idEmpresaCliente) {
        this.idEmpresaCliente = idEmpresaCliente;
    }

    public String getObligacionesPendientesString() {
        return obligacionesPendientesString;
    }

    public void setObligacionesPendientesString(String obligacionesPendientesString) {
        this.obligacionesPendientesString = obligacionesPendientesString;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getAnotaciones() {
        return anotaciones;
    }

    public void setAnotaciones(String anotaciones) {
        this.anotaciones = anotaciones;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdDeudor() {
        return idDeudor;
    }

    public void setIdDeudor(int idDeudor) {
        this.idDeudor = idDeudor;
    }

    public String getNombreDeudor() {
        return nombreDeudor;
    }

    public void setNombreDeudor(String nombreDeudor) {
        this.nombreDeudor = nombreDeudor;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public JSONArray getObligacionesPendientes() {
        return obligacionesPendientes;
    }

    public void setObligacionesPendientes(JSONArray obligacionesPendientes) {
        this.obligacionesPendientes = obligacionesPendientes;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public long getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public long getFechaUltimoContacto() {
        return fechaUltimoContacto;
    }

    public void setFechaUltimoContacto(long fechaUltimoContacto) {
        this.fechaUltimoContacto = fechaUltimoContacto;
    }

    public JSONArray getHistorialContactos() {
        return historialContactos;
    }

    public void setHistorialContactos(JSONArray historialContactos) {
        this.historialContactos = historialContactos;
    }

    public JSONArray getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(JSONArray multimedia) {
        this.multimedia = multimedia;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

}
