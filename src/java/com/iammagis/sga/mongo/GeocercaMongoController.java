/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Geocerca;
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
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author sebastianarizmendy
 */
public class GeocercaMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("geocerca");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idGeocerca", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idGeocerca").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idGeocerca", 1);
            collection.createIndex(index, "idGeocerca");

            BasicDBObject idEmpresa_creadora = new BasicDBObject("idEmpresa_creadora", 1);
            collection.createIndex(idEmpresa_creadora, "idEmpresa_creadora");

        }
        return id;
    }

    public DBObject findGeocerca(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("geocerca");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idGeocerca", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        return dBObject;
    }

    public Geocerca createGeocerca(Geocerca geocerca) {
        geocerca.setIdGeocerca(getNextID());
        String jsonString = gson.toJson(geocerca);
        DBObject geocercaMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("geocerca");
        WriteResult result = collection.insert(geocercaMONGO);
        DBCollection collectionPosicion = database.getCollection("geocerca");
        return geocerca;
    }

    public Geocerca editGeocerca(Geocerca geocerca) {
        String jsonString = gson.toJson(geocerca);
        DBObject geocercaMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("geocerca");
        collection.update(new BasicDBObject("idGeocerca", geocerca.getIdGeocerca()), geocercaMONGO);
        return geocerca;
    }

    public Geocerca removeGeocerca(Geocerca geocerca) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("geocerca");
        collection.remove(new BasicDBObject("idGeocerca", geocerca.getIdGeocerca()));
        return geocerca;
    }

    public List<DBObject> getGeocercas() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("geocerca");
        List<DBObject> dBObjects = collection.find().sort(new BasicDBObject("idGeocerca", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getGeocercaFromEmpresa(int idEmpresa) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("geocerca");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_creadora", idEmpresa); 

        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idGeocerca", -1)).toArray();
        return dBObjects;
    }

     

}
