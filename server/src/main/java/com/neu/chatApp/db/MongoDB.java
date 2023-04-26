package com.neu.chatApp.db;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;

import com.neu.chatApp.entity.User;

@Repository
public class MongoDB implements DB {
    private String uri;
    private CodecProvider pojoCodecProvider;
    private CodecRegistry pojoCodecRegistry;

    public MongoDB() {
        this.uri = new Yaml().load(MongoDB.class.getClassLoader().getResourceAsStream("db.yaml"));
        this.pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        this.pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(pojoCodecProvider));
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

    @Override
    public List<String> getOnlineUsers() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("chat_app").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<User> users = database.getCollection("users", User.class);
            return users.distinct("username", Filters.eq("isOnline", true), String.class).into(new ArrayList<>());
        }
    }

    @Override
    public void updateOnlineStatus(String username, boolean isOnline) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("chat_app").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<User> users = database.getCollection("users", User.class);
            User user = users.find(Filters.eq("username", username)).first();
            if (user != null) {
                user.setOnline(isOnline);
                users.replaceOne(Filters.eq("username", username), user);
            }
        }
    }

    @Override
    public void updateHostnameAndPort(String username, String hostname, int port) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("chat_app").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<User> users = database.getCollection("users", User.class);
            User user = users.find(Filters.eq("username", username)).first();
            if (user != null) {
                user.setHostname(hostname);
                user.setPort(port);
                users.replaceOne(Filters.eq("username", username), user);
            }
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