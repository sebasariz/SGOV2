package com.iammagis.sga.support;

import com.iammagis.sga.mongo.LogActividadesMongoController;
import com.iammagis.sga.mongo.beans.LogActividades;
import com.iammagis.sga.mongo.beans.Usuario;
import javax.servlet.http.HttpServletRequest;

public class CreateLogActividades {
   public static void createLogActividades(Usuario usuario, String mensaje, HttpServletRequest request) {
      String ip = request.getHeader("x-forwarded-for");
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getHeader("Proxy-Client-IP");
      }

      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getHeader("WL-Proxy-Client-IP");
      }

      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getRemoteAddr();
      }

      LogActividadesMongoController logActividadesMongoController = new LogActividadesMongoController();
      LogActividades logActividades = new LogActividades();
      logActividades.setNombre_usuario(usuario.getNombre() + " " + usuario.getApellidos());
      logActividades.setFecha(System.currentTimeMillis());
      logActividades.setIdEmpresa(usuario.getIdEmpresa());
      logActividades.setIdUsuario(usuario.getIdUsuario());
      logActividades.setIp(ip);
      logActividades.setLog(mensaje);
      logActividadesMongoController.createLogActividades(logActividades);
   }
}
