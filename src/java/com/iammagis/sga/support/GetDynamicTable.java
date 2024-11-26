package com.iammagis.sga.support;

import com.google.gson.Gson;
import com.iammagis.sga.mongo.ArchivoDireccionesMongoController;
import com.iammagis.sga.mongo.DeudorMongoController;
import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.IOTDeviceAlarmaMongoController;
import com.iammagis.sga.mongo.IOTDeviceMongoController;
import com.iammagis.sga.mongo.LogActividadesMongoController;
import com.iammagis.sga.mongo.ModuloMongoController;
import com.iammagis.sga.mongo.PaqueteMongoController;
import com.iammagis.sga.mongo.PedidoMongoController;
import com.iammagis.sga.mongo.PosicionMongoController;
import com.iammagis.sga.mongo.RutaRepartoMongoController;
import com.iammagis.sga.mongo.ServicioRiesgosInternoMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.ArchivoDirecciones;
import com.iammagis.sga.mongo.beans.Deudor;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.Geocerca;
import com.iammagis.sga.mongo.beans.IOTDevice;
import com.iammagis.sga.mongo.beans.IOTDeviceAlarma;
import com.iammagis.sga.mongo.beans.LogActividades;
import com.iammagis.sga.mongo.beans.Modulo;
import com.iammagis.sga.mongo.beans.Pago;
import com.iammagis.sga.mongo.beans.Paquete;
import com.iammagis.sga.mongo.beans.Pedido;
import com.iammagis.sga.mongo.beans.Producto;
import com.iammagis.sga.mongo.beans.RutaReparto;
import com.iammagis.sga.mongo.beans.ServicioRiesgoInterno;
import com.iammagis.sga.mongo.beans.Submodulo;
import com.iammagis.sga.mongo.beans.Usuario;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetDynamicTable {
   static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
   static SimpleDateFormat simpleDateFormatwithTime = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
   static SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy");
   static DecimalFormat decimalFormat = new DecimalFormat("###,##0");
   static DecimalFormat decimalFormatwithpoint = new DecimalFormat("###,##0.00");
   static Gson gson = new Gson();
   static UsuarioMongoController usuarioMongoController = new UsuarioMongoController();
   static PosicionMongoController posicionMongoController = new PosicionMongoController();
   static PedidoMongoController pedidoMongoController = new PedidoMongoController();
   static EmpresaMongoController empresaMongoController = new EmpresaMongoController();
   static DeudorMongoController deudorMongoController = new DeudorMongoController();
   static ModuloMongoController moduloMongoController = new ModuloMongoController();
   static IOTDeviceMongoController iOTDeviceMongoController = new IOTDeviceMongoController();
   static IOTDeviceAlarmaMongoController iOTDeviceAlarmaMongoController = new IOTDeviceAlarmaMongoController();
   static LogActividadesMongoController logActividadesMongoController = new LogActividadesMongoController();
   static ServicioRiesgosInternoMongoController servicioRiesgosInternoMongoController = new ServicioRiesgosInternoMongoController();
   static PaqueteMongoController paqueteMongoController = new PaqueteMongoController();
   static RutaRepartoMongoController rutaServiciosMongoController = new RutaRepartoMongoController();
   static ArchivoDireccionesMongoController archivoDireccionesMongoController = new ArchivoDireccionesMongoController();

   public static JSONArray getUsersTable(Usuario usuario) throws JSONException, UnsupportedEncodingException, IOException {
      ArrayList<DBObject> usuarios = new ArrayList();
      switch(usuario.getIdTipoUsuario()) {
      case 1:
         usuarios = new ArrayList(usuarioMongoController.getUsuarios());
         break;
      case 2:
         usuarios = new ArrayList(usuarioMongoController.getUsuariosInstitucion(usuario.getIdEmpresa()));
      case 3:
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = usuarios.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         Usuario empresa = (Usuario)gson.fromJson(JSON.serialize(dBObject), Usuario.class);
         data.put((Object)empresa.getDocumento());
         data.put((Object)empresa.getNombre());
         data.put((Object)empresa.getApellidos());
         data.put((Object)empresa.getEmail());
         String tipo = "";
         switch(empresa.getIdTipoUsuario()) {
         case 1:
            tipo = "<td>Root</td>";
            break;
         case 2:
            tipo = "<td>Administrador</td>";
            break;
         case 3:
            tipo = "<td>Vendedor</td>";
            break;
         case 4:
            tipo = "<td>Despachador</td>";
            break;
         case 5:
            tipo = "<td>Usuario</td>";
         }

         data.put((Object)tipo);
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar empresa\" onclick=\"LoadUsuarioJson(" + empresa.getIdUsuario() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar empresa\" onclick=\"EliminarUsuario(" + empresa.getIdUsuario() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getUsersPosiciones(Usuario usuario) throws JSONException, UnsupportedEncodingException, IOException {
      ArrayList<DBObject> usuarios = new ArrayList();
      System.out.println("load table 2");
      switch(usuario.getIdTipoUsuario()) {
      case 1:
         System.out.println("root");
         usuarios = new ArrayList(usuarioMongoController.getUsuarios());
         break;
      case 2:
         System.out.println("adminstrador");
         usuarios = new ArrayList(usuarioMongoController.getUsuariosInstitucion(usuario.getIdEmpresa()));
      case 3:
      }

      JSONArray datosCompletos = new JSONArray();
      System.out.println("usuarios: " + usuarios.size());
      Iterator var3 = usuarios.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         Usuario usuario1 = (Usuario)gson.fromJson(JSON.serialize(dBObject), Usuario.class);
         if (usuario1.getLat() != 0.0D && usuario1.getLat() != 0.0D && usuario1.getFecha() > System.currentTimeMillis() - 216000000L) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", usuario1.getIdUsuario());
            jSONObject.put("lat", usuario1.getLat());
            jSONObject.put("lng", usuario1.getLng());
            jSONObject.put("fecha", (Object)simpleDateFormatwithTime.format(new Date(usuario1.getFecha())));
            jSONObject.put("nombre", (Object)usuario1.getNombre());
            jSONObject.put("apellidos", (Object)usuario1.getApellidos());
            datosCompletos.put((Object)jSONObject);
         }
      }

      System.out.println("datosCompletos: " + datosCompletos);
      return datosCompletos;
   }

   public static ArrayList<DBObject> getUsersPosicionesMapaCalorVendedores(Usuario usuario, long fechaIncio, long fechaFin) throws IOException, JSONException {
      ArrayList<DBObject> posiciones = new ArrayList(posicionMongoController.getposicionsFromEmpresaAndFecha(usuario, fechaIncio, fechaFin));
      return posiciones;
   }

   public static ArrayList<DBObject> getUsersPosicionesMapaCalorPedidos(Usuario usuario, long fechaInicial, long fechaFinal) {
      ArrayList<DBObject> pedidos = new ArrayList(pedidoMongoController.getPedidosFromEmpresaAndFecha(usuario, fechaInicial, fechaFinal));
      return pedidos;
   }

   public static JSONArray getUsersTableSubmodulos(Usuario usuario) throws JSONException, UnsupportedEncodingException, IOException {
      ArrayList<DBObject> usuarios = new ArrayList();
      switch(usuario.getIdTipoUsuario()) {
      case 1:
         usuarios = new ArrayList(usuarioMongoController.getUsuarios());
         break;
      case 2:
         usuarios = new ArrayList(usuarioMongoController.getUsuariosInstitucion(usuario.getIdEmpresa()));
      case 3:
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = usuarios.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         Usuario empresa = (Usuario)gson.fromJson(JSON.serialize(dBObject), Usuario.class);
         data.put((Object)empresa.getDocumento());
         data.put((Object)empresa.getNombre());
         data.put((Object)empresa.getApellidos());
         data.put((Object)empresa.getEmail());
         String tipo = "";
         switch(empresa.getIdTipoUsuario()) {
         case 1:
            tipo = "<td>Root</td>";
            break;
         case 2:
            tipo = "<td>Administrador</td>";
            break;
         case 3:
            tipo = "<td>Vendedor</td>";
            break;
         case 4:
            tipo = "<td>Despachador</td>";
            break;
         case 5:
            tipo = "<td>Usuario</td>";
         }

         data.put((Object)tipo);
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Submodulos empresa\" onclick=\"LoadSubmodulosJson(" + empresa.getIdUsuario() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getEmpresasSubmodulos(Usuario usuario) throws IOException {
      List<DBObject> empresas = new ArrayList();
      if (usuario.getIdTipoUsuario() == 1) {
         empresas = empresaMongoController.getEmpresasByTipo(1);
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = ((List)empresas).iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(dBObject), Empresa.class);
         data.put((Object)empresa.getNit());
         data.put((Object)empresa.getNombre());
         data.put((Object)empresa.getDireccion());
         data.put((Object)empresa.getContacto());
         data.put((Object)empresa.getTelefono());
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Submodulos empresa\" onclick=\"LoadSubmodulosJson(" + empresa.getIdEmpresa() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getEmpresasTable(Usuario usuario) throws IOException {
      List empresas;
      if (usuario.getIdTipoUsuario() != 1) {
         empresas = empresaMongoController.getEmpresasByEpresaCreadora(usuario.getIdEmpresa());
      } else {
         empresas = empresaMongoController.getEmpresasByTipo(1);
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = empresas.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(dBObject), Empresa.class);
         data.put((Object)empresa.getNit());
         data.put((Object)empresa.getNombre());
         data.put((Object)empresa.getDireccion());
         data.put((Object)empresa.getContacto());
         data.put((Object)empresa.getTelefono());
         String tipo = "";
         switch(empresa.getTipoEmpresa()) {
         case 1:
            tipo = "<td>Empresa SGA</td>";
            break;
         case 2:
            tipo = "<td>Empresa cliente</td>";
         }

         data.put((Object)tipo);
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar empresa\" onclick=\"LoadEmpresaJson(" + empresa.getIdEmpresa() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Aprobar empresa\" onclick=\"EliminarEmpresa(" + empresa.getIdEmpresa() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getEmpresasTablePendientes(Usuario usuario) throws IOException {
      List empresas;
      if (usuario.getIdTipoUsuario() != 1) {
         empresas = empresaMongoController.getEmpresasByEpresaCreadoraPendientes(usuario.getIdEmpresa());
      } else {
         empresas = empresaMongoController.getEmpresasPendientes();
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = empresas.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(dBObject), Empresa.class);
         data.put((Object)empresa.getNit());
         data.put((Object)empresa.getNombre());
         data.put((Object)empresa.getDireccion());
         data.put((Object)empresa.getContacto());
         data.put((Object)empresa.getTelefono());
         String tipo = "";
         switch(empresa.getTipoEmpresa()) {
         case 1:
            tipo = "<td>Empresa SGA</td>";
            break;
         case 2:
            tipo = "<td>Empresa cliente</td>";
         }

         data.put((Object)tipo);
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-check-circle fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Aprobar empresa\" onclick=\"LoadAprobarEmpresa(" + empresa.getIdEmpresa() + ")\"></i><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar empresa\" onclick=\"LoadEmpresaJson(" + empresa.getIdEmpresa() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar empresa\" onclick=\"EliminarEmpresa(" + empresa.getIdEmpresa() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getHistorialClienteTable(Usuario usuario) throws IOException {
      List empresas;
      if (usuario.getIdTipoUsuario() != 1) {
         empresas = empresaMongoController.getEmpresasByEpresaCreadora(usuario.getIdEmpresa());
      } else {
         empresas = empresaMongoController.getEmpresas();
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = empresas.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(dBObject), Empresa.class);
         data.put((Object)empresa.getNit());
         data.put((Object)empresa.getNombre());
         data.put((Object)simpleDateFormatwithTime.format(new Date(empresa.getFechaUltimoPedido())));
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Hisotiral de pedidos\" onclick=\"LoadHistorialJson(" + empresa.getIdEmpresa() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getProductos(List<DBObject> productosFromEmpresa) {
      JSONArray datosCompletos = new JSONArray();
      Iterator var2 = productosFromEmpresa.iterator();

      while(var2.hasNext()) {
         DBObject dBObject = (DBObject)var2.next();
         JSONArray data = new JSONArray();
         Producto producto = (Producto)gson.fromJson(JSON.serialize(dBObject), Producto.class);
         data.put((Object)producto.getNombre());
         data.put((Object)producto.getReferencia());
         data.put(producto.getCantidad());
         data.put((Object)("$" + producto.getValor()));
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar empresa\" onclick=\"LoadProductoJson(" + producto.getIdProducto() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar empresa\" onclick=\"EliminarProducto(" + producto.getIdProducto() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static List<DBObject> getEmpresasDBOList(Usuario usuario) throws IOException {
      List<DBObject> dBObjects = new ArrayList();
      switch(usuario.getIdTipoUsuario()) {
      case 1:
         dBObjects = empresaMongoController.getEmpresasByTipo(1);
         break;
      case 2:
         ((List)dBObjects).add(empresaMongoController.findEmpresa(usuario.getIdEmpresa()));
      case 3:
      case 4:
      case 5:
      }

      return (List)dBObjects;
   }

   public static JSONArray getpedidos(List<DBObject> pedidos, Usuario usuario) throws IOException {
      JSONArray datosCompletos = new JSONArray();

      JSONArray data;
      for(Iterator var3 = pedidos.iterator(); var3.hasNext(); datosCompletos.put((Object)data)) {
         DBObject dBObject = (DBObject)var3.next();
         data = new JSONArray();
         Pedido pedido = (Pedido)gson.fromJson(JSON.serialize(dBObject), Pedido.class);
         Usuario usuarioVendedor = usuarioMongoController.findUsuario(pedido.getIdUsuarioCreador());
         if (pedido.getIncremental() == 0) {
            data.put(pedido.getIdPedido());
         } else {
            data.put(pedido.getIncremental());
         }

         if (pedido.getEmpresaCliente() != null) {
            data.put((Object)pedido.getEmpresaCliente().getNit());
            data.put((Object)("(" + pedido.getEmpresaCliente().getCodigo() + ") " + pedido.getEmpresaCliente().getNombre()));
         } else {
            data.put((Object)"");
            data.put((Object)"N/A");
         }

         data.put((Object)simpleDateFormatwithTime.format(new Date(pedido.getFechaCreacion())));
         if (usuarioVendedor != null) {
            data.put((Object)(usuarioVendedor.getNombre() + " " + usuarioVendedor.getApellidos()));
         } else {
            data.put((Object)"");
         }

         String estado = "";
         switch(pedido.getEstado()) {
         case -1:
            estado = "Eliminado";
         case 0:
         default:
            break;
         case 1:
            estado = "Pendiente";
            break;
         case 2:
            estado = "Empacado parcial";
            break;
         case 3:
            estado = "Completado";
            break;
         case 4:
            estado = "Despachado";
            break;
         case 5:
            estado = "Entregado";
            break;
         case 6:
            estado = "Despacho parcial";
            break;
         case 7:
            estado = "Cotización";
         }

         data.put((Object)estado);
         switch(usuario.getIdTipoUsuario()) {
         case 1:
            data.put((Object)("<td class=\"text-center\"><i class=\"fa fas fa-check-circle fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Entregar pedido\" onclick=\"EntregarPedidoJson(" + pedido.getIdPedido() + ")\"></i><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar pedido\" onclick=\"LoadPedidoJson(" + pedido.getIdPedido() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar empresa\" onclick=\"EliminarPedido(" + pedido.getIdPedido() + ")\"></i></td>"));
            break;
         case 2:
            data.put((Object)("<td class=\"text-center\"><i class=\"fa fas fa-check-circle fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Entregar pedido\" onclick=\"EntregarPedidoJson(" + pedido.getIdPedido() + ")\"></i><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar pedido\" onclick=\"LoadPedidoJson(" + pedido.getIdPedido() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar empresa\" onclick=\"EliminarPedido(" + pedido.getIdPedido() + ")\"></i></td>"));
            break;
         case 3:
            data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar pedido\" onclick=\"LoadPedidoJson(" + pedido.getIdPedido() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar empresa\" onclick=\"EliminarPedido(" + pedido.getIdPedido() + ")\"></i></td>"));
            break;
         case 4:
            data.put((Object)("<td class=\"text-center\"><i class=\"fa fas fa-check-circle fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Entregar pedido\" onclick=\"EntregarPedidoJson(" + pedido.getIdPedido() + ")\"></i></td>"));
            break;
         case 5:
            data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 5px;margin: 2px;border:1px solid;\" title=\"Editar pedido\" onclick=\"LoadPedidoJson(" + pedido.getIdPedido() + ")\"></i></td>"));
         }
      }

      return datosCompletos;
   }

   public static JSONArray getSubmodulosTable(ArrayList<Submodulo> submodulos, ArrayList<Submodulo> submodulosTotales) throws IOException {
      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = submodulosTotales.iterator();

      while(var3.hasNext()) {
         Submodulo submodulo = (Submodulo)var3.next();
         JSONArray data = new JSONArray();
         data.put((Object)submodulo.getNombre());
         Modulo modulo = moduloMongoController.findModulo(submodulo.getIdModulo());
         if (modulo != null) {
            data.put((Object)modulo.getNombre());
         } else {
            data.put((Object)"Null");
         }

         String checked = "";
         if (submodulos != null) {
            Iterator var8 = submodulos.iterator();

            while(var8.hasNext()) {
               Submodulo submoduloUsuario = (Submodulo)var8.next();
               if (submoduloUsuario != null && submodulo != null && submoduloUsuario.getIdSubmodulo() == submodulo.getIdSubmodulo()) {
                  checked = "checked";
               }
            }
         }

         data.put((Object)("<td class=\"text-center\"><input type=\"checkbox\" name=\"chk1\" " + checked + " id=\"chk\"  onclick=\"slect(this," + submodulo.getIdSubmodulo() + ")\"/></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getEmpresasEstadoPagoTable(Usuario usuario) throws IOException {
      List empresas;
      if (usuario.getIdTipoUsuario() != 1) {
         empresas = empresaMongoController.getEmpresasByEpresaCreadoraAndValorCartera(usuario.getIdEmpresa());
      } else {
         empresas = empresaMongoController.getEmpresas();
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = empresas.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(dBObject), Empresa.class);
         if (empresa.getFechaUltimoPedido() != 0L) {
            data.put((Object)empresa.getCodigo());
            data.put((Object)empresa.getNit());
            data.put((Object)empresa.getNombre());
            data.put((Object)empresa.getDireccion());
            data.put((Object)simpleDateFormat.format(new Date(empresa.getFechaUltimoPedido())));
            data.put((Object)simpleDateFormat.format(new Date(empresa.getFechaUltimoPago())));
            data.put((Object)decimalFormat.format(empresa.getValorPendientePago()));
            data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-money fa-2x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Historial pagos empresa\" onclick=\"LoadHisotiralPagos(" + empresa.getIdEmpresa() + ")\"></i><i class=\"fa fa-credit-card fa-2x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Generar pago empresa\" onclick=\"GenerarPago(" + empresa.getIdEmpresa() + ")\"></i></td>"));
            datosCompletos.put((Object)data);
         }
      }

      return datosCompletos;
   }

   public static JSONArray getHisotiralEmpresa(List<DBObject> dBObjects) {
      JSONArray datosCompletos = new JSONArray();
      Iterator var2 = dBObjects.iterator();

      while(var2.hasNext()) {
         DBObject dBObject = (DBObject)var2.next();
         JSONArray data = new JSONArray();
         Pago pago = (Pago)gson.fromJson(JSON.serialize(dBObject), Pago.class);
         data.put((Object)simpleDateFormat.format(new Date(pago.getFecha())));
         data.put((Object)decimalFormat.format(pago.getValor()));
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar pago\" onclick=\"EliminarPago(" + pago.getIdPago() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getpedidosHistorial(List<DBObject> pedidos) throws IOException {
      JSONArray datosCompletos = new JSONArray();
      Iterator var2 = pedidos.iterator();

      while(var2.hasNext()) {
         DBObject dBObject = (DBObject)var2.next();
         JSONArray data = new JSONArray();
         Pedido pedido = (Pedido)gson.fromJson(JSON.serialize(dBObject), Pedido.class);
         Usuario usuarioVendedor = usuarioMongoController.findUsuario(pedido.getIdUsuarioCreador());
         if (pedido.getIncremental() == 0) {
            data.put(pedido.getIdPedido());
         } else {
            data.put(pedido.getIncremental());
         }

         data.put((Object)simpleDateFormatwithTime.format(new Date(pedido.getFechaCreacion())));
         if (usuarioVendedor != null) {
            data.put((Object)(usuarioVendedor.getNombre() + " " + usuarioVendedor.getApellidos()));
         } else {
            data.put((Object)"");
         }

         String estado = "";
         switch(pedido.getEstado()) {
         case -1:
            estado = "Eliminado";
         case 0:
         default:
            break;
         case 1:
            estado = "Pendiente";
            break;
         case 2:
            estado = "Empacado parcial";
            break;
         case 3:
            estado = "Completado";
            break;
         case 4:
            estado = "Despachado";
            break;
         case 5:
            estado = "Entregado";
            break;
         case 6:
            estado = "Despacho parcial";
            break;
         case 7:
            estado = "Cotización";
         }

         data.put((Object)estado);
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fas fa-check-circle fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Entregar pedido\" onclick=\"EntregarPedidoJson(" + pedido.getIdPedido() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getpedidosHistorialVendedor(List<DBObject> pedidos, Usuario usuario) throws IOException {
      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = pedidos.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         Pedido pedido = (Pedido)gson.fromJson(JSON.serialize(dBObject), Pedido.class);
         Usuario usuarioVendedor = usuarioMongoController.findUsuario(pedido.getIdUsuarioCreador());
         if (pedido.getIncremental() == 0) {
            data.put(pedido.getIdPedido());
         } else {
            data.put(pedido.getIncremental());
         }

         data.put((Object)pedido.getEmpresaCliente().getNit());
         data.put((Object)("(" + pedido.getEmpresaCliente().getCodigo() + ") " + pedido.getEmpresaCliente().getNombre()));
         data.put((Object)simpleDateFormatwithTime.format(new Date(pedido.getFechaCreacion())));
         data.put((Object)(usuarioVendedor.getNombre() + " " + usuarioVendedor.getApellidos()));
         String estado = "";
         switch(pedido.getEstado()) {
         case -1:
            estado = "Eliminado";
         case 0:
         default:
            break;
         case 1:
            estado = "Pendiente";
            break;
         case 2:
            estado = "Empacado parcial";
            break;
         case 3:
            estado = "Completado";
            break;
         case 4:
            estado = "Despachado";
            break;
         case 5:
            estado = "Entregado";
            break;
         case 6:
            estado = "Despacho parcial";
            break;
         case 7:
            estado = "Cotización";
         }

         data.put((Object)estado);
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fas fa-check-circle fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Entregar pedido\" onclick=\"EntregarPedidoJson(" + pedido.getIdPedido() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getpedidosElminados(List<DBObject> pedidos, Usuario usuario) throws IOException {
      JSONArray datosCompletos = new JSONArray();

      JSONArray data;
      for(Iterator var3 = pedidos.iterator(); var3.hasNext(); datosCompletos.put((Object)data)) {
         DBObject dBObject = (DBObject)var3.next();
         data = new JSONArray();
         Pedido pedido = (Pedido)gson.fromJson(JSON.serialize(dBObject), Pedido.class);
         Usuario usuarioEliminador = usuarioMongoController.findUsuario(pedido.getIdUsuarioEliminador());
         if (pedido.getIncremental() == 0) {
            data.put(pedido.getIdPedido());
         } else {
            data.put(pedido.getIncremental());
         }

         data.put((Object)pedido.getEmpresaCliente().getNit());
         data.put((Object)("(" + pedido.getEmpresaCliente().getCodigo() + ") " + pedido.getEmpresaCliente().getNombre()));
         data.put((Object)simpleDateFormatwithTime.format(new Date(pedido.getFechaElimnacion())));
         if (usuarioEliminador != null) {
            data.put((Object)(usuarioEliminador.getNombre() + " " + usuarioEliminador.getApellidos()));
         } else {
            data.put((Object)"");
         }

         String estado = "";
         switch(pedido.getEstado()) {
         case -1:
            estado = "Eliminado";
         case 0:
         default:
            break;
         case 1:
            estado = "Pendiente";
            break;
         case 2:
            estado = "Empacado parcial";
            break;
         case 3:
            estado = "Completado";
            break;
         case 4:
            estado = "Despachado";
            break;
         case 5:
            estado = "Entregado";
            break;
         case 6:
            estado = "Despacho parcial";
            break;
         case 7:
            estado = "Cotización";
         }

         data.put((Object)estado);
         switch(usuario.getIdTipoUsuario()) {
         case 1:
            data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-check-circle fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Entregar pedido\" onclick=\"EntregarPedidoJson(" + pedido.getIdPedido() + ")\"></i><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar pedido\" onclick=\"LoadPedidoJson(" + pedido.getIdPedido() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar empresa\" onclick=\"EliminarPedido(" + pedido.getIdPedido() + ")\"></i></td>"));
            break;
         case 2:
            data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-check-circle fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Entregar pedido\" onclick=\"EntregarPedidoJson(" + pedido.getIdPedido() + ")\"></i><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar pedido\" onclick=\"LoadPedidoJson(" + pedido.getIdPedido() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar empresa\" onclick=\"EliminarPedido(" + pedido.getIdPedido() + ")\"></i></td>"));
            break;
         case 3:
            data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar pedido\" onclick=\"LoadPedidoJson(" + pedido.getIdPedido() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar empresa\" onclick=\"EliminarPedido(" + pedido.getIdPedido() + ")\"></i></td>"));
            break;
         case 4:
            data.put((Object)("<td class=\"text-center\"><i class=\"fa fas fa-check-circle fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Entregar pedido\" onclick=\"EntregarPedidoJson(" + pedido.getIdPedido() + ")\"></i></td>"));
         }
      }

      return datosCompletos;
   }

   public static List<DBObject> getEmpresasClienteDBOList(Usuario usuario) throws IOException {
      List<DBObject> dBObjects = new ArrayList();
      switch(usuario.getIdTipoUsuario()) {
      case 1:
         dBObjects = empresaMongoController.getEmpresasByTipo(2);
         break;
      case 2:
         dBObjects = empresaMongoController.getEmpresasByTipo(2, usuario.getIdEmpresa());
      case 3:
      case 4:
      case 5:
      }

      return (List)dBObjects;
   }

   public static JSONArray getDeudores(Usuario usuario) throws IOException {
      List deudores;
      if (usuario.getIdTipoUsuario() != 1) {
         deudores = deudorMongoController.getDeudorsInstitucion(usuario.getIdEmpresa());
      } else {
         deudores = deudorMongoController.getDeudors();
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = deudores.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         Deudor deudor = (Deudor)gson.fromJson(JSON.serialize(dBObject), Deudor.class);
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(empresaMongoController.findEmpresa(deudor.getIdEmpresaCliente())), Empresa.class);
         data.put((Object)empresa.getNombre());
         data.put((Object)deudor.getIdentificacion());
         data.put((Object)deudor.getNombreDeudor());
         data.put((Object)deudor.getTelefono());
         String tipo = "";
         switch(deudor.getEstado()) {
         case 1:
            tipo = "Nuevo";
            break;
         case 2:
            tipo = "Acuerdo de pago";
            break;
         case 3:
            tipo = "Sin contacto";
            break;
         case 4:
            tipo = "Cobro jurídico";
            break;
         case 5:
            tipo = "Pagado";
            break;
         case 6:
            tipo = "Localizado";
         }

         data.put((Object)tipo);
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar deudor\" onclick=\"LoadDeudorJson(" + deudor.getIdDeudor() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar deudor\" onclick=\"EliminarDeudor(" + deudor.getIdDeudor() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getDeudoresLista(Usuario usuario) throws IOException {
      List deudores;
      if (usuario.getIdTipoUsuario() != 1) {
         deudores = deudorMongoController.getDeudorsInstitucion(usuario.getIdEmpresa());
      } else {
         deudores = deudorMongoController.getDeudors();
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = deudores.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         Deudor deudor = (Deudor)gson.fromJson(JSON.serialize(dBObject), Deudor.class);
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(empresaMongoController.findEmpresa(deudor.getIdEmpresaCliente())), Empresa.class);
         data.put((Object)empresa.getNombre());
         data.put((Object)deudor.getIdentificacion());
         data.put((Object)deudor.getNombreDeudor());
         data.put((Object)("$ " + decimalFormat.format(deudor.getSaldoTotal())));
         data.put((Object)("$ " + decimalFormat.format(deudor.getSaldoActual())));
         String tipo = "";
         switch(deudor.getEstado()) {
         case 1:
            tipo = "Nuevo";
            break;
         case 2:
            tipo = "Acuerdo de pago";
            break;
         case 3:
            tipo = "Sin contacto";
            break;
         case 4:
            tipo = "Cobro jurídico";
            break;
         case 5:
            tipo = "Pagado";
            break;
         case 6:
            tipo = "Localizado";
         }

         data.put((Object)tipo);
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"gestión deudor\" onclick=\"LoadDeudorJson(" + deudor.getIdDeudor() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Notificar deudor\" onclick=\"NotificarDeudor(" + deudor.getIdDeudor() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getIodDevices(Usuario usuario) throws IOException {
      List iotDevices;
      if (usuario.getIdTipoUsuario() != 1) {
         iotDevices = iOTDeviceMongoController.getIOTDevicesInstitucion(usuario.getIdEmpresa());
      } else {
         iotDevices = iOTDeviceMongoController.getIOTDevices();
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = iotDevices.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         IOTDevice iOTDevice = (IOTDevice)gson.fromJson(JSON.serialize(dBObject), IOTDevice.class);
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(empresaMongoController.findEmpresa(iOTDevice.getIdEmpresa())), Empresa.class);
         data.put((Object)iOTDevice.getNombre());
         data.put((Object)simpleDateFormatwithTime.format(new Date(iOTDevice.getFechaUltimoRegistro())));
         data.put((Object)iOTDevice.getTipoString());
         data.put((Object)empresa.getNombre());
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar dispositivo\" onclick=\"LoadDeviceIOTJson(" + iOTDevice.getIdIOTDevice() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar dispositivo\" onclick=\"EliminarDeviceIOT(" + iOTDevice.getIdIOTDevice() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getIodDevicesAlarmas(Usuario usuario) throws IOException {
      List iotDevicesAlarmas;
      if (usuario.getIdTipoUsuario() != 1) {
         iotDevicesAlarmas = iOTDeviceAlarmaMongoController.getIOTDeviceAlarmasInstitucion(usuario.getIdEmpresa());
      } else {
         iotDevicesAlarmas = iOTDeviceAlarmaMongoController.getIOTDeviceAlarmasAlarma();
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = iotDevicesAlarmas.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         IOTDeviceAlarma iOTDeviceAlarma = (IOTDeviceAlarma)gson.fromJson(JSON.serialize(dBObject), IOTDeviceAlarma.class);
         data.put((Object)iOTDeviceAlarma.getNombre());
         IOTDevice iOTDevice = iOTDeviceMongoController.findIOTDevice(iOTDeviceAlarma.getIdIOTDevice());
         if (iOTDevice != null) {
            data.put((Object)iOTDevice.getNombre());
         } else {
            data.put((Object)"N/A");
         }

         data.put((Object)iOTDeviceAlarma.getTipo());
         data.put((Object)decimalFormatwithpoint.format(iOTDeviceAlarma.getValor()));
         data.put(iOTDeviceAlarma.getNumeroActivaciones());
         data.put((Object)simpleDateFormatwithTime.format(new Date(iOTDeviceAlarma.getFechaUltimoDisparo())));
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar alarma\" onclick=\"LoadIOTDeviceAlarmaJson(" + iOTDeviceAlarma.getIdIOTDeviceAlarma() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar alarma\" onclick=\"EliminarIOTDeviceAlarma(" + iOTDeviceAlarma.getIdIOTDeviceAlarma() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getEmpresasSGORoot() throws IOException {
      List<DBObject> empresasDBObjects = empresaMongoController.getEmpresasByTipo(1);
      JSONArray datosCompletos = new JSONArray();
      Iterator var2 = empresasDBObjects.iterator();

      while(var2.hasNext()) {
         DBObject dBObject = (DBObject)var2.next();
         JSONArray data = new JSONArray();
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(dBObject), Empresa.class);
         data.put((Object)empresa.getNombre());
         data.put((Object)simpleDateFormat.format(new Date(empresa.getFechaUltimoPago())));
         data.put((Object)simpleDateFormat.format(new Date(empresa.getFechaUltimoPedido())));
         data.put(usuarioMongoController.getUsuariosInstitucion(empresa.getIdEmpresa()).size());
         data.put(0);
         data.put(0);
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getLogsActividades(Usuario usuario, long fechaInicio, long fechaFin) {
      List logs;
      if (usuario.getIdTipoUsuario() == 1) {
         logs = logActividadesMongoController.getLogs(fechaInicio, fechaFin);
      } else {
         logs = logActividadesMongoController.getLogs(fechaInicio, fechaFin, usuario.getIdEmpresa());
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var7 = logs.iterator();

      while(var7.hasNext()) {
         DBObject dBObject = (DBObject)var7.next();
         JSONArray data = new JSONArray();
         LogActividades log = (LogActividades)gson.fromJson(JSON.serialize(dBObject), LogActividades.class);
         data.put((Object)log.getIp());
         data.put((Object)log.getNombre_usuario());
         data.put((Object)simpleDateFormatwithTime.format(new Date(log.getFecha())));
         data.put((Object)log.getLog());
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getServiciosriesgosinternos(Usuario usuario) throws IOException {
      List ServicioRiesgoInternoDBObjects;
      if (usuario.getIdTipoUsuario() != 1) {
         ServicioRiesgoInternoDBObjects = servicioRiesgosInternoMongoController.getServicioRiesgoInternosByEpresaCreadora(usuario.getIdEmpresa());
      } else {
         ServicioRiesgoInternoDBObjects = servicioRiesgosInternoMongoController.getServicioRiesgoInternos();
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = ServicioRiesgoInternoDBObjects.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         ServicioRiesgoInterno servicioRiesgoInterno = (ServicioRiesgoInterno)gson.fromJson(JSON.serialize(dBObject), ServicioRiesgoInterno.class);
         data.put((Object)servicioRiesgoInterno.getNombre());
         data.put((Object)servicioRiesgoInterno.getDescripcionCorta());
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(empresaMongoController.findEmpresa(servicioRiesgoInterno.getIdEmpresa())), Empresa.class);
         data.put((Object)empresa.getNombre());
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Editar servicio\" onclick=\"LoadServicioRiesgoInternoJson(" + servicioRiesgoInterno.getIdServicioRiesgoInterno() + ")\"></i><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar servicio\" onclick=\"EliminarServicioRiesgoInterno(" + servicioRiesgoInterno.getIdServicioRiesgoInterno() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static List<DBObject> getServiciosriesgosinternosTablero(Usuario usuario) throws IOException {
      List ServicioRiesgoInternoDBObjects;
      if (usuario.getIdTipoUsuario() != 1) {
         ServicioRiesgoInternoDBObjects = servicioRiesgosInternoMongoController.getServicioRiesgoInternosByEpresaCreadora(usuario.getIdEmpresa());
      } else {
         ServicioRiesgoInternoDBObjects = servicioRiesgosInternoMongoController.getServicioRiesgoInternos();
      }

      return ServicioRiesgoInternoDBObjects;
   }

   public static JSONArray getServiciosRuteoTableroUltimas48Horas(Usuario usuario) throws IOException {
      List ServicioRuteoDBObjects;
      if (usuario.getIdTipoUsuario() == 1) {
         ServicioRuteoDBObjects = paqueteMongoController.getPaqueteRuteosUltimas48Horas();
      } else {
         DBObject dBObject = empresaMongoController.findEmpresa(usuario.getIdEmpresa());
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(dBObject), Empresa.class);
         if (empresa.getTipoEmpresa() == 1) {
            ServicioRuteoDBObjects = paqueteMongoController.getPaqueteRuteosByEmpresaSGOUltimas48Horas(usuario.getIdEmpresa());
         } else {
            ServicioRuteoDBObjects = paqueteMongoController.getPaqueteRuteosByEmpresaCreadoraUltimas48Horas(usuario.getIdEmpresa());
         }
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var9 = ServicioRuteoDBObjects.iterator();

      while(var9.hasNext()) {
         DBObject dBObject = (DBObject)var9.next();
         JSONArray data = new JSONArray();
         Paquete paquete = (Paquete)gson.fromJson(JSON.serialize(dBObject), Paquete.class);
         data.put((Object)paquete.getGuia());
         data.put((Object)paquete.getIdentificacion());
         data.put((Object)paquete.getNombre_cliente());
         data.put((Object)paquete.getEstado());
         String menu = "<td class=\"text-center\">";
         menu = menu + "<i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Detalle servicio\" onclick=\"LoadDetalleServicioJson(" + paquete.getIdPaquete() + ")\"></i>";
         if (paquete.getEstado().equals("Solicitado")) {
            menu = menu + "<i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar servicio\" onclick=\"EliminarServicio(" + paquete.getIdPaquete() + ")\"></i>";
         }

         menu = menu + "</td>";
         data.put((Object)menu);
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getServiciosPendientesDistribucion(Usuario usuario) throws IOException {
      List ServicioRuteoDBObjects;
      if (usuario.getIdTipoUsuario() == 1) {
         ServicioRuteoDBObjects = paqueteMongoController.getPaqueteRuteosUltimas48Horas();
      } else {
         DBObject dBObject = empresaMongoController.findEmpresa(usuario.getIdEmpresa());
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(dBObject), Empresa.class);
         if (empresa.getTipoEmpresa() == 1) {
            ServicioRuteoDBObjects = paqueteMongoController.getPaqueteRuteosByEmpresaSGOByEstado(usuario.getIdEmpresa(), Paquete.estados[2]);
         } else {
            ServicioRuteoDBObjects = paqueteMongoController.getPaqueteRuteosByEmpresaCreadoraByEstado(usuario.getIdEmpresa(), Paquete.estados[2]);
         }
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var8 = ServicioRuteoDBObjects.iterator();

      while(var8.hasNext()) {
         DBObject dBObject = (DBObject)var8.next();
         JSONArray data = new JSONArray();
         Paquete paquete = (Paquete)gson.fromJson(JSON.serialize(dBObject), Paquete.class);
         data.put((Object)paquete.getGuia());
         data.put((Object)paquete.getIdentificacion());
         data.put((Object)paquete.getDireccion_origen());
         data.put((Object)paquete.getDireccion_entrega());
         data.put((Object)paquete.getEstado());
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Detalle servicio\" onclick=\"LoadDetalleServicioJson(" + paquete.getIdPaquete() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getRutasServicios(Usuario usuario) throws IOException, JSONException {
      List rutaServiciosDBObjects;
      if (usuario.getIdTipoUsuario() == 1) {
         rutaServiciosDBObjects = rutaServiciosMongoController.getrutasBySeguimientoRoot();
      } else {
         rutaServiciosDBObjects = rutaServiciosMongoController.getrutasBySeguimiento(usuario.getIdEmpresa());
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = rutaServiciosDBObjects.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         RutaReparto rutaServicio = (RutaReparto)gson.fromJson(JSON.serialize(dBObject), RutaReparto.class);
         data.put((Object)simpleDateFormat.format(new Date(rutaServicio.getFechaCreacion())));
         data.put((Object)rutaServicio.getNombre_geocerca());
         data.put((Object)rutaServicio.getNombre_conductor());
         String paquetes = rutaServicio.getPaquetes();
         JSONArray jSONArray = new JSONArray(paquetes);
         data.put(jSONArray.length());
         data.put((Object)rutaServicio.getEstado());
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar ruta\" onclick=\"eliminarRuta(" + rutaServicio.getIdRutaServicio() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getServicioRutaTable(List<DBObject> ServicioRuteoDBObjects) {
      JSONArray datosCompletos = new JSONArray();
      Iterator var2 = ServicioRuteoDBObjects.iterator();

      while(var2.hasNext()) {
         DBObject dBObject = (DBObject)var2.next();
         JSONArray data = new JSONArray();
         Paquete paquete = (Paquete)gson.fromJson(JSON.serialize(dBObject), Paquete.class);
         data.put((Object)paquete.getGuia());
         data.put((Object)paquete.getIdentificacion());
         data.put((Object)paquete.getDireccion_origen());
         data.put((Object)paquete.getDireccion_entrega());
         data.put((Object)paquete.getEstado());
         String menu = "<td class=\"text-center\">";
         menu = menu + "<i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Detalle servicio\" onclick=\"LoadDetalleServicioJson(" + paquete.getIdPaquete() + ")\"></i>";
         if (paquete.getEstado().equals("Solicitado")) {
            menu = menu + "<i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar servicio\" onclick=\"EliminarServicio(" + paquete.getIdPaquete() + ")\"></i>";
         }

         menu = menu + "</td>";
         data.put((Object)menu);
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getGeocercasTable(List<DBObject> dBObjectsGeocercas) {
      JSONArray datosCompletos = new JSONArray();
      Iterator var2 = dBObjectsGeocercas.iterator();

      while(var2.hasNext()) {
         DBObject dBObject = (DBObject)var2.next();
         JSONArray data = new JSONArray();
         Geocerca geocerca = (Geocerca)gson.fromJson(JSON.serialize(dBObject), Geocerca.class);
         data.put((Object)geocerca.getNombre());
         data.put(geocerca.getUsuarios_pertenecientes().size());
         String menu = "<td class=\"text-center\">";
         menu = menu + "<i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Detalle geocerca\" onclick=\"LoadGeocercaJson(" + geocerca.getIdGeocerca() + ")\"></i>";
         menu = menu + "<i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar geocerca\" onclick=\"EliminarGeocerca(" + geocerca.getIdGeocerca() + ")\"></i>";
         menu = menu + "</td>";
         data.put((Object)menu);
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getServiciosBuscarHistorial(List<DBObject> ServicioRuteoDBObjects) {
      JSONArray datosCompletos = new JSONArray();
      Iterator var2 = ServicioRuteoDBObjects.iterator();

      while(var2.hasNext()) {
         DBObject dBObject = (DBObject)var2.next();
         JSONArray data = new JSONArray();
         Paquete paquete = (Paquete)gson.fromJson(JSON.serialize(dBObject), Paquete.class);
         data.put((Object)paquete.getGuia());
         data.put((Object)paquete.getIdentificacion());
         data.put((Object)paquete.getNombre_cliente());
         data.put((Object)paquete.getEstado());
         String menu = "<td class=\"text-center\">";
         menu = menu + "<i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Detalle servicio\" onclick=\"LoadDetalleServicioJson(" + paquete.getIdPaquete() + ")\"></i>";
         if (paquete.getEstado().equals("Solicitado")) {
            menu = menu + "<i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar servicio\" onclick=\"EliminarServicio(" + paquete.getIdPaquete() + ")\"></i>";
         }

         menu = menu + "</td>";
         data.put((Object)menu);
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getServiciosPendientesRecoleccion(Usuario usuario) throws IOException {
      List ServicioRuteoDBObjects;
      if (usuario.getIdTipoUsuario() == 1) {
         ServicioRuteoDBObjects = paqueteMongoController.getPaqueteRuteosUltimas48Horas();
      } else {
         DBObject dBObject = empresaMongoController.findEmpresa(usuario.getIdEmpresa());
         Empresa empresa = (Empresa)gson.fromJson(JSON.serialize(dBObject), Empresa.class);
         if (empresa.getTipoEmpresa() == 1) {
            ServicioRuteoDBObjects = paqueteMongoController.getPaqueteRuteosByEmpresaSGOByEstado(usuario.getIdEmpresa(), Paquete.estados[0]);
         } else {
            ServicioRuteoDBObjects = paqueteMongoController.getPaqueteRuteosByEmpresaCreadoraByEstado(usuario.getIdEmpresa(), Paquete.estados[0]);
         }
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var8 = ServicioRuteoDBObjects.iterator();

      while(var8.hasNext()) {
         DBObject dBObject = (DBObject)var8.next();
         JSONArray data = new JSONArray();
         Paquete paquete = (Paquete)gson.fromJson(JSON.serialize(dBObject), Paquete.class);
         data.put((Object)paquete.getGuia());
         data.put((Object)paquete.getIdentificacion());
         data.put((Object)paquete.getDireccion_origen());
         data.put((Object)paquete.getDireccion_entrega());
         data.put((Object)paquete.getEstado());
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Detalle servicio\" onclick=\"LoadDetalleServicioJson(" + paquete.getIdPaquete() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static JSONArray getPaquetesBodega(List<DBObject> dBObjectsPaquetes) {
      JSONArray datosCompletos = new JSONArray();
      Iterator var2 = dBObjectsPaquetes.iterator();

      while(var2.hasNext()) {
         DBObject dBObject = (DBObject)var2.next();
         JSONArray data = new JSONArray();
         Paquete paquete = (Paquete)gson.fromJson(JSON.serialize(dBObject), Paquete.class);
         data.put((Object)paquete.getGuia());
         data.put((Object)paquete.getIdentificacion());
         data.put((Object)paquete.getZona_bodega());
         data.put((Object)paquete.getEstado());
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-edit fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Detalle servicio\" onclick=\"LoadDetalleServicioJson(" + paquete.getIdPaquete() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }

   public static Object getArchivosDirecciones(Usuario usuario) throws IOException {
      List archivoDBObjects;
      if (usuario.getIdTipoUsuario() == 1) {
         archivoDBObjects = archivoDireccionesMongoController.getArchivoDireccioness();
      } else {
         archivoDBObjects = archivoDireccionesMongoController.getArchivoDireccionesFromEmpresa(usuario.getIdEmpresa());
      }

      JSONArray datosCompletos = new JSONArray();
      Iterator var3 = archivoDBObjects.iterator();

      while(var3.hasNext()) {
         DBObject dBObject = (DBObject)var3.next();
         JSONArray data = new JSONArray();
         ArchivoDirecciones archivo = (ArchivoDirecciones)gson.fromJson(JSON.serialize(dBObject), ArchivoDirecciones.class);
         data.put((Object)simpleDateFormatwithTime.format(new Date(archivo.getFecha())));
         data.put((Object)archivo.getEstado());
         switch(archivo.getTipo()) {
         case 1:
            data.put((Object)"Archivo");
            break;
         case 2:
            data.put((Object)"JSON");
            break;
         case 3:
            data.put((Object)"FTP");
         }

         data.put(archivo.getRegistros());
         data.put((Object)("<a href='tmp/" + archivo.getArchivo() + "' target='_blank'>" + archivo.getArchivo() + "</a>"));
         data.put((Object)("<td class=\"text-center\"><i class=\"fa fa-remove fa-3x\" style=\"width:40px; height:40px; padding: 2px;margin: 1px;border:1px solid;\" title=\"Eliminar archivo\" onclick=\"EliminarArchivo(" + archivo.getIdArchivoDirecciones() + ")\"></i></td>"));
         datosCompletos.put((Object)data);
      }

      return datosCompletos;
   }
}
