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
@Table(name = "NMS_DEVICE_RESPONSE_TIME")
public class DeviceResponseTime {

    @EmbeddedId
    protected DeviceResponseTimePK deviceResponseTimePK;

    @Column(name = "RESPONSE_TIME")
    private BigDecimal responseTime;
}
