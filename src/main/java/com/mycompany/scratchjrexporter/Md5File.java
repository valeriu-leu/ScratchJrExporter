/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Dr
 */
public class Md5File {

    private final String md5;

    public Md5File(ResultSet rs) throws SQLException {
        md5 = rs.getString("MD5");
    }

    public String getMd5() {
        return md5;
    }

}
