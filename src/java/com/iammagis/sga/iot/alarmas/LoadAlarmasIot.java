package com.iammagis.sga.iot.alarmas;

import com.iammagis.sga.mongo.IOTDeviceMongoController;
import com.iammagis.sga.mongo.beans.IOTDeviceAlarma;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.CreateLogActividades;
import com.iammagis.sga.support.GetDynamicTable;
import java.util.ArrayList;
import java.util.Arrays;
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

public class LoadAlarmasIot extends Action {
   private static final String SUCCESS = "success";

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      HttpSession httpSession = request.getSession();
      Usuario usuario = (Usuario)httpSession.getAttribute("usuario");
      ActionErrors errores = new ActionErrors();
      if (usuario != null) {
         IOTDeviceMongoController iOTDeviceMongoController = new IOTDeviceMongoController();
         new ArrayList();
         List listaIotDispositivos;
         if (usuario.getIdTipoUsuario() == 1) {
            listaIotDispositivos = iOTDeviceMongoController.getIOTDevices();
         } else {
            listaIotDispositivos = iOTDeviceMongoController.getIOTDevicesInstitucion(usuario.getIdEmpresa());
         }

         request.setAttribute("iotDevicesAlarmas", GetDynamicTable.getIodDevicesAlarmas(usuario));
         request.setAttribute("iotDevices", listaIotDispositivos);
         request.setAttribute("tipos", Arrays.asList(IOTDeviceAlarma.tipoAlarma));
         String content = "/pages/iot/iotdevices_alarmas.jsp";
         request.setAttribute("contenido", content);
         CreateLogActividades.createLogActividades(usuario, "Ingresando a alarmas IOT.", request);
         return mapping.findForward("success");
      } else {
         errores.add("register", new ActionMessage("erros.timepoSesion"));
         this.saveErrors(request, errores);
         return new ActionForward(mapping.getInput());
      }
   }
}
