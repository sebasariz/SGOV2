/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.administracion.usuario;


import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.IOTDeviceMongoController; 
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.IOTDevice;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.CreateLogActividades;
import com.iammagis.sga.support.GetDynamicMenu;
import com.iammagis.sga.support.GetDynamicTable;
import com.mongodb.DBObject;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author sebastianarizmendy
 */
public class LoadInicio extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
        ActionErrors errores = new ActionErrors();
        if (usuario == null) {
            errores.add("register", new ActionMessage("erros.noPasswordNoUser"));
            saveErrors(request, errores);
            return mapping.findForward("fail");
        } else {
            usuario = usuarioMongoController.findUsuario(usuario.getIdUsuario());
            session.setAttribute("usuario", usuario);
            //setting menu
            String menu = GetDynamicMenu.getMenu(usuario);
            session.setAttribute("menu", menu);
            String content = "";

           
 
            //cargamos empresas clientes
            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            List<DBObject> dBObjects = empresaMongoController.getEmpresasByTipo(2);
            request.setAttribute("empresas", dBObjects); 
            //1 root
            //2 Administrador
            //3 vendedor
            //4 despachador
            //5 usuario 
            switch (usuario.getIdTipoUsuario()) {
                    case 1:
                        content = "/contenedor/root.jsp";
                        request.setAttribute("empresas_sgo", GetDynamicTable.getEmpresasSGORoot());
                        request.setAttribute("empresas_sgo_tamaño", empresaMongoController.getEmpresasByTipo(1).size());
                        request.setAttribute("empresas_clientes_tamaño", empresaMongoController.getEmpresasByTipo(2).size());
                        request.setAttribute("usuarios_registrados", usuarioMongoController.getUsuarios().size());
                        break;
                    case 2:
                        IOTDeviceMongoController iOTDeviceMongoController = new IOTDeviceMongoController();
                        List listIot = iOTDeviceMongoController.getIOTDevicesInstitucionAndTipo(usuario.getIdEmpresa(), IOTDevice.tipoDefine[0]);
                        request.setAttribute("devices", listIot);
                        List onOffIOT = iOTDeviceMongoController.getIOTDevicesInstitucionAndTipo(usuario.getIdEmpresa(), IOTDevice.tipoDefine[1]);
                        List dimmerIOT = iOTDeviceMongoController.getIOTDevicesInstitucionAndTipo(usuario.getIdEmpresa(), IOTDevice.tipoDefine[2]);
                        onOffIOT.addAll(dimmerIOT);
                        request.setAttribute("actuadores", onOffIOT);
                        content = "/contenedor/administrador.jsp";
                        break;
                    case 3:
                        content = "/contenedor/usuario.jsp";
                }
            request.setAttribute("contenido", content);

            CreateLogActividades.createLogActividades(usuario, "Ingresando tablero de control.", request);
            return mapping.findForward(SUCCESS);
        }
    }
}
