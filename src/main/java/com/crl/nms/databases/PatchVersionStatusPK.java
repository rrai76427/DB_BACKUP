/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crl.nms.databases;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author satwik
 */
@Embeddable
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PatchVersionStatusPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PATCH_MODULE_ID")
    private String patchModuleId;
    @JoinColumn(name = "NEKEY", referencedColumnName = "NEKEY") //, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NmsNeDetail nekey;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patchModuleId != null ? patchModuleId.hashCode() : 0);
        hash += nekey.getNodeId();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatchVersionStatusPK)) {
            return false;
        }
        PatchVersionStatusPK other = (PatchVersionStatusPK) object;
        if ((this.patchModuleId == null && other.patchModuleId != null) || (this.patchModuleId != null && !this.patchModuleId.equals(other.patchModuleId))) {
            return false;
        }
        if (this.nekey != other.nekey) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bos.PatchVersionStatusPK[ patchModuleId=" + patchModuleId + ", nekey=" + nekey + " ]";
    }

}
