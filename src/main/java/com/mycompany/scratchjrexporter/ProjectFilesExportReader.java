/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import static com.sun.tools.javac.tree.TreeInfo.name;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

/**
 *
 * @author Dr
 */
public class ProjectFilesExportReader {

    private final Connection connection;

    public ProjectFilesExportReader(SqlConnector connector) throws Exception {
        this.connection = connector.connect();
    }

    public byte[] read(Md5File md5File) throws Exception {
        String sql = "SELECT * FROM PROJECTFILES f WHERE f.MD5 = "
                + "'" + md5File.getMd5() + "'";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String base64 = rs.getString("CONTENTS");
                byte[] fileData = Base64.getDecoder().decode(base64.getBytes("UTF-8"));
                return fileData;
            }
        } catch (SQLException e) {
            throw new Exception("Error while reading project files. Error: " + e, e);
        }

        return new byte[0];
    }

}
