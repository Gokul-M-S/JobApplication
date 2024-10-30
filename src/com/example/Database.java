package com.example;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    public Database(){}

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
