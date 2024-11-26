/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mobile;
 

import java.io.File;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class UploadMultipleServiceImage extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static String BASE_DIRECTORY = "";
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

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        // check if the http request is a multipart request
        // with other words check that the http request can have uploaded files
        JSONObject jSONObjectResult = new JSONObject();
        String htmlForm = "";
        int id = 0;
        if (isMultipart) {
//            System.out.println("entre por multipart");

            //  Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();

            //  Create a new file upload handler
            org.apache.commons.fileupload.servlet.ServletFileUpload servletFileUpload = new org.apache.commons.fileupload.servlet.ServletFileUpload(factory);

            // Set upload parameters
            // See Apache Commons FileUpload for more information
            // http://jakarta.apache.org/commons/fileupload/using.html
            servletFileUpload.setSizeMax(-1); 
            try {
                BASE_DIRECTORY = getServlet().getServletContext().getRealPath("") + "/images/";
                String directory = "";

                // Parse the request
                List items = servletFileUpload.parseRequest(request);

                // Process the uploaded items
                Iterator iter = items.iterator();

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    // the param tag directory is sent as a request parameter to
                    // the server
                    // check if the upload directory is available
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        if (name.equalsIgnoreCase("id")) {
                            id = Integer.parseInt(item.getString());
                        }
                    } else {
                        //System.out.println("entrando por file");
                        String fileName = System.currentTimeMillis() + "-" + URLDecoder.decode(item.getName());
                        fileName = stripAccents(fileName).replaceAll(" ", "").replaceAll("\\s", "");
                       
                        File file = new File(directory, fileName);
                        file = new File(BASE_DIRECTORY, file.getPath());

                        // retrieve the parent file for creating the directories
                        File parentFile = file.getParentFile();

                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
//                        System.out.println("file: " + file.getAbsolutePath());
                        // writes the file to the filesystem
                        item.write(file); 
                    }
                }

                jSONObjectResult.put("estado", "ok");
                response.setStatus(HttpServletResponse.SC_ACCEPTED);

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                try {
                    jSONObjectResult.put("estado", "error");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(UploadMultipleServiceImage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            System.out.println("no es multipart");
            try {
                jSONObjectResult.put("estado", "error");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } catch (JSONException ex) {
                ex.printStackTrace();
                Logger.getLogger(UploadMultipleServiceImage.class.getName()).log(Level.SEVERE, null, ex);
            }

        } 
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObjectResult);
        return null;
    }
    private static final String ORIGINAL
            = "ÃÃ¡Ã‰Ã©ÃÃ­Ã“Ã³ÃšÃºÃ‘Ã±ÃœÃ¼";
    private static final String REPLACEMENT
            = "AaEeIiOoUuNnUu";

    private String stripAccents(String str) {
        if (str == null) {
            return null;
        }
        char[] array = str.toCharArray();
        for (int index = 0; index < array.length; index++) {
            int pos = ORIGINAL.indexOf(array[index]);
            if (pos > -1) {
                array[index] = REPLACEMENT.charAt(pos);
            }
        }
        return new String(array);
    }
}