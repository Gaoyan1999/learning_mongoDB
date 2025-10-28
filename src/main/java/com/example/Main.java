package com.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        // Load configuration from properties file
        Properties config = loadConfig();
        String connectionString = config.getProperty("mongodb.connection.string");
        String databaseName = config.getProperty("mongodb.database.name", "admin");

        if (connectionString == null || connectionString.trim().isEmpty()) {
            System.err.println("Error: MongoDB connection string not found in config.properties");
            System.err.println("Please copy example.properties to config.properties and fill in your MongoDB credentials");
            return;
        }

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase(databaseName);
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    private static Properties loadConfig() {
        Properties config = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Warning: config.properties not found. Using example.properties as template.");
                System.err.println("Please copy example.properties to config.properties and fill in your MongoDB credentials");
                // Try to load example.properties as fallback
                try (InputStream exampleInput = Main.class.getClassLoader().getResourceAsStream("example.properties")) {
                    if (exampleInput != null) {
                        config.load(exampleInput);
                    }
                }
            } else {
                config.load(input);
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
        return config;
    }
}
