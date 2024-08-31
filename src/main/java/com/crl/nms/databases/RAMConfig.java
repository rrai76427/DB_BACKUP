package com.crl.nms.databases;


import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *
 * @author Sneha Prajapati
 */

@Entity
@Table(name = "RAMCONFIG")
@XmlRootElement
public class RAMConfig {

    @Id
    @Basic(optional = false)
    @Column(name = "NEKEY")
    private String nekey;

    @Column(name = "THRESHOLD")
    private int threshold;

    @Column(name = "CURRENTVALUE")
    private int currentValue;

    @Column(name = "UPDATINGTIME")
    private Date updatingTime;

    public RAMConfig() {
    }

    public String getNekey() {
        return nekey;
    }

    public void setNekey(String nekey) {
        this.nekey = nekey;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public Date getUpdatingTime() {
        return updatingTime;
    }

    public void setUpdatingTime(Date updatingTime) {
        this.updatingTime = updatingTime;
    }



}
