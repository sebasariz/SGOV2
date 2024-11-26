/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.mobile;


import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.CreateLogActividades;
import java.io.PrintWriter;
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
public class LogOutMobile extends org.apache.struts.action.Action {

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
        String token = request.getParameter("token");

        UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
        Usuario usuario = usuarioMongoController.findUsuarioBySessionToken(token);
        if (usuario != null) {
            //entregamos el session token
            usuario.setSessionToken(null);
            usuarioMongoController.editUsuario(usuario);
            jSONObject.put("estado", "ok");
             
            CreateLogActividades.createLogActividades(usuario, "Cerrando sesion movil.", request);
        } else {
            //usuario no existe
            MessageResources messages = MessageResources.getMessageResources("com.iammagis.declara.resources");
            String message = messages.getMessage(request.getLocale(), "erros.session");
            jSONObject.put("error", message);
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
