/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

/**
 *
 * @author Dr
 */
public class ZipFileCreator {

    private FileOutputStream fos;
    private ZipArchiveOutputStream zos;
    private final SqlConnector connector;

    private static final String FILE_EXTENSION = ".sjr";

    public ZipFileCreator(SqlConnector connector) throws Exception {
        this.connector = connector;
    }

    public void createExportFile(String filePath, List<SqlProjectData> projects) throws Exception {
        for (SqlProjectData project : projects) {
            try {
                String fullPath = filePath;
                if (fullPath.toLowerCase().endsWith(".sjr")) {
                    fullPath = fullPath.substring(0, fullPath.length() - 4);
                }
                fullPath += "-" + project.getId() + FILE_EXTENSION;
                fos = new FileOutputStream(fullPath);
                zos = new ZipArchiveOutputStream(fos);

                this.writeBackgrounds();
                this.writeCharacters();

                this.writeJsonDataFile(project);
                this.writeThumbnail(project);
            } catch (Exception e) {
                this.close();
                throw e;
            } finally {
                this.close();
            }
        }
    }

    private void close() throws Exception {
        try {
            zos.finish();
            zos.close();
            fos.close();
        } catch (IOException e) {
            throw new Exception("Error while creating export file. Error: " + e, e);
        }
    }

    private void writeThumbnail(SqlProjectData projectData) throws Exception {
        ThumbnailExporter te = new ThumbnailExporter(connector, projectData);
        List<Md5File> thumbnails = te.getMd5Thumbnails();
        ProjectFilesExportReader pfe = new ProjectFilesExportReader(connector);

        for (Md5File c : thumbnails) {
            byte[] fileData = pfe.read(c);
            try {
                String fileName = c.getMd5();
                addFileToZip("thumbnails" + File.separator + fileName, fileData);
            } catch (IOException e) {
                throw new Exception("Error while creating file. Error: " + e, e);
            }
        }
    }

    private void writeJsonDataFile(SqlProjectData projectData) throws Exception {
        DataJsonExporter dje = new DataJsonExporter(projectData);

        String jsonData = dje.getJson();
        String fileName = "data.json";
        addFileToZip(fileName, jsonData.getBytes());
    }

    private void writeCharacters() throws Exception {
        CharactersExporter ce = new CharactersExporter(connector);
        List<Md5File> characers = ce.getMd5Files();
        ProjectFilesExportReader pfe = new ProjectFilesExportReader(connector);

        for (Md5File c : characers) {
            byte[] fileData = pfe.read(c);
            try {
                String fileName = c.getMd5();
                addFileToZip("characters" + File.separator + fileName, fileData);
            } catch (IOException e) {
                throw new Exception("Error while creating file. Error: " + e, e);
            }
        }
    }

    private void writeBackgrounds() throws Exception {
        BackgroundsExporter bge = new BackgroundsExporter(connector);
        List<Md5File> backgrounds = bge.getMd5Files();
        ProjectFilesExportReader pfe = new ProjectFilesExportReader(connector);

        for (Md5File bg : backgrounds) {
            byte[] fileData = pfe.read(bg);
            try {
                String fileName = bg.getMd5();
                addFileToZip("backgrounds" + File.separator + fileName, fileData);
            } catch (IOException e) {
                throw new Exception("Error while creating file. Error: " + e, e);
            }
        }
    }

    private void addFileToZip(String fileName, byte[] data) throws IOException {
        // Create a new ZipEntry for the file
        ZipArchiveEntry fileEntry = new ZipArchiveEntry("project" + File.separator + fileName);

        zos.putArchiveEntry(fileEntry);
        zos.write(data);
        zos.closeArchiveEntry();
    }
}
