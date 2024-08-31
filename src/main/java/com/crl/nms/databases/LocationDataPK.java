package com.crl.nms.databases;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//@Embeddable
@Data
@Getter
@Setter
public class LocationDataPK implements Serializable{
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "locationCode")
    String locationCode;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "lineId")
    int lineId;
}
