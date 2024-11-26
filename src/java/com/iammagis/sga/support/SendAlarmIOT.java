/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.support;

import com.google.gson.Gson;
import com.iammagis.sga.mongo.IOTDeviceAlarmaMongoController;
import com.iammagis.sga.mongo.IOTDeviceMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.IOTDevice;
import com.iammagis.sga.mongo.beans.IOTDeviceAlarma;
import com.iammagis.sga.mongo.beans.IOTDeviceRegister;
import com.iammagis.sga.mongo.beans.Usuario;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class SendAlarmIOT {

    static Gson gson = new Gson();
    static PropertiesAcces propertiesAcces = new PropertiesAcces();

    public static JSONArray sendAlarm(IOTDeviceRegister iOTDeviceRegister, String context) throws IOException, JSONException {
        
        //mirmamos si tiene alertas para enviar con el valor registrado
        IOTDeviceMongoController iOTDeviceMongoController = new IOTDeviceMongoController();
        IOTDeviceAlarmaMongoController iOTDeviceAlarmaMongoController = new IOTDeviceAlarmaMongoController();
        List<DBObject> IotDeviceAlarmsDbObjects = iOTDeviceAlarmaMongoController.getIotDeviceAlarmsIotDevice(iOTDeviceRegister.getIdIotDevice());
        String mensaje = "";
        JSONArray jSONArrayActuadores = new JSONArray(); 
        
//        System.out.println("send alarm: " + IotDeviceAlarmsDbObjects.size());
        for (DBObject dBObject : IotDeviceAlarmsDbObjects) {
            boolean send = false;
            IOTDeviceAlarma iOTDeviceAlarma = gson.fromJson(JSON.serialize(dBObject), IOTDeviceAlarma.class);
            IOTDevice iOTDevice = iOTDeviceMongoController.findIOTDevice(iOTDeviceAlarma.getIdIOTDevice());

            JSONArray jSONArrayHistory = new JSONArray(iOTDeviceAlarma.getjSONArrayHisotiralActivaciones());
            JSONObject jSONObjectHistory = new JSONObject();
            if (jSONArrayHistory.length() > 1) {
                jSONObjectHistory = jSONArrayHistory.getJSONObject(jSONArrayHistory.length() - 1);
            }

            if (iOTDevice.getUltimoValor().has(iOTDeviceAlarma.getKey())) {
                double valor = iOTDevice.getUltimoValor().getDouble(iOTDeviceAlarma.getKey());
                double treintaminutos = 30 * 60 * 1000;
//                double treintaminutos = 60 * 1000;
                boolean ingresar = false;
                System.out.println("entrando por ingresar");
                if (!jSONObjectHistory.has("value")) {
                    ingresar = true;
                    System.out.println("entrando por no contener valor");
                } else if (jSONObjectHistory.getDouble("fecha") + treintaminutos < System.currentTimeMillis()) {
                    ingresar = true;
                    System.out.println("entre por fecha");
                }

                if (ingresar) {
                    String titulo = "Alerta dispositivo";
                    if (iOTDeviceAlarma.getTipo().equals(IOTDeviceAlarma.tipoAlarma[0]) && iOTDeviceAlarma.getValor() > valor) {
                        //Menor que
                        mensaje += "El dispositivo: " + iOTDevice.getNombre() + ", ha registrado un valor inferior a: "
                                + iOTDeviceAlarma.getValor() + ", Valor registrado: " + valor;
                        iOTDeviceAlarma.setFechaUltimoDisparo(System.currentTimeMillis());
                        iOTDeviceAlarma.setNumeroActivaciones(iOTDeviceAlarma.getNumeroActivaciones() + 1);
                        send = true;
                    } else if (iOTDeviceAlarma.getTipo().equals(IOTDeviceAlarma.tipoAlarma[1]) && iOTDeviceAlarma.getValor() < valor) {
                        //Mayor que
                        mensaje += "El dispositivo: " + iOTDevice.getNombre() + ", ha registrado un valor superior a: "
                                + iOTDeviceAlarma.getValor() + ", Valor registrado: " + valor;
                        iOTDeviceAlarma.setFechaUltimoDisparo(System.currentTimeMillis());
                        iOTDeviceAlarma.setNumeroActivaciones(iOTDeviceAlarma.getNumeroActivaciones() + 1);
                        send = true;
                    }

                    jSONObjectHistory.put("fecha", System.currentTimeMillis());
                    jSONObjectHistory.put("valor", valor);
                    jSONArrayHistory.put(jSONObjectHistory);
                    iOTDeviceAlarma.setjSONArrayHisotiralActivaciones(jSONArrayHistory.toString());

                    iOTDeviceAlarmaMongoController.editIOTDeviceAlarma(iOTDeviceAlarma);
                    iOTDevice.setFechaUltimaAlerta(System.currentTimeMillis());
                    iOTDeviceMongoController.editIOTDevice(iOTDevice);

                    if (send) {
                        System.out.println("sending");
                        sendNotification(iOTDevice.getIdEmpresa(), titulo, mensaje, new JSONObject(), context);
                    }
                    
                    //aqui cargamos si tiene actuaciones
                    if(!iOTDeviceAlarma.getIddeviceactuacion().equals("") &&
                            !iOTDeviceAlarma.getValordeviceactuacion().equals("")){
                        //es porque tiene cositas diferentes
                        String[] idActuaciones = iOTDeviceAlarma.getIddeviceactuacion().split(",");
                        String[]  valorActuaciones = iOTDeviceAlarma.getValordeviceactuacion().split(",");
                        int i =0;
                        while (i<idActuaciones.length) {
                            String uuidActuacion = idActuaciones[i];
                            String valorActuacion = valorActuaciones[i];
                            JSONObject jSONObjectActuacion = new JSONObject(); 
                            jSONObjectActuacion.put("uuid_sensor", iOTDevice.getUuid());
                            jSONObjectActuacion.put("variable_sensor", iOTDeviceAlarma.getKey());
                            jSONObjectActuacion.put("type_sensor", iOTDeviceAlarma.getTipo());
                            jSONObjectActuacion.put("value_sensor", iOTDeviceAlarma.getValor()); 
                            jSONObjectActuacion.put("uuid_actuador", uuidActuacion);
                            jSONObjectActuacion.put("value_actuador", valorActuacion);
                            jSONArrayActuadores.put(jSONObjectActuacion);
                            i++;
                        }
                        
                    }
                }
            }
        }
        return jSONArrayActuadores;

    }

    private static void sendNotification(int idInstitucion, String titulo, String mensaje, JSONObject data, String context) throws IOException, JSONException {
        UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
        List<DBObject> usuarioDbObject = usuarioMongoController.getUsuariosInstitucionAndTipo(idInstitucion, 2);
        ArrayList<String> usuariosString = new ArrayList<>();
        JSONArray jSONArrayPushTokens = new JSONArray();
        for (DBObject dBObject : usuarioDbObject) {
            Usuario usuario = gson.fromJson(JSON.serialize(dBObject), Usuario.class
            );
            usuariosString.add(usuario.getEmail());
//            System.out.println("usuario.getEmail(): " + usuario.getEmail());
            //obtenemos los tokens
            if (usuario.getjSONArrayAndroid() != null) {
                JSONArray jSONArrayTokensUsuario = new JSONArray(usuario.getjSONArrayAndroid());
                for (int i = 0; i < jSONArrayTokensUsuario.length(); i++) {
                    JSONObject jSONObjectMap = jSONArrayTokensUsuario.getJSONObject(i);
                    if (jSONObjectMap.has("token")) {
                        jSONArrayPushTokens.put(jSONObjectMap.getString("token"));
                    }
                }
            }
        }
        //aqui falta la plantilla de la notificacion para que salga bonito
        String template = Correo.getTemplate(Correo.alarmaTemplate, context);
        template = template.replace("$mensaje", mensaje);
        template = template.replace("$server", propertiesAcces.resourcesServer);
        template = template.replace("$server", propertiesAcces.resourcesServer);
        template = template.replace("$server", propertiesAcces.resourcesServer);
        template = template.replace("$server", propertiesAcces.resourcesServer);
        template = template.replace("$server", propertiesAcces.resourcesServer);
        template = template.replace("$banner", "img/template/banner.png");
        Correo correo = new Correo(titulo, template, usuariosString);
        correo.start();

        //enviando notificacion push
        SendPushMessage.send_FCM_NotificationMulti(jSONArrayPushTokens, titulo, mensaje, data);

    }

}
