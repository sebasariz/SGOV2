package com.iammagis.sga.iot;

import com.iammagis.sga.support.Correo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

public class TestingSendmail extends Action {
   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("status", (Object)"200");
      jSONObject.put("message", (Object)"send email");
      ArrayList<String> correos = new ArrayList();
      correos.add("sebasariz@iammagis.com");
      Correo correo = new Correo("Hola bebe", "que mas pues", correos);
      correo.run();
      response.setContentType("application/json");
      PrintWriter printWriter = response.getWriter();
      printWriter.print(jSONObject);
      return null;
   }
}
