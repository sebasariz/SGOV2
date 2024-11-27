/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.IOTDevice;
import com.iammagis.sga.support.PropertiesAcces;
import com.iammagis.sga.support.SessionControl;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author sebastianarizmendy
 */
public class IOTDeviceMongoController {
   PropertiesAcces propertiesAcces = new PropertiesAcces();
   Gson gson = new Gson();

   public int getNextID() {
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      DBCursor dBCursor = collection.find().sort(new BasicDBObject("idIOTDevice", -1)).limit(1);
      int id = 1;
      if (dBCursor.hasNext()) {
         DBObject dBObject = dBCursor.next();
         id = Integer.parseInt(dBObject.get("idIOTDevice").toString());
         ++id;
      } else {
         BasicDBObject index = new BasicDBObject("idIOTDevice", 1);
         collection.createIndex(index, "idIOTDevice");
         BasicDBObject index1 = new BasicDBObject("idEmpresa", 1);
         collection.createIndex(index1, "idEmpresa");
         BasicDBObject index2 = new BasicDBObject("tipo", 1);
         collection.createIndex(index2, "tipo");
         BasicDBObject index3 = new BasicDBObject("uuid", 1);
         collection.createIndex(index3, "uuid");
      }

      return id;
   }

   public IOTDevice findIOTDevice(int id) throws IOException {
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      BasicDBObject basicDBObject = new BasicDBObject();
      basicDBObject.append("idIOTDevice", id);
      DBObject dBObject = collection.findOne(basicDBObject);
      if (dBObject != null) {
         IOTDevice iotdevice = (IOTDevice)this.gson.fromJson(JSON.serialize(dBObject), IOTDevice.class);
         return iotdevice;
      } else {
         return null;
      }
   }

   public IOTDevice createIOTDevice(IOTDevice iotdevice) {
      iotdevice.setIdIOTDevice(this.getNextID());
      String jsonString = this.gson.toJson(iotdevice);
      DBObject iotdeviceMONGO = (DBObject)JSON.parse(jsonString);
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      collection.insert(new DBObject[]{iotdeviceMONGO});
      DBCollection collectionPosicion = database.getCollection("iotdevice");
      return iotdevice;
   }

   public IOTDevice editIOTDevice(IOTDevice iotdevice) {
      String jsonString = this.gson.toJson(iotdevice);
      DBObject iotdeviceMONGO = (DBObject)JSON.parse(jsonString);
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      collection.update(new BasicDBObject("idIOTDevice", iotdevice.getIdIOTDevice()), iotdeviceMONGO);
      return iotdevice;
   }

   public IOTDevice removeIOTDevice(IOTDevice iotdevice) {
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      collection.remove(new BasicDBObject("idIOTDevice", iotdevice.getIdIOTDevice()));
      return iotdevice;
   }

   public List<DBObject> getIOTDevices() throws IOException {
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      List<DBObject> dBObjects = collection.find().toArray();
      return dBObjects;
   }
   
   public List<DBObject> getIOTDevicesByTipo(String idTipoIOTDevice) throws IOException {
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      BasicDBObject basicDBObject = new BasicDBObject(); 
      basicDBObject.append("tipoString", idTipoIOTDevice);
      List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
      return dBObjects;
   }

   public List<DBObject> getIOTDevicesInstitucion(Integer idInstitucion) throws IOException {
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      BasicDBObject basicDBObject = new BasicDBObject();
      basicDBObject.append("idEmpresa", idInstitucion);
      List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
      return dBObjects;
   }

   public List<DBObject> getIOTDevicesInstitucionAndTipo(Integer idInstitucion, String idTipoIOTDevice) throws IOException {
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      BasicDBObject basicDBObject = new BasicDBObject();
      basicDBObject.append("idEmpresa", idInstitucion);
      basicDBObject.append("tipoString", idTipoIOTDevice);
      List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
      return dBObjects;
   }

   public IOTDevice findIOTDeviceByIdentificacion(String idEmpresa) {
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      BasicDBObject basicDBObject = new BasicDBObject();
      basicDBObject.append("idEmpresa", idEmpresa);
      DBObject dBObject = collection.findOne(basicDBObject);
      if (dBObject != null) {
         IOTDevice iotdevice = (IOTDevice)this.gson.fromJson(JSON.serialize(dBObject), IOTDevice.class);
         return iotdevice;
      } else {
         return null;
      }
   }

   public IOTDevice findIOTDeviceByUuid(String uuid) {
      MongoClient mongoClient = SessionControl.getMongoClient();
      DB database = mongoClient.getDB(this.propertiesAcces.mongoDB);
      DBCollection collection = database.getCollection("iotdevice");
      BasicDBObject basicDBObject = new BasicDBObject();
      basicDBObject.append("uuid", uuid);
      DBObject dBObject = collection.findOne(basicDBObject);
      if (dBObject != null) {
         IOTDevice iotdevice = (IOTDevice)this.gson.fromJson(JSON.serialize(dBObject), IOTDevice.class);
         return iotdevice;
      } else {
         return null;
      }
   }

}
