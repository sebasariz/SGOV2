/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;

import org.apache.struts.action.ActionForm;
import org.json.JSONObject;


/**
 *
 * @author sebastianarizmendy
 */
public class IOTDevice extends ActionForm {

    transient public static String tipoDefine[] = {"Sensor", "Servo", "Motor de paso", "Led RGB"};
    transient public static String tipoVisorDefina[] = {"Reloj", "Digital"};
    int idIOTDevice;
    String nombre;
    int idEmpresa;
    String uuid;
    String tipoString;
    int tipoVisor;
    long fechaUltimoRegistro;
    long fechaUltimaAlerta;
    JSONObject ultimoValor;
    JSONObject valoresMax;
    JSONObject valoresMin;

    public long getFechaUltimaAlerta() {
        return fechaUltimaAlerta;
    }

    public void setFechaUltimaAlerta(long fechaUltimaAlerta) {
        this.fechaUltimaAlerta = fechaUltimaAlerta;
    }

    public static String[] getTipoDefine() {
        return tipoDefine;
    }

    public static void setTipoDefine(String[] tipoDefine) {
        IOTDevice.tipoDefine = tipoDefine;
    }

    public static String[] getTipoVisorDefina() {
        return tipoVisorDefina;
    }

    public static void setTipoVisorDefina(String[] tipoVisorDefina) {
        IOTDevice.tipoVisorDefina = tipoVisorDefina;
    }

    public JSONObject getUltimoValor() {
        return ultimoValor;
    }

    public void setUltimoValor(JSONObject ultimoValor) {
        this.ultimoValor = ultimoValor;
    }

    public JSONObject getValoresMax() {
        return valoresMax;
    }

    public void setValoresMax(JSONObject valoresMax) {
        this.valoresMax = valoresMax;
    }

    public JSONObject getValoresMin() {
        return valoresMin;
    }

    public void setValoresMin(JSONObject valoresMin) {
        this.valoresMin = valoresMin;
    }

    
  

    public String getTipoString() {
        return tipoString;
    }

    public void setTipoString(String tipoString) {
        this.tipoString = tipoString;
    }

    public int getTipoVisor() {
        return tipoVisor;
    }

    public void setTipoVisor(int tipoVisor) {
        this.tipoVisor = tipoVisor;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdIOTDevice() {
        return idIOTDevice;
    }

    public void setIdIOTDevice(int idIOTDevice) {
        this.idIOTDevice = idIOTDevice;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getFechaUltimoRegistro() {
        return fechaUltimoRegistro;
    }

    public void setFechaUltimoRegistro(long fechaUltimoRegistro) {
        this.fechaUltimoRegistro = fechaUltimoRegistro;
    }

}
