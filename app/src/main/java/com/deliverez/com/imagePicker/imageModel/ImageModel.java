package com.deliverez.com.imagePicker.imageModel;

import java.io.Serializable;

public class ImageModel implements Serializable {
    private int id;
    private String name;
    private String pathFile;
    private String pathFolder;
    private String stringType;

    public ImageModel(String name, String pathFile, String pathFolder, String stringType) {
        this.name = name;
        this.pathFile = pathFile;
        this.pathFolder = pathFolder;
        this.stringType = stringType;
    }

    public String getPathFile() {
        return this.pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathFolder() {
        return this.pathFolder;
    }

    public void setPathFolder(String pathFolder) {
        this.pathFolder = pathFolder;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStringType() {
        return stringType;
    }

    public void setStringType(String stringType) {
        this.stringType = stringType;
    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pathFile='" + pathFile + '\'' +
                ", pathFolder='" + pathFolder + '\'' +
                '}';
    }
}
