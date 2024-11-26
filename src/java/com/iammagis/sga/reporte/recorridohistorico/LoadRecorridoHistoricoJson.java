/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.reporte.recorridohistorico;

import com.iammagis.sga.mongo.PosicionMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.PropertiesAcces;
import com.mongodb.DBObject;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class LoadRecorridoHistoricoJson extends org.apache.struts.action.Action {

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
        ActionErrors errores = new ActionErrors();
        JSONObject jSONObject = new JSONObject();

        if (usuario != null) {
            long fechaIncio = Long.parseLong(request.getParameter("fechaInicio"));
            long fechaFin = Long.parseLong(request.getParameter("fechaFin"));
            double idVendedor = Double.parseDouble(request.getParameter("idVendedor"));

            Empresa empresa = (Empresa) session.getAttribute("empresa");
            if (empresa != null) {
                PropertiesAcces propertiesAcces = new PropertiesAcces();
                jSONObject.put("icon", propertiesAcces.resourcesServer + empresa.getPuntero());
            } else {
                jSONObject.put("icon", "img/puntero.png");
            }
            PosicionMongoController posicionMongoController = new PosicionMongoController();
            ArrayList<DBObject> posiciones = new ArrayList<>(posicionMongoController.buscarPosiciones(fechaIncio, fechaFin, (int)idVendedor));
            jSONObject.put("posiciones", posiciones);
        } else {
            errores.add("register", new ActionMessage("erros.timepoSesion"));
            saveErrors(request, errores);
            return (new ActionForward(mapping.getInput()));
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
