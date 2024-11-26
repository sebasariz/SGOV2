/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo.front;


import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author sebastianarizmendy
 */
public class LoadFrontLogin extends org.apache.struts.action.Action {

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

//        TOKEN
//        String uuid = "4986e8af-d50c-47dd-92e3-9dd76157-f5b0";
        String uuid = request.getParameter("uuid");
        System.out.println("uuid: " + uuid);
        EmpresaMongoController empresaMongoController = new EmpresaMongoController();
        Empresa empresa = empresaMongoController.getEmpresaByUUid(uuid);
        HttpSession session = request.getSession();
        System.out.println("empresa: " + empresa);
        if (empresa != null && uuid != null) {
            if (empresa.getLogo() != null) {
                session.setAttribute("logo", empresa.getLogo());
            } else {
                session.setAttribute("logo", "img/logo.png");
            }
            if (empresa.getPuntero() != null) {
                session.setAttribute("puntero", empresa.getPuntero());;
            } else {
                session.setAttribute("puntero", "img/puntero.png");;
            }
            if (empresa.getColorPrimario() != null) {
                session.setAttribute("primary", empresa.getColorPrimario());
            } else {
                session.setAttribute("primary", "#00003e");
            }
            if (empresa.getColorSecundario() != null) {
                session.setAttribute("secundario", empresa.getColorSecundario());
            } else {
                session.setAttribute("secundario", "#68b4e3");
            }
        } else {
            System.out.println("entre por null: " + session.getAttribute("logo"));
            if (session.getAttribute("logo") == null) {
                session.setAttribute("logo", "img/logo.png");
            }
            if (session.getAttribute("puntero") == null) {
                session.setAttribute("puntero", "img/puntero.png");
            }
            if (session.getAttribute("primary") == null) {
                session.setAttribute("primary", "#00003e");
            }
            if (session.getAttribute("secundario") == null) {
                session.setAttribute("secundario", "#68b4e3");
            }
        }
        return mapping.findForward(SUCCESS);
    }
}
