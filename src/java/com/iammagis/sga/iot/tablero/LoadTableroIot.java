package com.iammagis.sga.iot.tablero;

import com.iammagis.sga.mongo.IOTDeviceMongoController;
import com.iammagis.sga.mongo.beans.IOTDevice;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.CreateLogActividades;
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

public class LoadTableroIot extends Action {
   private static final String SUCCESS = "success";

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      HttpSession httpSession = request.getSession();
      Usuario usuario = (Usuario)httpSession.getAttribute("usuario");
      ActionErrors errores = new ActionErrors();
      if (usuario != null) {
         IOTDeviceMongoController iOTDeviceMongoController = new IOTDeviceMongoController();
         List listIotDevices;
         List listIotActuadores;
         if (usuario.getIdTipoUsuario() == 1) {
            listIotDevices = iOTDeviceMongoController.getIOTDevicesByTipo(IOTDevice.tipoDefine[0]);
            listIotActuadores = iOTDeviceMongoController.getIOTDevicesByTipo(IOTDevice.tipoDefine[1]);
         } else {
            listIotDevices = iOTDeviceMongoController.getIOTDevicesInstitucionAndTipo(usuario.getIdEmpresa(),IOTDevice.tipoDefine[0]);
            listIotActuadores = iOTDeviceMongoController.getIOTDevicesInstitucionAndTipo(usuario.getIdEmpresa(),IOTDevice.tipoDefine[1]);
         }
          System.out.println("listIotActuadores: "+listIotActuadores.size());
         request.setAttribute("devices", listIotDevices);
         request.setAttribute("actuadores", listIotActuadores);
         
         
         
         String content = "/pages/iot/iotdevices_tablerocontrol.jsp";
         request.setAttribute("contenido", content);
         CreateLogActividades.createLogActividades(usuario, "Ingresando a tablero de control.", request);
         return mapping.findForward("success");
      } else {
         errores.add("register", new ActionMessage("erros.timepoSesion"));
         this.saveErrors(request, errores);
         return new ActionForward(mapping.getInput());
      }
   }
}
