/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Direccion;
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
import java.util.List;

/**
 *
 * @author sebastianarizmendy
 */
public class DireccionMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("direccion");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idDireccion", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idDireccion").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idDireccion", 1);
            collection.createIndex(index, "idDireccion");

            BasicDBObject index1 = new BasicDBObject("direccion", 1);
            collection.createIndex(index1, "direccion");

            BasicDBObject index2 = new BasicDBObject();
            index2.append("direccion", 1);
            index2.append("ciudad", 1);
            index2.append("departamento", 1);
            collection.createIndex(index2, "direccionAnd");

        }
        return id;
    }

    public Direccion findDireccion(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("direccion");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idDireccion", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Direccion direccion = gson.fromJson(JSON.serialize(dBObject), Direccion.class);
            return direccion;
        } else {
            return null;
        }
    }

    public Direccion createDireccion(Direccion direccion) {
        direccion.setIdDireccion(getNextID());
        String jsonString = gson.toJson(direccion);
        DBObject direccionMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("direccion");
        WriteResult result = collection.insert(direccionMONGO);
        DBCollection collectionPosicion = database.getCollection("direccion");
        return direccion;
    }

    public Direccion editDireccion(Direccion direccion) {
        String jsonString = gson.toJson(direccion);
        DBObject direccionMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("direccion");
        collection.update(new BasicDBObject("idDireccion", direccion.getIdDireccion()), direccionMONGO);
        return direccion;
    }

    public Direccion removeDireccion(Direccion direccion) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("direccion");
        collection.remove(new BasicDBObject("idDireccion", direccion.getIdDireccion()));
        return direccion;
    }

    public List<DBObject> getDireccions() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("direccion");
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    }

    public List<DBObject> getDireccionFromDireccion(String direccion, String ciudad, String departamento) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("direccion");

        BasicDBObject basicDBObject = new BasicDBObject("direccion", direccion);
        BasicDBList and = new BasicDBList();
        DBObject and1 = new BasicDBObject("ciudad", ciudad);
        DBObject and2 = new BasicDBObject("departamento", departamento);
        and.add(and1);
        and.add(and2);
        basicDBObject.append("$and", and);

//        System.out.println("basicDBObject: " + basicDBObject);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

}
