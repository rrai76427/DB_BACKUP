package com.crl.nms.pojo;

import java.io.Serializable;

/**
 *
 * @author ashwinimehta
 */

public class NmsNeHdd implements Serializable {

    private static final long serialVersionUID = 1L;

    private int hddno;

    private String filesysname;

    private String totalsize;

    private String usagePercent;

    private String diskutil;

    private String mounton;

    private String hddkey;

    private String disk_available;


    private NmsNeDetail nekey;


    private NodeDefinition nodeKey;

    public NmsNeHdd() {
    }

    public String getDisk_available() {
        return disk_available;
    }

    public void setDisk_available(String disk_available) {
        this.disk_available = disk_available;
    }

    public NmsNeHdd(String hddkey) {
        this.hddkey = hddkey;
    }

    public NmsNeHdd(String hddkey, int hddno, String usagePercent) {
        this.hddkey = hddkey;
        this.hddno = hddno;
        this.usagePercent = usagePercent;
    }

    public int getHddno() {
        return hddno;
    }

    public void setHddno(int hddno) {
        this.hddno = hddno;
    }

    public String getFilesysname() {
        return filesysname;
    }

    public void setFilesysname(String filesysname) {
        this.filesysname = filesysname;
    }

    public String getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(String totalsize) {
        this.totalsize = totalsize;
    }

    public String getUsagePercent() {
        return usagePercent;
    }

    public void setUsagePercent(String usagePercent) {
        this.usagePercent = usagePercent;
    }

    public String getDiskutil() {
        return diskutil;
    }

    public void setDiskutil(String diskutil) {
        this.diskutil = diskutil;
    }

    public String getMounton() {
        return mounton;
    }

    public void setMounton(String mounton) {
        this.mounton = mounton;
    }

    public String getHddkey() {
        return hddkey;
    }

    public void setHddkey(String hddkey) {
        this.hddkey = hddkey;
    }

    public NmsNeDetail getNekey() {
        return nekey;
    }

    public void setNekey(NmsNeDetail nekey) {
        this.nekey = nekey;
    }

    public NodeDefinition getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(NodeDefinition nodeKey) {
        this.nodeKey = nodeKey;
    }


}
