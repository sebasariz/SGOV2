/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.ServicioRiesgoInterno;
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
public class ServicioRiesgosInternoMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInterno");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idServicioRiesgoInterno", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idServicioRiesgoInterno").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idServicioRiesgoInterno", 1);
            collection.createIndex(index, "idServicioRiesgoInterno");
        }
        return id;
    }

    public DBObject findServicioRiesgoInterno(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInterno");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idServicioRiesgoInterno", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        return dBObject;
//        if (dBObject != null) {
//            ServicioRiesgoInterno servicioRiesgoInterno = gson.fromJson(JSON.serialize(dBObject), ServicioRiesgoInterno.class);
//            return servicioRiesgoInterno;
//        } else {
//            return null;
//        }
    }

    public ServicioRiesgoInterno createServicioRiesgoInterno(ServicioRiesgoInterno servicioRiesgoInterno) {
        servicioRiesgoInterno.setIdServicioRiesgoInterno(getNextID());
        String jsonString = gson.toJson(servicioRiesgoInterno);
        DBObject servicioRiesgoInternoMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInterno");
        WriteResult result = collection.insert(servicioRiesgoInternoMONGO);
        DBCollection collectionPosicion = database.getCollection("servicioRiesgoInterno");
        return servicioRiesgoInterno;
    }

    public ServicioRiesgoInterno editServicioRiesgoInterno(ServicioRiesgoInterno servicioRiesgoInterno) {
        String jsonString = gson.toJson(servicioRiesgoInterno);
        DBObject servicioRiesgoInternoMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInterno");
        collection.update(new BasicDBObject("idServicioRiesgoInterno", servicioRiesgoInterno.getIdServicioRiesgoInterno()), servicioRiesgoInternoMONGO);
        return servicioRiesgoInterno;
    }

    public ServicioRiesgoInterno removeServicioRiesgoInterno(ServicioRiesgoInterno servicioRiesgoInterno) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInterno");
        collection.remove(new BasicDBObject("idServicioRiesgoInterno", servicioRiesgoInterno.getIdServicioRiesgoInterno()));
        return servicioRiesgoInterno;
    }

    public List<DBObject> getServicioRiesgoInternos() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInterno");
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    }

    public List<DBObject> getServicioRiesgoInternosByTipo(int tipoServicioRiesgoInterno) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInterno");
        List<DBObject> dBObjects = collection.find(new BasicDBObject("tipoServicioRiesgoInterno", tipoServicioRiesgoInterno)).toArray();
        return dBObjects;
    }

    public List<DBObject> getServicioRiesgoInternosByTipo(int tipoServicioRiesgoInterno, int idServicioRiesgoInternoCreadora) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInterno");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("tipoServicioRiesgoInterno", tipoServicioRiesgoInterno);
        basicDBObject.put("idServicioRiesgoInternoCreadora", idServicioRiesgoInternoCreadora);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public ServicioRiesgoInterno findServicioRiesgoInternoByNit(String nit, int servicioRiesgoInternoCreadora) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInterno");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("nit", nit);
        basicDBObject.append("idServicioRiesgoInternoCreadora", servicioRiesgoInternoCreadora);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            ServicioRiesgoInterno servicioRiesgoInterno = gson.fromJson(JSON.serialize(dBObject), ServicioRiesgoInterno.class);
            return servicioRiesgoInterno;
        } else {
            return null;
        }
    }

    public List<DBObject> getServicioRiesgoInternosByEpresaCreadora(int idServicioRiesgoInterno) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInterno");
        BasicDBObject basicDBObject = new BasicDBObject();
        System.out.println("idServicioRiesgoInterno: " + idServicioRiesgoInterno);
        basicDBObject.put("idEmpresa", idServicioRiesgoInterno);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

}
