/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crl.nms.databases;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author satwik
 */
@Entity
@Table(name = "PATCH_VERSION_STATUS")
@XmlRootElement
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchVersionStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PatchVersionStatusPK patchVersionStatusPK;

    @JoinColumn(name = "NODE_ID", referencedColumnName = "NODE_KEY") //, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NodeDefinition nodeId;

    @Column(name = "PATCH_NAME")
    private String patchName;
    @Column(name = "VERSION_NO")
    private Long versionNo;
    @Column(name = "RELEASE_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDateTime;
    @Column(name = "BUILD_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date buildDateTime;
    @Column(name = "CHECKSUM")
    private BigInteger checksum;
    @Column(name = "PATCH_SIZE")
    private Long patchSize;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "UPDATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "UPLOAD_START_DATE")
    private Date uploadStartDate;

    @Column(name = "UPLOAD_END_DATE")
    private Date uploadEndDate;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patchVersionStatusPK != null ? patchVersionStatusPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatchVersionStatus)) {
            return false;
        }
        PatchVersionStatus other = (PatchVersionStatus) object;
        if ((this.patchVersionStatusPK == null && other.patchVersionStatusPK != null) || (this.patchVersionStatusPK != null && !this.patchVersionStatusPK.equals(other.patchVersionStatusPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bos.PatchVersionStatus[ patchVersionStatusPK=" + patchVersionStatusPK + " ]";
    }

}
