/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.ServicioRiesgoInternoHasEmpresa;
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
public class ServicioRiesgosInternoHasEmpresaMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInternoHasEmpresa");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idServicioRiesgoInternoHasEmpresaHasEmpresa", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idServicioRiesgoInternoHasEmpresaHasEmpresa").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idServicioRiesgoInternoHasEmpresaHasEmpresa", 1);
            collection.createIndex(index, "idServicioRiesgoInternoHasEmpresaHasEmpresa");
        }
        return id;
    }

    public DBObject findServicioRiesgoInternoHasEmpresa(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInternoHasEmpresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idServicioRiesgoInternoHasEmpresaHasEmpresa", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        return dBObject; 
    }

    public ServicioRiesgoInternoHasEmpresa createServicioRiesgoInternoHasEmpresa(ServicioRiesgoInternoHasEmpresa servicioRiesgoInternoHasEmpresa) {
        servicioRiesgoInternoHasEmpresa.setIdServicioRiesgoInternoHasEmpresa(getNextID());
        String jsonString = gson.toJson(servicioRiesgoInternoHasEmpresa);
        DBObject servicioRiesgoInternoHasEmpresaMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInternoHasEmpresa");
        WriteResult result = collection.insert(servicioRiesgoInternoHasEmpresaMONGO);
        DBCollection collectionPosicion = database.getCollection("servicioRiesgoInternoHasEmpresa");
        return servicioRiesgoInternoHasEmpresa;
    }

    public ServicioRiesgoInternoHasEmpresa editServicioRiesgoInternoHasEmpresa(ServicioRiesgoInternoHasEmpresa servicioRiesgoInternoHasEmpresa) {
        String jsonString = gson.toJson(servicioRiesgoInternoHasEmpresa);
        DBObject servicioRiesgoInternoHasEmpresaMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInternoHasEmpresa");
        collection.update(new BasicDBObject("idServicioRiesgoInternoHasEmpresaHasEmpresa", servicioRiesgoInternoHasEmpresa.getIdServicioRiesgoInternoHasEmpresa()), servicioRiesgoInternoHasEmpresaMONGO);
        return servicioRiesgoInternoHasEmpresa;
    }

    public ServicioRiesgoInternoHasEmpresa removeServicioRiesgoInternoHasEmpresa(ServicioRiesgoInternoHasEmpresa servicioRiesgoInternoHasEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInternoHasEmpresa");
        collection.remove(new BasicDBObject("idServicioRiesgoInternoHasEmpresaHasEmpresa", servicioRiesgoInternoHasEmpresa.getIdServicioRiesgoInternoHasEmpresa()));
        return servicioRiesgoInternoHasEmpresa;
    }

    public List<DBObject> getServicioRiesgoInternoHasEmpresas() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInternoHasEmpresa");
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    }

    public List<DBObject> getServicioRiesgoInternoHasEmpresasByTipo(int tipoServicioRiesgoInternoHasEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("servicioRiesgoInternoHasEmpresa");
        List<DBObject> dBObjects = collection.find(new BasicDBObject("tipoServicioRiesgoInternoHasEmpresa", tipoServicioRiesgoInternoHasEmpresa)).toArray();
        return dBObjects;
    }
     
       

}
