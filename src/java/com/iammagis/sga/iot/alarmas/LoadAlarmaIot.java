package com.iammagis.sga.iot.alarmas;

import com.iammagis.sga.mongo.IOTDeviceAlarmaMongoController;
import com.iammagis.sga.mongo.beans.IOTDeviceAlarma;
import com.iammagis.sga.mongo.beans.Usuario;
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

public class LoadAlarmaIot extends Action {
   private static final String SUCCESS = "success";

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      HttpSession session = request.getSession();
      Usuario usuario = (Usuario)session.getAttribute("usuario");
      JSONObject jSONObject = new JSONObject();
      if (usuario != null) {
         IOTDeviceAlarmaMongoController iOTDeviceAlarmaMongoController = new IOTDeviceAlarmaMongoController();
         int id = Integer.parseInt(request.getParameter("id"));
         IOTDeviceAlarma iOTDeviceAlarma = iOTDeviceAlarmaMongoController.findIOTDeviceAlarma(id);
         jSONObject = new JSONObject(iOTDeviceAlarma);
      } else {
         MessageResources messages = MessageResources.getMessageResources("com.iammagis.resources.ApplicationResource");
         String message = messages.getMessage(request.getLocale(), "erros.timepoSesion");
         jSONObject.put("error", (Object)message);
      }

      response.setContentType("application/json");
      PrintWriter printWriter = response.getWriter();
      printWriter.print(jSONObject);
      return null;
   }
}
