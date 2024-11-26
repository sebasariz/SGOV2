/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.administracion.productos;


import com.iammagis.sga.mongo.ProductoMongoController;
import com.iammagis.sga.mongo.beans.Producto;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.GetDynamicTable;
import com.iammagis.sga.support.XLSReaderMasiva;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class MasivaProductos extends org.apache.struts.action.Action {

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
            Producto producto = (Producto) form;
            ProductoMongoController productoMongoController = new ProductoMongoController();

            FormFile formFile = producto.getFile();
            File file = null;
            try {
                // get file from the bean
                String fname = formFile.getFileName();
                if (fname.length() == 0) {
                    System.out.println("sin archivo");
                } else {
                    fname = fname.replace(" ", "");
                    fname = System.currentTimeMillis() + fname;
                    // save file in the app server 
                    file = new File(getServlet().getServletContext().getRealPath("") + "/docs/" + fname);
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(formFile.getFileData());
                    fos.close();
                    System.out.println("producto.getReinicio(): " + producto.getReinicio());
//                    if (producto.getReinicio() == 1) {
//                        //reiniciamos el invenario
//                        productoMongoController.removeProductosFromEmpresa(usuario.getIdEmpresa());
//                    }

                    JSONObject jSONObjectRetorno = XLSReaderMasiva.getFromXLSAndCreateProductos(file);
                    if (!jSONObjectRetorno.has("error")) {
                        //estan completos los fabricantes
                        if (jSONObjectRetorno.has("arrayDatos")) {
                            JSONArray jSONArrayProductos = jSONObjectRetorno.getJSONArray("arrayDatos");
                            System.out.println("jSONArrayProductos : " + jSONArrayProductos.length());
                            for (int i = 0; i < jSONArrayProductos.length(); i++) {
                                JSONObject jSONObjectProductos = jSONArrayProductos.getJSONObject(i);
                                if (jSONObjectProductos.has("referencia")) {
                                    Producto productoIn = productoMongoController.findByReferencia(jSONObjectProductos.getString("referencia"), usuario.getIdEmpresa());
                                    if (productoIn == null) {
                                        productoIn = new Producto();
                                        productoIn.setIdEmpresa(usuario.getIdEmpresa());
                                        productoIn.setReferencia(jSONObjectProductos.getString("referencia"));
                                        if (jSONObjectProductos.has("nombre")) {
                                            productoIn.setNombre(jSONObjectProductos.getString("nombre"));
                                        }
                                        if (jSONObjectProductos.has("cantidad")) {
                                            productoIn.setCantidad(jSONObjectProductos.getInt("cantidad"));
                                        }
                                        if (jSONObjectProductos.has("valor")) {
                                            productoIn.setValor(jSONObjectProductos.getDouble("valor"));
                                        }
                                        productoMongoController.createProducto(productoIn);
                                    } else {

                                        if (jSONObjectProductos.has("nombre")) {
                                            productoIn.setNombre(jSONObjectProductos.getString("nombre"));
                                        }
                                        if (jSONObjectProductos.has("cantidad")) {
                                            if (producto.getReinicio() == 1) {
                                                productoIn.setCantidad(jSONObjectProductos.getInt("cantidad"));
                                            } else {
                                                productoIn.setCantidad(productoIn.getCantidad() + jSONObjectProductos.getInt("cantidad"));
                                            }
                                        }
                                        if (jSONObjectProductos.has("valor")) {
                                            productoIn.setValor(jSONObjectProductos.getDouble("valor"));
                                        }
//                                        System.out.println("referencia: "+jSONObjectProductos.getString("referencia"));
//                                        System.out.println("nombre: "+jSONObjectProductos.getString("nombre"));
//                                        System.out.println("cantidad: "+jSONObjectProductos.getInt("cantidad"));
                                        productoMongoController.editProducto(productoIn);

                                    }
                                }
                            }
                        } else {
                            jSONObject.put("error", "Error de formato de archivo, recuerde que debe ser .XLS");
                        }
                        jSONObject.put("productos", GetDynamicTable.getProductos(productoMongoController.getproductosFromEmpresa(usuario)));
                    } else {
                        jSONObject.put("error", jSONObjectRetorno.getString("error"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("ERROR-------" + e);
            }
            System.out.println("jSONObject: " + jSONObject);
        } else {
            MessageResources messages = MessageResources.getMessageResources("com.iammagis.resources.ApplicationResource");
            String message = messages.getMessage(request.getLocale(), "erros.timepoSesion");
            jSONObject.put("error", message);
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
