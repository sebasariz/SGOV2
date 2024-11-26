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
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class CreateEmpresa extends org.apache.struts.action.Action {

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
            empresa.setUuid(UUID.randomUUID().toString());
            if (empresa.getTipoEmpresa() == 2) {
                empresa.setIdEmpresaCreadora(usuario.getIdEmpresa());
            } else {
                empresa.setIdEmpresaCreadora(0);
            }
            //validar el email
            EmpresaMongoController empresaMongoController = new EmpresaMongoController();
            empresa.setValorPendientePago(0);
            empresa.setEstado(1);
            empresa.setFechaUltimoPedido(System.currentTimeMillis());
            empresa.setFehaRegistro(System.currentTimeMillis());
            FormFile cara = empresa.getFile();
            File foto = null;
            try {
                // get file from the bean
                String fname = cara.getFileName();
                if (fname.length() == 0) {
                    System.out.println("sin archivo");
                } else {
                    fname = fname.replace(" ", "");
                    fname = System.currentTimeMillis() + fname;
                    // save file in the app server 
                    foto = new File(getServlet().getServletContext().getRealPath("") + "/img/logo/" + fname);
                    FileOutputStream fos = new FileOutputStream(foto);
                    fos.write(cara.getFileData());
                    fos.close();
                    empresa.setLogo("img/logo/" + fname);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("ERROR-------" + e);
            }

            FormFile punteroForm = empresa.getFilePuntero();
            try {
                // get file from the bean
                String fname = punteroForm.getFileName();
                if (fname.length() == 0) {
                    System.out.println("sin archivo");
                } else {
                    fname = fname.replace(" ", "");
                    fname = System.currentTimeMillis() + fname;
                    // save file in the app server 
                    File puntero = new File(getServlet().getServletContext().getRealPath("") + "/img/punteros/" + fname);
                    FileOutputStream fos = new FileOutputStream(puntero);
                    fos.write(punteroForm.getFileData());
                    fos.close();
                    empresa.setPuntero("img/punteros/" + fname);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("ERROR-------" + e);
            }
            empresaMongoController.createEmpresa(empresa);
            jSONObject.put("empresas", GetDynamicTable.getEmpresasTable(usuario));

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
