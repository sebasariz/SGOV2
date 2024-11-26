package com.iammagis.sga.iot.reportes;

import com.iammagis.sga.mongo.IOTDeviceAlarmaMongoController;
import com.iammagis.sga.mongo.IOTDeviceRegisterMongoController;
import com.iammagis.sga.mongo.beans.Usuario;
import com.mongodb.DBObject;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.json.JSONObject;

public class BuscarIotReporte extends Action {
   private static final String SUCCESS = "success";

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      HttpSession httpSession = request.getSession();
      Usuario usuario = (Usuario)httpSession.getAttribute("usuario");
      ActionErrors errores = new ActionErrors();
      JSONObject jSONObject = new JSONObject();
      if (usuario != null) {
         long fechaIncio = Long.parseLong(request.getParameter("fechaInicio"));
         long fechaFin = Long.parseLong(request.getParameter("fechaFin"));
         int idIOTDevice = Integer.parseInt(request.getParameter("idIOTDevice"));
         IOTDeviceAlarmaMongoController iOTDeviceAlarmaMongoController = new IOTDeviceAlarmaMongoController();
         List<DBObject> iOTDeviceAlarmas = iOTDeviceAlarmaMongoController.getIotDeviceAlarmsIotDevice(idIOTDevice);
         jSONObject.put("iOTDeviceAlarmas", (Collection)iOTDeviceAlarmas);
         IOTDeviceRegisterMongoController iOTDeviceRegisterMongoController = new IOTDeviceRegisterMongoController();
         System.out.println("idIOTDevice: " + idIOTDevice);
         System.out.println("fechaIncio: " + fechaIncio);
         System.out.println("fechaFin: " + fechaFin);
         List<DBObject> dBObjectsRegister = iOTDeviceRegisterMongoController.getRegistrosAndFecha(idIOTDevice, fechaIncio, fechaFin);
         System.out.println("dBObjectsRegister: " + dBObjectsRegister.size());
         jSONObject.put("dBObjectsRegister", (Collection)dBObjectsRegister);
         response.setContentType("application/json");
         PrintWriter var18 = response.getWriter();
         var18.print(jSONObject);
         return null;
      } else {
         errores.add("register", new ActionMessage("erros.timepoSesion"));
         this.saveErrors(request, errores);
         return new ActionForward(mapping.getInput());
      }
   }
}
