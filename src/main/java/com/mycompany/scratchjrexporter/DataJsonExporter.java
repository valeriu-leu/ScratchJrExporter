/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import java.util.Objects;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Dr
 */
public class DataJsonExporter {

    private final SqlProjectData project;

    public DataJsonExporter(SqlProjectData project) {
        this.project = project;
    }

    public String getJson() throws Exception {
        JSONParser parser = new JSONParser();

        JSONObject json = new JSONObject();
        try {
            JSONObject projectJson = (JSONObject) parser.parse(project.getJson());

            json.put("id", Objects.toString(project.getId()));
            json.put("ctime", project.getCtime());
            json.put("mtime", project.getMtime());
            json.put("name", project.getName());
            json.put("json", projectJson);

            JSONObject thumbnail = (JSONObject) parser.parse(project.getThunbnail());
            json.put("thumbnail", thumbnail);
            json.put("deleted", project.getDeleted());
            json.put("version", project.getVersion());
            json.put("isgift", Objects.toString(project.getIsGift()));
            json.put("assetLibraryVersion", 0);

        } catch (ParseException e) {
            throw new Exception("Error while parsing project json data. Error: " + e, e);
        }

        return json.toJSONString();
    }
}
