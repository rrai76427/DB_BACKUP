package com.crl.nms.pojo;

public class DataBaseBackupDirectoryContent {
    String fileName;
    String dateOfFileCreation;
    String fileSize;

    public DataBaseBackupDirectoryContent(String fileName, String dateOfFileCreation, String fileSize) {
        this.fileName = fileName;
        this.dateOfFileCreation = dateOfFileCreation;
        this.fileSize = fileSize;
    }

    public DataBaseBackupDirectoryContent() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDateOfFileCreation() {
        return dateOfFileCreation;
    }

    public void setDateOfFileCreation(String dateOfFileCreation) {
        dateOfFileCreation = dateOfFileCreation;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
