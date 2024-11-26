/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.administracion.asignacionmodulosempresas;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.SubmoduloMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.Submodulo;
import com.iammagis.sga.mongo.beans.Usuario;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class SaveSubmodulosEmpresa extends org.apache.struts.action.Action {

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
            int id = Integer.parseInt(request.getParameter("id"));
            boolean state = Boolean.parseBoolean(request.getParameter("state"));
            int idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));

//            System.out.println("id: " + id + " state:" + state + " idUsuario: " + idUsuario);
            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            DBObject dBObject = empresaMongoController.findEmpresa(idEmpresa);
            Gson gson = new Gson();
            Empresa empresa = gson.fromJson(JSON.serialize(dBObject), Empresa.class);

            SubmoduloMongoController submoduloMongoController = new SubmoduloMongoController();
            ArrayList<Submodulo> Submodulos = empresa.getSubmodulos();
            if (Submodulos == null) {
                Submodulos = new ArrayList<>();
            }

            Submodulo submodulo = submoduloMongoController.findSubmodulo(id);

            if (state) {
                System.out.println("agregando");
                //agergarlo
                Submodulos.add(submodulo);

            } else {

                for (int cont = 0; cont < Submodulos.size(); cont++) {
                    if (Submodulos.get(cont).getIdSubmodulo() == submodulo.getIdSubmodulo()) {
                        //eliminarlo
                        System.out.println("eliminando");
                        Submodulos.remove(cont);
                    }
                }
            }
            empresa.setSubmodulos(Submodulos);
            empresaMongoController.editEmpresa(empresa);

        } else {
            MessageResources messages = MessageResources.getMessageResources("com.iammagis.appropiate.ApplicationResource");
            String message = messages.getMessage(request.getLocale(), "erros.timepoSesion");
            jSONObject.put("error", message);
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
