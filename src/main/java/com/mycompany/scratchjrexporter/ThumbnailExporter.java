/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Dr
 */
public class ThumbnailExporter {

    private List<Md5File> md5Thumbnails;

    public ThumbnailExporter(SqlConnector connector, SqlProjectData project) throws Exception {
        md5Thumbnails = new ArrayList<>();

        JSONParser parser = new JSONParser();
        JSONObject thumbnail = (JSONObject) parser.parse(project.getThunbnail());
        String md5 = (String) thumbnail.get("md5");

        Connection conn = connector.connect();
        String sql = "SELECT f.* FROM PROJECTFILES f WHERE f.MD5 = "
                + "'" + md5 + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set  
            while (rs.next()) {
                Md5File bg = new Md5File(rs);
                md5Thumbnails.add(bg);
            }
        } catch (SQLException e) {
            throw new Exception("Error while parsing thumbnails. Error: " + e, e);
        }
    }

    public List<Md5File> getMd5Thumbnails() {
        return md5Thumbnails;
    }
}
