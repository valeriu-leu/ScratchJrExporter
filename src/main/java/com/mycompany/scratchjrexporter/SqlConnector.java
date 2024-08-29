/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dr
 */
public class SqlConnector {
    
    private final String url;
    
    public SqlConnector(String url) {
        this.url = url;
    }
    
    public Connection connect() throws Exception {
        // SQLite connection string  
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new Exception("Error while connecting to the database. Error: " + e, e);
        }
        return conn;
    }
    
}
