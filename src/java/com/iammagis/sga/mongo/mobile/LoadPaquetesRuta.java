/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.mobile;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.RutaRepartoMongoController;
import com.iammagis.sga.mongo.PaqueteMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.RutaReparto;
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
public class LoadPaquetesRuta extends org.apache.struts.action.Action {

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
            Gson gson = new Gson();
            int idRuta = Integer.parseInt(request.getParameter("idRuta"));
            RutaRepartoMongoController rutaServiciosMongoController = new RutaRepartoMongoController();
            DBObject dBObject = rutaServiciosMongoController.findRutaServicio(idRuta);
            RutaReparto rutaServicio = gson.fromJson(JSON.serialize(dBObject), RutaReparto.class);

            PaqueteMongoController paqueteMongoController = new PaqueteMongoController();
            JSONArray jSONArrayPaquetes = new JSONArray(rutaServicio.getPaquetes());
            JSONArray jSONArrayPaquetesSend = new JSONArray();
            for (int i = 0; i < jSONArrayPaquetes.length(); i++) {
                int idPaquete = jSONArrayPaquetes.getInt(i);
                DBObject dBObjectPaquete = paqueteMongoController.findPaqueteRuteo(idPaquete);
                jSONArrayPaquetesSend.put(dBObjectPaquete);
            }
            jSONObject.put("paquetes", jSONArrayPaquetesSend);
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
