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
import com.iammagis.sga.support.GetDynamicTable;
import com.iammagis.sga.support.PropertiesAcces;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class EditUsuario extends org.apache.struts.action.Action {

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
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            Usuario usuarioRegister = (Usuario) form;
            //validar el email
            UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
            Usuario usuarioAnterior = usuarioMongoController.findUsuario(usuarioRegister.getIdUsuario());
            usuarioRegister.setSubmodulos(usuarioAnterior.getSubmodulos());
            usuarioMongoController.editUsuario(usuarioRegister);
            //envio de correo
            PropertiesAcces propertiesAcces = new PropertiesAcces();
            ArrayList<String> emails = new ArrayList<>();
            emails.add(usuarioRegister.getEmail());

            Gson gson = new Gson();
            if (usuario.getIdTipoUsuario() == 1) {
                String template = Correo.getTemplate(Correo.inscripcionTemplate, getServlet().getServletContext().getRealPath(""));
                template = template.replace("$nombre", usuarioRegister.getNombre() + " " + usuarioRegister.getApellidos());
                template = template.replace("$usuario", usuarioRegister.getEmail());
                template = template.replace("$password", usuarioRegister.getPass());
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$banner", "img/template/banner.png");
                Correo correo = new Correo("Actualización S.G.O.", template, emails);
                correo.start();
            } else {
                //aqui lo enviamos con el nombre de la empresa y el logo
                EmpresaMongoController empresaMongoController = new EmpresaMongoController();
                DBObject dBObject = empresaMongoController.findEmpresa(usuario.getIdEmpresa());
                Empresa empresa = gson.fromJson(JSON.serialize(dBObject), Empresa.class);

                String template = Correo.getTemplate(Correo.inscripcionTemplate, getServlet().getServletContext().getRealPath(""));
                template = template.replace("$nombre", usuarioRegister.getNombre() + " " + usuarioRegister.getApellidos());
                template = template.replace("$usuario", usuarioRegister.getEmail());
                template = template.replace("$password", usuarioRegister.getPass());
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                template = template.replace("$server", propertiesAcces.resourcesServer);
                if (empresa.getLogo() != null) {
                    template = template.replace("$banner", empresa.getLogo());
                } else {
                    template = template.replace("$banner", "");
                }
                Correo correo = new Correo("Actualización " + empresa.getNombre(), template, emails);
                correo.start();

            }

            jSONObject.put("usuarios", GetDynamicTable.getUsersTable(usuario));

        } else {
            MessageResources messages = MessageResources.getMessageResources("com.iammagis.resources.ApplicationResource");
            String message = messages.getMessage(request.getLocale(), "erros.timepoSesion");
            jSONObject.put("error", message);
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
