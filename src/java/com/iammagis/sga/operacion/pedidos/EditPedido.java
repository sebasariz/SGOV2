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
public class EditPedido extends org.apache.struts.action.Action {

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
            Pedido pedido = (Pedido) form;
            PedidoMongoController pedidoMongoController = new PedidoMongoController();
            Pedido pedidoAnteior = pedidoMongoController.findPedido(pedido.getIdPedido());

            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            Gson gson = new Gson();
            DBObject dBObject = empresaMongoController.findEmpresa(pedidoAnteior.getEmpresaCliente().getIdEmpresa());
            Empresa empresaCliente = gson.fromJson(JSON.serialize(dBObject), Empresa.class);
            ProductoMongoController productoMongoController = new ProductoMongoController();
            double valorTotalAnterior = 0;

            //devolvemos todos los productos que se tenian en el pedido anterior al inventario
            JSONArray jSONArrayAnterior = new JSONArray(pedidoAnteior.getArrayProductosSolicitadosString());
            for (int i = 0; i < jSONArrayAnterior.length(); i++) {
                JSONObject jSONObjectProducto = jSONArrayAnterior.getJSONObject(i);
                Producto producto = productoMongoController.findProducto(jSONObjectProducto.getInt("itemid"));
                valorTotalAnterior += jSONObjectProducto.getDouble("valorVenta");
                int cantidad = jSONObjectProducto.getInt("cantidad");
                producto.setCantidad(producto.getCantidad() + cantidad);
                productoMongoController.editProducto(producto);
            }
            empresaCliente.setValorPendientePago(empresaCliente.getValorPendientePago() + valorTotalAnterior);
            //verificacion de productos
            //generamos el nuevo pedido y sacamos de inventario
            double valorTotal = 0;
            JSONArray jSONArray = new JSONArray(pedido.getArrayProductosSolicitadosString());
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObjectProducto = jSONArray.getJSONObject(i);
                Producto producto = productoMongoController.findProducto(jSONObjectProducto.getInt("itemid"));
                valorTotal += jSONObjectProducto.getDouble("valorVenta");
                int cantidad = jSONObjectProducto.getInt("cantidad"); 
                producto.setCantidad(producto.getCantidad() - cantidad);
                productoMongoController.editProducto(producto);

            }
            empresaCliente.setFechaUltimoPedido(System.currentTimeMillis());
            empresaCliente.setValorPendientePago(empresaCliente.getValorPendientePago() - valorTotal);
            empresaMongoController.editEmpresa(empresaCliente);
            pedidoAnteior.setEmpresaCliente(empresaCliente);
            pedidoAnteior.setNumeroGuia(pedido.getNumeroGuia());
            pedidoAnteior.setTransportadora(pedido.getTransportadora());
            pedidoAnteior.setNotaGuia(pedido.getNotaGuia());

            pedidoAnteior.setNota(pedido.getNota());
            pedidoAnteior.setArrayProductosSolicitadosString(pedido.getArrayProductosSolicitadosString());
            if (pedidoAnteior.getEstado() == 3) {
                pedidoAnteior.setFechaDespacho(System.currentTimeMillis());
            }
            pedidoMongoController.editPedido(pedidoAnteior);

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
