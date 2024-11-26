/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Empresa;
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
public class EmpresaMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idEmpresa", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idEmpresa").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idEmpresa", 1);
            collection.createIndex(index, "idEmpresa");
            BasicDBObject codigo = new BasicDBObject("codigo", 1);
            collection.createIndex(codigo, "codigo");
        }
        return id;
    }

    public DBObject findEmpresa(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        return dBObject;
//        if (dBObject != null) {
//            Empresa empresa = gson.fromJson(JSON.serialize(dBObject), Empresa.class);
//            return empresa;
//        } else {
//            return null;
//        }
    }

    public Empresa createEmpresa(Empresa empresa) {
        empresa.setIdEmpresa(getNextID());
        String jsonString = gson.toJson(empresa);
        DBObject empresaMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        WriteResult result = collection.insert(empresaMONGO);
        DBCollection collectionPosicion = database.getCollection("empresa");
        return empresa;
    }

    public Empresa editEmpresa(Empresa empresa) {
        String jsonString = gson.toJson(empresa);
        DBObject empresaMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        collection.update(new BasicDBObject("idEmpresa", empresa.getIdEmpresa()), empresaMONGO);
        return empresa;
    }

    public Empresa removeEmpresa(Empresa empresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        collection.remove(new BasicDBObject("idEmpresa", empresa.getIdEmpresa()));
        return empresa;
    }

    public List<DBObject> getEmpresas() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    }

    public List<DBObject> getEmpresasByTipo(int tipoEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        List<DBObject> dBObjects = collection.find(new BasicDBObject("tipoEmpresa", tipoEmpresa)).toArray();
        return dBObjects;
    }

    public List<DBObject> getEmpresasByTipo(int tipoEmpresa, int idEmpresaCreadora) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("tipoEmpresa", tipoEmpresa);
        basicDBObject.put("idEmpresaCreadora", idEmpresaCreadora);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public Empresa findEmpresaByCodigo(String string, int empresaCreadora) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("codigo", string);
        basicDBObject.append("idEmpresaCreadora", empresaCreadora);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Empresa empresa = gson.fromJson(JSON.serialize(dBObject), Empresa.class);
            return empresa;
        } else {
            return null;
        }
    }

    public Empresa findEmpresaByNit(String nit, int empresaCreadora) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("nit", nit);
        basicDBObject.append("idEmpresaCreadora", empresaCreadora);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Empresa empresa = gson.fromJson(JSON.serialize(dBObject), Empresa.class);
            return empresa;
        } else {
            return null;
        }
    }

    public List<DBObject> getEmpresasByEpresaCreadora(int idEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        System.out.println("idEmpresa: " + idEmpresa);
        basicDBObject.put("idEmpresaCreadora", idEmpresa);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public List<DBObject> getEmpresasByEpresaCreadoraPendientes(int idEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresaCreadora", idEmpresa);
        basicDBObject.put("estado", 2);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public List<DBObject> getEmpresasByEpresaCreadoraAndValorCartera(int idEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresaCreadora", idEmpresa);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("valorPendientePago", -1)).toArray();
        return dBObjects;
    }

    public void removeEmpresasMasiva(int idEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idEmpresaCreadora", idEmpresa);
        collection.remove(basicDBObject);
    }

    public Empresa getEmpresaByUUid(String uuid) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("uuid", uuid);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Empresa empresa = gson.fromJson(JSON.serialize(dBObject), Empresa.class);
            return empresa;
        } else {
            return null;
        }
    }

    public List<DBObject> getEmpresasByEpresaCreadoraAndZona(int idEmpresa, String zona) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresaCreadora", idEmpresa);
        System.out.println("zona: " + zona);
        if (!zona.equals("0")) {
            basicDBObject.append("vendedor", zona);
        }
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("valorPendientePago", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getEmpresasPendientes() {
          MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("empresa");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("estado", 2);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

}
