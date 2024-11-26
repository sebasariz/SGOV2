/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.administracion.empresa;

import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
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
public class MasivaCliente extends org.apache.struts.action.Action {

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
            Empresa empresa = (Empresa) form;
            EmpresaMongoController empresaMongoController = new EmpresaMongoController();

            //eliminamos las empresas clientes de esta empresa
            empresaMongoController.removeEmpresasMasiva(usuario.getIdEmpresa());

            FormFile formFile = empresa.getFile();
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

                    JSONObject jSONObjectRetorno = XLSReaderMasiva.getFromXLSAndCreateClientes(file);
                    if (!jSONObject.has("error")) {
                        //estan completos los fabricantes
                        JSONArray jSONArrayClientes = jSONObjectRetorno.getJSONArray("arrayDatos");
                        System.out.println("jSONArrayClientes: " + jSONArrayClientes.length());
                        for (int i = 0; i < jSONArrayClientes.length(); i++) {
                            JSONObject jSONObjectEmpresa = jSONArrayClientes.getJSONObject(i);
                            System.out.println("JSON Cliente; " + jSONObjectEmpresa);
                            if (jSONObjectEmpresa.has("codigo")) {
                                Empresa empresaIn = empresaMongoController.findEmpresaByCodigo(jSONObjectEmpresa.get("codigo") + "", usuario.getIdEmpresa());
                                if (empresaIn == null) {
                                    empresaIn = new Empresa();
                                    empresaIn.setTipoEmpresa(2);
                                    empresaIn.setIdEmpresaCreadora(usuario.getIdEmpresa());
                                    empresaIn.setCodigo(jSONObjectEmpresa.getString("codigo"));
                                    empresaIn.setEstado(1);
                                    if (jSONObjectEmpresa.has("nombre")) {
                                        empresaIn.setNombre(jSONObjectEmpresa.getString("nombre"));
                                    }
                                    if (jSONObjectEmpresa.has("nit")) {
                                        empresaIn.setNit(jSONObjectEmpresa.getString("nit"));
                                    }
                                    if (jSONObjectEmpresa.has("direccion")) {
                                        empresaIn.setDireccion(jSONObjectEmpresa.getString("direccion"));
                                    }
                                    if (jSONObjectEmpresa.has("telefono")) {
                                        empresaIn.setTelefono(jSONObjectEmpresa.getString("telefono"));
                                    }
                                    if (jSONObjectEmpresa.has("ciudad")) {
                                        empresaIn.setCiudad(jSONObjectEmpresa.getString("ciudad"));
                                    }
                                    empresa.setValorPendientePago(0);
                                    empresa.setFechaUltimoPedido(System.currentTimeMillis());
                                    empresaMongoController.createEmpresa(empresaIn);
                                }
                            }

                        }

                        jSONObject.put("empresas", GetDynamicTable.getEmpresasTable(usuario));
                    } else {
                        jSONObject.put("error", jSONObjectRetorno.getString("error"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("ERROR-------" + e);
                jSONObject.put("error", "Error de formato de archivo, recuerde que debe ser .XLS");
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
