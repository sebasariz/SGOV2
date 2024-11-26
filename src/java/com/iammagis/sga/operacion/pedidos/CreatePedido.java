/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.operacion.pedidos;

import com.google.gson.Gson;
import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.PedidoMongoController;
import com.iammagis.sga.mongo.ProductoMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.Pedido;
import com.iammagis.sga.mongo.beans.Producto;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.GetDynamicTable;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class CreatePedido extends org.apache.struts.action.Action {

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
            Gson gson = new Gson();
            Pedido pedido = (Pedido) form;
            PedidoMongoController pedidoMongoController = new PedidoMongoController();
            pedido.setFechaCreacion(System.currentTimeMillis());
            pedido.setIdUsuarioCreador(usuario.getIdUsuario());
            pedido.setUsuarioCreador(usuario);
            pedido.setEstado(1);
            pedido.setNotaGuia("");
            pedido.setNumeroGuia("");
            pedido.setIdEmpresaCreadora(usuario.getIdEmpresa());
            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            Empresa empresaCreadora = gson.fromJson(JSON.serialize(empresaMongoController.findEmpresa(usuario.getIdEmpresa())), Empresa.class);
            empresaCreadora.setFechaUltimoPedido(System.currentTimeMillis());
            pedido.setEmmpresaCreadora(empresaCreadora);
            empresaCreadora.setIncremental(empresaCreadora.getIncremental() + 1);
            empresaMongoController.editEmpresa(empresaCreadora);
            pedido.setIncremental(empresaCreadora.getIncremental());
            Empresa empresaCliente = gson.fromJson(JSON.serialize(empresaMongoController.findEmpresa(pedido.getIdEmpresaCliente())), Empresa.class);
            pedido.setEmpresaCliente(empresaCliente);
            //verificacion de productos
            double valorTotal = 0;
            JSONArray jSONArray = new JSONArray(pedido.getArrayProductosSolicitadosString());
            //verificacion de existencia
//            for (int i = 0; i < jSONArray.length(); i++) {
//                JSONObject jSONObjectProducto = jSONArray.getJSONObject(i);
//                ProductoMongoController productoMongoController = new ProductoMongoController();
//                Producto producto = productoMongoController.findProducto(Integer.parseInt(jSONObjectProducto.get("itemid").toString()));
//                valorTotal += jSONObjectProducto.getDouble("valorVenta");
//                int cantidad = jSONObjectProducto.getInt("cantidad");
//                if (producto.getCantidad() < cantidad) {
//                    //no se tiene la candidad necesaria 
//                    String message = "La cantidad de:" + producto.getNombre() + " requerida no se enceuntra disponible, ha solicitado: "
//                            + cantidad + ", y la disponibilidad es de: " + producto.getCantidad();
//                    jSONObject.put("error", message);
//                    response.setContentType("application/json");
//                    PrintWriter printWriter = response.getWriter();
//                    printWriter.print(jSONObject);
//                    return null;
//                }
//            }
            //descuento por creacion de pedido
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObjectProducto = jSONArray.getJSONObject(i);
                ProductoMongoController productoMongoController = new ProductoMongoController();
                Producto producto = productoMongoController.findProducto(Integer.parseInt(jSONObjectProducto.get("itemid").toString()));
                valorTotal += jSONObjectProducto.getDouble("valorVenta");
                int cantidad = jSONObjectProducto.getInt("cantidad");
                producto.setCantidad(producto.getCantidad() - cantidad);
                productoMongoController.editProducto(producto);
            }
            empresaCliente.setFechaUltimoPedido(System.currentTimeMillis());
            empresaCliente.setValorPendientePago(empresaCliente.getValorPendientePago() - valorTotal);
            empresaMongoController.editEmpresa(empresaCliente);

            pedido = pedidoMongoController.createPedido(pedido);

            List<DBObject> pedidos = pedidoMongoController.getPedidosPendientes(usuario);
            jSONObject.put("pedidos", GetDynamicTable.getpedidos(pedidos, usuario));
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
