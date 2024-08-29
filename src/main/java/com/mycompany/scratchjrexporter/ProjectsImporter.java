/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author valeriu
 */
public class ProjectsImporter extends SqlImporter {

    public ProjectsImporter(SqlConnector connector) {
        super(connector);
    }

    public void writeToProjects(InputStream inputStream, String fileName) throws Exception {
        Connection conn = connector.connect();

        JSONObject json = this.parseJson(inputStream);

        String sql = "INSERT INTO PROJECTS (NAME, JSON, THUMBNAIL, DELETED, VERSION, ISGIFT)"
                + " VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set parameters for the insert statement
            pstmt.setString(1, (String) json.get("name"));
            pstmt.setString(2, ((JSONObject) json.get("json")).toJSONString());
            pstmt.setString(3, ((JSONObject) json.get("thumbnail")).toJSONString());
            pstmt.setString(4, "NO");
            pstmt.setString(5, "iOSv01");
            pstmt.setString(6, "0");

            // Execute the insert
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("rowsAffected: " + rowsAffected + "; writeToProjectFiles: " + fileName);

        } catch (SQLException e) {
            throw new Exception("Error while importing project. Error: " + e, e);
        }
    }

    private JSONObject parseJson(InputStream inputStream) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject projectJson;
        try {
            String jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            projectJson = (JSONObject) parser.parse(jsonString);
        } catch (IOException | ParseException e) {
            throw new Exception("Error while reading data.json Error: " + e, e);
        }
        return projectJson;
    }

}
