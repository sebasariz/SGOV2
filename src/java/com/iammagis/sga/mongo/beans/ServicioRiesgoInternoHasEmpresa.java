/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.beans;

import org.json.JSONArray;
/**
 *
 * @author sebastianarizmendy
 */
public class ServicioRiesgoInternoHasEmpresa {
    
    int idServicioRiesgoInternoHasEmpresa;
    int idEmpresa;
    JSONArray personasSoliciatadas;
    JSONArray documentosPorServicioEmpresa;
    long fecha_creacion;

    public JSONArray getDocumentosPorServicioEmpresa() {
        return documentosPorServicioEmpresa;
    }

    public void setDocumentosPorServicioEmpresa(JSONArray documentosPorServicioEmpresa) {
        this.documentosPorServicioEmpresa = documentosPorServicioEmpresa;
    }
    
    public int getIdServicioRiesgoInternoHasEmpresa() {
        return idServicioRiesgoInternoHasEmpresa;
    }

    public void setIdServicioRiesgoInternoHasEmpresa(int idServicioRiesgoInternoHasEmpresa) {
        this.idServicioRiesgoInternoHasEmpresa = idServicioRiesgoInternoHasEmpresa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public JSONArray getPersonasSoliciatadas() {
        return personasSoliciatadas;
    }

    public void setPersonasSoliciatadas(JSONArray personasSoliciatadas) {
        this.personasSoliciatadas = personasSoliciatadas;
    }

    public long getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(long fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    
    
    
}
