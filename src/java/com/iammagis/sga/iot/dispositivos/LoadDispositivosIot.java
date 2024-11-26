package com.iammagis.sga.iot.dispositivos;

import com.google.gson.Gson;
import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.IOTDevice;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.CreateLogActividades;
import com.iammagis.sga.support.GetDynamicTable;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
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

public class LoadDispositivosIot extends Action {
   private static final String SUCCESS = "success";

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      HttpSession httpSession = request.getSession();
      Usuario usuario = (Usuario)httpSession.getAttribute("usuario");
      ActionErrors errores = new ActionErrors();
      if (usuario != null) {
         EmpresaMongoController empresaMongoController = new EmpresaMongoController();
         List empresas = new ArrayList();
         if (usuario.getIdTipoUsuario() == 1) {
            empresas = empresaMongoController.getEmpresasByTipo(1);
         } else {
            DBObject dBObject = empresaMongoController.findEmpresa(usuario.getIdEmpresa());
            Gson gson = new Gson();
            Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(dBObject), Empresa.class);
            ((List)empresas).add(empresa);
         }

         request.setAttribute("empresas", empresas);
         List tipos = Arrays.asList(IOTDevice.tipoDefine);
         request.setAttribute("tipos", tipos);
         request.setAttribute("devices", GetDynamicTable.getIodDevices(usuario));
         String content = "/pages/iot/iotdevices.jsp";
         request.setAttribute("contenido", content);
         CreateLogActividades.createLogActividades(usuario, "Ingresando dispositivos IOT.", request);
         return mapping.findForward("success");
      } else {
         errores.add("register", new ActionMessage("erros.timepoSesion"));
         this.saveErrors(request, errores);
         return new ActionForward(mapping.getInput());
      }
   }
}
