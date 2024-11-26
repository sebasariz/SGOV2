/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Paquete;
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
public class PaqueteMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idPaquete", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idPaquete").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idPaquete", 1);
            collection.createIndex(index, "idPaquete");

            BasicDBObject idEmpresa_creadora = new BasicDBObject("idEmpresa_creadora", 1);
            collection.createIndex(idEmpresa_creadora, "idEmpresa_creadora");

            BasicDBObject idEmpresa_sgo = new BasicDBObject("idEmpresa_sgo", 1);
            collection.createIndex(idEmpresa_sgo, "idEmpresa_sgo");

            BasicDBObject indexidentificacion = new BasicDBObject("identificacion", 1);
            collection.createIndex(indexidentificacion, "identificacion");

            BasicDBObject direccion_entrega = new BasicDBObject("direccion_entrega", 1);
            collection.createIndex(direccion_entrega, "direccion_entrega");

            BasicDBObject direccion_origen = new BasicDBObject("direccion_origen", 1);
            collection.createIndex(direccion_origen, "direccion_origen");

            BasicDBObject estado = new BasicDBObject("estado", 1);
            collection.createIndex(estado, "estado");
            
            BasicDBObject zona_bodega = new BasicDBObject("zona_bodega", 1);
            collection.createIndex(zona_bodega, "zona_bodega");

        }
        return id;
    }

    public DBObject findPaqueteRuteo(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idPaquete", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        return dBObject;
    }

    public Paquete createPaqueteRuteo(Paquete paquete) {
        paquete.setIdPaquete(getNextID());
        String jsonString = gson.toJson(paquete);
        DBObject paqueteMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        WriteResult result = collection.insert(paqueteMONGO);
        DBCollection collectionPosicion = database.getCollection("paquete");
        return paquete;
    }

    public Paquete editPaqueteRuteo(Paquete paquete) {
        String jsonString = gson.toJson(paquete);
        DBObject paqueteMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        collection.update(new BasicDBObject("idPaquete", paquete.getIdPaquete()), paqueteMONGO);
        return paquete;
    }

    public Paquete removePaqueteRuteo(Paquete paquete) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        collection.remove(new BasicDBObject("idPaquete", paquete.getIdPaquete()));
        return paquete;
    }

    public List<DBObject> getPaqueteRuteos() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        List<DBObject> dBObjects = collection.find().sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosByEmpresaCreadoraUltimas48Horas(int idPaquete) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_creadora", idPaquete);
        basicDBObject.put("fecha_creacion", new BasicDBObject("$gte", calendar.getTimeInMillis()));

        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosByEmpresaSGOUltimas48Horas(int idPaquete) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_sgo", idPaquete);
//        basicDBObject.put("fecha_creacion", new BasicDBObject("$gte", calendar.getTimeInMillis()));
        System.out.println("entrando por empresa sgo1 ");
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).limit(500).toArray();
        System.out.println("dBObjects: " + dBObjects);
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosUltimas48Horas() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("fecha_creacion", new BasicDBObject("$gte", calendar.getTimeInMillis()));
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosByEmpresaSGOByEstado(int idEmpresa, String estado) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_sgo", idEmpresa);
        basicDBObject.put("estado", estado);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosByEmpresaCreadoraByEstado(int idEmpresa, String estado) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_creadora", idEmpresa);
        basicDBObject.put("estado", estado);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public DBObject findDireccionAnterior(Paquete paquete) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        System.out.println("paquete.getDireccion_entrega(): " + paquete.getDireccion_entrega());
        basicDBObject.put("direccion_entrega", paquete.getDireccion_entrega());
        DBObject dBObject = collection.findOne(basicDBObject);
        return dBObject;
    }

    public DBObject findDireccionAnteriorRecoleccion(Paquete paquete) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        System.out.println("paquete.getDireccion_entrega(): " + paquete.getDireccion_entrega());
        basicDBObject.put("direccion_origen", paquete.getDireccion_origen());
        DBObject dBObject = collection.findOne(basicDBObject);
        return dBObject;
    }

    public void updatePositions(Paquete paquete) {
        DBObject dBObjectSet = new BasicDBObject();
        DBObject paqueteMONGO = new BasicDBObject();
        paqueteMONGO.put("lat_entrega", paquete.getLat_entrega());
        paqueteMONGO.put("lng_entrega", paquete.getLng_entrega());

        dBObjectSet.put("$set", paqueteMONGO);

        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        collection.updateMulti(new BasicDBObject("direccion_entrega", paquete.getDireccion_entrega()), dBObjectSet);

    }

    public List<DBObject> getPaqueteRuteosPendientesDeRuta() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject(); 
        basicDBObject.put("estado", "En bodega");
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosPreEnrutados() {

        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("estado", "Enrutamiento");
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosPreEnrutadosRecoleccion() {

        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("estado", "Enrutamiento Recoleccion");
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosByEmpresaSGOPreEnRuta(int idEmpresa) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_sgo", idEmpresa);
        basicDBObject.put("estado", "Enrutamiento");
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosByEmpresaCreadoraPreEnRuta(int idEmpresa) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_creadora", idEmpresa);
        basicDBObject.put("estado", "Enrutamiento");
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteoFromNumeroGuia(int idEmpresaCreadora, String texto) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_sgo", idEmpresaCreadora);
        basicDBObject.put("guia", texto);
//        System.out.println("basicDBObject: " + basicDBObject);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        System.out.println("dBObjects: " + dBObjects);
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteoFromIdentificaicon(int idEmpresaCreadora, String texto) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_sgo", idEmpresaCreadora);
        basicDBObject.put("identificacion", texto);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosByEmpresaSGOPreEnRutaRecoleccion(int idEmpresa) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_sgo", idEmpresa);
        basicDBObject.put("estado", "Enrutamiento Recoleccion");
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPaqueteRuteosByEmpresaCreadoraPreEnRutaRecoleccion(int idEmpresa) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("paquete");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresa_creadora", idEmpresa);
        basicDBObject.put("estado", "Enrutamiento Recoleccion");
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPaquete", -1)).toArray();
        return dBObjects;
    }

}
