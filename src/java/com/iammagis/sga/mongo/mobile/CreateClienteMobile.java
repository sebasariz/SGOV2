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
public class CreateClienteMobile extends org.apache.struts.action.Action {

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

            Empresa empresa = new Empresa();
            empresa.setNit(request.getParameter("nit"));
            empresa.setNombre(request.getParameter("nombre"));
            empresa.setCodigo("Cod");
            empresa.setDireccion(request.getParameter("direccion"));
            empresa.setCiudad(request.getParameter("ciudad"));
            empresa.setTelefono(request.getParameter("telefono"));
            empresa.setContacto(request.getParameter("contacto"));

            empresa.setEstado(2);
            empresa.setFechaUltimoPago(System.currentTimeMillis());
            empresa.setFechaUltimoPedido(System.currentTimeMillis());
            empresa.setIdEmpresaCreadora(usuario.getIdEmpresa());

            empresa.setTipoEmpresa(2);
            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            empresaMongoController.createEmpresa(empresa);

            jSONObject.put("estado", "ok");
 
            CreateLogActividades.createLogActividades(usuario, "Creando cliente desde movil.", request);
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
