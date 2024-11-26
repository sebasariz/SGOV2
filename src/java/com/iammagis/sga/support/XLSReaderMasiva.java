package com.iammagis.sga.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class XLSReaderMasiva {
   static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
   static SimpleDateFormat horaDateFormat = new SimpleDateFormat("hh:mm");
   static NumberFormat numberFormat = new DecimalFormat("##0.##");

   public static JSONObject getFromXLSAndCreateFabricantes(File file) throws IOException, InvalidFormatException, ParseException, JSONException {
      JSONObject jSONObject = new JSONObject();
      if (file == null) {
         return null;
      } else {
         Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
         int numeropaginas = workbook.getNumberOfSheets();
         System.out.println("numero de paginas: " + numeropaginas);
         Sheet sheet = workbook.getSheetAt(0);
         JSONArray arrayDatos = new JSONArray();

         for(int i = 1; i <= sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            JSONObject jSONObjectProducto = new JSONObject();
            if (row.getCell(0) == null) {
               System.out.println("Termine por null");
               return jSONObject;
            }

            if (row.getCell(0).getCellType() == 1) {
               jSONObjectProducto.put("identificador", (Object)row.getCell(0).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error identificador formato, linea: " + i));
            }

            if (row.getCell(1).getCellType() == 1) {
               jSONObjectProducto.put("nombre", (Object)row.getCell(1).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error nombre formato, linea: " + i));
            }

            if (row.getCell(2).getCellType() == 1) {
               jSONObjectProducto.put("contacto", (Object)row.getCell(1).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error contacto formato, linea: " + i));
            }

            if (row.getCell(3).getCellType() == 1) {
               jSONObjectProducto.put("direccion", (Object)row.getCell(3).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error direccion formato, linea: " + i));
            }

            if (row.getCell(4).getCellType() == 1) {
               jSONObjectProducto.put("ciudad", (Object)row.getCell(4).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error ciudad formato, linea: " + i));
            }

            if (row.getCell(5).getCellType() == 1) {
               jSONObjectProducto.put("telefono", (Object)row.getCell(5).getStringCellValue());
            } else if (row.getCell(5).getCellType() == 0) {
               jSONObjectProducto.put("telefono", (Object)(row.getCell(5).getNumericCellValue() + ""));
            } else {
               jSONObject.put("error", (Object)("telefono error formato, linea: " + i));
            }

            arrayDatos.put((Object)jSONObjectProducto);
         }

         jSONObject.put("arrayDatos", (Object)arrayDatos);
         return jSONObject;
      }
   }

   public static JSONObject getFromXLSAndCreateClientes(File file) throws IOException, InvalidFormatException, ParseException, JSONException {
      JSONObject jSONObject = new JSONObject();
      if (file == null) {
         return null;
      } else {
         Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
         int numeropaginas = workbook.getNumberOfSheets();
         System.out.println("numero de paginas: " + numeropaginas);
         Sheet sheet = workbook.getSheetAt(0);
         JSONArray arrayDatos = new JSONArray();
         System.out.println("Numero de lineas : " + sheet.getLastRowNum());

         for(int i = 1; i <= sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            JSONObject jSONObjectProducto = new JSONObject();
            if (row.getCell(0) == null) {
               System.out.println("Termine por null, linea: " + i);
               jSONObject.put("arrayDatos", (Object)arrayDatos);
               return jSONObject;
            }

            if (row.getCell(0).getCellType() == 1) {
               jSONObjectProducto.put("codigo", (Object)row.getCell(0).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error codigo formato, linea: " + i));
            }

            if (row.getCell(1).getCellType() == 1) {
               jSONObjectProducto.put("nombre", (Object)row.getCell(1).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error nombre formato, linea: " + i));
            }

            if (row.getCell(2).getCellType() == 1) {
               jSONObjectProducto.put("nit", (Object)row.getCell(2).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error nit formato, linea: " + i));
            }

            if (row.getCell(3).getCellType() == 1) {
               jSONObjectProducto.put("direccion", (Object)row.getCell(3).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error direccion formato, linea: " + i));
            }

            if (row.getCell(4) != null && row.getCell(4).getCellType() == 1) {
               jSONObjectProducto.put("ciudad", (Object)row.getCell(4).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("ciudad error formato, linea: " + i));
            }

            if (row.getCell(5) != null && row.getCell(5).getCellType() == 1) {
               jSONObjectProducto.put("telefono", (Object)row.getCell(5).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("telefono error formato, linea: " + i));
            }

            arrayDatos.put((Object)jSONObjectProducto);
         }

         return jSONObject;
      }
   }

   public static JSONObject getFromXLSAndCreateProductos(File file) throws IOException, InvalidFormatException, ParseException, JSONException {
      JSONObject jSONObject = new JSONObject();
      if (file == null) {
         return null;
      } else {
         System.out.println("cargando productos");
         Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
         int numeropaginas = workbook.getNumberOfSheets();
         System.out.println("numero de paginas productos: " + numeropaginas);
         Sheet sheet = workbook.getSheetAt(0);
         JSONArray arrayDatos = new JSONArray();

         for(int i = 1; i <= sheet.getLastRowNum(); ++i) {
            try {
               Row row = sheet.getRow(i);
               JSONObject jSONObjectProducto = new JSONObject();
               if (row.getCell(0) == null) {
                  System.out.println("Termine por null: ");
                  jSONObject.put("arrayDatos", (Object)arrayDatos);
                  return jSONObject;
               }

               if (row.getCell(0).getCellType() == 1) {
                  System.out.println("row.getCell(0).getStringCellValue(): " + row.getCell(0).getStringCellValue());
                  jSONObjectProducto.put("referencia", (Object)row.getCell(0).getStringCellValue());
               } else if (row.getCell(0).getCellType() == 0) {
                  System.out.println("row.getCell(0).getStringCellValue(): " + row.getCell(0).getNumericCellValue());
                  jSONObjectProducto.put("referencia", (Object)numberFormat.format(row.getCell(0).getNumericCellValue()));
               } else {
                  jSONObject.put("error", (Object)("Error referencia formato, linea: " + i));
               }

               if (row.getCell(1).getCellType() == 1) {
                  jSONObjectProducto.put("nombre", (Object)row.getCell(1).getStringCellValue());
               } else if (row.getCell(1).getCellType() == 0) {
                  jSONObjectProducto.put("nombre", (Object)numberFormat.format(row.getCell(1).getNumericCellValue()));
               } else {
                  jSONObject.put("error", (Object)("Error nombre formato, linea: " + i));
               }

               if (row.getCell(2).getCellType() == 1) {
                  jSONObjectProducto.put("cantidad", Integer.parseInt(row.getCell(2).getStringCellValue()));
               } else if (row.getCell(3).getCellType() == 0) {
                  jSONObjectProducto.put("cantidad", (int)row.getCell(2).getNumericCellValue());
               } else {
                  jSONObject.put("error", (Object)("cantidad error formato, linea: " + i));
               }

               if (row.getCell(3).getCellType() == 1) {
                  jSONObjectProducto.put("valor", (Object)row.getCell(3).getStringCellValue());
               } else if (row.getCell(3).getCellType() == 0) {
                  System.out.println("row.getCell(3).getNumericCellValue(): " + row.getCell(3).getNumericCellValue());
                  jSONObjectProducto.put("valor", row.getCell(3).getNumericCellValue());
               } else {
                  jSONObject.put("error", (Object)("cantidad error formato, linea: " + i));
               }

               arrayDatos.put((Object)jSONObjectProducto);
            } catch (Exception var9) {
               var9.printStackTrace();
            }
         }

         System.out.println("arrayDatos: " + arrayDatos);
         jSONObject.put("arrayDatos", (Object)arrayDatos);
         System.out.println("jSONObject: " + jSONObject);
         return jSONObject;
      }
   }

   public static JSONObject getFromXLSAndCreateDeudores(File file) throws JSONException, IOException, InvalidFormatException {
      JSONObject jSONObject = new JSONObject();
      if (file == null) {
         return null;
      } else {
         Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
         int numeropaginas = workbook.getNumberOfSheets();
         Sheet sheet = workbook.getSheetAt(0);
         JSONArray arrayDatos = new JSONArray();

         for(int i = 1; i <= sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            JSONObject jSONObjectProducto = new JSONObject();
            if (row.getCell(0) == null) {
               return jSONObject;
            }

            if (row.getCell(0).getCellType() == 1) {
               jSONObjectProducto.put("identificacion", (Object)row.getCell(0).getStringCellValue());
            } else if (row.getCell(0).getCellType() == 0) {
               jSONObjectProducto.put("identificacion", (Object)numberFormat.format(row.getCell(0).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Error identificacion formato, linea: " + i));
            }

            if (row.getCell(1) != null && row.getCell(1).getCellType() == 1) {
               jSONObjectProducto.put("nombre", (Object)row.getCell(1).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error nombre formato, linea: " + i));
            }

            if (row.getCell(2) != null && row.getCell(2).getCellType() == 1) {
               jSONObjectProducto.put("representante_legal", (Object)row.getCell(2).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error nit formato, linea: " + i));
            }

            if (row.getCell(3) != null && row.getCell(3).getCellType() == 1) {
               jSONObjectProducto.put("contacto", (Object)row.getCell(3).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Error contacto formato, linea: " + i));
            }

            if (row.getCell(4) != null && row.getCell(4).getCellType() == 1) {
               jSONObjectProducto.put("obligacion", (Object)row.getCell(4).getStringCellValue());
            } else if (row.getCell(4).getCellType() == 0) {
               jSONObjectProducto.put("obligacion", (Object)numberFormat.format(row.getCell(4).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("obligacion error formato, linea: " + i));
            }

            long fechaVencimiento;
            if (row.getCell(5) != null && row.getCell(5).getCellType() == 1) {
               jSONObjectProducto.put("fecha_emision", (Object)row.getCell(5).getStringCellValue());
            } else if (row.getCell(5) != null && row.getCell(5).getCellType() == 2) {
               fechaVencimiento = (long)(row.getCell(5).getNumericCellValue() - 25569.0D) * 86400L;
               jSONObjectProducto.put("fecha_emision", fechaVencimiento);
            } else if (row.getCell(5) != null && row.getCell(5).getCellType() == 0) {
               fechaVencimiento = (long)(row.getCell(5).getNumericCellValue() - 25569.0D) * 86400L;
               jSONObjectProducto.put("fecha_emision", fechaVencimiento);
            } else {
               jSONObject.put("error", (Object)("Fecha emisión error formato, linea: " + i));
            }

            if (row.getCell(6) != null && row.getCell(6).getCellType() == 1) {
               jSONObjectProducto.put("fecha_vencimiento", (Object)row.getCell(6).getStringCellValue());
            } else if (row.getCell(5) != null && row.getCell(6).getCellType() == 2) {
               fechaVencimiento = (long)(row.getCell(6).getNumericCellValue() - 25569.0D) * 86400L;
               jSONObjectProducto.put("fecha_vencimiento", fechaVencimiento);
            } else if (row.getCell(5) != null && row.getCell(6).getCellType() == 0) {
               fechaVencimiento = (long)(row.getCell(6).getNumericCellValue() - 25569.0D) * 86400L;
               jSONObjectProducto.put("fecha_vencimiento", fechaVencimiento);
            } else {
               jSONObject.put("error", (Object)("Fecha vencimiento error formato, linea: " + i));
            }

            if (row.getCell(7) != null && row.getCell(7).getCellType() == 1) {
               jSONObjectProducto.put("valor", (Object)row.getCell(7).getStringCellValue());
            } else if (row.getCell(7).getCellType() == 0) {
               jSONObjectProducto.put("valor", (Object)numberFormat.format(row.getCell(7).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Valor error formato, linea: " + i));
            }

            if (row.getCell(8) != null && row.getCell(8).getCellType() == 1) {
               jSONObjectProducto.put("direccion", (Object)row.getCell(8).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Dirección error formato, linea: " + i));
            }

            if (row.getCell(9) != null && row.getCell(9).getCellType() == 1) {
               jSONObjectProducto.put("ciudad", (Object)row.getCell(9).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Ciudad error formato, linea: " + i));
            }

            if (row.getCell(10) != null && row.getCell(10).getCellType() == 1) {
               jSONObjectProducto.put("telefono", (Object)row.getCell(10).getStringCellValue());
            } else if (row.getCell(10).getCellType() == 0) {
               jSONObjectProducto.put("telefono", (Object)numberFormat.format(row.getCell(10).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Telefono error formato, linea: " + i));
            }

            if (row.getCell(11) != null && row.getCell(11).getCellType() == 1) {
               jSONObjectProducto.put("correo", (Object)row.getCell(11).getStringCellValue());
            } else {
               jSONObject.put("error", (Object)("Correo error formato, linea: " + i));
            }

            arrayDatos.put((Object)jSONObjectProducto);
         }

         jSONObject.put("arrayDatos", (Object)arrayDatos);
         return jSONObject;
      }
   }

   public static JSONObject getFromXLSAndCreatePaquetes(File file) throws JSONException, FileNotFoundException, IOException, InvalidFormatException {
      JSONObject jSONObject = new JSONObject();
      if (file == null) {
         return null;
      } else {
         Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
         int numeropaginas = workbook.getNumberOfSheets();
         System.out.println("numero de paginas: " + numeropaginas);
         Sheet sheet = workbook.getSheetAt(0);
         JSONArray arrayDatos = new JSONArray();
         System.out.println("Numero de lineas : " + sheet.getLastRowNum());

         for(int i = 1; i <= sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            JSONObject jSONObjectProducto = new JSONObject();
            if (row.getCell(0) == null) {
               System.out.println("Termine por null, linea: " + i);
               jSONObject.put("arrayDatos", (Object)arrayDatos);
               return jSONObject;
            }

            if (row.getCell(0) != null && row.getCell(0).getCellType() == 1) {
               jSONObjectProducto.put("identificacion", (Object)row.getCell(0).getStringCellValue());
            } else if (row.getCell(0) != null && row.getCell(0).getCellType() == 0) {
               jSONObjectProducto.put("identificacion", (Object)numberFormat.format(row.getCell(0).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Error codigo formato, linea: " + i));
            }

            if (row.getCell(1) != null && row.getCell(1).getCellType() == 1) {
               jSONObjectProducto.put("nombre", (Object)row.getCell(1).getStringCellValue());
            } else if (row.getCell(1) != null && row.getCell(1).getCellType() == 0) {
               jSONObjectProducto.put("nombre", (Object)numberFormat.format(row.getCell(1).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Error nombre formato, linea: " + i));
            }

            if (row.getCell(2) != null && row.getCell(2).getCellType() == 1) {
               jSONObjectProducto.put("correos", (Object)row.getCell(2).getStringCellValue());
            } else if (row.getCell(2) != null && row.getCell(2).getCellType() == 0) {
               jSONObjectProducto.put("correos", (Object)numberFormat.format(row.getCell(2).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Error nit formato, linea: " + i));
            }

            if (row.getCell(3) != null && row.getCell(3).getCellType() == 1) {
               jSONObjectProducto.put("direccion_entrega", (Object)row.getCell(3).getStringCellValue());
            } else if (row.getCell(3) != null && row.getCell(3).getCellType() == 0) {
               jSONObjectProducto.put("direccion_entrega", (Object)numberFormat.format(row.getCell(3).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Error direccion formato, linea: " + i));
            }

            if (row.getCell(4) != null && row.getCell(4).getCellType() == 1) {
               jSONObjectProducto.put("descripcion", (Object)row.getCell(4).getStringCellValue());
            } else if (row.getCell(4) != null && row.getCell(4).getCellType() == 0) {
               jSONObjectProducto.put("descripcion", (Object)numberFormat.format(row.getCell(4).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("ciudad error formato, linea: " + i));
            }

            if (row.getCell(5) != null && row.getCell(5).getCellType() == 1) {
               jSONObjectProducto.put("largo", (Object)row.getCell(5).getStringCellValue());
            } else if (row.getCell(5) != null && row.getCell(5).getCellType() == 0) {
               jSONObjectProducto.put("largo", (Object)numberFormat.format(row.getCell(5).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("telefono error formato, linea: " + i));
            }

            if (row.getCell(6) != null && row.getCell(6).getCellType() == 1) {
               jSONObjectProducto.put("ancho", (Object)row.getCell(6).getStringCellValue());
            } else if (row.getCell(6) != null && row.getCell(6).getCellType() == 0) {
               jSONObjectProducto.put("ancho", (Object)numberFormat.format(row.getCell(6).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("telefono error formato, linea: " + i));
            }

            if (row.getCell(7) != null && row.getCell(7).getCellType() == 1) {
               jSONObjectProducto.put("alto", (Object)row.getCell(7).getStringCellValue());
            } else if (row.getCell(7) != null && row.getCell(7).getCellType() == 0) {
               jSONObjectProducto.put("alto", (Object)numberFormat.format(row.getCell(7).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("telefono error formato, linea: " + i));
            }

            if (row.getCell(8) != null && row.getCell(8).getCellType() == 1) {
               jSONObjectProducto.put("peso", (Object)row.getCell(8).getStringCellValue());
            } else if (row.getCell(8) != null && row.getCell(8).getCellType() == 0) {
               jSONObjectProducto.put("peso", (Object)numberFormat.format(row.getCell(8).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("telefono error formato, linea: " + i));
            }

            arrayDatos.put((Object)jSONObjectProducto);
         }

         jSONObject.put("arrayDatos", (Object)arrayDatos);
         return jSONObject;
      }
   }

   public static JSONObject getFromXLSAndDirecciones(File file) throws JSONException, FileNotFoundException, IOException, InvalidFormatException {
      JSONObject jSONObject = new JSONObject();
      if (file == null) {
         return null;
      } else {
         Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
         int numeropaginas = workbook.getNumberOfSheets();
         System.out.println("numero de paginas: " + numeropaginas);
         Sheet sheet = workbook.getSheetAt(0);
         JSONArray arrayDatos = new JSONArray();
         System.out.println("Numero de lineas : " + sheet.getLastRowNum());

         for(int i = 1; i <= sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            JSONObject jSONObjectProducto = new JSONObject();
            if (row.getCell(0) == null) {
               System.out.println("Termine por null, linea: " + i);
               jSONObject.put("arrayDatos", (Object)arrayDatos);
               return jSONObject;
            }

            if (row.getCell(0) != null && row.getCell(0).getCellType() == 1) {
               jSONObjectProducto.put("guia", (Object)row.getCell(0).getStringCellValue());
            } else if (row.getCell(0) != null && row.getCell(0).getCellType() == 0) {
               jSONObjectProducto.put("guia", (Object)numberFormat.format(row.getCell(0).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Error codigo formato, linea: " + i));
            }

            if (row.getCell(1) != null && row.getCell(1).getCellType() == 1) {
               jSONObjectProducto.put("direccion", (Object)row.getCell(1).getStringCellValue());
            } else if (row.getCell(1) != null && row.getCell(1).getCellType() == 0) {
               jSONObjectProducto.put("direccion", (Object)numberFormat.format(row.getCell(1).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Error nombre formato, linea: " + i));
            }

            if (row.getCell(2) != null && row.getCell(2).getCellType() == 1) {
               jSONObjectProducto.put("ciudad", (Object)row.getCell(2).getStringCellValue());
            } else if (row.getCell(2) != null && row.getCell(2).getCellType() == 0) {
               jSONObjectProducto.put("ciudad", (Object)numberFormat.format(row.getCell(2).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Error nit formato, linea: " + i));
            }

            if (row.getCell(3) != null && row.getCell(3).getCellType() == 1) {
               jSONObjectProducto.put("departamento", (Object)row.getCell(3).getStringCellValue());
            } else if (row.getCell(3) != null && row.getCell(3).getCellType() == 0) {
               jSONObjectProducto.put("departamento", (Object)numberFormat.format(row.getCell(3).getNumericCellValue()));
            } else {
               jSONObject.put("error", (Object)("Error direccion formato, linea: " + i));
            }

            arrayDatos.put((Object)jSONObjectProducto);
         }

         jSONObject.put("arrayDatos", (Object)arrayDatos);
         return jSONObject;
      }
   }
}
