package com.iammagis.sga.support;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeoApiContext.Builder;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleServices {
   static PropertiesAcces propertiesAcces = new PropertiesAcces();
   static GeoApiContext context;
   static List<String> keys;

   public static JSONObject getLatitudLongitud(String direccion) throws JSONException {
      JSONObject jSONObject = new JSONObject();

      try {
         System.out.println("direccion: " + direccion);
         GeocodingResult[] results = (GeocodingResult[])GeocodingApi.geocode(context, direccion).await();
         jSONObject.put("lat", results[0].geometry.location.lat);
         jSONObject.put("lng", results[0].geometry.location.lng);
         jSONObject.put("direccion", (Object)direccion);
      } catch (ApiException var3) {
         Logger.getLogger(GoogleServices.class.getName()).log(Level.SEVERE, (String)null, var3);
         jSONObject.put("error", (Object)var3.getMessage());
      } catch (InterruptedException var4) {
         Logger.getLogger(GoogleServices.class.getName()).log(Level.SEVERE, (String)null, var4);
         jSONObject.put("error", (Object)var4.getMessage());
      } catch (IOException var5) {
         Logger.getLogger(GoogleServices.class.getName()).log(Level.SEVERE, (String)null, var5);
         jSONObject.put("error", (Object)var5.getMessage());
      }

      return jSONObject;
   }

   public static JSONObject parseJavaGoogleFormatJSFormat(String jSONObjectIn) throws JSONException {
      keys = new ArrayList();
      String data = parseJavaGoogleFormatJavaScriptFormat(jSONObjectIn);

      for(int i = 0; i < keys.size(); ++i) {
         StringBuffer newStr = new StringBuffer();

         for(int w = 0; w < ((String)keys.get(i)).length(); ++w) {
            if (Character.isUpperCase(((String)keys.get(i)).charAt(w))) {
               newStr.append("_");
               newStr.append(Character.toLowerCase(((String)keys.get(i)).charAt(w)));
            } else {
               newStr.append(((String)keys.get(i)).charAt(w));
            }
         }

         data = data.replaceAll((String)keys.get(i), newStr.toString());
      }

      data = data.replaceAll("STREET_ADDRESS", "STREET_ADDRESS".toLowerCase());
      data = data.replaceAll("STREET_ADDRESS", "CONVENIENCE_STORE".toLowerCase());
      data = data.replaceAll("htmlInstructions", "instructions");
      data = data.replaceAll("in_seconds", "value");
      data = data.replaceAll("in_meters", "value");
      data = data.replaceAll("human_readable", "text");
      JSONObject jSONObject = new JSONObject(data);
      return jSONObject;
   }

   public static String parseJavaGoogleFormatJavaScriptFormat(String jSONObjectIn) throws JSONException {
      JSONObject jSONObject = new JSONObject(jSONObjectIn);
      Iterator iterator = jSONObject.keys();

      String data;
      while(iterator.hasNext()) {
         data = iterator.next().toString();
         getAllKeys(jSONObject.get(data));
         keys.add(data);
      }

      data = jSONObject.toString();
      return data;
   }

   public static void getAllKeys(Object object1) throws JSONException {
      if (object1 instanceof JSONObject) {
         parseJavaGoogleFormatJavaScriptFormat(object1.toString());
      } else if (object1 instanceof JSONArray) {
         JSONArray array = new JSONArray(object1.toString());

         for(int i = 0; i < array.length(); ++i) {
            getAllKeys(array.get(i));
         }
      }

   }

   public static void main(String[] args) throws JSONException {
      String jsonIN = "{\n    \"geocodedWaypoints\": [{\n            \"geocoderStatus\": \"ok\",\n            \"partialMatch\": false,\n            \"placeId\": \"ChIJbelerlIoRI4RLl6qgWMI7EU\",\n            \"types\": [\"STREET_ADDRESS\"]\n        }, {\n            \"geocoderStatus\": \"ok\",\n            \"partialMatch\": false,\n            \"placeId\": \"ChIJYSC_CJEpRI4RAmxmkIrvhaU\",\n            \"types\": [\"CONVENIENCE_STORE\", \"establishment\", \"food\", \"POINT_OF_INTEREST\", \"store\"]\n        }, {\n            \"geocoderStatus\": \"ok\",\n            \"partialMatch\": false,\n            \"placeId\": \"ChIJbelerlIoRI4RLl6qgWMI7EU\",\n            \"types\": [\"STREET_ADDRESS\"]\n        }],\n    \"routes\": [{\n            \"summary\": \"Av. 33\",\n            \"legs\": [{\n                    \"steps\": [{\n                            \"htmlInstructions\": \"Head \\u003cb\\u003esoutheast\\u003c/b\\u003e on \\u003cb\\u003eAv. 33\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003eCl. 37\\u003c/b\\u003e toward \\u003cb\\u003eAv. Carabobo\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003eCra 52\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 67,\n                                \"humanReadable\": \"67 m\"\n                            },\n                            \"duration\": {\n                                \"inSeconds\": 22,\n                                \"humanReadable\": \"1 min\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.2386108,\n                                \"lng\": -75.5738365\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.238381,\n                                \"lng\": -75.573274\n                            },\n                            \"polyline\": {\n                                \"points\": \"inae@noglMFQRo@Po@\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"Turn \\u003cb\\u003eright\\u003c/b\\u003e at the 2nd cross street onto \\u003cb\\u003eBolivar\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003eCra. 51\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 302,\n                                \"humanReadable\": \"0.3 km\"\n                            },\n                            \"maneuver\": \"turn-right\",\n                            \"duration\": {\n                                \"inSeconds\": 102,\n                                \"humanReadable\": \"2 mins\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.238381,\n                                \"lng\": -75.573274\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.235851900000001,\n                                \"lng\": -75.5742744\n                            },\n                            \"polyline\": {\n                                \"points\": \"{lae@|kglMj@Np@RRDZHrBn@rBl@`az^L`@J\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eCl. 34\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 166,\n                                \"humanReadable\": \"0.2 km\"\n                            },\n                            \"maneuver\": \"turn-right\",\n                            \"duration\": {\n                                \"inSeconds\": 52,\n                                \"humanReadable\": \"1 min\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.235851900000001,\n                                \"lng\": -75.5742744\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.2364349,\n                                \"lng\": -75.5756561\n                            },\n                            \"polyline\": {\n                                \"points\": \"a}`e@drglMSl@Ql@_@hA_@fAGNCHAF?D\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eCra. 54\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 287,\n                                \"humanReadable\": \"0.3 km\"\n                            },\n                            \"maneuver\": \"turn-right\",\n                            \"duration\": {\n                                \"inSeconds\": 68,\n                                \"humanReadable\": \"1 min\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.2364349,\n                                \"lng\": -75.5756561\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.2389449,\n                                \"lng\": -75.5754421\n                            },\n                            \"polyline\": {\n                                \"points\": \"u`ae@zzglMUDI@_@F_@fk@I@W?cBBG?SCYGc@IUESEIAs@oca}@U\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"At the roundabout, take the \\u003cb\\u003e4th\\u003c/b\\u003e exit onto \\u003cb\\u003eAv. 33\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 1613,\n                                \"humanReadable\": \"1.6 km\"\n                            },\n                            \"maneuver\": \"roundabout-right\",\n                            \"duration\": {\n                                \"inSeconds\": 208,\n                                \"humanReadable\": \"3 mins\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.2389449,\n                                \"lng\": -75.5754421\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.2393297,\n                                \"lng\": -75.5887652\n                            },\n                            \"polyline\": {\n                                \"points\": \"kpae@nyglMGMEIGMMEQEKAGAOCQDODKBIFGBEBEDIDGFKHAFGR?daf?F?J@F@B?B@B@bbb@bbb@@@@@@@bb@@@@@B?@\\\\@V@t@@b@?`@@^?^@n@@n@@V?X@`@@ZBrBBxA?bA?X?d@@p@@pANjJBlB@\\\\@`@@t@@~@BxC@b@?b@BvADxB@|@?X@\\\\@^?`A@bABpBBhC\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"Keep \\u003cb\\u003eleft\\u003c/b\\u003e to stay on \\u003cb\\u003eAv. 33\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 1547,\n                                \"humanReadable\": \"1.5 km\"\n                            },\n                            \"maneuver\": \"keep-left\",\n                            \"duration\": {\n                                \"inSeconds\": 221,\n                                \"humanReadable\": \"4 mins\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.2393297,\n                                \"lng\": -75.5887652\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.2388797,\n                                \"lng\": -75.60274620000001\n                            },\n                            \"polyline\": {\n                                \"points\": \"yrae@xljlM@|@@HBjA@\\\\?\\\\@N@N?\\\\@\\\\DfC?fb~BBvC@|ABdC@jB@d@@n@BtADdDDrC?j@?PDpCFbDJtI@`@@fA@dA@dABbA@~@B`A?zapalajafcj\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"At \\u003cb\\u003eGlorieta Sta. Gema\\u003c/b\\u003e, take the \\u003cb\\u003e2nd\\u003c/b\\u003e exit and stay on \\u003cb\\u003eAv. 33\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 642,\n                                \"humanReadable\": \"0.6 km\"\n                            },\n                            \"maneuver\": \"roundabout-right\",\n                            \"duration\": {\n                                \"inSeconds\": 117,\n                                \"humanReadable\": \"2 mins\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.2388797,\n                                \"lng\": -75.60274620000001\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.238543,\n                                \"lng\": -75.60835970000001\n                            },\n                            \"polyline\": {\n                                \"points\": \"_pae@ddmlMA?A??@A?A@A?A@C@A@aba@A@A@ab?@cdaf?dad@F?D@fbd@B@dbbbbb@bbd@B@jn@B@B@D@D?B?D@l@Bx@dn@p@BbA@bA@dADbADbAA|@A~@@b@@b@BjA?n@Bl@@T@vcpbv\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"At the roundabout, take the \\u003cb\\u003e4th\\u003c/b\\u003e exit onto \\u003cb\\u003eCra. 83\\u003c/b\\u003e\\u003cdiv style\\u003d\\\"font-size:0.9em\\\"\\u003eDestination will be on the right\\u003c/div\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 463,\n                                \"humanReadable\": \"0.5 km\"\n                            },\n                            \"maneuver\": \"roundabout-right\",\n                            \"duration\": {\n                                \"inSeconds\": 56,\n                                \"humanReadable\": \"1 min\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.238543,\n                                \"lng\": -75.60835970000001\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.2359201,\n                                \"lng\": -75.6058662\n                            },\n                            \"polyline\": {\n                                \"points\": \"{mae@fgnlMA??@A?A@A??@?@A??@A@?@?@?@?@?@?@?@@@?@@??@@@@??@@??@@?@??@@?@?@?@?@?@?@A@?@A@??A@??A@??A?A@??A?ANGJGHIJGJGNKNMNSPULUTg@Pg@FMn@qAP_@P]Ve@nulopqtopklerglctercra\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }],\n                    \"distance\": {\n                        \"inMeters\": 5087,\n                        \"humanReadable\": \"5.1 km\"\n                    },\n                    \"duration\": {\n                        \"inSeconds\": 846,\n                        \"humanReadable\": \"14 mins\"\n                    },\n                    \"startLocation\": {\n                        \"lat\": 6.2386108,\n                        \"lng\": -75.5738365\n                    },\n                    \"endLocation\": {\n                        \"lat\": 6.2359201,\n                        \"lng\": -75.6058662\n                    },\n                    \"startAddress\": \"Cra 52 #37-03, Medellín, Antioquia, Colombia\",\n                    \"endAddress\": \"Cra. 83 #32B-07, Medellín, Antioquia, Colombia\"\n                }, {\n                    \"steps\": [{\n                            \"htmlInstructions\": \"Head \\u003cb\\u003enorth\\u003c/b\\u003e on \\u003cb\\u003eCra. 83\\u003c/b\\u003e toward \\u003cb\\u003eCl. 32E\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 413,\n                                \"humanReadable\": \"0.4 km\"\n                            },\n                            \"duration\": {\n                                \"inSeconds\": 53,\n                                \"humanReadable\": \"1 min\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.2359201,\n                                \"lng\": -75.6058662\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.238408100000001,\n                                \"lng\": -75.60840150000001\n                            },\n                            \"polyline\": {\n                                \"points\": \"o}`e@twmlMS@SBUDMBSFMDQJUNQPMNOTWd@Q\\\\Q^o@pAGLQf@Uf@mtqtorolojkfkfm@yf\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"At the roundabout, take the \\u003cb\\u003e1st\\u003c/b\\u003e exit onto \\u003cb\\u003eAv. 33\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 577,\n                                \"humanReadable\": \"0.6 km\"\n                            },\n                            \"maneuver\": \"roundabout-right\",\n                            \"duration\": {\n                                \"inSeconds\": 101,\n                                \"humanReadable\": \"2 mins\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.238408100000001,\n                                \"lng\": -75.60840150000001\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.238588,\n                                \"lng\": -75.6032568\n                            },\n                            \"polyline\": {\n                                \"points\": \"amae@ngnlMCCACEAEUGQAWAUCm@?o@CkAAc@Ac@@_A@}@EcAEcAAeAAcACcAAq@bo?s@@u@?E@C@gbibidk\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"At \\u003cb\\u003eGlorieta Sta. Gema\\u003c/b\\u003e, take the \\u003cb\\u003e2nd\\u003c/b\\u003e exit and stay on \\u003cb\\u003eAv. 33\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 1299,\n                                \"humanReadable\": \"1.3 km\"\n                            },\n                            \"maneuver\": \"roundabout-right\",\n                            \"duration\": {\n                                \"inSeconds\": 201,\n                                \"humanReadable\": \"3 mins\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.238588,\n                                \"lng\": -75.6032568\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.239124599999999,\n                                \"lng\": -75.5916317\n                            },\n                            \"polyline\": {\n                                \"points\": \"enae@jgmlMBA@C@aba@C@A@C?cbe@E?E?G?EAGAEAEACCECCAACCCAECCAAy@A]AaAAcAA_ACaACkAGiA?a@MuIGaDGqC?SAi@Aq@IoFE_DAECqBAY?WCoDAYCwC\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"Keep \\u003cb\\u003eleft\\u003c/b\\u003e to stay on \\u003cb\\u003eAv. 33\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 1736,\n                                \"humanReadable\": \"1.7 km\"\n                            },\n                            \"maneuver\": \"keep-left\",\n                            \"duration\": {\n                                \"inSeconds\": 200,\n                                \"humanReadable\": \"3 mins\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.239124599999999,\n                                \"lng\": -75.5916317\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.2393689,\n                                \"lng\": -75.5760232\n                            },\n                            \"polyline\": {\n                                \"points\": \"oqae@t~jlMAm@Am@AiA?QCyAAS?qas?SCc@Ac@AiA?kawa}@?sBAqBA_AC}@?y@GmD?EEoCAWAgACeBC{cak?M?kaw?WAe@?g@GiDEkDAE?_@CeBAg@?{@A_@AgA?}A?}@?S?U?Y?]Am@Am@A]A]Ae@Ac@@S?ebg@ebidgde@A@CXa@\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }, {\n                            \"htmlInstructions\": \"At the roundabout, take the \\u003cb\\u003e2nd\\u003c/b\\u003e exit onto \\u003cb\\u003eAv. 33\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003eCl. 37\\u003c/b\\u003e\",\n                            \"distance\": {\n                                \"inMeters\": 279,\n                                \"humanReadable\": \"0.3 km\"\n                            },\n                            \"maneuver\": \"roundabout-right\",\n                            \"duration\": {\n                                \"inSeconds\": 68,\n                                \"humanReadable\": \"1 min\"\n                            },\n                            \"startLocation\": {\n                                \"lat\": 6.2393689,\n                                \"lng\": -75.5760232\n                            },\n                            \"endLocation\": {\n                                \"lat\": 6.2386108,\n                                \"lng\": -75.5738365\n                            },\n                            \"polyline\": {\n                                \"points\": \"asae@b}glMTIHEJGLIHMFMBIDM@G?E?EAIGMEIGMEi@?S?E@mh]DOFSn@_BRe@\"\n                            },\n                            \"travelMode\": \"driving\"\n                        }],\n                    \"distance\": {\n                        \"inMeters\": 4304,\n                        \"humanReadable\": \"4.3 km\"\n                    },\n                    \"duration\": {\n                        \"inSeconds\": 623,\n                        \"humanReadable\": \"10 mins\"\n                    },\n                    \"startLocation\": {\n                        \"lat\": 6.2359201,\n                        \"lng\": -75.6058662\n                    },\n                    \"endLocation\": {\n                        \"lat\": 6.2386108,\n                        \"lng\": -75.5738365\n                    },\n                    \"startAddress\": \"Cra. 83 #32B-07, Medellín, Antioquia, Colombia\",\n                    \"endAddress\": \"Cra 52 #37-03, Medellín, Antioquia, Colombia\"\n                }],\n            \"waypointOrder\": [0],\n            \"overviewPolyline\": {\n                \"points\": \"inae@noglMZaAPo@j@NdAXnCx@vFbBeBlFMf@_BVqCFyAUgB]aAWMWGMME]GWEa@Jc@Rc@\\\\Kh@B^pzhhd@Bt@DzCDvCFjEB|EBbCRxMFtDRhPFvGJ|GLfHLzNHvGJvJZlTHvGD`CAl@Il@E@olehgxfzjpphp\\\\B|@Bx@DNDtBBhCJfCC|BBfAH~DAh@bvc@C@cf?fdlldledenktqvo^Y`@i@b@}@Xu@jBuD\\\\e@f@a@^Q`@Kh@iras@i@Ha@J_@Pg@`@]d@i@bA{AfDc@|@a@h@w@h@g@HCCGEMg@GkCEoB?cB@}@EcAGiCEgCAq@bo@iB@INg@pwdyeykoqkg}EEaCKuC]kTM_JMqHIyJMkIGeDCuEGiHO{IM_LWwQCaG?aCEwCCcA?w@H]NSXa@titmvwjwfu?kiwmwe}@@SNm@v@sBRe@\"\n            },\n            \"bounds\": {\n                \"northeast\": {\n                    \"lat\": 6.2399776,\n                    \"lng\": -75.573274\n                },\n                \"southwest\": {\n                    \"lat\": 6.235851900000001,\n                    \"lng\": -75.6085591\n                }\n            },\n            \"copyrights\": \"Map data ©2020\",\n            \"warnings\": []\n        }]\n}\n";
      JSONObject jSONObject = parseJavaGoogleFormatJSFormat(jsonIN);
      System.out.println("jSONObject: " + jSONObject);
   }

   static {
      context = (new Builder()).apiKey(propertiesAcces.GOOGLE_FMC).build();
   }
}