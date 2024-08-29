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
public class CharactersExporter extends Md5FilesContainer {

    public CharactersExporter(SqlConnector connector) throws Exception {

        Connection conn = connector.connect();
        String sql = "SELECT s.*\n"
                + "FROM USERSHAPES s\n"
                + "JOIN PROJECTFILES f on f.MD5 = s.MD5";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set  
            while (rs.next()) {
                Md5File bg = new Md5File(rs);
                this.addMd5File(bg);
            }
        } catch (SQLException e) {
            throw new Exception("Error while parsing backgrounds. Error: " + e, e);
        }
    }

}
