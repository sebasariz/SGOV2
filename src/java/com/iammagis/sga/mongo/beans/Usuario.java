/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;
import org.json.JSONArray;
/**
 *
 * @author sebasariz
 */
public class Usuario extends ActionForm {
    
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String pass;
    private String sessionToken;
    private String documento;
    private String telefono;
    private double lat;
    private double lng;
    private long fecha;
     
    
    
    //1 root
    //2 Administrador 
    //3 usuario 
    private int idTipoUsuario;
    private int idEmpresa;
    private int idEmpresaCliente;
    private String jSONArrayAndroid;
    private ArrayList<Submodulo> submodulos;
 
    
    
    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
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

    
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getjSONArrayAndroid() {
        return jSONArrayAndroid;
    }

    public void setjSONArrayAndroid(String jSONArrayAndroid) {
        this.jSONArrayAndroid = jSONArrayAndroid;
    }

    
    

    public ArrayList<Submodulo> getSubmodulos() {
        return submodulos;
    }

    public void setSubmodulos(ArrayList<Submodulo> submodulos) {
        this.submodulos = submodulos;
    }

     

    
    
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    } 

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdEmpresaCliente() {
        return idEmpresaCliente;
    }

    public void setIdEmpresaCliente(int idEmpresaCliente) {
        this.idEmpresaCliente = idEmpresaCliente;
    }

    
    
     
    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }
    
    

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    
    

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    } 
}
