package com.iammagis.sga.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class SendPushMessage {
   private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
   static final PropertiesAcces propertiesAcces = new PropertiesAcces();

   public static void sendPush(String tokenId, String title, String message, JSONObject jSONObjectData) {
      try {
         URL url = new URL("https://fcm.googleapis.com/fcm/send");
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.setUseCaches(false);
         conn.setDoInput(true);
         conn.setDoOutput(true);
         conn.setRequestMethod("POST");
         conn.setRequestProperty("Authorization", "key=" + propertiesAcces.GOOGLE_FMC);
         conn.setRequestProperty("Content-Type", "application/json");
         JSONObject infoJson = new JSONObject();
         infoJson.put("title", (Object)title);
         infoJson.put("body", (Object)message);
         JSONObject json = new JSONObject();
         json.put("to", (Object)tokenId.trim());
         json.put("notification", (Object)infoJson);
         json.put("data", (Object)jSONObjectData);
         json.put("priority", (Object)"high");
         System.out.println("json :" + json.toString());
         System.out.println("infoJson :" + infoJson.toString());
         OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
         wr.write(json.toString());
         wr.flush();
         int status = 0;
         if (null != conn) {
            status = conn.getResponseCode();
         }

         if (status != 0) {
            if (status == 200) {
               BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
               System.out.println("Android Notification Response : " + reader.readLine());
            } else if (status == 401) {
               System.out.println("Notification Response : TokenId : " + tokenId + " Error occurred :");
            } else if (status == 501) {
               System.out.println("Notification Response : [ errorCode=ServerError ] TokenId : " + tokenId);
            } else if (status == 503) {
               System.out.println("Notification Response : FCM Service is Unavailable  TokenId: " + tokenId);
            }
         }
      } catch (MalformedURLException var11) {
         System.out.println("Error occurred while sending push Notification!.." + var11.getMessage());
      } catch (Exception var12) {
         System.out.println("Reading URL, Error occurred while sending push Notification!.." + var12.getMessage());
      }

   }

   public static void send_FCM_NotificationMulti(JSONArray regId, String title, String message, JSONObject data) {
      try {
         URL url = new URL("https://fcm.googleapis.com/fcm/send");
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.setUseCaches(false);
         conn.setDoInput(true);
         conn.setDoOutput(true);
         conn.setRequestMethod("POST");
         conn.setRequestProperty("Authorization", "key=" + propertiesAcces.GOOGLE_FMC);
         conn.setRequestProperty("Content-Type", "application/json");
         JSONObject objData = null;
         JSONObject notif = null;
         data = new JSONObject();
         notif = new JSONObject();
         notif.put("title", (Object)title);
         notif.put("body", (Object)message);
         objData = new JSONObject();
         objData.put("registration_ids", (Object)regId);
         objData.put("data", (Object)data);
         objData.put("notification", (Object)notif);
         OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
         wr.write(objData.toString());
         wr.flush();
         int status = 0;
         if (null != conn) {
            status = conn.getResponseCode();
         }

         if (status != 0) {
            if (status == 200) {
               BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
               System.out.println("Android Notification Response : " + reader.readLine());
            } else if (status == 401) {
               System.out.println("Notification Response : TokenId : " + regId + " Error occurred     :");
            } else if (status == 501) {
               System.out.println("Notification Response : [ errorCode=ServerError ] TokenId:     " + regId);
            } else if (status == 503) {
               System.out.println("Notification Response : FCM Service is Unavailable  TokenId: " + regId);
            }
         }
      } catch (MalformedURLException var11) {
         System.out.println("Error occurred while sending push Notification!.." + var11.getMessage());
      } catch (IOException var12) {
         System.out.println("Reading URL, Error occurred while sending push Notification!.." + var12.getMessage());
      } catch (Exception var13) {
         System.out.println("Error occurred while sending push Notification!.." + var13.getMessage());
      }

   }

   public static void main(String[] args) {
      String idDevice = "f2MkRiYvSB-CkwyvaleHnm:APA91bFxPsoVuIXojs9fop2O_Ii5U6eqLbgXUbIk1WTcnf2v5mWR_eJ4nG686psZLfVdjMczHwH6yuGsrzKwAH6qZPim06mx6N-ShYvpEAxr5tgwQt1LYBUv0-uwtW7R5K6-7xqI9poo";
      sendPush(idDevice, "Titulo", "Mensaje", new JSONObject());
   }
}
