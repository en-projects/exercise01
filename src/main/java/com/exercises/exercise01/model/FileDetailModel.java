package com.exercises.exercise01.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FileDetailModel implements Serializable {

    private int id;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("upload_date")
    private String uploadDate;

    public FileDetailModel() {
    }

    private FileDetailModel(int id, String fileName, String uploadDate) {
        this.id = id;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
    }

    public static FileDetailModel of(int id, String fileName, String uploadDate) {
        return new FileDetailModel(id, fileName, uploadDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "FileDetailModel{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                '}';
    }
}
