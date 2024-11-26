/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.operacion.pedidos;

import com.iammagis.sga.mongo.PedidoMongoController;
import com.iammagis.sga.mongo.beans.Pedido;
import com.iammagis.sga.mongo.beans.Usuario;
import com.iammagis.sga.support.GenerateSolicitudDespacho;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
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
public class ImprimirPedidoID extends org.apache.struts.action.Action {

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

        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));

        Usuario usuariolog = (Usuario) session.getAttribute("usuario");
        if (usuariolog != null) {
            PedidoMongoController pedidoMongoController = new PedidoMongoController();
            Pedido pedido = pedidoMongoController.findPedido(id);

            String htmlFact = GenerateSolicitudDespacho.generateHTMLPedido(pedido);
            File file = new File(getServlet().getServletContext().getRealPath("") + File.separator + "tmp" + File.separator + "pedido-" + pedido.getIdPedido() + ".pdf");

            try (OutputStream outputStream = new FileOutputStream(file)) {
                Document document = new Document(PageSize.LETTER);
                PdfWriter writer = PdfWriter.getInstance(document, outputStream);
                document.open();
                InputStream is = new ByteArrayInputStream(htmlFact.getBytes());
                XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
                document.close();
            }

            InputStream in = new FileInputStream(file);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=pedido" + pedido.getIdPedido() + ".pdf");
            ServletOutputStream out = response.getOutputStream();

            byte[] outputByte = new byte[4096];
            //copy binary content to output stream
            while (in.read(outputByte, 0, 4096) != -1) {
                out.write(outputByte, 0, 4096);
            }
            in.close();
            out.flush();
            out.close();
            file.delete();
        }
        return null;
    }
}
