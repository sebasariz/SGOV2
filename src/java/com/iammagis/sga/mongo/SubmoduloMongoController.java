/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Submodulo;
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
public class SubmoduloMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("submodulo");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idSubmodulo", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idSubmodulo").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idSubmodulo", 1);
            collection.createIndex(index, "idSubmodulo");
        }
        return id;
    }

    public Submodulo findSubmodulo(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("submodulo");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idSubmodulo", id);

        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Submodulo submodulo = gson.fromJson(JSON.serialize(dBObject), Submodulo.class);
            return submodulo;
        } else {
            return null;
        }
    }
 
    public Submodulo createSubmodulo(Submodulo submodulo) {
        submodulo.setIdSubmodulo(getNextID());
        String jsonString = gson.toJson(submodulo);
        DBObject submoduloMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("submodulo");
        WriteResult result = collection.insert(submoduloMONGO);
        DBCollection collectionPosicion = database.getCollection("submodulo");
        return submodulo;
    }

    public Submodulo editSubmodulo(Submodulo submodulo) {
        String jsonString = gson.toJson(submodulo);
        DBObject submoduloMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("submodulo");
        collection.update(new BasicDBObject("idSubmodulo", submodulo.getIdSubmodulo()), submoduloMONGO);
        return submodulo;
    }

    public Submodulo removeSubmodulo(Submodulo submodulo) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("submodulo");
        collection.remove(new BasicDBObject("idSubmodulo", submodulo.getIdSubmodulo()));
        return submodulo;
    }

    public List<DBObject> getSubmodulos() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("submodulo");
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    } 

}
