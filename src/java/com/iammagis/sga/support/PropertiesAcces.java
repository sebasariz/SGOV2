package com.iammagis.sga.support;

import java.util.ResourceBundle;

public class PropertiesAcces {
   private static final String OPTION_FILE_NAME = "com/iammagis/sga/server";
   public String resourcesServer = "";
   public String mongouser = "";
   public String mongopassword = "";
   public int mongoPort = 30003;
   public String mongoserver = "";
   public String mongoDB = "";
   public String SMTP_HOST_NAME;
   public String SMTP_HOST_PORT;
   public String SMTP_AUTH_USER;
   public String SMTP_FROM_USER;
   public String SMTP_AUTH_PWD;
   public String GOOGLE_FMC;
   public double IVA;

   public PropertiesAcces() {
      ResourceBundle pe = ResourceBundle.getBundle("com/iammagis/sga/server");
      this.mongouser = pe.getString("mongouser").trim();
      this.mongopassword = pe.getString("mongopassword").trim();
      this.mongoPort = Integer.parseInt(pe.getString("mongoPort").trim());
      this.mongoserver = pe.getString("mongoserver").trim();
      this.mongoDB = pe.getString("mongoDB").trim();
      this.resourcesServer = pe.getString("resourcesServer").trim();
      this.SMTP_HOST_NAME = pe.getString("SMTP_HOST_NAME").trim();
      this.SMTP_HOST_PORT = pe.getString("SMTP_HOST_PORT").trim();
      this.SMTP_AUTH_USER = pe.getString("SMTP_AUTH_USER").trim();
      this.SMTP_FROM_USER = pe.getString("SMTP_FROM_USER").trim();
      this.SMTP_AUTH_PWD = pe.getString("SMTP_AUTH_PWD").trim();
      this.GOOGLE_FMC = pe.getString("GOOGLE_FMC").trim();
      this.IVA = Double.parseDouble(pe.getString("IVA"));
   }
}
