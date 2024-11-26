/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.operacion.pedidos.eliminados;


import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.PedidoMongoController;
import com.iammagis.sga.mongo.ProductoMongoController;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.CreateLogActividades;
import com.iammagis.sga.support.GetDynamicTable;
import com.mongodb.DBObject;
import java.util.List;
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
public class LoadPedidosEliminados extends org.apache.struts.action.Action {

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
            String content = "/pages/operacion/pedido_eliminados.jsp";
            request.setAttribute("contenido", content);
            //cargamos empresas clientes
            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            List<DBObject> dBObjects = empresaMongoController.getEmpresasByTipo(2);
            request.setAttribute("empresas", dBObjects);
            
            ProductoMongoController productoMongoController = new ProductoMongoController();
            List<DBObject> dBObjectsProductos = productoMongoController.getproductosFromEmpresa(usuario);
            request.setAttribute("productos", dBObjectsProductos);
            
            //cargamos los pedisos pendientes
            PedidoMongoController pedidoMongoController = new PedidoMongoController();
            List<DBObject> pedidos = pedidoMongoController.getPedidosElimnados(usuario);
            request.setAttribute("pedidos", GetDynamicTable.getpedidosElminados(pedidos,usuario));
            
            CreateLogActividades.createLogActividades(usuario, "Ingresando a eliminar pedidos.", request);
        } else {
            errores.add("register", new ActionMessage("erros.timepoSesion"));
            saveErrors(request, errores);
            return (new ActionForward(mapping.getInput()));
        }
        return mapping.findForward(SUCCESS);
    }
}
