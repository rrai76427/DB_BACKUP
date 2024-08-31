package com.crl.nms.databases;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
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
@Table(name = "NMS_ENUM_PRIVACY_PROTOCOL")
@Entity
@Transactional
public class PrivacyProtocolEnum {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PROTOCOL")
    private String protocol;
}
