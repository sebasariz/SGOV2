/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.mobile;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.PaqueteMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Paquete;
import com.iammagis.sga.mongo.beans.Usuario;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class SaveComentario extends org.apache.struts.action.Action {

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
            String anotacion = request.getParameter("anotacion");
            int idPaquete = Integer.parseInt(request.getParameter("idPaquete"));
            Gson gson = new Gson();
            PaqueteMongoController paqueteMongoController = new PaqueteMongoController();
            DBObject dBObject = paqueteMongoController.findPaqueteRuteo(idPaquete);
            Paquete paquete = gson.fromJson(JSON.serialize(dBObject), Paquete.class);

            JSONArray jSONArray = paquete.getjSONArray_anotaciones();
            if (jSONArray == null) {
                jSONArray = new JSONArray();
            }

            JSONObject jSONObjectAnotacion = new JSONObject();
            jSONObjectAnotacion.put("nombre", usuario.getNombre() + " " + usuario.getApellidos());
            jSONObjectAnotacion.put("fecha", System.currentTimeMillis());
            jSONObjectAnotacion.put("anotacion", anotacion);

            
            jSONArray.put(jSONObjectAnotacion);
            paquete.setjSONArray_anotaciones(jSONArray);
            paqueteMongoController.editPaqueteRuteo(paquete);
            
            
            dBObject = paqueteMongoController.findPaqueteRuteo(idPaquete);
            paquete = gson.fromJson(JSON.serialize(dBObject), Paquete.class);
            jSONObject.put("estado", "ok");
            jSONObject.put("json", paquete.getjSONArray_anotaciones());
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
