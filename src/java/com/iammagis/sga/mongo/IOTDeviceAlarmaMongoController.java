/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.IOTDeviceAlarma;
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
public class IOTDeviceAlarmaMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdevicealarma");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idIOTDeviceAlarma", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idIOTDeviceAlarma").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idIOTDeviceAlarma", 1);
            collection.createIndex(index, "idIOTDeviceAlarma");

            BasicDBObject index1 = new BasicDBObject("idIOTDevice", 1);
            collection.createIndex(index1, "idIOTDevice");

            BasicDBObject index2 = new BasicDBObject("tipo", 1);
            collection.createIndex(index2, "tipo");

            BasicDBObject index3 = new BasicDBObject("idEmpresa", 1);
            collection.createIndex(index3, "idEmpresa");

        }
        return id;
    }

    public IOTDeviceAlarma findIOTDeviceAlarma(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdevicealarma");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idIOTDeviceAlarma", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            IOTDeviceAlarma iotdevicealarma = gson.fromJson(JSON.serialize(dBObject), IOTDeviceAlarma.class);
            return iotdevicealarma;
        } else {
            return null;
        }
    }

    public IOTDeviceAlarma createIOTDeviceAlarma(IOTDeviceAlarma iotdevicealarma) {
        iotdevicealarma.setIdIOTDeviceAlarma(getNextID());
        String jsonString = gson.toJson(iotdevicealarma);
        DBObject iotdevicealarmaMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdevicealarma");
        WriteResult result = collection.insert(iotdevicealarmaMONGO);
        DBCollection collectionPosicion = database.getCollection("iotdevicealarma");
        return iotdevicealarma;
    }

    public IOTDeviceAlarma editIOTDeviceAlarma(IOTDeviceAlarma iotdevicealarma) {
        String jsonString = gson.toJson(iotdevicealarma);
        DBObject iotdevicealarmaMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdevicealarma");
        collection.update(new BasicDBObject("idIOTDeviceAlarma", iotdevicealarma.getIdIOTDeviceAlarma()), iotdevicealarmaMONGO);
        return iotdevicealarma;
    }

    public IOTDeviceAlarma removeIOTDeviceAlarma(IOTDeviceAlarma iotdevicealarma) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdevicealarma");
        System.out.println("search: "+new BasicDBObject("idIOTDeviceAlarma", iotdevicealarma.getIdIOTDeviceAlarma()));
        WriteResult result =collection.remove(new BasicDBObject("idIOTDeviceAlarma", iotdevicealarma.getIdIOTDeviceAlarma()));
        System.out.println("eliminando: "+result.toString());
        return iotdevicealarma;
    }

    public List<DBObject> getIOTDeviceAlarmasAlarma() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdevicealarma");
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    }

    public List<DBObject> getIOTDeviceAlarmasInstitucion(int idEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdevicealarma");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idEmpresa);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public List<DBObject> getIotDeviceAlarmsIotDevice(int idIOTDevice) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdevicealarma");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idIOTDevice", idIOTDevice);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

}
