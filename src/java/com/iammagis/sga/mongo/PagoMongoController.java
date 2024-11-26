/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Pago;
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
public class PagoMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pago");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idPago", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idPago").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idPago", 1);
            collection.createIndex(index, "idPago");
            BasicDBObject fecha = new BasicDBObject("fecha", 1);
            collection.createIndex(fecha, "fecha");
            BasicDBObject idUsuario = new BasicDBObject("idEmpresa", 1);
            collection.createIndex(idUsuario, "idEmpresa");

        }
        return id;
    }

    public Pago findPago(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pago");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idPago", id);

        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Pago pago = gson.fromJson(JSON.serialize(dBObject), Pago.class);
            return pago;
        } else {
            return null;
        }
    }

    public Pago createPago(Pago pago) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);

        pago.setIdPago(getNextID());
        String jsonString = gson.toJson(pago);
        DBObject pagoMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pago");
        WriteResult result = collection.insert(pagoMONGO);
        DBCollection collectionPago = database.getCollection("pago");
        return pago;
    }

    public List<DBObject> getpagosFromEmpresa(int idEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pago");
        List<DBObject> dBObjects;
        dBObjects = collection.find(new BasicDBObject("idEmpresa", idEmpresa)).sort(new BasicDBObject("fecha", -1)).toArray(); 
        return dBObjects;
    }
 
     public void removePago(int id) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pago");
        collection.remove(new BasicDBObject("idPago", id)); 
    }

}
