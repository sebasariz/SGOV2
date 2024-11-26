package com.iammagis.sga.support;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Arrays;

public class SessionControl {
   private static MongoClient mongoClient;
   private static PropertiesAcces propertiesAcces = new PropertiesAcces();

   public static MongoClient getMongoClient() {
      if (mongoClient == null) {
         MongoCredential credential = MongoCredential.createCredential(propertiesAcces.mongouser, propertiesAcces.mongoDB, propertiesAcces.mongopassword.toCharArray());
         mongoClient = new MongoClient(new ServerAddress(propertiesAcces.mongoserver, propertiesAcces.mongoPort), Arrays.asList(credential));
      }

      return mongoClient;
   }
}
