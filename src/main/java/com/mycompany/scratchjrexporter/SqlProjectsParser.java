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

/**
 *
 * @author Dr
 */
public class SqlProjectsParser {

    private List<SqlProjectData> projects;

    public SqlProjectsParser(SqlConnector connector) throws Exception {
        projects = new ArrayList<>();

        Connection conn = connector.connect();
        String sql = "SELECT * FROM PROJECTS WHERE JSON IS NOT NULL";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set  
            while (rs.next()) {
                SqlProjectData project = new SqlProjectData(rs);
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new Exception("Error while parsing project. Error: " + e, e);
        }
    }

    public List<SqlProjectData> getProjects() {
        return projects;
    }

}
