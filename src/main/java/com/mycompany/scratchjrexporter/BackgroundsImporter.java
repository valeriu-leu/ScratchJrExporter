/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

/**
 *
 * @author valeriu
 */
public class BackgroundsImporter extends SqlImporter {

    public BackgroundsImporter(SqlConnector connector) {
        super(connector);
    }

    public void writeToBackgrounds(InputStream inputStream, String fileName) throws Exception {
        Connection conn = connector.connect();

        // Check if the MD5 already exists
        String checkSql = "SELECT COUNT(*) FROM USERBKGS WHERE MD5 = ?";
        boolean exists = false;
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);

        checkStmt.setString(1, fileName);
        try (ResultSet rs = checkStmt.executeQuery()) {
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        }

        if (exists) {
            return;
        }

        super.writeToProjectFiles(inputStream, fileName);

        String sql = "INSERT INTO USERBKGS (MD5, WIDTH, HEIGHT, EXT, VERSION)"
                + " VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set parameters for the insert statement
            pstmt.setString(1, fileName);
            pstmt.setString(2, "480");
            pstmt.setString(3, "360");
            pstmt.setString(4, getExtension(fileName));
            pstmt.setString(5, "iOSv01");

            // Execute the insert
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("rowsAffected: " + rowsAffected + "; writeToProjectFiles: " + fileName);

        } catch (SQLException e) {
            throw new Exception("Error while parsing project. Error: " + e, e);
        }
    }

}
