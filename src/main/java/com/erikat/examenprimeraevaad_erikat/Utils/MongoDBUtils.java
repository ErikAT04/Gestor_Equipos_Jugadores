package com.erikat.examenprimeraevaad_erikat.Utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.Properties;

public class MongoDBUtils {
    static MongoClient cliente;
    static MongoDatabase database;
    static MongoCollection collection;
    static {
        try{
            Properties dbConf = new Properties();
            dbConf.load(R.getDBConfig("MongoDB.properties"));
            String host = dbConf.getProperty("host");
            String user = dbConf.getProperty("user");
            String pass = dbConf.getProperty("password");
            String port = dbConf.getProperty("port");
            cliente = new MongoClient(new MongoClientURI("mongodb://"+user+":"+pass+"@"+host+":"+port+"/?authSource=admin")); //Se conecta a la base de datos en función de la url
            database = cliente.getDatabase("ExamenEquipos");
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
    public static MongoCollection<Document> getCollection(String name){
        return database.getCollection(name);
    }
    public static void close(){
        cliente.close();
    }
}
