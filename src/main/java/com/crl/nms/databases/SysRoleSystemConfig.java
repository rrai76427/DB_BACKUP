package com.crl.nms.databases;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 *
 * @author Satwik Anmol
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "NMS_SYSROLE_SYSTEM_CONFIGURATION")
@Entity
@Transactional
public class SysRoleSystemConfig {

    @Id
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "DETAIL")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode detail;
}
