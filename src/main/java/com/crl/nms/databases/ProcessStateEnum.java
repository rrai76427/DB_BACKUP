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
@Table(name = "NMS_PROCESS_STATE_ENUM")
@Entity
@Transactional
public class ProcessStateEnum {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BACKGROUND_COLOR")
    private String backgroundColor;

    @Column(name = "BACKGROUND_COLOR_DARK")
    private String backgroundColorDark;

    @Column(name = "COLOR")
    private String color;
}
