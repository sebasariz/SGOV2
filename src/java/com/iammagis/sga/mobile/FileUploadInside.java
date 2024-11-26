package com.iammagis.sga.mobile;

import com.google.gson.Gson;
import com.iammagis.sga.mongo.PaqueteMongoController;
import com.iammagis.sga.mongo.beans.Paquete;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileUploadInside extends HttpServlet {
   private static String BASE_DIRECTORY = "";
   private static final String SUCCESS = "success";
   private static final String ORIGINAL = "ÁáÉéÍíÓóÚúÑñÜü";
   private static final String REPLACEMENT = "AaEeIiOoUuNnUu";

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      ServletOutputStream out = response.getOutputStream();
      out.write("Servidor propiedad de Iam Magis S.A.S. Metodo Get no soportado.".getBytes());
      out.flush();
      out.close();
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      boolean isMultipart = ServletFileUpload.isMultipartContent(request);
      JSONObject jSONObjectResult = new JSONObject();
      if (isMultipart) {
         FileItemFactory factory = new DiskFileItemFactory();
         ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
         servletFileUpload.setSizeMax(-1L);

         try {
            BASE_DIRECTORY = this.getServletContext().getRealPath("/") + "/images/";
            String directory = "";
            List items = servletFileUpload.parseRequest(request);
            Iterator iter = items.iterator();
            int id = 0;

            Gson gson;
            while(iter.hasNext()) {
               FileItem item = (FileItem)iter.next();
               String fileName;
               if (item.isFormField()) {
                  fileName = item.getFieldName();
                  if (fileName.equalsIgnoreCase("id")) {
                     id = Integer.parseInt(item.getString());
                  }
               } else {
                  fileName = System.currentTimeMillis() + "-" + URLDecoder.decode(item.getName());
                  fileName = this.stripAccents(fileName).replaceAll(" ", "").replaceAll("\\s", "");
                  gson = new Gson();
                  PaqueteMongoController paqueteMongoController = new PaqueteMongoController();
                  DBObject dBObject = paqueteMongoController.findPaqueteRuteo(id);
                  Paquete paquete = (Paquete)gson.fromJson(JSON.serialize(dBObject), Paquete.class);
                  JSONArray jSONArrayMultimedia = paquete.getjSONArray_multimedia();
                  if (jSONArrayMultimedia == null) {
                     jSONArrayMultimedia = new JSONArray();
                  }

                  JSONObject jSONObject = new JSONObject();
                  jSONObject.put("archivo", (Object)fileName);
                  jSONArrayMultimedia.put((Object)jSONObject);
                  paquete.setjSONArray_multimedia(jSONArrayMultimedia);
                  paqueteMongoController.editPaqueteRuteo(paquete);
                  File file = new File(directory, fileName);
                  file = new File(BASE_DIRECTORY, file.getPath());
                  File parentFile = file.getParentFile();
                  if (parentFile != null) {
                     parentFile.mkdirs();
                  }

                  System.out.println("file: " + file.getAbsolutePath());
                  item.write(file);
               }
            }

            PaqueteMongoController paqueteMongoController = new PaqueteMongoController();
            DBObject dBObject = paqueteMongoController.findPaqueteRuteo(id);
            gson = new Gson();
            Paquete paquete = (Paquete)gson.fromJson(JSON.serialize(dBObject), Paquete.class);
            JSONArray jSONArray = paquete.getjSONArray_multimedia();
            jSONObjectResult.put("multimedia", (Object)jSONArray);
            jSONObjectResult.put("estado", (Object)"ok");
            response.setStatus(202);
         } catch (Exception var23) {
            var23.printStackTrace();
            response.setStatus(500);

            try {
               jSONObjectResult.put("estado", (Object)"error");
            } catch (JSONException var22) {
               var22.printStackTrace();
               Logger.getLogger(FileUploadInside.class.getName()).log(Level.SEVERE, (String)null, var22);
            }
         }
      } else {
         System.out.println("no es multipart");

         try {
            jSONObjectResult.put("estado", (Object)"error");
            response.setStatus(400);
         } catch (JSONException var21) {
            var21.printStackTrace();
            Logger.getLogger(FileUploadInside.class.getName()).log(Level.SEVERE, (String)null, var21);
         }
      }

      ServletOutputStream out = response.getOutputStream();
      out.write(jSONObjectResult.toString().getBytes());
      out.flush();
      out.close();
   }

   private String stripAccents(String str) {
      if (str == null) {
         return null;
      } else {
         char[] array = str.toCharArray();

         for(int index = 0; index < array.length; ++index) {
            int pos = "ÁáÉéÍíÓóÚúÑñÜü".indexOf(array[index]);
            if (pos > -1) {
               array[index] = "AaEeIiOoUuNnUu".charAt(pos);
            }
         }

         return new String(array);
      }
   }
}
