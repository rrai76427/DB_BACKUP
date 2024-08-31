package com.crl.nms.databases;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.*;

import java.util.Date;

/**
 *
 * @author Sneha Prajapati
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class WidgetMetaDataPK {

    @Column(name = "NETYPE")
    private Integer neType;

    @Column(name = "WIDGET_CONFIG_ID")
    private Integer widgetConfigId;

    @Column(name = "COMPUTATIONAL_SUBJECT_ID")
    private Integer computationalSubjectId;
}
