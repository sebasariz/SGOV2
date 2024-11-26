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
public class Paquete extends ActionForm {

    int idPaquete;
    String guia;
    int idEmpresa_creadora;
    int idEmpresa_sgo;
    long fecha_creacion;
    long fecha_entrega;
    long fecha_ingreso_bodega;
    String zona_bodega;
    //1. Solicitado
    //2. Recogido
    //3. En bodega
    //4. Ruta asignada
    //5. Reparto
    //6. Entregado
    //7. Direccion errada - En bodega
    //8. Persona no encontrada
    //9. Devolucion
    //10. Facturado
    //11. Finalizado
    //12. Enrutamiento
    public static String[] estados = {"Solicitado", "Recogido", "En bodega",
        "Ruta asignada", "Reparto", "Entregado", "Direccion errada - En bodega",
        "Persona no encontrada", "Persona no encontrada", "Devolucion", "Facturado",
        "Finalizado", "Enrutamiento"};
    String estado;
    String identificacion;
    String nombre_cliente;
    String correo;
    String direccion_origen;
    double lat_origen;
    double lng_origen;
    String direccion_entrega;
    double lat_entrega;
    double lng_entrega;
    String descripcion_paquete;
    String descripcion_extra;
    JSONArray jSONArray_trazabilidad;
    JSONArray jSONArray_anotaciones;
    JSONArray jSONArray_multimedia;
    double largo;
    double ancho;
    double alto;
    double peso;
    double valor_envio;
    transient FormFile file;
    transient double idEmpresaCliente;

    public String getZona_bodega() {
        return zona_bodega;
    }

    public void setZona_bodega(String zona_bodega) {
        this.zona_bodega = zona_bodega;
    }

    
    public long getFecha_ingreso_bodega() {
        return fecha_ingreso_bodega;
    }

    public void setFecha_ingreso_bodega(long fecha_ingreso_bodega) {
        this.fecha_ingreso_bodega = fecha_ingreso_bodega;
    }

    
    
    public double getIdEmpresaCliente() {
        return idEmpresaCliente;
    }

    public void setIdEmpresaCliente(double idEmpresaCliente) {
        this.idEmpresaCliente = idEmpresaCliente;
    }

     
    
    

    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getLat_origen() {
        return lat_origen;
    }

    public void setLat_origen(double lat_origen) {
        this.lat_origen = lat_origen;
    }

    public double getLng_origen() {
        return lng_origen;
    }

    public void setLng_origen(double lng_origen) {
        this.lng_origen = lng_origen;
    }

    public double getLat_entrega() {
        return lat_entrega;
    }

    public void setLat_entrega(double lat_entrega) {
        this.lat_entrega = lat_entrega;
    }

    public double getLng_entrega() {
        return lng_entrega;
    }

    public void setLng_entrega(double lng_entrega) {
        this.lng_entrega = lng_entrega;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public int getIdEmpresa_creadora() {
        return idEmpresa_creadora;
    }

    public void setIdEmpresa_creadora(int idEmpresa_creadora) {
        this.idEmpresa_creadora = idEmpresa_creadora;
    }

    public int getIdEmpresa_sgo() {
        return idEmpresa_sgo;
    }

    public void setIdEmpresa_sgo(int idEmpresa_sgo) {
        this.idEmpresa_sgo = idEmpresa_sgo;
    }

    public long getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(long fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public long getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(long fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getDireccion_origen() {
        return direccion_origen;
    }

    public void setDireccion_origen(String direccion_origen) {
        this.direccion_origen = direccion_origen;
    }

    public String getDireccion_entrega() {
        return direccion_entrega;
    }

    public void setDireccion_entrega(String direccion_entrega) {
        this.direccion_entrega = direccion_entrega;
    }

    public String getDescripcion_paquete() {
        return descripcion_paquete;
    }

    public void setDescripcion_paquete(String descripcion_paquete) {
        this.descripcion_paquete = descripcion_paquete;
    }

    public String getDescripcion_extra() {
        return descripcion_extra;
    }

    public void setDescripcion_extra(String descripcion_extra) {
        this.descripcion_extra = descripcion_extra;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getValor_envio() {
        return valor_envio;
    }

    public void setValor_envio(double valor_envio) {
        this.valor_envio = valor_envio;
    }

    public JSONArray getjSONArray_trazabilidad() {
        return jSONArray_trazabilidad;
    }

    public void setjSONArray_trazabilidad(JSONArray jSONArray_trazabilidad) {
        this.jSONArray_trazabilidad = jSONArray_trazabilidad;
    }

    public JSONArray getjSONArray_anotaciones() {
        return jSONArray_anotaciones;
    }

    public void setjSONArray_anotaciones(JSONArray jSONArray_anotaciones) {
        this.jSONArray_anotaciones = jSONArray_anotaciones;
    }

    public JSONArray getjSONArray_multimedia() {
        return jSONArray_multimedia;
    }

    public void setjSONArray_multimedia(JSONArray jSONArray_multimedia) {
        this.jSONArray_multimedia = jSONArray_multimedia;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

}
