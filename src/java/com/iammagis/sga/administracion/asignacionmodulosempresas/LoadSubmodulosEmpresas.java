/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.administracion.asignacionmodulosempresas;


import com.iammagis.sga.mongo.beans.TipoUsuario;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.CreateLogActividades;
import com.iammagis.sga.support.GetDynamicTable;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author sebastianarizmendy
 */
public class LoadSubmodulosEmpresas extends org.apache.struts.action.Action {

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
        if (usuario != null) {
            request.setAttribute("empresas", GetDynamicTable.getEmpresasSubmodulos(usuario));
            String content = "/pages/administracion/submodulosempresas.jsp";
            request.setAttribute("contenido", content);
            //1 root
            //2 Administrador
            //3 vendedor
            //4 despachador
            //5 usuario 
            ArrayList<TipoUsuario> tipoUsuarios = new ArrayList<>();
            switch (usuario.getIdTipoUsuario()) {
                case 1:
                    //Root 
                    break;
                case 2:
                    //Administrador  
                    break;
                case 3:
                default:
                    break;
            }
            request.setAttribute("tipos", tipoUsuarios); 
            CreateLogActividades.createLogActividades(usuario, "Ingresando submodulos a empresas.", request);
        } else {
            errores.add("register", new ActionMessage("erros.timepoSesion"));
            saveErrors(request, errores);
            return (new ActionForward(mapping.getInput()));
        }
        return mapping.findForward(SUCCESS);
    }
}
