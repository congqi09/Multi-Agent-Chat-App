package com.neu.chatApp.server.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.yaml.snakeyaml.Yaml;

import com.neu.chatApp.util.entity.User;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDB implements DB {
    private String uri;
    private CodecProvider pojoCodecProvider;
    private CodecRegistry pojoCodecRegistry;

    public MongoDB() {
        this.uri = new Yaml().load(MongoDB.class.getClassLoader().getResourceAsStream("db.yaml"));
        this.pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        this.pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    }

    @Override
    public void insert(User user) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("chat_app").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<User> users = database.getCollection("users", User.class);
            users.insertOne(user);
        }
    }

    @Override
    public User select(String username) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("chat_app").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<User> users = database.getCollection("users", User.class);
            return users.find(Filters.eq("username", username)).first();
        }
    }

    public static void main(String[] args) {
        MongoDB db = new MongoDB();
        db.select("test");
        User user = new User("test", "1234");
        db.insert(user);
        System.out.println(db.select("test"));
    }
}