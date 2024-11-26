/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.mongo;


import com.google.gson.Gson;
import com.iammagis.sga.mongo.beans.LogActividades;
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
import java.util.regex.Pattern;

/**
 *
 * @author sebastianarizmendy
 */
public class LogActividadesMongoController {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
    Gson gson = new Gson();
    Calendar calendar = Calendar.getInstance();
    long start;

    public LogActividadesMongoController() {
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
        DBCollection collection = database.getCollection("logActividades-" + start);
        DBCursor dBCursor = collection.find().sort(new BasicDBObject("idLogActividades", -1)).limit(1);
        int id = 1;
        if (dBCursor.hasNext()) {
            DBObject dBObject = dBCursor.next();
            id = Integer.parseInt(dBObject.get("idLogActividades").toString());
            id++;
        } else {
            BasicDBObject index = new BasicDBObject("idLogActividades", 1);
            collection.createIndex(index, "idLogActividades");

            BasicDBObject idLogActividades = new BasicDBObject("idLogActividades", 1);
            collection.createIndex(idLogActividades, "idLogActividades");

            BasicDBObject idEmpresa = new BasicDBObject("idEmpresa", 1);
            collection.createIndex(idEmpresa, "idEmpresa");
        }
        return id;
    }

    public LogActividades findLogActividades(int id) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("logActividades-" + start);
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idLogActividades", id);
        DBObject dBObject = collection.findOne(basicDBObject);
        if (dBObject != null) {
            LogActividades logActividades = gson.fromJson(JSON.serialize(dBObject), LogActividades.class);
            return logActividades;
        } else {
            return null;
        }
    }

    public LogActividades createLogActividades(LogActividades logActividades) {
        logActividades.setIdLogActividades(getNextID());
        String jsonString = gson.toJson(logActividades);
        DBObject logActividadesMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("logActividades-" + start);
        WriteResult result = collection.insert(logActividadesMONGO); 
        return logActividades;
    }

    public LogActividades editLogActividades(LogActividades logActividades) {
        String jsonString = gson.toJson(logActividades);
        DBObject logActividadesMONGO = (DBObject) JSON.parse(jsonString);
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("logActividades-" + start);
        collection.update(new BasicDBObject("idLogActividades", logActividades.getIdLogActividades()), logActividadesMONGO);
        return logActividades;
    }

    public LogActividades removeLogActividades(LogActividades logActividades) {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("logActividades-" + start);
        collection.remove(new BasicDBObject("idLogActividades", logActividades.getIdLogActividades()));
        return logActividades;
    }

    public List<DBObject> getLogActividadess() throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("logActividades-" + start);
        List<DBObject> dBObjects = collection.find().toArray();
        return dBObjects;
    }

    public List<DBObject> getLogActividadessInstitucion(Integer idInstitucion) throws IOException {
        MongoClient mongoClient = SessionControl.getMongoClient();
        DB database = mongoClient.getDB(propertiesAcces.mongoDB);
        DBCollection collection = database.getCollection("logActividades-" + start);
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idInstitucion);
        List<DBObject> dBObjects = collection.find(basicDBObject).toArray();
        return dBObjects;
    }

    public List<DBObject> getLogs(long fechaIicial, long fechaFinal) {
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
        DBCollection collection = database.getCollection("logActividades-" + start);
        List<DBObject> dBObjects;
        BasicDBObject basicDBObject = new BasicDBObject();
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

            collection = database.getCollection("logActividades-" + start);
            List<DBObject> dBObjectsFin;
            dBObjectsFin = collection.find(basicDBObject).toArray();
            dBObjects.addAll(dBObjectsFin);

            calendarInicial.add(Calendar.MONTH, 1);
        }
        return dBObjects;
    }

    public List<DBObject> getLogs(long fechaIicial, long fechaFinal, int idEmpresa) {
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
        DBCollection collection = database.getCollection("logActividades-" + start);
        List<DBObject> dBObjects;
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("idEmpresa", idEmpresa);
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

            collection = database.getCollection("logActividades-" + start);
            List<DBObject> dBObjectsFin;
            dBObjectsFin = collection.find(basicDBObject).toArray();
            dBObjects.addAll(dBObjectsFin);

            calendarInicial.add(Calendar.MONTH, 1);
        }
        return dBObjects;
    }

}
