/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Modulo;
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
public class ModuloMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("modulo");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idModulo", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idModulo").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idModulo", 1);
            collection.createIndex(index, "idModulo");
        }
        return id;
    }

    public Modulo findModulo(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("modulo");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idModulo", id);

        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Modulo modulo = gson.fromJson(JSON.serialize(dBObject), Modulo.class);
            return modulo;
        } else {
            return null;
        }
    }
 
    public Modulo createModulo(Modulo modulo) {
        modulo.setIdModulo(getNextID());
        String jsonString = gson.toJson(modulo);
        DBObject moduloMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("modulo");
        WriteResult result = collection.insert(moduloMONGO);
        DBCollection collectionPosicion = database.getCollection("modulo");
        return modulo;
    }

    public Modulo editModulo(Modulo modulo) {
        String jsonString = gson.toJson(modulo);
        DBObject moduloMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("modulo");
        collection.update(new BasicDBObject("idModulo", modulo.getIdModulo()), moduloMONGO);
        return modulo;
    }

    public Modulo removeModulo(Modulo modulo) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("modulo");
        collection.remove(new BasicDBObject("idModulo", modulo.getIdModulo()));
        return modulo;
    }

    public List<DBObject> getModulos() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("modulo");
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    } 

}
