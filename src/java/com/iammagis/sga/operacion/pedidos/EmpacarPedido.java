/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.operacion.pedidos;


import com.iammagis.sga.mongo.PedidoMongoController;
import com.iammagis.sga.mongo.beans.Pedido;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.GetDynamicTable;
import com.mongodb.DBObject;
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
public class EmpacarPedido extends org.apache.struts.action.Action {

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
            pedidoAnteior.setEstado(pedido.getEstado());
            JSONArray arrayPedidosAnterior = new JSONArray(pedidoAnteior.getArrayProductosSolicitadosString());
            JSONArray arrayPedidos = new JSONArray(pedido.getArrayProductosSolicitadosString());
            for (int i = 0; i < arrayPedidos.length(); i++) {
                JSONObject jSONObjectProducto = arrayPedidos.getJSONObject(i);
                for (int w = 0; w < arrayPedidosAnterior.length(); w++) {
                    JSONObject jSONObjectProductoAnterior = arrayPedidosAnterior.getJSONObject(w);
                    if (jSONObjectProducto.has("empacados")
                            && jSONObjectProducto.getInt("itemid") == jSONObjectProductoAnterior.getInt("itemid")) {
                        jSONObjectProductoAnterior.put("empacados", jSONObjectProducto.getInt("empacados"));
                        arrayPedidosAnterior.put(w, jSONObjectProductoAnterior);
                    }
                }
            }
            pedidoAnteior.setArrayProductosSolicitadosString(arrayPedidosAnterior.toString());

            pedidoAnteior.setNumeroGuia(pedido.getNumeroGuia());
            pedidoAnteior.setTransportadora(pedido.getTransportadora());
            pedidoAnteior.setNotaGuia(pedido.getNotaGuia());

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
