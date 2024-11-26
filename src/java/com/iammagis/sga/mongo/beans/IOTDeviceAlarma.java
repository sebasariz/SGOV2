package com.iammagis.sga.mongo.beans;

import org.apache.struts.action.ActionForm;

public class IOTDeviceAlarma extends ActionForm {
   public static transient String[] tipoAlarma = new String[]{"Menor que", "Mayor que", "Cambio estado"};
   int idIOTDeviceAlarma;
   int idIOTDevice;
   int idEmpresa;
   String nombre;
   String key;
   String tipo;
   int tipoActuacicion;
   double valor;
   int numeroActivaciones;
   long fechaUltimoDisparo;
   long fechaCreacion;
   String jSONArrayHisotiralActivaciones;
   String iddeviceactuacion;
   String valordeviceactuacion;

   public String getIddeviceactuacion() {
      return this.iddeviceactuacion;
   }

   public void setIddeviceactuacion(String iddeviceactuacion) {
      this.iddeviceactuacion = iddeviceactuacion;
   }

   public String getValordeviceactuacion() {
      return this.valordeviceactuacion;
   }

   public void setValordeviceactuacion(String valordeviceactuacion) {
      this.valordeviceactuacion = valordeviceactuacion;
   }

   public int getTipoActuacicion() {
      return this.tipoActuacicion;
   }

   public void setTipoActuacicion(int tipoActuacicion) {
      this.tipoActuacicion = tipoActuacicion;
   }

   public String getKey() {
      return this.key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public int getIdEmpresa() {
      return this.idEmpresa;
   }

   public void setIdEmpresa(int idEmpresa) {
      this.idEmpresa = idEmpresa;
   }

   public String getjSONArrayHisotiralActivaciones() {
      return this.jSONArrayHisotiralActivaciones;
   }

   public void setjSONArrayHisotiralActivaciones(String jSONArrayHisotiralActivaciones) {
      this.jSONArrayHisotiralActivaciones = jSONArrayHisotiralActivaciones;
   }

   public int getNumeroActivaciones() {
      return this.numeroActivaciones;
   }

   public void setNumeroActivaciones(int numeroActivaciones) {
      this.numeroActivaciones = numeroActivaciones;
   }

   public long getFechaUltimoDisparo() {
      return this.fechaUltimoDisparo;
   }

   public void setFechaUltimoDisparo(long fechaUltimoDisparo) {
      this.fechaUltimoDisparo = fechaUltimoDisparo;
   }

   public long getFechaCreacion() {
      return this.fechaCreacion;
   }

   public void setFechaCreacion(long fechaCreacion) {
      this.fechaCreacion = fechaCreacion;
   }

   public int getIdIOTDeviceAlarma() {
      return this.idIOTDeviceAlarma;
   }

   public void setIdIOTDeviceAlarma(int idIOTDeviceAlarma) {
      this.idIOTDeviceAlarma = idIOTDeviceAlarma;
   }

   public int getIdIOTDevice() {
      return this.idIOTDevice;
   }

   public void setIdIOTDevice(int idIOTDevice) {
      this.idIOTDevice = idIOTDevice;
   }

   public String getNombre() {
      return this.nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public String getTipo() {
      return this.tipo;
   }

   public void setTipo(String tipo) {
      this.tipo = tipo;
   }

   public double getValor() {
      return this.valor;
   }

   public void setValor(double valor) {
      this.valor = valor;
   }
}