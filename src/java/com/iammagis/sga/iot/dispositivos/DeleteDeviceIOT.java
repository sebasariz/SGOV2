package com.iammagis.sga.iot.dispositivos;

import com.iammagis.sga.mongo.IOTDeviceMongoController;
import com.iammagis.sga.mongo.beans.IOTDevice;
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

public class DeleteDeviceIOT extends Action {
   private static final String SUCCESS = "success";

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      HttpSession session = request.getSession();
      Usuario usuario = (Usuario)session.getAttribute("usuario");
      JSONObject jSONObject = new JSONObject();
      if (usuario != null) {
         IOTDeviceMongoController iOTDeviceMongoController = new IOTDeviceMongoController();
         int id = Integer.parseInt(request.getParameter("id"));
         IOTDevice iOTDevice = new IOTDevice();
         iOTDevice.setIdIOTDevice(id);
         iOTDeviceMongoController.removeIOTDevice(iOTDevice);
         jSONObject.put("dispositivos", (Object)GetDynamicTable.getIodDevices(usuario));
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
