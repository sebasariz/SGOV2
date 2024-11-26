package com.iammagis.sga.iot;

import com.iammagis.sga.mongo.IOTDeviceMongoController;
import com.iammagis.sga.mongo.IOTDeviceRegisterMongoController;
import com.iammagis.sga.mongo.beans.IOTDevice;
import com.iammagis.sga.mongo.beans.IOTDeviceRegister;
import com.iammagis.sga.support.SendAlarmIOT;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendDataIOTDevice extends Action {


    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        JSONObject jSONObject = new JSONObject();
        String imaiot = request.getParameter("uuid");

        try {
            if (imaiot.equalsIgnoreCase("IOTIM")) {
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                reader = request.getReader();

                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                } finally {
                    reader.close();
                }

                if (sb.toString() != null && !sb.toString().equals("")) {
                    JSONArray jSONArrayDevicesActuadores = new JSONArray();
                    JSONObject jsonObjectDevices = new JSONObject(sb.toString());
                    Iterator keys = jsonObjectDevices.keys(); 
                    while (keys.hasNext()) {
                        String keyGet = (String) keys.next();
                        JSONObject jsonObjectValores = jsonObjectDevices.getJSONObject(keyGet);
                        System.out.println("jsonIn in: " + jsonObjectValores);
                        IOTDeviceMongoController deviceMongoController = new IOTDeviceMongoController();
                        IOTDevice iOTDevice = deviceMongoController.findIOTDeviceByUuid(jsonObjectValores.getString("uuid"));
                        if (iOTDevice != null) {
                            jsonObjectValores.remove("uuid");
                            iOTDevice.setFechaUltimoRegistro(System.currentTimeMillis());
                            iOTDevice.setUltimoValor(jsonObjectValores);
                            JSONObject jSONObjectMax = iOTDevice.getValoresMax();
                            JSONObject jSONObjectMin = iOTDevice.getValoresMin();
                            Iterator iterator = jsonObjectValores.keys();

                            while (iterator.hasNext()) {
                                String key = (String) iterator.next();
                                double valor = jsonObjectValores.getDouble(key);
                                if (jSONObjectMax != null && jSONObjectMax.has(key)) {
                                    if (jSONObjectMax.getDouble(key) < valor) {
                                        jSONObjectMax.put(key, valor);
                                    }
                                } else {
                                    if (jSONObjectMax == null) {
                                        jSONObjectMax = new JSONObject();
                                    }

                                    jSONObjectMax.put(key, valor);
                                }

                                if (jSONObjectMin != null && jSONObjectMin.has(key)) {
                                    if (jSONObjectMin.getDouble(key) > valor) {
                                        jSONObjectMin.put(key, valor);
                                    }
                                } else {
                                    if (jSONObjectMin == null) {
                                        jSONObjectMin = new JSONObject();
                                    }

                                    jSONObjectMin.put(key, valor);
                                }
                            }

                            iOTDevice.setValoresMax(jSONObjectMax);
                            iOTDevice.setValoresMin(jSONObjectMin);
                            IOTDeviceMongoController iOTDeviceMongoController = new IOTDeviceMongoController();
                            iOTDeviceMongoController.editIOTDevice(iOTDevice);
                            IOTDeviceRegisterMongoController iOTDeviceRegisterMongoController = new IOTDeviceRegisterMongoController();
                            IOTDeviceRegister iOTDeviceRegister = new IOTDeviceRegister();
                            iOTDeviceRegister.setFecha(System.currentTimeMillis());
                            iOTDeviceRegister.setIdIotDevice(iOTDevice.getIdIOTDevice());
                            iOTDeviceRegister.setjSONObject(jsonObjectValores);
                            iOTDeviceRegisterMongoController.createIOTDeviceRegister(iOTDeviceRegister);
                            JSONArray jSONArrayActuacionDispositivo = SendAlarmIOT.sendAlarm(iOTDeviceRegister, this.getServlet().getServletContext().getRealPath(""));
                            System.out.println("cargando los devices: " + jSONArrayActuacionDispositivo.length());

                            for (int i = 0; i < jSONArrayActuacionDispositivo.length(); ++i) {
                                System.out.println("jSONArrayActuacionDispositivo.getJSONObject(i): " + jSONArrayActuacionDispositivo.getJSONObject(i));
                                jSONArrayDevicesActuadores.put((Object) jSONArrayActuacionDispositivo.getJSONObject(i));
                            }
                        } else {
                            jSONObject.put("error", (Object) ("Error de token dispositivo no registrado en SGO: " + jsonObjectValores.getString("uuid")));
                        }
                    }

                    jSONObject.put("actuadores", (Object) jSONArrayDevicesActuadores);

                } else {
                    jSONObject.put("error", (Object) ("Error de JSON dispositivo no registrado en SGO: " + sb.toString()));
                }
            } else {
                jSONObject.put("error", (Object) "Error de token de IAM MAGIS S.A.S. SHA2");
            }
        } catch (IOException | JSONException var28) {
            Logger.getLogger(SendDataIOTDevice.class.getName()).log(Level.SEVERE, (String) null, var28);
        }

        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
