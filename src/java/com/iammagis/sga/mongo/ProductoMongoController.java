/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Producto;
import com.iammagis.sga.mongo.beans.Usuario;
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
public class ProductoMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("producto");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idProducto", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idProducto").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idProducto", 1);
            collection.createIndex(index, "idProducto");
            BasicDBObject index1 = new BasicDBObject("idEmpresa", 1);
            collection.createIndex(index1, "idEmpresa");
            BasicDBObject referencia = new BasicDBObject("referencia", 1);
            collection.createIndex(referencia, "referencia");
        }
        return id;
    }

    public Producto findProducto(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("producto");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idProducto", id);

        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Producto producto = gson.fromJson(JSON.serialize(dBObject), Producto.class);
            return producto;
        } else {
            return null;
        }
    }

    public Producto createProducto(Producto producto) {
        producto.setIdProducto(getNextID());
        String jsonString = gson.toJson(producto);
        DBObject productoMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("producto");
        WriteResult result = collection.insert(productoMONGO);
        DBCollection collectionPosicion = database.getCollection("producto");
        return producto;
    }

    public Producto editProducto(Producto producto) {
        String jsonString = gson.toJson(producto);
        DBObject productoMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("producto");
        collection.update(new BasicDBObject("idProducto", producto.getIdProducto()), productoMONGO);
        return producto;
    }

    public Producto removeProducto(Producto producto) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("producto");
        collection.remove(new BasicDBObject("idProducto", producto.getIdProducto()));
        return producto;
    }

    public List<DBObject> getProductos() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("producto");
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    }

    public List<DBObject> getproductosFromEmpresa(Usuario usuario) { 
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("producto");
        List<DBObject> dBObjects;
        if (usuario.getIdTipoUsuario() != 1) {
            dBObjects = collection.find(new BasicDBObject("idEmpresa", usuario.getIdEmpresa())).toArray();
        } else {
            dBObjects = collection.find().toArray();
        } 
        return dBObjects;
    }

    public Producto findByReferencia(String string,int idEmpresaCreadora) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("producto");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("referencia", string);
        basicDBObject.append("idEmpresa", idEmpresaCreadora);

        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Producto producto = gson.fromJson(JSON.serialize(dBObject), Producto.class);
            return producto;
        } else {
            return null;
        }
    }

    public void removeProductosFromEmpresa(int idEmpresa) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("producto");
        collection.remove(new BasicDBObject("idEmpresa", idEmpresa));
    }

}
