/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.reporte.indicadores;

import com.iammagis.sga.mongo.PedidoMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Usuario;
import com.mongodb.DBObject;
import java.io.PrintWriter;
import java.util.List;
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
public class ResultadoIndicadores extends org.apache.struts.action.Action {

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
        HttpSession httpSession = request.getSession();
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        ActionErrors errores = new ActionErrors();
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            long fechaIncio = Long.parseLong(request.getParameter("fechaInicio"));
            long fechaFin = Long.parseLong(request.getParameter("fechaFin"));

            //cargamos los indicadores de las fechas
            PedidoMongoController pedidoMongoController = new PedidoMongoController();
            List<DBObject> dBObjects = pedidoMongoController.getPedidosFromEmpresaAndFecha(usuario, fechaIncio, fechaFin);
            jSONObject.put("pedidos", dBObjects);

            UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
            List<DBObject> dBObjectsUsuarios = usuarioMongoController.getUsuariosInstitucion(usuario.getIdEmpresa());
            jSONObject.put("usuarios", dBObjectsUsuarios);
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