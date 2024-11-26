package com.iammagis.sga.iot.alarmas;

import com.iammagis.sga.mongo.IOTDeviceAlarmaMongoController;
import com.iammagis.sga.mongo.beans.IOTDeviceAlarma;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.GetDynamicTable;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONObject;

public class EditAlarmaIot extends Action {
   private static final String SUCCESS = "success";

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      HttpSession session = request.getSession();
      Usuario usuario = (Usuario)session.getAttribute("usuario");
      JSONObject jSONObject = new JSONObject();
      if (usuario != null) {
         IOTDeviceAlarma deviceIOTDeviceAlarma = (IOTDeviceAlarma)form;
         IOTDeviceAlarmaMongoController iOTDeviceAlarmaMongoController = new IOTDeviceAlarmaMongoController();
         IOTDeviceAlarma iOTDeviceAlarmaAnterior = iOTDeviceAlarmaMongoController.findIOTDeviceAlarma(deviceIOTDeviceAlarma.getIdIOTDeviceAlarma());
         deviceIOTDeviceAlarma.setNumeroActivaciones(iOTDeviceAlarmaAnterior.getNumeroActivaciones());
         deviceIOTDeviceAlarma.setFechaUltimoDisparo(iOTDeviceAlarmaAnterior.getFechaUltimoDisparo());
         deviceIOTDeviceAlarma.setjSONArrayHisotiralActivaciones(iOTDeviceAlarmaAnterior.getjSONArrayHisotiralActivaciones());
         iOTDeviceAlarmaMongoController.editIOTDeviceAlarma(deviceIOTDeviceAlarma);
         jSONObject.put("iotDevicesAlarmas", (Object)GetDynamicTable.getIodDevicesAlarmas(usuario));
      } else {
         MessageResources messages = MessageResources.getMessageResources("com.iammagis.sga.resources.ApplicationResource");
         String message = messages.getMessage(request.getLocale(), "erros.timepoSesion");
         jSONObject.put("error", (Object)message);
      }

      response.setContentType("application/json");
      PrintWriter printWriter = response.getWriter();
      printWriter.print(jSONObject);
      return null;
   }
}
