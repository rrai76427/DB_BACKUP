package com.crl.nms.databases;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
@Table(name = "NMS_WIDGET_CONFIG")
public class WidgetConfig {

    @Id
    @Column(name = "WIDGET_CONFIG_ID")
    private Integer widgetConfigId;

    @Column(name = "WIDGET_NAME")
    private String widgetName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "OPTION")
    private JsonNode option;

    @Column(name = "MULTIPLE_NETYPE_FLAG")
    private Integer multipleNeTypeFlag;

}
