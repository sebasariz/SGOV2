package com.iammagis.sga.administracion.usuario;

import com.google.gson.Gson;
import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.IOTDeviceMongoController;
import com.iammagis.sga.mongo.PedidoMongoController;
import com.iammagis.sga.mongo.ProductoMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.IOTDevice;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.CreateLogActividades;
import com.iammagis.sga.support.GetDynamicMenu;
import com.iammagis.sga.support.GetDynamicTable;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class LoginUsuario extends Action {

    private static final String SUCCESS = "success";

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Usuario usuario = (Usuario) form;
        System.out.println("usuario: " + usuario.getEmail());
        HttpSession session = request.getSession();
        UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
        usuario = usuarioMongoController.loginUsuario(usuario);
        ActionErrors errores = new ActionErrors();
        if (usuario == null) {
            errores.add("register", new ActionMessage("erros.noPasswordNoUser"));
            this.saveErrors(request, errores);
            return mapping.findForward("fail");
        } else {
            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            DBObject dBObjectEmpresa = empresaMongoController.findEmpresa(usuario.getIdEmpresa());
            Gson gson = new Gson();
            Empresa empresa = (Empresa) gson.fromJson(JSON.serialize(dBObjectEmpresa), Empresa.class);
            if (empresa != null && empresa.getActiva() == 0) {
                errores.add("register", new ActionMessage("erros.noEmpresaActiva"));
                this.saveErrors(request, errores);
                return mapping.findForward("fail");
            } else {
                this.settingEmpresaAttibutes(empresa, session);
                session.setAttribute("empresa", empresa);
                session.setAttribute("usuario", usuario);
                String menu = GetDynamicMenu.getMenu(usuario);
                session.setAttribute("menu", menu);
                String content = "";
                PedidoMongoController pedidoMongoController = new PedidoMongoController();
                List<DBObject> pedidos = pedidoMongoController.getPedidosPendientes(usuario);
                request.setAttribute("pedidos", GetDynamicTable.getpedidos(pedidos, usuario));
                ProductoMongoController productoMongoController = new ProductoMongoController();
                List<DBObject> dBObjectsProductos = productoMongoController.getproductosFromEmpresa(usuario);
                request.setAttribute("productos", dBObjectsProductos);
                switch (usuario.getIdTipoUsuario()) {
                    case 1:
                        content = "/contenedor/root.jsp";
                        request.setAttribute("empresas_sgo", GetDynamicTable.getEmpresasSGORoot());
                        request.setAttribute("empresas_sgo_tamaño", empresaMongoController.getEmpresasByTipo(1).size());
                        request.setAttribute("empresas_clientes_tamaño", empresaMongoController.getEmpresasByTipo(2).size());
                        request.setAttribute("usuarios_registrados", usuarioMongoController.getUsuarios().size());
                        break;
                    case 2:
                        IOTDeviceMongoController iOTDeviceMongoController = new IOTDeviceMongoController();
                        List listIot = iOTDeviceMongoController.getIOTDevicesInstitucionAndTipo(usuario.getIdEmpresa(), IOTDevice.tipoDefine[0]);
                        request.setAttribute("devices", listIot);
                        List onOffIOT = iOTDeviceMongoController.getIOTDevicesInstitucionAndTipo(usuario.getIdEmpresa(), IOTDevice.tipoDefine[1]);
                        List dimmerIOT = iOTDeviceMongoController.getIOTDevicesInstitucionAndTipo(usuario.getIdEmpresa(), IOTDevice.tipoDefine[2]);
                        onOffIOT.addAll(dimmerIOT);
                        request.setAttribute("actuadores", onOffIOT);
                        content = "/contenedor/administrador.jsp";
                        break;
                    case 3:
                        content = "/contenedor/usuario.jsp";
                }

                CreateLogActividades.createLogActividades(usuario, "Ingresando SGO.", request);
                request.setAttribute("contenido", content);
                return mapping.findForward("success");
            }
        }
    }

    private void settingEmpresaAttibutes(Empresa empresa, HttpSession session) {
        if (empresa != null) {
            if (empresa.getLogo() != null) {
                session.setAttribute("logo", empresa.getLogo());
            } else {
                session.setAttribute("logo", "img/logo.png");
            }

            if (empresa.getPuntero() != null) {
                session.setAttribute("puntero", empresa.getPuntero());
            } else {
                session.setAttribute("puntero", "img/puntero.png");
            }

            if (empresa.getColorPrimario() != null) {
                session.setAttribute("primary", empresa.getColorPrimario());
            } else {
                session.setAttribute("primary", "#b7cd24");
            }

            if (empresa.getColorSecundario() != null) {
                session.setAttribute("secundario", empresa.getColorSecundario());
            } else {
                session.setAttribute("secundario", "#68b4e3");
            }

        }
    }
}
