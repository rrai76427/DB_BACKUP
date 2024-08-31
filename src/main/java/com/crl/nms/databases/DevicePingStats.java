package com.crl.nms.databases;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
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
@Entity
@Table(name = "NMS_NE_DEVICE_PING_STATS")
public class DevicePingStats {

    @Id
    @Column(name = "NEKEY")
    private String neKey;

    @Column(name = "PACKET_LOSS_PERCENT")
    private BigDecimal packetLossPercent;

    @Column(name = "RESPONSE_TIME")
    private BigDecimal responseTime;

    @Column(name = "UPDATED_ON")
    private Date updatedOn;

}
