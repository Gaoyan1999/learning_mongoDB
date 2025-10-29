package com.example.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * MongoDB 工具类，用于创建和管理 MongoDB 连接
 */
public class MongoDBUtil {
    
    /**
     * 创建 MongoDB 客户端连接
     * @param connectionString MongoDB 连接字符串
     * @return MongoClient 对象
     * @throws IllegalArgumentException 如果连接字符串为空或无效
     */
    public static MongoClient createClient(String connectionString) {
        if (connectionString == null || connectionString.trim().isEmpty()) {
            throw new IllegalArgumentException("MongoDB connection string cannot be null or empty");
        }
        return MongoClients.create(connectionString);
    }
    
    /**
     * 获取指定的数据库
     * @param client MongoClient 对象
     * @param databaseName 数据库名称
     * @return MongoDatabase 对象
     */
    public static MongoDatabase getDatabase(MongoClient client, String databaseName) {
        if (client == null) {
            throw new IllegalArgumentException("MongoClient cannot be null");
        }
        if (databaseName == null || databaseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Database name cannot be null or empty");
        }
        return client.getDatabase(databaseName);
    }
}

