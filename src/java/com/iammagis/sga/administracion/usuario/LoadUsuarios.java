/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.administracion.usuario;


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
public class LoadUsuarios extends org.apache.struts.action.Action {

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
            request.setAttribute("usuarios", GetDynamicTable.getUsersTable(usuario));
            String content = "/pages/administracion/usuarios.jsp";
            request.setAttribute("contenido", content);
            request.setAttribute("empresas", GetDynamicTable.getEmpresasDBOList(usuario));
            request.setAttribute("empresas_cliente", GetDynamicTable.getEmpresasClienteDBOList(usuario));

            //1 root
            //2 Administrador
            //3 vendedor
            //4 despachador
            //5 usuario 
            ArrayList<TipoUsuario> tipoUsuarios = new ArrayList<>(); 
            switch (usuario.getIdTipoUsuario()) {
                case 1:  
                    //cargamos los tipos de usuario que puede exponer  
                    tipoUsuarios.add(new TipoUsuario(1, "Root"));
                    tipoUsuarios.add(new TipoUsuario(2, "Administrador"));
                    tipoUsuarios.add(new TipoUsuario(3, "Vendedor"));
                    tipoUsuarios.add(new TipoUsuario(4, "Despachador"));
                    tipoUsuarios.add(new TipoUsuario(6, "Repartidor"));
                    break;
                case 2: 
                    //setiamos lso tipso de usuario
                    tipoUsuarios.add(new TipoUsuario(2, "Administrador"));
                    tipoUsuarios.add(new TipoUsuario(3, "Vendedor"));
                    tipoUsuarios.add(new TipoUsuario(4, "Despachador"));
                    tipoUsuarios.add(new TipoUsuario(6, "Repartidor"));
                    tipoUsuarios.add(new TipoUsuario(5, "Usuario"));
                    break;
                case 3:
                default:
                    break;
            }
            request.setAttribute("tipos", tipoUsuarios);
             
            CreateLogActividades.createLogActividades(usuario, "Ingreso a usuarios.", request);
        } else {
            errores.add("register", new ActionMessage("erros.timepoSesion"));
            saveErrors(request, errores);
            return (new ActionForward(mapping.getInput()));
        }
        return mapping.findForward(SUCCESS);
    }
}
