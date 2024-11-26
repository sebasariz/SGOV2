/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.RutaRecoleccion;
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
public class RutaRecoleccionMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextSequence() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("counters");
        BasicDBObject find = new BasicDBObject();
        find.put("_id", "idRutaServicio");
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", 1));
        DBObject obj = collection.findAndModify(find, update);
//        System.out.println("obj: " + obj);
        Object objInc = obj.get("seq");
//        System.out.println("objInc: " + objInc);
        return ((Number) objInc).intValue();
    }

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collectionConfig = database.getCollection("config");
        DBCursor dBCursor = collectionConfig.find().sort(new BasicDBObject("idRutaServicio", -1)).limit(1);
        int id = getNextSequence();
        if (!dBCursor.hasNext()) {
            DBCollection collection = database.getCollection("rutaRecoleccion");
            BasicDBObject index = new BasicDBObject("idRutaServicio", 1);
            collection.createIndex(index, "idRutaServicio");

            BasicDBObject idConductor = new BasicDBObject("idConductor", 1);
            collection.createIndex(idConductor, "idConductor");

            BasicDBObject idEmpresaCreadora = new BasicDBObject("idEmpresaCreadora", 1);
            collection.createIndex(idEmpresaCreadora, "idEmpresaCreadora");

            BasicDBObject estado = new BasicDBObject("estado", 1);
            collection.createIndex(estado, "estado");

        }
        return id;
    }

    public DBObject findRutaServicio(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idRutaServicio", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        return dBObject;
    }

    public RutaRecoleccion createRutaServicio(RutaRecoleccion rutaRecoleccion) {
        rutaRecoleccion.setIdRutaServicio(getNextID());
        String jsonString = gson.toJson(rutaRecoleccion);
        DBObject rutaRecoleccionMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        WriteResult result = collection.insert(rutaRecoleccionMONGO);
        DBCollection collectionPosicion = database.getCollection("rutaRecoleccion");
        return rutaRecoleccion;
    }

    public RutaRecoleccion editRutaServicio(RutaRecoleccion rutaRecoleccion) {
        String jsonString = gson.toJson(rutaRecoleccion);
        DBObject rutaRecoleccionMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        collection.update(new BasicDBObject("idRutaServicio", rutaRecoleccion.getIdRutaServicio()), rutaRecoleccionMONGO);
        return rutaRecoleccion;
    }

    public RutaRecoleccion removeRutaServicio(RutaRecoleccion rutaRecoleccion) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        collection.remove(new BasicDBObject("idRutaServicio", rutaRecoleccion.getIdRutaServicio()));
        return rutaRecoleccion;
    }

    public List<DBObject> getRutaServicios() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        List<DBObject> dBObjects = collection.find().sort(new BasicDBObject("idRutaServicio", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getRutaServiciosByEmpresaCreadoraUltimas48Horas(int idRutaServicio) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_creadora", idRutaServicio);
        basicDBObject.put("fecha_creacion", new BasicDBObject("$gte", calendar.getTimeInMillis()));

        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idRutaServicio", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getrutasByEstado(String estado) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("estado", estado);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idRutaServicio", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getrutasByEstadoAndEmpresa(String estado, int idEmpresa) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("estado", estado);
        basicDBObject.put("idEmpresa_creadora", idEmpresa);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idRutaServicio", -1)).toArray();
        return dBObjects;
    }
    
    

    public List<DBObject> getrutasBySeguimientoRoot() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        BasicDBObject basicDBObject = new BasicDBObject();
        BasicDBList or = new BasicDBList();
        DBObject or1 = new BasicDBObject("estado", "Preparada");
        DBObject or2 = new BasicDBObject("estado", "Ejecución");
//        DBObject or3 = new BasicDBObject("estado", 6);
        or.add(or1);
        or.add(or2);
//        or.add(or3);
        basicDBObject.append("$or", or);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idRutaServicio", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getrutasBySeguimiento(int idEmpresa) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        BasicDBObject basicDBObject = new BasicDBObject();
        BasicDBList or = new BasicDBList();
        DBObject or1 = new BasicDBObject("estado", "Preparada");
        DBObject or2 = new BasicDBObject("estado", "Ejecución");
//        DBObject or3 = new BasicDBObject("estado", 6);
        or.add(or1);
        or.add(or2);
//        or.add(or3);
        basicDBObject.append("$or", or);
        basicDBObject.put("idEmpresa_creadora", idEmpresa);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idRutaServicio", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getRutaServiciosByUsuarioConductor(int idUsuario) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("rutaRecoleccion");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idConductor", idUsuario);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idRutaServicio", -1)).toArray();
        return dBObjects;
    }
}
