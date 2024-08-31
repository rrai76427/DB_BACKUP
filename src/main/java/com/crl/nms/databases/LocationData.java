package com.crl.nms.databases;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
@Table(name = "NMS_LOCATION_DATA")
@Entity
@IdClass(LocationDataPK.class)
public class LocationData {

    @Id
    @Column(name = "locationCode")
    String locationCode;

    @Column(name = "PARENT_LOCATION_CODE")
    String parentLocationCode;

    @Id
    @Column(name = "lineId")
    int lineId;

    @Column(name = "sNo")
    private Integer sNo;

    @Column(name = "locationType")
    private String locationType;

    @Column(name = "locationName")
    private String locationName;

    @Column(name = "mnemonics")
    private String mnemonics;

    @Column(name = "act")
    private String act;

    @Column(name = "position")
    private String position;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "plotNo")
    private String plotNo;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pin")
    private String pin;

    @Column(name = "country")
    private String country;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "geoFence")
    private JsonNode geoFence;

    @Column(name = "validFrom")
    private Date  validFrom;

    @Column(name = "validUpto")
    private Date  validUpto;

    @Column(name = "validity")
    private String validity;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "GEOMAP_TYPE")
    private Short geomapType;
}
