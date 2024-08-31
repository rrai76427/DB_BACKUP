package com.crl.nms.databases;

import jakarta.persistence.*;
import lombok.*;

/**
 *
 * @author Sneha Prajapati
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
@Builder
public class NmsNeDeviceInterfaceDetailPK {

//    @Basic(optional = false)
//    @JoinColumn(name = "NEKEY", referencedColumnName = "NEKEY")
//    @ManyToOne(optional = false)
//    private NmsNeDetail nekey;

    @Basic(optional = false)
    @Column(name = "NEKEY")
    private String nekey;

    @Basic(optional = false)
    @Column(name = "INTERFACE_ID")
    private String interfaceId;
}
