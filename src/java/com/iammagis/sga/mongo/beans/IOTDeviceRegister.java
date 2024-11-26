/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;

import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class IOTDeviceRegister {
    
    int idIotDeviceRegister;
    int idIotDevice;
    long fecha;
    JSONObject jSONObject;

    public JSONObject getjSONObject() {
        return jSONObject;
    }

    public void setjSONObject(JSONObject jSONObject) {
        this.jSONObject = jSONObject;
    }

    
    public int getIdIotDeviceRegister() {
        return idIotDeviceRegister;
    }

    

    public void setIdIotDeviceRegister(int idIotDeviceRegister) {
        this.idIotDeviceRegister = idIotDeviceRegister;
    }

    public int getIdIotDevice() {
        return idIotDevice;
    }

    public void setIdIotDevice(int idIotDevice) {
        this.idIotDevice = idIotDevice;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    } 
    
}
