package com.crl.nms.databases;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 *
 * @author Sneha Prajapati
 */

@Entity
@Table(name = "NMS_GROUP_TOPOLOGY")

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupTopology {

    @Id
    @Basic(optional = false)
    @Column(name = "GROUP_ID")
    private Integer groupId;

    @Column(name = "GROUP_NAME")
    private String groupName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "DETAILS")
    private JsonNode details;
}