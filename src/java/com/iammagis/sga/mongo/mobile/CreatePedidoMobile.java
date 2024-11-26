/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.mobile;


import com.google.gson.Gson; 
import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.PedidoMongoController;
import com.iammagis.sga.mongo.ProductoMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.Pedido;
import com.iammagis.sga.mongo.beans.Producto;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.CreateLogActividades;
import com.mongodb.util.JSON;
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
public class CreatePedidoMobile extends org.apache.struts.action.Action {

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
            Gson gson = new Gson();
            int idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));
            String jsonArray = request.getParameter("jsonArray");
            String nota = request.getParameter("nota");

            double lng = 0;
            double lat = 0;
            if (request.getParameter("lng") != null && request.getParameter("lat") != null) {
                lng = Double.parseDouble(request.getParameter("lng"));
                lat = Double.parseDouble(request.getParameter("lat"));
            }

            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            Empresa empresaCliente = gson.fromJson(JSON.serialize(empresaMongoController.findEmpresa(idEmpresa)), Empresa.class);
            Empresa empresaCreadora = gson.fromJson(JSON.serialize(empresaMongoController.findEmpresa(usuario.getIdEmpresa())), Empresa.class);

            empresaCliente.setFechaUltimoPedido(System.currentTimeMillis());
            PedidoMongoController pedidoMongoController = new PedidoMongoController();
            Pedido pedido = new Pedido();
            pedido.setLat(lat);
            pedido.setLng(lng);
            pedido.setIdUsuarioCreador(usuario.getIdUsuario());
            pedido.setUsuarioCreador(usuario);
            pedido.setEmpresaCliente(empresaCliente);
            pedido.setEmmpresaCreadora(empresaCreadora);
            pedido.setEstado(1);
            pedido.setNota(nota);
            pedido.setFechaCreacion(System.currentTimeMillis());
            pedido.setIdEmpresaCreadora(usuario.getIdEmpresa());
            empresaCreadora.setIncremental(empresaCreadora.getIncremental() + 1);
            empresaMongoController.editEmpresa(empresaCreadora);
            pedido.setIncremental(empresaCreadora.getIncremental());
            //verificacion de productos
            pedido.setArrayProductosSolicitadosString(jsonArray);
            JSONArray jSONArray = new JSONArray(pedido.getArrayProductosSolicitadosString());

            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObjectProducto = jSONArray.getJSONObject(i);
                ProductoMongoController productoMongoController = new ProductoMongoController();
                Producto producto = productoMongoController.findProducto(jSONObjectProducto.getInt("itemid"));
                if (producto != null) {
                    int cantidad = jSONObjectProducto.getInt("cantidad");;
                    producto.setCantidad(producto.getCantidad() - cantidad);
                    productoMongoController.editProducto(producto);
                } else {
                    jSONObject.put("error", 400);
                    jSONObject.put("msg", "El producto de referencia: " + jSONObjectProducto.getInt("itemid") + " no existe");
                    
                }
            }
            pedido.setArrayProductosSolicitadosString(jSONArray.toString());
            empresaCliente.setFechaUltimoPedido(System.currentTimeMillis());
            empresaMongoController.editEmpresa(empresaCliente);

            pedido = pedidoMongoController.createPedido(pedido);
           
 
            CreateLogActividades.createLogActividades(usuario, "Creando pedido desde movil.", request);
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
