/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.administracion.usuario;

import com.google.gson.Gson;
import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.Correo;
import com.iammagis.sga.support.PropertiesAcces;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author sebastianarizmendy
 */
public class Recuperar extends org.apache.struts.action.Action {

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
        ActionErrors errores = new ActionErrors();

        Usuario usuarioRegister = (Usuario) form;
        //validar el email
        UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
        Usuario usuarioExist = usuarioMongoController.emailExist(usuarioRegister);
        Gson gson = new Gson();
        PropertiesAcces propertiesAcces = new PropertiesAcces();
        if (usuarioExist != null) {
//envio de correo 
            ArrayList<String> emails = new ArrayList<>();
            emails.add(usuarioExist.getEmail());
            //envio de correo 
            if (usuarioExist.getIdTipoUsuario() == 1) {
                String template = Correo.getTemplate(Correo.recordarUsuarioTemplate, getServlet().getServletContext().getRealPath(""));
                template = template.replace("$nombre", usuarioExist.getNombre() + " " + usuarioExist.getApellidos());
                template = template.replace("$usuario", usuarioExist.getEmail());
                template = template.replace("$password", usuarioExist.getPass());
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                Correo correo = new Correo("Actualización S.G.O.", template, emails);
                correo.start();
            } else {
                //aqui lo enviamos con el nombre de la empresa y el logo
                EmpresaMongoController empresaMongoController = new EmpresaMongoController();
                DBObject dBObject = empresaMongoController.findEmpresa(usuarioExist.getIdEmpresa());
                Empresa empresa = gson.fromJson(JSON.serialize(dBObject), Empresa.class);

                String template = Correo.getTemplate(Correo.recordarUsuarioTemplate, getServlet().getServletContext().getRealPath(""));
                template = template.replace("$nombre", usuarioExist.getNombre() + " " + usuarioExist.getApellidos());
                template = template.replace("$usuario", usuarioExist.getEmail());
                template = template.replace("$password", usuarioExist.getPass());
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                Correo correo = new Correo("Actualización a " + empresa.getNombre(), template, emails);
                correo.start();

            }

            errores.add("complete", new ActionMessage("sga.datos.enviados"));
            saveErrors(request, errores);
        } else {
            errores.add("register", new ActionMessage("erros.noPasswordNoUser"));
            saveErrors(request, errores);
        }

        return mapping.findForward(SUCCESS);
    }
}
