package com.example;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    
    private static Database instance = new Database();
    private Database(){}
    public static Database getInstance(){
        return instance;
    }
    public String getUrl(){
        return URL;
    }
    public String getUser(){
        return USER;
    }
    public String getPass(){
        return PASS;
    }
}
