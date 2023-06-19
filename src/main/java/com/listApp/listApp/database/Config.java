package com.listApp.listApp.database;

public class Config {

    public String dbURL;
    public String dbUsername;
    public String dbPassword;

    public Config() {
        this.dbURL = "jdbc:postgresql://localhost:5432/appDB10";
        this.dbUsername = "postgres";
        this.dbPassword = "password";
    }
    public String getDbURL() {
        return dbURL;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
