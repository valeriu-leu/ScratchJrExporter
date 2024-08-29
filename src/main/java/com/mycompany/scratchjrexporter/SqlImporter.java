/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

/**
 *
 * @author valeriu
 */
public abstract class SqlImporter {

    protected final SqlConnector connector;

    public SqlImporter(SqlConnector connector) {
        this.connector = connector;
    }

    public void writeToProjectFiles(InputStream inputStream, String fileName) throws Exception {
        byte[] content = inputStream.readAllBytes();

        // Convert byte array to Base64 encoded string
        String base64Encoded = Base64.getEncoder().encodeToString(content);

        Connection conn = connector.connect();
        String sql = "INSERT INTO PROJECTFILES (MD5, CONTENTS) VALUES (?, ?) ON CONFLICT(MD5) DO NOTHING";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set parameters for the insert statement
            pstmt.setString(1, fileName);
            pstmt.setString(2, base64Encoded);

            // Execute the insert
            int rowsAffected = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error while parsing project. Error: " + e, e);
        }
    }

    public static String getExtension(String fileName) {
        // Find the last occurrence of the dot
        int lastDotIndex = fileName.lastIndexOf('.');

        // Extract the substring after the last dot
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        } else {
            return "svg";
        }
    }

}
