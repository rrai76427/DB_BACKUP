package com.crl.nms.pojo;

import java.util.List;

public class ListOfFileInDataBaseBackupDirectory {

    List<DataBaseBackupDirectoryContent> dataBaseBackupDirectoryContentList;

    public ListOfFileInDataBaseBackupDirectory() {
    }

    public ListOfFileInDataBaseBackupDirectory(List<DataBaseBackupDirectoryContent> dataBaseBackupDirectoryContentList) {
        this.dataBaseBackupDirectoryContentList = dataBaseBackupDirectoryContentList;
    }

    public List<DataBaseBackupDirectoryContent> getDataBaseBackupDirectoryContentList() {
        return dataBaseBackupDirectoryContentList;
    }

    public void setDataBaseBackupDirectoryContentList(List<DataBaseBackupDirectoryContent> dataBaseBackupDirectoryContentList) {
        this.dataBaseBackupDirectoryContentList = dataBaseBackupDirectoryContentList;
    }
}
