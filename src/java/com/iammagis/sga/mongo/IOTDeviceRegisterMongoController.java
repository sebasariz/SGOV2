/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.IOTDeviceRegister;
import com.iammagis.sga.support.PropertiesAcces;
import com.iammagis.sga.support.SessionControl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author sebastianarizmendy
 */
public class IOTDeviceRegisterMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();
    Calendar calendar = Calendar.getInstance();
    long start;

    public IOTDeviceRegisterMongoController() {
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,
                calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE,
                calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND,
                calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
                calendar.getActualMinimum(Calendar.MILLISECOND));
        start = calendar.getTimeInMillis();
    }

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
//        System.out.println("collection: " + "iotdeviceRegister-" + start);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idIotDeviceRegister", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idIotDeviceRegister").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idIotDeviceRegister", 1);
            collection.createIndex(index, "idIotDeviceRegister");

            BasicDBObject index1 = new BasicDBObject("fecha", 1);
            index1.append("idIotDevice", 1);
            collection.createIndex(index1, "fecha");

            BasicDBObject index2 = new BasicDBObject("idIotDevice", 1);
            collection.createIndex(index2, "idIotDevice");

        }
        return id;
    }

    public IOTDeviceRegister findIOTDeviceRegister(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idIotDeviceRegister", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            IOTDeviceRegister iotdeviceRegister = gson.fromJson(JSON.serialize(dBObject), IOTDeviceRegister.class);
            return iotdeviceRegister;
        } else {
            return null;
        }
    }

    public IOTDeviceRegister createIOTDeviceRegister(IOTDeviceRegister iotdeviceRegister) {
        iotdeviceRegister.setIdIotDeviceRegister(getNextID());
        String jsonString = gson.toJson(iotdeviceRegister);
        DBObject iotdeviceRegisterMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        WriteResult result = collection.insert(iotdeviceRegisterMONGO);
        DBCollection collectionPosicion = database.getCollection("iotdeviceRegister-" + start);
        return iotdeviceRegister;
    }

    public IOTDeviceRegister editIOTDeviceRegister(IOTDeviceRegister iotdeviceRegister) {
        String jsonString = gson.toJson(iotdeviceRegister);
        DBObject iotdeviceRegisterMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        collection.update(new BasicDBObject("idIotDeviceRegister", iotdeviceRegister.getIdIotDeviceRegister()), iotdeviceRegisterMONGO);
        return iotdeviceRegister;
    }

    public IOTDeviceRegister removeIOTDeviceRegister(IOTDeviceRegister iotdeviceRegister) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        collection.remove(new BasicDBObject("idIotDeviceRegister", iotdeviceRegister.getIdIotDeviceRegister()));
        return iotdeviceRegister;
    }
    
    public List<DBObject> removeRegisterFromIOTIDDevice(int idIotDevice) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,2020); 
        Calendar calendarInicial = calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance(); 
 
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,
                calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE,
                calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND,
                calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
                calendar.getActualMinimum(Calendar.MILLISECOND));
        start = calendar.getTimeInMillis();

        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        List<DBObject> dBObjects;
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idIotDevice", idIotDevice); 
//        System.out.println("basicDBObject: " + basicDBObject);
        dBObjects = collection.find(basicDBObject).toArray();

        while (calendarInicial.get(Calendar.MONTH) < calendarFinal.get(Calendar.MONTH)) {
            calendar=calendarInicial;
            calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY,calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE,calendar.getActualMinimum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND,calendar.getActualMinimum(Calendar.SECOND));
            calendar.set(Calendar.MILLISECOND,calendar.getActualMinimum(Calendar.MILLISECOND));
            start = calendar.getTimeInMillis(); 
            collection = database.getCollection("iotdeviceRegister-" + start); 
            collection.remove(basicDBObject); 
            calendarInicial.add(Calendar.MONTH, 1);
        }
        return dBObjects;
    }

    public List<DBObject> getIOTDeviceRegisters() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    }

    public List<DBObject> getIOTDeviceRegistersInstitucion(Integer idInstitucion) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idInstitucion);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public List<DBObject> getIOTDeviceRegistersInstitucionAndTipo(Integer idInstitucion, int idTipoIOTDeviceRegister) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idInstitucion);
        basicDBObject.append("tipo", idTipoIOTDeviceRegister);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public IOTDeviceRegister findIOTDeviceRegisterByIdentificacion(String idEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idEmpresa);
        IOTDeviceRegister iotdeviceRegister;
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            iotdeviceRegister = gson.fromJson(JSON.serialize(dBObject), IOTDeviceRegister.class);
        } else {
            return null;
        }
        return iotdeviceRegister;
    }

    public IOTDeviceRegister findIOTDeviceRegisterByUuid(String uuid) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("uuid", uuid);
        IOTDeviceRegister iotdeviceRegister;
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            iotdeviceRegister = gson.fromJson(JSON.serialize(dBObject), IOTDeviceRegister.class);
        } else {
            return null;
        }
        return iotdeviceRegister;
    }

    public List<DBObject> getRegistrosAndFecha(int idIotDevice, long fechaIicial, long fechaFinal) {
        Calendar calendarInicial = Calendar.getInstance();
        calendarInicial.setTimeInMillis(fechaIicial);

        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.setTimeInMillis(fechaFinal);

        calendar.setTimeInMillis(fechaIicial);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,
                calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE,
                calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND,
                calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
                calendar.getActualMinimum(Calendar.MILLISECOND));
        start = calendar.getTimeInMillis();

        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("iotdeviceRegister-" + start);
        List<DBObject> dBObjects;
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idIotDevice", idIotDevice);
        BasicDBList and = new BasicDBList();
        DBObject and1 = new BasicDBObject("fecha", new BasicDBObject("$gte", fechaIicial));
        DBObject and2 = new BasicDBObject("fecha", new BasicDBObject("$lte", fechaFinal));
        and.add(and1);
        and.add(and2);
        basicDBObject.append("$and", and);
//        System.out.println("basicDBObject: " + basicDBObject);
        dBObjects = collection.find(basicDBObject).toArray();

        while (calendarInicial.get(Calendar.MONTH) < calendarFinal.get(Calendar.MONTH)) {
            calendar.setTimeInMillis(fechaFinal);
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY,
                    calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE,
                    calendar.getActualMinimum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND,
                    calendar.getActualMinimum(Calendar.SECOND));
            calendar.set(Calendar.MILLISECOND,
                    calendar.getActualMinimum(Calendar.MILLISECOND));
            start = calendar.getTimeInMillis();

            collection = database.getCollection("iotdeviceRegister-" + start);
            List<DBObject> dBObjectsFin;
            dBObjectsFin = collection.find(basicDBObject).toArray();
            dBObjects.addAll(dBObjectsFin);
            
            calendarInicial.add(Calendar.MONTH, 1);
        }
        return dBObjects;
    }

}
