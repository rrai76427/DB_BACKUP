package com.crl.nms.databases;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Date;

/**
 *
 * @author Sneha Prajapati
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class DevicePacketLossPK {

    @Column(name = "NEKEY")
    private String neKey;

    @Column(name = "UPDATED_ON")
    private Date updatedOn;


}
