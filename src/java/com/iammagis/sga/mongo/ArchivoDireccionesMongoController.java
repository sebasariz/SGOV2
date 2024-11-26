/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.ArchivoDirecciones;
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
public class ArchivoDireccionesMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("archivoDirecciones");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idArchivoDirecciones", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idArchivoDirecciones").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idArchivoDirecciones", 1);
            collection.createIndex(index, "idArchivoDirecciones"); 

        }
        return id;
    }

    public ArchivoDirecciones findArchivoDirecciones(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("archivoDirecciones");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idArchivoDirecciones", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            ArchivoDirecciones archivoDirecciones = gson.fromJson(JSON.serialize(dBObject), ArchivoDirecciones.class);
            return archivoDirecciones;
        } else {
            return null;
        }
    }

    public ArchivoDirecciones createArchivoDirecciones(ArchivoDirecciones archivoDirecciones) {
        archivoDirecciones.setIdArchivoDirecciones(getNextID());
        String jsonString = gson.toJson(archivoDirecciones);
        DBObject archivoDireccionesMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("archivoDirecciones");
        WriteResult result = collection.insert(archivoDireccionesMONGO);
        DBCollection collectionPosicion = database.getCollection("archivoDirecciones");
        return archivoDirecciones;
    }

    public ArchivoDirecciones editArchivoDirecciones(ArchivoDirecciones archivoDirecciones) {
        String jsonString = gson.toJson(archivoDirecciones);
        DBObject archivoDireccionesMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("archivoDirecciones");
        collection.update(new BasicDBObject("idArchivoDirecciones", archivoDirecciones.getIdArchivoDirecciones()), archivoDireccionesMONGO);
        return archivoDirecciones;
    }

    public ArchivoDirecciones removeArchivoDirecciones(ArchivoDirecciones archivoDirecciones) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("archivoDirecciones");
        collection.remove(new BasicDBObject("idArchivoDirecciones", archivoDirecciones.getIdArchivoDirecciones()));
        return archivoDirecciones;
    }

    public List<DBObject> getArchivoDireccioness() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("archivoDirecciones");
        List<DBObject> dBObjects = collection.find().sort(new BasicDBObject("idArchivoDirecciones", -1)).toArray();
        return dBObjects;
    }
 

    public List<DBObject> getArchivoDireccionesFromEmpresa(int idEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("archivoDirecciones");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idEmpresa);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idArchivoDirecciones", -1)).toArray();
        return dBObjects;
    }

}
