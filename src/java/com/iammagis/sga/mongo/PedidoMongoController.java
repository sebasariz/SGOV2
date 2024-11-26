/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Pedido;
import com.iammagis.sga.mongo.beans.Usuario;
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
public class PedidoMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idPedido", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idPedido").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idPedido", 1);
            collection.createIndex(index, "idPedido");
        }
        return id;
    }

    public Pedido findPedido(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idPedido", id);

        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Pedido pedido = gson.fromJson(JSON.serialize(dBObject), Pedido.class);
            return pedido;
        } else {
            return null;
        }
    }

    public Pedido createPedido(Pedido pedido) {
        pedido.setIdPedido(getNextID());
        String jsonString = gson.toJson(pedido);
        DBObject pedidoMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");
        WriteResult result = collection.insert(pedidoMONGO);
        DBCollection collectionPosicion = database.getCollection("pedido");
        return pedido;
    }

    public Pedido editPedido(Pedido pedido) {
        String jsonString = gson.toJson(pedido);
        DBObject pedidoMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");
        collection.update(new BasicDBObject("idPedido", pedido.getIdPedido()), pedidoMONGO);
        return pedido;
    }

    public Pedido removePedido(Pedido pedido) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");
        collection.remove(new BasicDBObject("idPedido", pedido.getIdPedido()));
        return pedido;
    }

    public List<DBObject> getPedidos() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");
        List<DBObject> dBObjects = collection.find().sort(new BasicDBObject("idPedido", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPedidosPendientes(Usuario usuario) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");

        BasicDBObject basicDBObject = new BasicDBObject();
        BasicDBList or = new BasicDBList();
        DBObject or1 = new BasicDBObject("estado", 1);
        DBObject or2 = new BasicDBObject("estado", 2);
        DBObject or3 = new BasicDBObject("estado", 3);
//        DBObject or4 = new BasicDBObject("estado", 6);
        or.add(or1);
        or.add(or2);
        or.add(or3);
//        or.add(or4);
        basicDBObject.append("$or", or);
        if (usuario.getIdTipoUsuario() != 1) {
            basicDBObject.append("idEmpresaCreadora", usuario.getIdEmpresa());
        } 
        if (usuario.getIdTipoUsuario() == 3) {
            basicDBObject.append("idUsuarioCreador", usuario.getIdUsuario());
        }
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("_id", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPedidosPendientesFromUsuarioVendedor(Usuario usuario) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");

        BasicDBObject basicDBObject = new BasicDBObject();
        BasicDBList or = new BasicDBList();
        DBObject or1 = new BasicDBObject("estado", 1);
        DBObject or2 = new BasicDBObject("estado", 2);
//        DBObject or3 = new BasicDBObject("estado", 6);
        or.add(or1);
        or.add(or2);
//        or.add(or3);
        basicDBObject.append("$or", or);
        if (usuario.getIdTipoUsuario() != 1) {
            basicDBObject.append("idUsuarioCreador", usuario.getIdUsuario());
        }
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPedido", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPedidosFromEmpresaAndFecha(Usuario usuario, long fechaIncial, long fechaFinal) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");

        List<DBObject> dBObjects;
        BasicDBObject basicDBObject = new BasicDBObject();
        BasicDBList and = new BasicDBList();
        DBObject and1 = new BasicDBObject("fechaCreacion", new BasicDBObject("$gte", fechaIncial));
        DBObject and2 = new BasicDBObject("fechaCreacion", new BasicDBObject("$lte", fechaFinal));
        and.add(and1);
        and.add(and2);
        basicDBObject.append("$and", and);
        if (usuario.getIdTipoUsuario() != 1) {
            basicDBObject.append("idEmpresaCreadora", usuario.getIdEmpresa());
            dBObjects = collection.find(basicDBObject).toArray();
        } else {
            dBObjects = collection.find(basicDBObject).toArray();
        }
        return dBObjects;
    }

    public List<DBObject> getHistorialCliente(int idCliente) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("empresaCliente.idEmpresa", idCliente);
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPedido", -1)).limit(100).toArray();
        return dBObjects;
    }

    public List<DBObject> getHistorialVendedor(Usuario usuario, long fechaIncial, long fechaFinal) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");

        BasicDBObject basicDBObject = new BasicDBObject();
        BasicDBList and = new BasicDBList();
        DBObject and1 = new BasicDBObject("fechaCreacion", new BasicDBObject("$gte", fechaIncial));
        DBObject and2 = new BasicDBObject("fechaCreacion", new BasicDBObject("$lte", fechaFinal));
        and.add(and1);
        and.add(and2);
        basicDBObject.append("$and", and);
        if (usuario.getIdTipoUsuario() != 1) {
            basicDBObject.append("idUsuarioCreador", usuario.getIdUsuario());
        }
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("idPedido", -1)).limit(100).toArray();
        return dBObjects;
    }

    public List<DBObject> getPedidosElimnados(Usuario usuario) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("estado", -1);
        if (usuario.getIdTipoUsuario() != 1) {
            basicDBObject.append("idEmpresaCreadora", usuario.getIdEmpresa());
        }
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("_id", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getPedidosPendientesUsuario(Usuario usuario) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");

        BasicDBObject basicDBObject = new BasicDBObject();
        BasicDBList or = new BasicDBList();
        DBObject or1 = new BasicDBObject("estado", 1);
        DBObject or2 = new BasicDBObject("estado", 2);
        DBObject or3 = new BasicDBObject("estado", 3);
        or.add(or1);
        or.add(or2);
        or.add(or3);
        basicDBObject.append("$or", or);
        if (usuario.getIdTipoUsuario() != 1) {
            basicDBObject.append("idEmpresaCliente", usuario.getIdEmpresaCliente());
        }
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("_id", -1)).toArray();
        return dBObjects;
    }

    public List<DBObject> getultimos100Pedidos(Usuario usuario) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("pedido");

        BasicDBObject basicDBObject = new BasicDBObject();
        if (usuario.getIdTipoUsuario() != 1) {
            basicDBObject.append("idEmpresaCreadora", usuario.getIdEmpresa());
        }
        List<DBObject> dBObjects = collection.find(basicDBObject).sort(new BasicDBObject("_id", -1)).limit(100).toArray();
        return dBObjects;
    }

}
