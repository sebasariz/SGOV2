package com.iammagis.sga.support;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GeneratePDF {
   public static void main(String[] args) throws DocumentException, IOException {
      String text = "<html>     <body>        <table border=\"1\" style=\"width: 400px;  border-collapse: collapse;\">            <tr>                <td style=\"background-color: gray; color:white;\">Codigo</td>                <td style=\"background-color: gray; color:white;\">Nit</td>                <td style=\"background-color: gray; color:white;\">Empresa</td>            </tr>            <tr>                <td>0001</td>                <td>900361103-3</td>                <td>Iam Magis S.A.S.</td>            </tr>        </table>        <table border=\"1\" style=\"width: 400px;  border-collapse: collapse;\">             <tr>                <td colspan=\"2\" style=\"background-color: gray; color:white;\">Direccion:</td>                <td style=\"background-color: gray; color:white;\">Telefono:</td>            </tr>            <tr>                <td colspan=\"2\">Cll 32e #80a - 57 int 201</td>                <td>3006162540</td>            </tr>            <tr>                <td style=\"background-color: gray; color:white;\">N. pedido:</td>                <td style=\"background-color: gray; color:white;\">Fecha creación:</td>                <td style=\"background-color: gray; color:white;\">Fecha despacho:</td>            </tr>            <tr>                <td>185</td>                <td>04/11/2019</td>                <td>05/11/2019</td>            </tr>             <tr>                <td colspan=\"3\" style=\"text-align: center; background-color: gray; color:white;\">Productos</td>            </tr>        </table>        <table border=\"1\" style=\"width: 400px;  border-collapse: collapse;\">            <tr>                <td style=\"background-color: gray; color:white;\">Referencia:</td>                <td style=\"background-color: gray; color:white;\">Item:</td>                <td style=\"background-color: gray; color:white;\">Cantidad:</td>                <td style=\"background-color: gray; color:white;\">Valor:</td>            </tr>             <tr>                <td>1001</td>                <td>Disco 1</td>                <td>2</td>                <td>$226.000</td>            </tr>             <tr>                <td>3026</td>                <td>Pastas 34</td>                <td>12</td>                <td>$1200.200</td>            </tr>        </table>        <table border=\"1\" style=\"width: 400px;  border-collapse: collapse;\">            <tr>                <td style=\"background-color: gray; color:white;\">Subtotal:</td>                <td style=\"background-color: gray; color:white;\">Iva:</td>                <td style=\"background-color: gray; color:white;\">Total:</td>            </tr>            <tr>                <td>$529.000</td>                <td>$118.000</td>                <td>$647.000</td>            </tr>        </table>    </body></html>";
      File fileIn = new File("test.pdf");
      generatePDF(text, fileIn);
   }

   public static File generatePDF(String htmlFact, File fileIn) throws DocumentException, IOException {
      FileOutputStream outputStream = new FileOutputStream(fileIn);

      try {
         Document document = new Document(PageSize.LETTER);
         PdfWriter writer = PdfWriter.getInstance(document, outputStream);
         document.open();
         InputStream is = new ByteArrayInputStream(htmlFact.getBytes());
         XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
         document.close();
      } catch (Throwable var7) {
         try {
            outputStream.close();
         } catch (Throwable var6) {
            var7.addSuppressed(var6);
         }

         throw var7;
      }

      outputStream.close();
      return fileIn;
   }
}
