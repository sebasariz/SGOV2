/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.mobile;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.Correo;
import com.iammagis.sga.support.CreateLogActividades;
import com.iammagis.sga.support.PropertiesAcces;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class RememberMobile extends org.apache.struts.action.Action {

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

        JSONObject jSONObject = new JSONObject();
        String email = request.getParameter("email");
        UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        Usuario usuarioRegister = usuarioMongoController.emailExist(usuario);
        if (usuarioRegister != null) {
            PropertiesAcces propertiesAcces = new PropertiesAcces();
            Gson gson = new Gson();
            //envio de correo 
            ArrayList<String> emails = new ArrayList<>();
            emails.add(usuarioRegister.getEmail());
            //envio de correo  
            if (usuarioRegister.getIdTipoUsuario() == 1) {
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
                template = template.replace("$banner", empresa.getLogo());
                Correo correo = new Correo("Actualización a " + empresa.getNombre(), template, emails);
                correo.start();

            }
            jSONObject.put("estado", "ok");
             
            CreateLogActividades.createLogActividades(usuario, "Recuperando clave movil.", request);
        } else {
            //usuario no existe
            MessageResources messages = MessageResources.getMessageResources("com.iammagis.declara.resources");
            String message = messages.getMessage(request.getLocale(), "erros.session");
            jSONObject.put("error", message);
        }

        jSONObject.put("estado", "ok");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
