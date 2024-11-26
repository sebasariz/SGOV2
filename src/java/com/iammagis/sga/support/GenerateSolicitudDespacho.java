package com.iammagis.sga.support;

import com.iammagis.sga.mongo.beans.Pedido;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GenerateSolicitudDespacho {
   static DecimalFormat decimalFormat = new DecimalFormat("###,##0");
   static SimpleDateFormat simpleDateFormatwithTime = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
   static PropertiesAcces propertiesAcces = new PropertiesAcces();

   public static String generateHTMLPedido(Pedido pedido) throws JSONException {
      String fechaDespacho = "N/A";
      if (pedido.getFechaDespacho() != 0L) {
         fechaDespacho = simpleDateFormatwithTime.format(new Date(pedido.getFechaDespacho()));
      }

      String usuarioVendedor = "";
      if (pedido.getUsuarioCreador() != null) {
         usuarioVendedor = pedido.getUsuarioCreador().getNombre() + " " + pedido.getUsuarioCreador().getApellidos();
      }

      String htmlFact = "<html>     <body>        <table border=\"1\" style=\"width: 100%;  border-collapse: collapse;\">            <tr>                <td style=\"background-color: gray; color:white;\">Codigo</td>                <td style=\"background-color: gray; color:white;\">Nit</td>                <td style=\"background-color: gray; color:white;\">Empresa</td>            </tr>            <tr>                <td>" + pedido.getEmpresaCliente().getCodigo() + "</td>                <td>" + pedido.getEmpresaCliente().getNit() + "</td>                <td>" + pedido.getEmpresaCliente().getNombre() + "</td>            </tr>        </table>        <table border=\"1\" style=\"width: 100%;  border-collapse: collapse;\">             <tr>                <td style=\"background-color: gray; color:white;\">Vendedor:</td>                <td style=\"background-color: gray; color:white;\">Direccion:</td>                <td style=\"background-color: gray; color:white;\">Telefono:</td>            </tr>            <tr>                <td>" + usuarioVendedor + "</td>                <td>" + pedido.getEmpresaCliente().getDireccion() + "</td>                <td>" + pedido.getEmpresaCliente().getTelefono() + "</td>            </tr>            <tr>                <td style=\"background-color: gray; color:white;\">N. pedido:</td>                <td style=\"background-color: gray; color:white;\">Fecha creación:</td>                <td style=\"background-color: gray; color:white;\">Fecha despacho:</td>            </tr>            <tr>                <td>" + pedido.getIncremental() + "</td>                <td>" + simpleDateFormatwithTime.format(new Date(pedido.getFechaCreacion())) + "</td>                <td>" + fechaDespacho + "</td>            </tr>             <tr>                <td colspan=\"3\" style=\"text-align: center; background-color: gray; color:white;\">Productos</td>            </tr>        </table>        <table border=\"1\" style=\"width: 100%;  border-collapse: collapse;\">            <tr>                <td style=\"background-color: gray; color:white;\">Referencia</td>                <td style=\"background-color: gray; color:white;\">Item</td>                <td style=\"background-color: gray; color:white;\">Cant pedida</td>                <td style=\"background-color: gray; color:white;\">Cant despachada</td>                <td style=\"background-color: gray; color:white;\">Valor</td>            </tr> ";
      JSONArray jSONArray = new JSONArray(pedido.getArrayProductosSolicitadosString());
      double valorSubtotal = 0.0D;

      double valorVenta;
      for(int i = 0; i < jSONArray.length(); ++i) {
         JSONObject jSONObject = jSONArray.getJSONObject(i);
         valorVenta = 0.0D;
         if (jSONObject.has("valorVenta")) {
            valorVenta = jSONObject.getDouble("valorVenta");
         }

         if (jSONObject.has("empacados") && jSONObject.has("cantidad") && jSONObject.getInt("empacados") != 0) {
            valorVenta = valorVenta / (double)jSONObject.getInt("cantidad") * (double)jSONObject.getInt("empacados");
         }

         if (jSONObject.has("empacados")) {
            htmlFact = htmlFact + "            <tr>                <td>" + jSONObject.getString("referencia") + "</td>                <td style=\"padding: 5px;\">" + jSONObject.getString("nombre").split("\\(Ref:")[0] + "</td>                <td>" + jSONObject.getInt("cantidad") + "</td>                <td>" + jSONObject.getInt("empacados") + "</td>                <td> $ " + decimalFormat.format(valorVenta) + "</td>            </tr> ";
         } else {
            htmlFact = htmlFact + "            <tr>                <td>" + jSONObject.getString("referencia") + "</td>                <td style=\"padding: 5px;\">" + jSONObject.getString("nombre").split("\\(Ref:")[0] + "</td>                <td>" + jSONObject.getInt("cantidad") + "</td>                <td>0</td>                <td> $ " + decimalFormat.format(valorVenta) + "</td>            </tr> ";
         }

         valorSubtotal += valorVenta;
      }

      double iva = valorSubtotal * propertiesAcces.IVA;
      valorVenta = iva + valorSubtotal;
      htmlFact = htmlFact + "   </table>        <table border=\"1\" style=\"width: 100%;  border-collapse: collapse;\">            <tr>                <td style=\"background-color: gray; color:white;\">Subtotal:</td>                <td style=\"background-color: gray; color:white;\">Iva:</td>                <td style=\"background-color: gray; color:white;\">Total:</td>            </tr>            <tr>                <td>$" + decimalFormat.format(valorSubtotal) + "</td>                <td>$" + decimalFormat.format(iva) + "</td>                <td>$" + decimalFormat.format(valorVenta) + "</td>            </tr>        </table>";
      htmlFact = htmlFact + "       <table border=\"1\" style=\"width: 100%;  border-collapse: collapse;\">             <tr>                 <td colspan=\"3\" style=\"text-align: center; background-color: gray; color:white;\">Nota</td>             </tr>             <tr>                 <td colspan=\"3\" >" + pedido.getNota() + "</td>         </tr>             <tr>                 <td colspan=\"3\" style=\"text-align: center; background-color: gray; color:white;\">Keys terceros</td>             </tr>             <tr>                 <td colspan=\"3\">" + pedido.getIdTercero() + "</td>         </tr>        </table>    </body></html>";
      return htmlFact;
   }

   public static void sendMail(Pedido pedido, String context) throws JSONException, FileNotFoundException, DocumentException, IOException {
      String getPedidoString = generateHTMLPedido(pedido);
      File file = new File(context + File.separator + "tmp" + File.separator + "pedido-" + pedido.getIdPedido() + ".pdf");
      FileOutputStream outputStream = new FileOutputStream(file);

      try {
         Document document = new Document(PageSize.LETTER);
         PdfWriter writer = PdfWriter.getInstance(document, outputStream);
         document.open();
         InputStream is = new ByteArrayInputStream(getPedidoString.getBytes());
         XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, Charset.forName("UTF-8"));
         document.close();
      } catch (Throwable var9) {
         try {
            outputStream.close();
         } catch (Throwable var8) {
            var9.addSuppressed(var8);
         }

         throw var9;
      }

      outputStream.close();
      ArrayList correos = new ArrayList();
      String[] correosArray = pedido.getEmmpresaCreadora().getCorreodespacho().split(",");

      for(int i = 0; i < correosArray.length; ++i) {
         correos.add(correosArray[i]);
      }

      Correo correo = new Correo("Nuevo pedido solicitado", getPedidoString, correos, file);
      correo.start();
   }
}
