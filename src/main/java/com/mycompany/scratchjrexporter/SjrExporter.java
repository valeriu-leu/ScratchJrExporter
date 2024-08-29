/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Enumeration;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

/**
 *
 * @author valeriu
 */
public class SjrExporter {

    private final String inputPath;
    private final SqlConnector connector;

    public SjrExporter(String inputPath, SqlConnector connector) {
        this.inputPath = inputPath;
        this.connector = connector;
    }

    public void importToDatabase() throws IOException, Exception {
        try (ZipFile zipFile = new ZipFile(new File(inputPath))) {
            // Get entries in the ZIP file
            Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();

            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();

                // Print entry name (path within the ZIP)
                String fileName = Paths.get(entry.getName()).getFileName().toString();

                // If the entry is a directory, skip it
                if (entry.isDirectory()) {
                    continue;
                }

                // Write backgrounds
                if (entry.getName().toLowerCase().startsWith("project/backgrounds")) {
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        BackgroundsImporter bi = new BackgroundsImporter(connector);
                        bi.writeToBackgrounds(inputStream, fileName);
                    }
                }

                // Write user shapes
                if (entry.getName().toLowerCase().startsWith("project/characters")) {
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        UsershapesImporter us = new UsershapesImporter(connector);
                        us.writeToShapes(inputStream, fileName);
                    }
                }
                
                // Write user shapes
                if (entry.getName().toLowerCase().startsWith("project/thumbnails")) {
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        ProjectsImporter pi = new ProjectsImporter(connector);
                        pi.writeToProjectFiles(inputStream, fileName);
                    }
                }
                
                // Write user shapes
                if (entry.getName().toLowerCase().startsWith("project/data.json")) {
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        ProjectsImporter pi = new ProjectsImporter(connector);
                        pi.writeToProjects(inputStream, fileName);
                    }
                }
                
            }
        } catch (IOException e) {
            throw e;
        }
    }

}
