package com.crl.nms.databases;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "NMS_GEOMAP_TYPE_LIST")
@Entity
@Transactional
public class GeoMapTypeList {

    @Id
    @Column(name = "ID")
    private Short id;

    @Column(name = "NAME")
    private String name;
}
