package com.crl.nms.databases;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author meenuchhindra
 */
@Entity
@Table(name = "NE_CONNECTIVITY")
@XmlRootElement
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NeConnectivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected NeConnectivityPK neConnectivityPK;

    @Column(name = "SRC_NODEID")
    private String srcNodeid;

    @Column(name = "SRC_NEIP")
    private String srcNeip;

    @Column(name = "SRC_PORT_STATUS")
    private Short srcPortStatus;

    @Column(name = "DEST_NODEID")
    private String destNodeid;

    @Column(name = "DEST_NEIP")
    private String destNeip;

    @Column(name = "DEST_PORT_STATUS")
    private Short destPortStatus;

    @Column(name = "LINKID")
    private Long linkid;

    @Column(name = "LINKTYPE")
    private String linktype;

    @Column(name = "LINKSTATUS")
    private Short linkstatus;

    @Column(name = "PSRC_IP")
    private String psrcIp;

    @Column(name = "PDEST_IP")
    private String pdestIp;

    @Column(name = "UPDATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

}
