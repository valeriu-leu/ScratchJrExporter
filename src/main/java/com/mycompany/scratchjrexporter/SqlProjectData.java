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
public class SqlProjectData {

    private final int id;
    private final String ctime;
    private final String mtime;
    private final String name;
    private final String json;
    private final String thunbnail;
    private final String deleted;
    private final String version;
    private final int isGift;

    public SqlProjectData(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        ctime = rs.getString("CTIME");
        mtime = rs.getString("MTIME");
        name = rs.getString("NAME");
        json = rs.getString("JSON");
        thunbnail = rs.getString("THUMBNAIL");
        deleted = rs.getString("DELETED");
        version = rs.getString("VERSION");
        isGift = rs.getInt("ISGIFT");

    }

    public String getDeleted() {
        return deleted;
    }

    public String getVersion() {
        return version;
    }

    public int getIsGift() {
        return isGift;
    }

    public int getId() {
        return id;
    }

    public String getCtime() {
        return ctime;
    }

    public String getMtime() {
        return mtime;
    }

    public String getName() {
        return name;
    }

    public String getJson() {
        return json;
    }

    public String getThunbnail() {
        return thunbnail;
    }

    @Override
    public String toString() {
        return id + " \t " + name + " \t Created on:" + ctime + "\t Deleted:" + deleted;
    }

}
