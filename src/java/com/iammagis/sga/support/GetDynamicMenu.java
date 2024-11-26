package com.iammagis.sga.support;

import com.google.gson.Gson;
import com.iammagis.sga.mongo.IOTDeviceMongoController;
import com.iammagis.sga.mongo.ModuloMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.IOTDevice;
import com.iammagis.sga.mongo.beans.Modulo;
import com.iammagis.sga.mongo.beans.Submodulo;
import com.iammagis.sga.mongo.beans.Usuario;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class GetDynamicMenu {
   public static String getMenu(Usuario usuario) throws IOException {
      String menu = "";
      Gson gson = new Gson();
      if (usuario.getSubmodulos() != null) {
         ArrayList<Submodulo> subModulos = new ArrayList(usuario.getSubmodulos());
         ModuloMongoController moduloMongoController = new ModuloMongoController();
         ArrayList<DBObject> modulos = new ArrayList(moduloMongoController.getModulos());
         Iterator var6 = modulos.iterator();

         while(var6.hasNext()) {
            DBObject moduloDBObject = (DBObject)var6.next();
            Modulo modulo = (Modulo)gson.fromJson(JSON.serialize(moduloDBObject), Modulo.class);
            if (modulo.getSubmodulos() == null) {
               modulo.setSubmodulos(new ArrayList());
            }

            subModulos.stream().filter((submodulo) -> {
               return submodulo != null && submodulo.getIdModulo() == modulo.getIdModulo();
            }).forEachOrdered((submodulo) -> {
               modulo.getSubmodulos().add(submodulo);
            });
            if (!modulo.getSubmodulos().isEmpty()) {
               menu = menu + "<li class=\"has-submenu\">                        <a href=\"#" + modulo.getIdModulo() + "\" data-toggle=\"collapse\" aria-expanded=\"false\">                            <i class=\"\"></i> <span class=\"nav-text\">" + modulo.getNombre() + "</span>                        </a>                        <div class=\"sub-menu collapse secondary list-style-circle\" id=\"" + modulo.getIdModulo() + "\"><ul>";
               menu = (String)modulo.getSubmodulos().stream().map((submodulo) -> {
                  return "<li> <a href=\"" + submodulo.getAccion() + ".sga\" class=\"animsition-link\"\">" + submodulo.getNombre() + " </a></li>";
               }).reduce(menu, String::concat);
               menu = menu + " </ul></div></li>";
            }
         }
      }

      return menu;
   } 
}
