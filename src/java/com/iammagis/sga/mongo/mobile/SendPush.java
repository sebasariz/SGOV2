/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.mobile;

import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.SendPushMessage;
import java.io.BufferedReader;
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
public class SendPush extends org.apache.struts.action.Action {

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

            BufferedReader reader = null;
            //obtenemos el parametro que esta actualizando
            StringBuilder sb = new StringBuilder();
            reader = request.getReader();
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } finally {
                reader.close();
            }
            
            if (sb.toString() != null && !sb.toString().equals("")) {
                JSONObject jsonObjectDevice = new JSONObject(sb.toString());
//                System.out.println("agregando: " + jsonObjectDevice);
                JSONArray jSONArrayAndroid = new JSONArray();
                if (usuario.getjSONArrayAndroid() != null) {
                    jSONArrayAndroid = new JSONArray(usuario.getjSONArrayAndroid());
                }
                boolean contiene = false;
                for (int i = 0; i < jSONArrayAndroid.length(); i++) {
                    String pushIn = jSONArrayAndroid.getString(i);
                    if (pushIn.equals(jsonObjectDevice)) {
                        contiene = true;
                    }
                }
                if (!contiene) {
                    jSONArrayAndroid.put(jsonObjectDevice);
                }
                usuario.setjSONArrayAndroid(jSONArrayAndroid.toString());
                usuarioMongoController.editUsuario(usuario);

            }
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
