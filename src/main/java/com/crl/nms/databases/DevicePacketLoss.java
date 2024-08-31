package com.crl.nms.databases;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

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
@Table(name = "NMS_DEVICE_PACKET_LOSS")
public class DevicePacketLoss {

    @EmbeddedId
    protected DevicePacketLossPK devicePacketLossPK;

    @Column(name = "PACKET_LOSS_PERCENT")
    private BigDecimal packetLossPercent;
}
