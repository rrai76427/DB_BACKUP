package  com.crl.nms.databases;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.*;

/**
 *
 * @author ashwinimehta
 */
@Entity
@Table(name = "NMS_ALARM_CLASSIFICATION")
@XmlRootElement
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NmsAlarmClassification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ALARM_ID")
    private Short alarmId;

    @Column(name = "SEVERITY")
    private Short severity;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "INFO_EVENT_FLG")
    private Short infoEventFlg;

    @Column(name = "NOTIFICATION")
    private Short notification;

    @Column(name = "AUDIO")
    private Short audio;

    @Column(name = "CORRECTIVE_ACTION")
    private String correctiveAction;

    @OneToMany(mappedBy = "alarmId")
    private List<NmsAlarms> nmsAlarmsList;

}
