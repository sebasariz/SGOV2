/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.mobile;


import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.CreateLogActividades;
import com.mongodb.DBObject;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class LoginMobile extends org.apache.struts.action.Action {

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
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        
        UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
        Usuario usuario = new Usuario();
        usuario.setEmail(user);
        usuario.setPass(pass);
        JSONObject jSONObject = new JSONObject();
        usuario = usuarioMongoController.loginUsuario(usuario);
        if (usuario != null) {
            //login 
            String token = UUID.randomUUID().toString();
            usuario.setSessionToken(token);
            jSONObject.put("nombre", usuario.getNombre());
            jSONObject.put("apellidos", usuario.getApellidos());
            jSONObject.put("token", token);
            jSONObject.put("email", usuario.getEmail());
            jSONObject.put("idTipo", usuario.getIdTipoUsuario());
            
            if(usuario.getIdEmpresa()!=0){
                EmpresaMongoController empresaMongoController = new EmpresaMongoController();
                DBObject empresa= empresaMongoController.findEmpresa(usuario.getIdEmpresa());
                jSONObject.put("empresa", empresa);
            }
            System.out.println("jSONObject: " + jSONObject);
            usuarioMongoController.editUsuario(usuario);
            
            CreateLogActividades.createLogActividades(usuario, "Ingresando a movil.", request);
        } else {
            //no login
            jSONObject.put("error", "Error de usuario o clave");
        }
//        System.out.println("user: " + user + " pass:" + pass);

        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
