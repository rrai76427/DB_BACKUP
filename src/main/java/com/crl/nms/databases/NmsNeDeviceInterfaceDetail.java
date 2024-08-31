package com.crl.nms.databases;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.Date;

/**
 *
 * @author Sneha Prajapati
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "NMS_NE_DEVICE_INTERFACE_DETAIL")
@Entity
@Transactional
public class NmsNeDeviceInterfaceDetail {

    @EmbeddedId
    NmsNeDeviceInterfaceDetailPK nmsNeDeviceInterfaceDetailPK;

    @Column(name = "MAC")
    private String mac;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "TX")
    private String tx;

    @Column(name = "RX")
    private String rx;

    @Column(name = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @JoinColumn(name = "NEKEY", referencedColumnName = "NEKEY", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NmsNeDetail nmsNeDetail;
}
