package com.example;

import com.example.util.ConfigUtil;
import com.example.util.MongoDBUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Properties;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;

public class Main {
    public static void main(String[] args) {
        
        // Load configuration from properties file
        Properties config = ConfigUtil.loadConfig();
        String uri;
        try {
            uri = ConfigUtil.getRequiredProperty(config, "mongodb.connection.string");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
            return;
        }
        try (MongoClient mongoClient = MongoDBUtil.createClient(uri)) {
            MongoDatabase database = MongoDBUtil.getDatabase(mongoClient, "sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");
            Document doc = collection.find(eq("title", "Back to the Future")).first();
            
            if (doc != null) {
                System.out.println("\n=== 查询结果 ===");
                System.out.println("标题: " + doc.getString("title"));
                System.out.println("年份: " + doc.getInteger("year"));
                System.out.println("\n完整文档:");
                System.out.println(doc.toJson());
            } else {
                System.out.println("未找到匹配的文档");
            }
        } catch (Exception e) {
            System.err.println("错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
