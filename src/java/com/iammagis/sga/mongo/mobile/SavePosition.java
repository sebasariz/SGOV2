/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.mobile;


import com.iammagis.sga.mongo.PosicionMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Posicion;
import com.iammagis.sga.mongo.beans.Usuario;
import java.io.PrintWriter;
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
public class SavePosition extends org.apache.struts.action.Action {

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
        JSONObject jSONObject = new JSONObject();
        
        UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
        String token = request.getParameter("token");
        
        Usuario usuario = usuarioMongoController.UserBySession(token);
        if (usuario != null) {
            double lat = Double.parseDouble(request.getParameter("lat"));
            double lng = Double.parseDouble(request.getParameter("lng"));
            PosicionMongoController posicionMongoController = new PosicionMongoController();
            Posicion posicion = new Posicion();
            posicion.setLat(lat);
            posicion.setLng(lng);
            posicion.setIdEmpresa(usuario.getIdEmpresa());
            posicion.setIdUsuario(usuario.getIdUsuario());
            posicion.setFecha(System.currentTimeMillis());
            posicionMongoController.createPosicion(posicion);
            
            usuario.setLat(lat);
            usuario.setLng(lng);
            usuario.setFecha(System.currentTimeMillis());
//            System.out.println("user update: "+ usuario.getIdUsuario());
            usuarioMongoController.editUsuario(usuario);
            
        } else {
            jSONObject.put("error", 400);
            jSONObject.put("msg", "Error token");
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
