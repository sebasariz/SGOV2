/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.Posicion;
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
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author sebastianarizmendy
 */
public class PosicionMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();
    Calendar calendar = Calendar.getInstance();
    long start;

    public PosicionMongoController() {
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,
                calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE,
                calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND,
                calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
                calendar.getActualMinimum(Calendar.MILLISECOND));
        start = calendar.getTimeInMillis();

    }

    public int getNextID() {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("posicion-" + start);
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idPosicion", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idPosicion").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idPosicion", 1);
            collection.createIndex(index, "idPosicion");

        }
        return id;
    }

    public Posicion findPosicion(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("posicion-" + start);
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idPosicion", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Posicion posicion = gson.fromJson(JSON.serialize(dBObject), Posicion.class);
            return posicion;
        } else {
            return null;
        }
    }

    public Posicion createPosicion(Posicion posicion) {
        posicion.setIdPosicion(getNextID());
        String jsonString = gson.toJson(posicion);
        DBObject posicionMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("posicion-" + start);
        WriteResult result = collection.insert(posicionMONGO);
        return posicion;
    }

    public List<DBObject> getposicionsFromEmpresa(Usuario usuario) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("posicion-" + start);
        List<DBObject> dBObjects;
        if (usuario.getIdTipoUsuario() != 1) {
            dBObjects = collection.find(new BasicDBObject("idEmpresa", usuario.getIdEmpresa())).toArray();
        } else {
            dBObjects = collection.find().toArray();
        }
        return dBObjects;
    }

    public Posicion findByReferencia(String string) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("posicion-" + start);
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("referencia", string);

        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            Posicion posicion = gson.fromJson(JSON.serialize(dBObject), Posicion.class);
            return posicion;
        } else {
            return null;
        }
    }

    public List<DBObject> buscarPosiciones(long fechaIicial, long fechaFinal, long idTecnico) {
        Calendar calendarInicial = Calendar.getInstance();
        calendarInicial.setTimeInMillis(fechaIicial);

        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.setTimeInMillis(fechaFinal);

        calendar.setTimeInMillis(fechaIicial);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,
                calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE,
                calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND,
                calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
                calendar.getActualMinimum(Calendar.MILLISECOND));
        start = calendar.getTimeInMillis();

        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("posicion-" + start);
        List<DBObject> dBObjects;
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idUsuario", idTecnico);
        BasicDBList and = new BasicDBList();
        DBObject and1 = new BasicDBObject("fecha", new BasicDBObject("$gte", fechaIicial));
        DBObject and2 = new BasicDBObject("fecha", new BasicDBObject("$lte", fechaFinal));
        and.add(and1);
        and.add(and2);
        basicDBObject.append("$and", and);
        dBObjects = collection.find(basicDBObject).toArray();

        while (calendarInicial.get(Calendar.MONTH) < calendarFinal.get(Calendar.MONTH)) {
            calendar.setTimeInMillis(fechaFinal);
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY,
                    calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE,
                    calendar.getActualMinimum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND,
                    calendar.getActualMinimum(Calendar.SECOND));
            calendar.set(Calendar.MILLISECOND,
                    calendar.getActualMinimum(Calendar.MILLISECOND));
            start = calendar.getTimeInMillis();

            collection = database.getCollection("posicion-" + start);
            List<DBObject> dBObjectsFin;
            dBObjectsFin = collection.find(basicDBObject).toArray();
            dBObjects.addAll(dBObjectsFin);

            calendarInicial.add(Calendar.MONTH, 1);
        }
        return dBObjects;
    }

    public List<DBObject> getposicionsFromEmpresaAndFecha(Usuario usuario, long fechaIicial, long fechaFinal) {

        Calendar calendarInicial = Calendar.getInstance();
        calendarInicial.setTimeInMillis(fechaIicial);

        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.setTimeInMillis(fechaFinal);

        calendar.setTimeInMillis(fechaIicial);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,
                calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE,
                calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND,
                calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
                calendar.getActualMinimum(Calendar.MILLISECOND));
        start = calendar.getTimeInMillis();

        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("posicion-" + start);
        List<DBObject> dBObjects;
        BasicDBObject basicDBObject = new BasicDBObject();
        BasicDBList and = new BasicDBList();
        DBObject and1 = new BasicDBObject("fecha", new BasicDBObject("$gte", fechaIicial));
        DBObject and2 = new BasicDBObject("fecha", new BasicDBObject("$lte", fechaFinal));
        and.add(and1);
        and.add(and2);
        basicDBObject.append("$and", and);
        if (usuario.getIdTipoUsuario() != 1) {
            basicDBObject.append("idEmpresa", usuario.getIdEmpresa());
            dBObjects = collection.find(basicDBObject).toArray();
        } else {
            dBObjects = collection.find(basicDBObject).toArray();
        }

        while (calendarInicial.get(Calendar.MONTH) < calendarFinal.get(Calendar.MONTH)) {
            calendar.setTimeInMillis(fechaFinal);
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY,
                    calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE,
                    calendar.getActualMinimum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND,
                    calendar.getActualMinimum(Calendar.SECOND));
            calendar.set(Calendar.MILLISECOND,
                    calendar.getActualMinimum(Calendar.MILLISECOND));
            start = calendar.getTimeInMillis();

            collection = database.getCollection("posicion-" + start);
            List<DBObject> dBObjectsFin;
            basicDBObject = new BasicDBObject();
            basicDBObject.append("idEmpresa", usuario.getIdEmpresa());
            dBObjectsFin = collection.find(basicDBObject).toArray();
            dBObjects.addAll(dBObjectsFin);

            calendarInicial.add(Calendar.MONTH, 1);
        }
        return dBObjects;
    }
}
