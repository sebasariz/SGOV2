/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.reporte.estadocuenta;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.PagoMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.Pago;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.GetDynamicTable;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.PrintWriter;
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
public class EliminarPago extends org.apache.struts.action.Action {

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
            PagoMongoController pagoMongoController = new PagoMongoController();
            int id = Integer.parseInt(request.getParameter("id"));
            Pago pago = pagoMongoController.findPago(id);
            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            DBObject dBObject = empresaMongoController.findEmpresa(pago.getIdEmpresa());
            Gson gson = new Gson();
            Empresa empresa = gson.fromJson(JSON.serialize(dBObject), Empresa.class);
            empresa.setValorPendientePago(empresa.getValorPendientePago() - pago.getValor());
            empresaMongoController.editEmpresa(empresa);
            pagoMongoController.removePago(id);
            
            
             jSONObject.put("empresas", GetDynamicTable.getEmpresasEstadoPagoTable(usuario));

        } else {
            MessageResources messages = MessageResources.getMessageResources("com.iammagis.resources.ApplicationResource");
            String message = messages.getMessage(request.getLocale(), "erros.timepoSesion");
            jSONObject.put("error", message);
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}