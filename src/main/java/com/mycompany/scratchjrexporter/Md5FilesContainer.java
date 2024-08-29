/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scratchjrexporter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dr
 */
public class Md5FilesContainer {

    private List<Md5File> md5Files;

    public Md5FilesContainer() {
        this.md5Files = new ArrayList<>();
    }

    public List<Md5File> getMd5Files() {
        return md5Files;
    }
    
    public void addMd5File(Md5File md5) {
        md5Files.add(md5);
    }

}
