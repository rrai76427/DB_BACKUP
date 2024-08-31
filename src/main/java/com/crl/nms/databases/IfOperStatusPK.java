package com.crl.nms.databases;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@Builder
@Setter
@Getter
@Embeddable
public class IfOperStatusPK {

    @Column(name = "nekey")
    private String neKey;

    @Column(name = "trap_oid")
    private String trapOid;

    @Column(name = "updated_on")
    private Date updatedOn;

    public IfOperStatusPK() {
    }
}
