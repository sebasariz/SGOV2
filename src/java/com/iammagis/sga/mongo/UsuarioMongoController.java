/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
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
import java.util.regex.Pattern;

/**
 *
 * @author sebastianarizmendy
 */
public class UsuarioMongoController {

    
    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idUsuario", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = (int)Double.parseDouble(dBObject.get("idUsuario").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idUsuario", 1);
            collection.createIndex(index, "idUsuario");
        }
        return id;
    }

    public Usuario findUsuario(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idUsuario", id); 
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Usuario usuario = gson.fromJson(JSON.serialize(dBObject), Usuario.class);
            return usuario;
        } else {
            return null;
        }
    }

    public Usuario createUsuario(Usuario usuario) {
        usuario.setIdUsuario(getNextID());
        String jsonString = gson.toJson(usuario);
        DBObject usuarioMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        WriteResult result = collection.insert(usuarioMONGO);
        DBCollection collectionPosicion = database.getCollection("usuario");
        return usuario;
    }

    public Usuario editUsuario(Usuario usuario) {
        String jsonString = gson.toJson(usuario);
        DBObject usuarioMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        collection.update(new BasicDBObject("idUsuario", usuario.getIdUsuario()), usuarioMONGO);
        return usuario;
    }

    public Usuario removeUsuario(Usuario usuario) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        collection.remove(new BasicDBObject("idUsuario", usuario.getIdUsuario()));
        return usuario;
    }

    public List<DBObject> getUsuarios() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    }
    
    public List<DBObject> getUsuariosInstitucion(Integer idInstitucion) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idInstitucion);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public List<DBObject> getUsuariosInstitucionAndTipo(Integer idInstitucion, int idTipoUsuario) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idInstitucion);
        basicDBObject.append("idTipoUsuario", idTipoUsuario);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public Usuario userExist(Usuario usuario) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("email", usuario.getEmail());
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            usuario = gson.fromJson(JSON.serialize(dBObject), Usuario.class);
            return usuario;
        } else {
            return null;
        }
    }

    public Usuario findUsuarioBySessionToken(String sessionToken) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("sessionToken", sessionToken);
        DBObject dBObject = collection.findOne(basicDBObject);
        Usuario usuario;
        if (dBObject != null) {
            usuario = gson.fromJson(JSON.serialize(dBObject), Usuario.class);
        } else {
            return null;
        }
        return usuario;
    }

    public Usuario loginUsuario(Usuario usuario) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        BasicDBObject basicDBObject = new BasicDBObject();

        basicDBObject.append("email", Pattern.compile("^" + usuario.getEmail() + "$", Pattern.CASE_INSENSITIVE));
        basicDBObject.append("pass", Pattern.compile("^" + usuario.getPass() + "$", Pattern.CASE_INSENSITIVE));

        DBObject dBObject = collection.findOne(basicDBObject);
//        System.out.println("dBObject: " + dBObject);
        usuario = null;
        if (dBObject != null) {
            usuario = gson.fromJson(JSON.serialize(dBObject), Usuario.class);
        } else {
            return null;
        }
        return usuario;
    }

    public Usuario emailExist(Usuario usuario) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("email", usuario.getEmail());

        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            usuario = gson.fromJson(JSON.serialize(dBObject), Usuario.class);
        } else {
            return null;
        }
        return usuario;
    }

    public Usuario UserBySession(String sessionToken) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("usuario");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("sessionToken", sessionToken);
        DBObject dBObject = collection.findOne(basicDBObject);
        Usuario usuario = gson.fromJson(JSON.serialize(dBObject), Usuario.class);
        return usuario;
    }
 

}
