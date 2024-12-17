package com.crl.nms.messages;

public class ImportingMessage {
    String databaseName;
    String fileName;


    public ImportingMessage() {
    }

    public ImportingMessage(String databaseName, String fileName) {
        this.databaseName = databaseName;
        this.fileName = fileName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
