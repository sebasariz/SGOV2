package com.iammagis.sga.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Correo extends Thread {
   static PropertiesAcces propertiesAcess = new PropertiesAcces();
   private final String mensaje;
   private final List<String> emails;
   private final String subject;
   private boolean deleteAttach;
   private File attach = null;
   public static int inscripcionTemplate = 1;
   public static int recordarUsuarioTemplate = 2;
   public static int ftpTemplate = 3;
   public static int alarmaTemplate = 4;

   public static String getTemplate(int type, String context) {
      String template = "";

      try {
         BufferedReader in = null;
         if (type == inscripcionTemplate) {
            in = new BufferedReader(new FileReader(context + File.separator + "template/bienvenida_usuario.html"));
         } else if (type == recordarUsuarioTemplate) {
            in = new BufferedReader(new FileReader(context + File.separator + "template/recordar_usuario.html"));
         } else if (type == ftpTemplate) {
            in = new BufferedReader(new FileReader(context + File.separator + "template/ftp_recordatorio.html"));
         } else if (type == alarmaTemplate) {
            in = new BufferedReader(new FileReader(context + File.separator + "template/alarma.html"));
         }

         String str;
         while((str = in.readLine()) != null) {
            template = template + str;
         }

         in.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      return template;
   }

   public void run() {
      try {
         this.enviarAlertas();
      } catch (MessagingException var2) {
         var2.printStackTrace();
      } catch (UnsupportedEncodingException var3) {
         Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, (String)null, var3);
      }

   }

   public Correo(String subject, String mensaje, List<String> emails, File attFile) {
      this.mensaje = mensaje;
      this.emails = emails;
      this.subject = subject;
      this.attach = attFile;
   }

   public Correo(String subject, String mensaje, List<String> emails) {
      this.mensaje = mensaje;
      this.emails = emails;
      this.subject = subject;
   }

   private void enviarAlertas() throws MessagingException, UnsupportedEncodingException {
      System.setProperty("javax.net.debug", "ssl,handshake");
      System.setProperty("https.protocols", "TLSv1.3");
      System.setProperty("jdk.tls.client.cipherSuites", "TLS_AES_256_GCM_SHA384");
      System.out.println("enviando mail");
      Properties props = new Properties();
      props.put("mail.transport.protocol", "smtp");
      props.put("mail.smtps.host", propertiesAcess.SMTP_HOST_PORT);
      props.put("mail.smtp.ssl.trust", propertiesAcess.SMTP_HOST_PORT);
      props.put("mail.smtps.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.ssl.enable", "false");
      props.put("mail.debug", "true");
      props.put("mail.smtp.socketFactory.fallback", "true");
      props.put("mail.smtp.ssl.protocols", "TLSv1.3");
      Session mailSession = Session.getDefaultInstance(props);
      mailSession.setDebug(true);
      Transport transport = mailSession.getTransport();
      MimeMessage message = new MimeMessage(mailSession);
      message.setFrom(new InternetAddress(propertiesAcess.SMTP_AUTH_USER + "@iammagis.com", propertiesAcess.SMTP_FROM_USER));
      message.setSubject(this.subject);

      for(int i = 0; i < this.emails.size(); ++i) {
         message.addRecipient(RecipientType.TO, new InternetAddress((String)this.emails.get(i)));
      }

      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(this.mensaje, "text/html");
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);
      if (this.attach != null) {
         messageBodyPart = new MimeBodyPart();
         DataSource source = new FileDataSource(this.attach);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(this.attach.getName());
         multipart.addBodyPart(messageBodyPart);
      }

      System.out.println("User: " + propertiesAcess.SMTP_AUTH_USER);
      System.out.println("Pass: " + propertiesAcess.SMTP_AUTH_PWD);
      message.setContent(multipart);
      transport.connect(propertiesAcess.SMTP_HOST_NAME, Integer.parseInt(propertiesAcess.SMTP_HOST_PORT), propertiesAcess.SMTP_AUTH_USER, propertiesAcess.SMTP_AUTH_PWD);
      transport.sendMessage(message, message.getRecipients(RecipientType.TO));
      transport.close();
      if (this.attach != null && this.deleteAttach) {
         this.attach.delete();
      }

      System.out.println("Mensajes enviados");
   }

   public static void main(String[] args) throws UnsupportedEncodingException {
      ArrayList<String> mail = new ArrayList();
      mail.add("sebasariz@iammagis.com");
      Correo correo = new Correo("hola", "que mas", mail);

      try {
         correo.enviarAlertas();
      } catch (MessagingException var4) {
         Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, (String)null, var4);
      }

   }
}
