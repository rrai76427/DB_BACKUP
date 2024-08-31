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
@Table(name = "NMS_LINK_ENUM")
@Entity
@Transactional
public class LinkStatusEnum {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COLOR")
    private String color;
}
