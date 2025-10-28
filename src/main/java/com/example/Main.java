package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Hello World application
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        logger.info("Starting Hello World application");
        System.out.println("Hello World!");
        System.out.println("Welcome to your learning environment!");
        logger.info("Hello World application completed successfully");
    }
}
