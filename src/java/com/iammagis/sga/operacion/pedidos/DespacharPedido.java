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
public class DespacharPedido extends org.apache.struts.action.Action {

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

            double valorTotal = 0;
            boolean completo = true;
            JSONArray arrayPedidosFaltante = new JSONArray();
            JSONArray arrayPedidos = new JSONArray(pedido.getArrayProductosSolicitadosString());
            ProductoMongoController productoMongoController = new ProductoMongoController();
            for (int i = 0; i < arrayPedidos.length(); i++) {
                JSONObject jSONObjectProducto = arrayPedidos.getJSONObject(i);
                valorTotal += jSONObjectProducto.getDouble("valorVenta") / jSONObjectProducto.getInt("cantidad") * jSONObjectProducto.getInt("empacados");
                if (jSONObjectProducto.getInt("cantidad") > jSONObjectProducto.getInt("empacados")) {
                    completo = false;
                    jSONObjectProducto.put("cantidad", jSONObjectProducto.getInt("cantidad") - jSONObjectProducto.getInt("empacados"));
                    jSONObjectProducto.put("empacados", 0);
                    arrayPedidosFaltante.put(jSONObjectProducto);
                }

                //aqui descontamos mtodo lo que se despacho
                Producto producto = productoMongoController.findProducto(jSONObjectProducto.getInt("itemid"));
                if (producto != null) {
                    System.out.println("producto: " + producto.getNombre());
                    System.out.println("empacados: " + jSONObjectProducto.getInt("empacados"));
//                    producto.setCantidad(producto.getCantidad() - jSONObjectProducto.getInt("empacados"));
//                    System.out.println("cantidad: " + producto.getCantidad());
//                    productoMongoController.editProducto(producto);
                } else {
                    System.out.println("el producto no existe");
                    jSONObject.put("error", "El producto no existe en el inventario con el itemid: " + jSONObjectProducto.getInt("itemid"));
                }
            }
            pedidoAnteior.setNumeroGuia(pedido.getNumeroGuia());
            pedidoAnteior.setTransportadora(pedido.getTransportadora());
            pedidoAnteior.setNotaGuia(pedido.getNotaGuia());
            pedidoAnteior.setNota(pedido.getNota());
            pedidoAnteior.setArrayProductosSolicitadosString(pedido.getArrayProductosSolicitadosString());

            if (completo) {
                pedidoAnteior.setEstado(4);
            } else {
                pedidoAnteior.setEstado(6);
                //aqui creamos el empacado parcial
                Pedido pedidoFaltante = new Pedido();
                pedidoFaltante.setFechaCreacion(System.currentTimeMillis());
                pedidoFaltante.setEmpresaCliente(pedidoAnteior.getEmpresaCliente());
                pedidoFaltante.setEstado(1);
                pedidoFaltante.setIdEmpresaCliente(pedidoAnteior.getIdEmpresaCliente());
                pedidoFaltante.setIdEmpresaCreadora(pedidoAnteior.getIdEmpresaCreadora());
                pedidoFaltante.setIdUsuarioCreador(pedidoAnteior.getIdUsuarioCreador());

                Gson gson = new Gson();
                EmpresaMongoController empresaMongoController = new EmpresaMongoController();
                Empresa empresaCreadora = gson.fromJson(JSON.serialize(empresaMongoController.findEmpresa(usuario.getIdEmpresa())), Empresa.class);
                pedido.setEmmpresaCreadora(empresaCreadora);
                empresaCreadora.setIncremental(empresaCreadora.getIncremental() + 1);
                empresaMongoController.editEmpresa(empresaCreadora);
                pedidoFaltante.setIncremental(empresaCreadora.getIncremental());
                
                
                        
                pedidoFaltante.setLat(pedidoAnteior.getLat());
                pedidoFaltante.setLng(pedidoAnteior.getLng());
                pedidoFaltante.setNota(pedidoAnteior.getNota());
                pedidoFaltante.setNotaGuia(pedidoAnteior.getNotaGuia());
                pedidoFaltante.setArrayProductosSolicitadosString(arrayPedidosFaltante.toString());
                pedidoMongoController.createPedido(pedidoFaltante);
                //agregmamos los productos faltantes
                jSONObject.put("error", "Se genero un pedido nuevo con los articulos faltantes.");
            }
            //aqui hago el calculo para sumarle la deuda pendiente al cliente
            Gson gson = new Gson();
            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            System.out.println("pedidoAnteior.getEmpresaCliente().getIdEmpresa(): " + pedidoAnteior.getEmpresaCliente().getIdEmpresa());
            DBObject dBObjectEmrpesaClietne = empresaMongoController.findEmpresa(pedidoAnteior.getEmpresaCliente().getIdEmpresa());
            Empresa empresaCliente = gson.fromJson(JSON.serialize(dBObjectEmrpesaClietne), Empresa.class);
            if (empresaCliente != null) { 
                empresaCliente.setFechaUltimoPedido(System.currentTimeMillis());
                empresaCliente.setValorPendientePago(empresaCliente.getValorPendientePago() - valorTotal);
                empresaMongoController.editEmpresa(empresaCliente);
                pedidoMongoController.editPedido(pedidoAnteior);
            }

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
