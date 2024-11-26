/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.operacion.historialvendedor;


import com.iammagis.sga.mongo.PedidoMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.GetDynamicTable;
import com.mongodb.DBObject;
import java.io.PrintWriter;
import java.util.List;
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
public class LoadHistorialVendedorJson extends org.apache.struts.action.Action {

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
            long fechaIncio = Long.parseLong(request.getParameter("fechaInicio"));
            long fechaFin = Long.parseLong(request.getParameter("fechaFin"));
            int idVendedor = Integer.parseInt(request.getParameter("idVendedor"));
            UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
            Usuario usuarioVendedor = usuarioMongoController.findUsuario(idVendedor);
            PedidoMongoController pedidoMongoController = new PedidoMongoController();
            List<DBObject> dBObjects = pedidoMongoController.getHistorialVendedor(usuarioVendedor, fechaIncio, fechaFin);
            System.out.println("dBObjects: " + dBObjects);
            jSONObject.put("historial", GetDynamicTable.getpedidosHistorialVendedor(dBObjects, usuario));
            //lo mandamos derecho para no realizar el loop en el server 

        } else {
            MessageResources messages = MessageResources.getMessageResources("com.iammagis.sga.resources.ApplicationResource");
            String message = messages.getMessage(request.getLocale(), "erros.timepoSesion");
            jSONObject.put("error", message);
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
