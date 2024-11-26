/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Deudor;
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
import java.util.regex.Pattern;

/**
 *
 * @author sebastianarizmendy
 */
public class DeudorMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idDeudor", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idDeudor").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idDeudor", 1);
            collection.createIndex(index, "idDeudor");

            BasicDBObject index1 = new BasicDBObject("identificacion", 1);
            collection.createIndex(index1, "identificacion");

            BasicDBObject index2 = new BasicDBObject("estado", 1);
            collection.createIndex(index2, "estado");

        }
        return id;
    }

    public Deudor findDeudor(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idDeudor", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Deudor deudor = gson.fromJson(JSON.serialize(dBObject), Deudor.class);
            return deudor;
        } else {
            return null;
        }
    }

    public Deudor createDeudor(Deudor deudor) {
        deudor.setIdDeudor(getNextID());
        String jsonString = gson.toJson(deudor);
        DBObject deudorMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        WriteResult result = collection.insert(deudorMONGO);
        DBCollection collectionPosicion = database.getCollection("deudor");
        return deudor;
    }

    public Deudor editDeudor(Deudor deudor) {
        String jsonString = gson.toJson(deudor);
        DBObject deudorMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        collection.update(new BasicDBObject("idDeudor", deudor.getIdDeudor()), deudorMONGO);
        return deudor;
    }

    public Deudor removeDeudor(Deudor deudor) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        collection.remove(new BasicDBObject("idDeudor", deudor.getIdDeudor()));
        return deudor;
    }

    public List<DBObject> getDeudors() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    }

    public List<DBObject> getDeudorsInstitucion(Integer idInstitucion) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idInstitucion);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public List<DBObject> getDeudorsInstitucionAndTipo(Integer idInstitucion, int idTipoDeudor) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idInstitucion);
        basicDBObject.append("idTipoDeudor", idTipoDeudor);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public Deudor userExist(Deudor deudor) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("email", deudor.getEmail());
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            deudor = gson.fromJson(JSON.serialize(dBObject), Deudor.class);
            return deudor;
        } else {
            return null;
        }
    }

    public Deudor emailExist(Deudor deudor) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("email", deudor.getEmail());

        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            deudor = gson.fromJson(JSON.serialize(dBObject), Deudor.class);
        } else {
            return null;
        }
        return deudor;
    }

    public Deudor findDeudorByIdentificacion(String identificacion,int idEmpresaCliente) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("deudor");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("identificacion", identificacion);
        basicDBObject.append("idEmpresaCliente", identificacion);
        Deudor deudor;
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            deudor = gson.fromJson(JSON.serialize(dBObject), Deudor.class);
        } else {
            return null;
        }
        return deudor;
    }

}
